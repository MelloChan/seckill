package com.mello.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.mello.domain.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis DAO
 * Created by MelloChan on 2017/10/14.
 */
public class RedisDao {
    private JedisPool jedisPool;
    private final static Logger LOG = LoggerFactory.getLogger(RedisDao.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    /**
     * 获取seckill商品cache
     *
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(long seckillId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "seckill:" + seckillId;
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes != null) {
                Seckill seckill = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                return seckill;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 写入cache
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill){
        try(Jedis jedis=jedisPool.getResource()) {
            String key="seckill:"+seckill.getSeckillId();
            byte[] bytes=ProtostuffIOUtil.toByteArray(seckill,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //超时缓存
            int timeout=60*60;
            String result=jedis.setex(key.getBytes(),timeout,bytes);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
