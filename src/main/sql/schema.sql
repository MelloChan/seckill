-- 创建DB
CREATE database seckill;
use seckill;

-- 创建库存表 (创建时不要在字段上加单引号)
  CREATE TABLE seckill(
seckill_id  BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR(120) NOT NULL COMMENT '商品名称',
number INT NOT NULL COMMENT '库存数量',
start_time TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY ide_start_time(start_time),
KEY ide_end_time(end_time),
KEY ide_create_time(create_time)
)ENGINE=InnoDB auto_increment=1000 DEFAULT charset=utf8 comment='秒杀库存表';

-- 初始化数据
INSERT INTO seckill(name,number,start_time,end_time)
VALUES ('1000元秒杀iPhone8',100,'2017-11-11 00:00:00','2017-11-12 00:00:00'),
('800元秒杀ipad',200,'2017-11-11 00:00:00','2017-11-12 00:00:00'),
('500元秒杀小米6',300,'2017-11-11 00:00:00','2017-11-12 00:00:00'),
('300元秒杀小米5',400,'2017-11-11 00:00:00','2017-11-12 00:00:00'),
('100元秒杀红米note',500,'2017-11-11 00:00:00','2017-11-12 00:00:00');
INSERT INTO seckill(name, number, start_time, end_time) VALUES
  ('demo02',100,'2017-10-04 10:00:00','2017-11-11 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关信息
CREATE TABLE success_seckill(
seckill_id BIGINT NOT NULL COMMENT '秒杀商品id',
user_phone BIGINT NOT NULL COMMENT '秒杀用户手机号',
state TINYINT NOT NULL DEFAULT -1 COMMENT '状态表示: -1:无效 0:成功 1:已付款',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id,user_phone),
KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT charset=utf8 comment='秒杀成功明细表';

SELECT * FROM seckill;
SELECT * FROM success_seckill;
DROP TABLE success_seckill;
DELETE FROM success_seckill WHERE seckill_id=1005;
UPDATE seckill SET seckill.seckill.number=100 WHERE seckill.seckill.seckill_id=1005