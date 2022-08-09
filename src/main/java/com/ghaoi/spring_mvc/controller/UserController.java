package com.ghaoi.spring_mvc.controller;

import com.ghaoi.spring_mvc.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/controller")
@ResponseBody// 当前方法(或类)返回非页面数据，如果没有该注解，默认返回html页面
public class UserController {

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public Object sayHi(String name) {
        return "Hi! Spring MVC: " + name;
    }

    @GetMapping("/get-type")
    public String getType() {
        return "Hi, GetMapping!";
    }

    @PostMapping("/post-type")
    public String postType() {
        return "Hi, PostMapping!";
    }

    @GetMapping("/register")
    public String register(String name, int age) {
        return "注册成功! " + name + ", age = " + age;
    }

    /**
     * 接收一个对象参数
     * @param user
     * @return
     */
    @GetMapping("/new-register")
    public User newRegister(@RequestBody User user) {
        return user;
    }

    @GetMapping("/time")
    public String setTime(@RequestParam(value = "time", required = false) String createTime) {
        // 省略上千行代码...
        return "设置时间为: " + createTime;
    }

    @PostMapping("/p/{name}/{age}")// 加上{}表示动态参数，只要类型匹配就能获取到
    public String urlParam(@PathVariable String name, @PathVariable Integer age) {
        return "name = " + name + ", age = " + age;
    }

    @RequestMapping("/file")
    public String upload(@RequestPart("myfile") MultipartFile file) throws IOException {
        // 1. 配置上传文件目录

        // 2. 生成动态文件名(包括后缀)

        // 保存文件，transferTo方法用来将接收文件传输到给定目标路径
        file.transferTo(new File("/Users/harley/Desktop/code/FileTest/test.jpeg"));
        return "上传成功";
    }
}
