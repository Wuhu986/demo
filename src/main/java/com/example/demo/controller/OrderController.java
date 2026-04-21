package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/purchase")
    public Result<?> purchase(@RequestBody Map<String, String> body, HttpServletRequest request) {
        Long buyerId = parseUserId(request);
        if (buyerId == null) return Result.unauthorized("请先登录");
        String productId = body.get("productId");
        return orderService.purchase(buyerId, productId);
    }


@GetMapping("/my")
    public Result<List<Order>> myOrders(HttpServletRequest request) {
        Long userId = parseUserId(request);
        if (userId == null) return Result.unauthorized("请先登录");
        List<Order> orders = orderService.list(
            new QueryWrapper<Order>().eq("buyer_id", userId).orderByDesc("create_time")
        );
        return Result.success(orders);
    }

    private Long parseUserId(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        return JwtUtil.getUserId(auth.substring(7));
    }
}