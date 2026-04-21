package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/record")
    public Result<?> record(@RequestBody Payment payment, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            Long userId = JwtUtil.getUserId(authHeader.substring(7));
            payment.setUserId(userId);
        }
        boolean saved = paymentService.save(payment);
        return saved ? Result.success("支付记录成功", null) : Result.error("支付记录失败");
    }
}