
package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product")
public class Product {
    @TableId
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String status;
    private String createTime;
    private String updateTime;

}
