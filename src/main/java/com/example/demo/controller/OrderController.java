package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result<?> create(@RequestBody Order order) {
        boolean saved = orderService.save(order);
        return saved ? Result.success("订单创建成功", null) : Result.error("订单创建失败");
    }

    @GetMapping("/list")
    public Result<List<Order>> list() {
        List<Order> list = orderService.list();
        return Result.success(list);
    }
}
