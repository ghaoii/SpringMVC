package com.ghaoi.spring_mvc.controller;

import com.ghaoi.spring_mvc.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/controller")
@ResponseBody// 当前方法(或类)返回非页面数据，如果没有该注解，默认返回html页面
public class UserController {

    @Value("${upload.path}")
    private String uploadPath;

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
        if(file == null) {
            return "没有传入文件, 上传失败";
        }
        // 1. 配置上传文件目录
        String filePath = uploadPath;
        // 2. 生成动态文件名(包括后缀)
        String fileName = UUID.randomUUID() +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 保存文件，transferTo方法用来将接收文件传输到给定目标路径
        file.transferTo(new File(filePath + fileName));
        System.out.println(fileName);
        return "上传成功";
    }


    /**
     * Spring MVC 是基于 Servlet的，因此所有映射方法中都内置了下面两个参数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cookie")
    public Cookie[] getCookie(HttpServletRequest request, HttpServletResponse response) {
        // 获取所有 cookie 信息
        Cookie[] cookies = request.getCookies();
        // 获取Session中的信息，传入 key 值
        String userAgent = request.getHeader("User-Agent");
        return cookies;
    }

    @RequestMapping("/cookie2")
    public String getCookie2(@CookieValue("Harley") String str) {
        // @CookieValue()的参数列表中，传入cookie的key值
        return str;
    }

    @RequestMapping("/session")
    public String getUserAgent(@RequestHeader("User-Agent") String userAgent) {
        return userAgent;
    }

    /**
     * 登录 存储session
     * @param request
     * @return
     */
    @RequestMapping("/setsession")
    // 当方法参数中接收了request对象，SpringMVC就会自动将request对象传入
    public String setSession(HttpServletRequest request) {
        // 获取session对象，参数为true表示如果拿不到session对象，那么会创建一个session对象
        // 反之，如果参数为false，那么即使没有获取到session对象，也不会创建对象
        HttpSession session = request.getSession(true);
        // 设置username属性
        if(session != null) {
            // 使用指定的名称绑定一个对象到该 session 会话
            session.setAttribute("username", "Harley");
        }
        return "用户登录成功";
    }

    /**
     * 验证用户登录状态
     * @param request
     * @return
     */
    @RequestMapping("/getsession")
    public String getSession(HttpServletRequest request) {
        // 现在我们是验证用户登录的，如果没有session传入，则不要创建
        HttpSession session = request.getSession(false);
        // 先初始化一个userName
        String userName = "nothing";
        // 当session对象存在，且username属性不为空，我们就简单认为登录了
        if(session != null && session.getAttribute("username") != null) {
            userName = (String) session.getAttribute("username");
        }
        // 返回用户的名称
        return userName;
    }

    @RequestMapping("/getsession2")
    // 注解中传入需要获取的属性，然后设置为无需必传，否则没有获取到session的时候会报错
    public String getSession2(@SessionAttribute(value = "username", required = false) String userName) {
        return userName;
    }

    @RequestMapping("/gethtml")
    public String getHtml(String name) {
        return "<h1>Hello: " + name + "</h1>";
    }

    @RequestMapping("/getjson")
    public Object getJson(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("curtime", LocalDate.now());
        return map;
    }
}
