package com.example.demo.controller;
import com.example.demo.common.Result;
import com.example.demo.model.User;
import com.example.demo.model.UserRequest;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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

    @GetMapping("/balance")
    public Result<?> getBalance(HttpServletRequest request) {
        Long userId = parseUserId(request);
        if (userId == null) return Result.unauthorized("请先登录");
        return userService.getBalance(userId);
    }

    @PostMapping("/recharge")
    public Result<?> recharge(@RequestBody Map<String, Double> body, HttpServletRequest request) {
        Long userId = parseUserId(request);
        if (userId == null) return Result.unauthorized("请先登录");
        Double amount = body.get("amount");
        return userService.recharge(userId, amount);
    }

    private Long parseUserId(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        return JwtUtil.getUserId(auth.substring(7));
    }
}