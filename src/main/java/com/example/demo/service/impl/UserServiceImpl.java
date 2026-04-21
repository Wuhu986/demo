package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Result<?> login(User user) {
        if (user.getPhone() == null || user.getPassword() == null) {
            return Result.error("手机号或密码不能为空");
        }

        User dbUser = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, user.getPhone())
                .eq(User::getPassword, user.getPassword()));

        if (dbUser == null) {
            return Result.unauthorized("手机号或密码错误");
        }

        String token = JwtUtil.generateToken(dbUser.getId());
        return Result.success(token);
    }

    @Override
    public Result<?> register(User user) {
        if (user.getPhone() == null || user.getPassword() == null) {
            return Result.error("手机号或密码不能为空");
        }

        User exists = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, user.getPhone()));

        if (exists != null) {
            return Result.error("该手机号已注册");
        }

        boolean saved = this.save(user);
        return saved ? Result.success("注册成功") : Result.error("注册失败");
    }

    @Override
    public Result<?> getInfo(String userId) {
        User user = this.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<?> recharge(Long userId, Double amount) {
        if (amount == null || amount <= 0) return Result.error("充值金额必须大于0");
        this.update(new UpdateWrapper<User>()
                .setSql("balance = balance + " + amount)
                .eq("id", userId));
        User user = this.getById(userId);
        return Result.success("充值成功", user.getBalance());
    }

    @Override
    public Result<?> getBalance(Long userId) {
        User user = this.getById(userId);
        if (user == null) return Result.error("用户不存在");
        return Result.success(user.getBalance() != null ? user.getBalance() : 0.0);
    }
}
