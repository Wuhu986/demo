package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/publish")
    public Result<?> publish(@RequestBody Product product) {
        boolean saved = productService.save(product);
        return saved ? Result.success("商品发布成功", null) : Result.error("商品发布失败");
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize) {
        Page<Product> page = productService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }

    @GetMapping("/search")
    public Result<?> search(@RequestParam String keyword,
                            @RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Product> page = productService.page(new Page<>(pageNum, pageSize),
                new QueryWrapper<Product>()
                        .like("name", keyword)
                        .or()
                        .like("description", keyword)
        );
        return Result.success(page);
    }
}
