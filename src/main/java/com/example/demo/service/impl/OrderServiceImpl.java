package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.mappers.OrderMapper;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> purchase(Long buyerId, String productId) {
        Product product = productService.getById(productId);
        if (product == null) return Result.error("商品不存在");
        if ("已售".equals(product.getStatus())) return Result.error("该商品已售出");
        if (product.getUserId().equals(buyerId)) return Result.error("不能购买自己发布的商品");

        double price = product.getPrice() != null ? product.getPrice() : 0.0;

        // 原子扣款：余额充足时才更新（mapper.update 返回 int 受影响行数）
        int affected = userMapper.update(null, new UpdateWrapper<User>()
                .setSql("balance = balance - " + price)
                .eq("id", buyerId)
                .ge("balance", price));
        if (affected == 0) return Result.error("余额不足，请先充值");

        // 原子加款到卖家
        userMapper.update(null, new UpdateWrapper<User>()
                .setSql("balance = balance + " + price)
                .eq("id", product.getUserId()));

        // 标记商品已售
        product.setStatus("已售");
        productService.updateById(product);

        // 创建订单
        Order order = new Order();
        order.setProductId(Long.parseLong(productId));
        order.setBuyerId(buyerId);
        order.setSellerId(product.getUserId());
        order.setPrice(price);
        order.setStatus("已支付");
        this.save(order);

        return Result.success("购买成功", order);
    }
}