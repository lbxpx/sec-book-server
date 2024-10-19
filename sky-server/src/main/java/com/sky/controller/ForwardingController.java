package com.sky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {

    @GetMapping(value = { "/", "/{path:[^.]*}", "/**/{path:[^.]*}" })
    public String redirect() {
        return "forward:/index.html";
    }
    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }
}