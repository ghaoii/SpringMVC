package com.ghaoi.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody// 不返回页面
@RequestMapping("/calc")
public class Calc {

    @RequestMapping("/sum")
    public String sum(Integer num1, Integer num2) {
        return "<h1>计算结果: " + (num1 + num2) + "</h1><br><a href='javascript:history.go(-1);'>返回上一步</a>";
    }
}
