package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/record")
    public Result<?> record(@RequestBody Payment payment) {
        boolean saved = paymentService.save(payment);
        return saved ? Result.success("支付记录成功", null) : Result.error("支付记录失败");
    }
}
