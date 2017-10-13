package com.mello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by MelloChan on 2017/10/11.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
