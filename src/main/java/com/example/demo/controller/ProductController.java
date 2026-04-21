package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/publish")
    public Result<?> publish(@RequestBody Product product, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Result.unauthorized("请先登录");
        }
        Long userId = JwtUtil.getUserId(authHeader.substring(7));
        if (userId == null) {
            return Result.unauthorized("无效的 Token");
        }
        product.setUserId(userId);
        boolean saved = productService.save(product);
        return saved ? Result.success("商品发布成功", null) : Result.error("商品发布失败");
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product == null) {
            return Result.error("商品不存在");
        }
        return Result.success(product);
    }

    @PutMapping("/{id}/sold")
    public Result<?> markSold(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product == null) return Result.error("商品不存在");
        product.setStatus("已售");
        productService.updateById(product);
        return Result.success("商品已标记为已售", null);
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize) {
        Page<Product> page = productService.page(new Page<>(pageNum, pageSize),
                new QueryWrapper<Product>().ne("status", "已售"));
        return Result.success(page);
    }

    @GetMapping("/search")
    public Result<?> search(@RequestParam String keyword,
                            @RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Product> page = productService.page(new Page<>(pageNum, pageSize),
                new QueryWrapper<Product>()
                        .ne("status", "已售")
                        .and(w -> w.like("name", keyword).or().like("description", keyword))
        );
        return Result.success(page);
    }
}
