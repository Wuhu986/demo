package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.Result;
import com.example.demo.model.User;

public interface UserService extends IService<User> {
    Result<?> login(User user);
    Result<?> register(User user);
    Result<?> getInfo(String userId);
    Result<?> recharge(Long userId, Double amount);
    Result<?> getBalance(Long userId);
}
