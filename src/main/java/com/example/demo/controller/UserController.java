package com.example.demo.controller;
import com.example.demo.common.Result;
import com.example.demo.model.User;
import com.example.demo.model.UserRequest;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/handle")
    public Result<?> handle(@RequestBody UserRequest userRequest) {

        String action = userRequest.getAction();

        if (action == null) return Result.error("操作类型不能为空");

        User user = userRequest.getData();
        switch (action) {
            case "login":
                return userService.login(user);
            case "register":
                return userService.register(user);
            default:
                return Result.error("不支持的操作类型: " + action);
        }
    }

    @GetMapping("/info/{userId}")
    public Result<User> userInfo(@PathVariable String userId) {
        if (userId == null) {
            return Result.unauthorized("ユーザー情報を取得できません");
        }
        User user = (User) userService.getInfo(userId).getData();
        return Result.success(user);
    }

    @GetMapping("/checkLogin")
    public Result<User> checkLogin(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return Result.unauthorized("未登录");
        }
        return Result.success(null);
    }
}