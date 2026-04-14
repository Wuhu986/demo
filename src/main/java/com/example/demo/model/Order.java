
package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("orders")
public class Order {
    @TableId
    private Long id;
    private Long productId;
    private Long buyerId;
    private Long sellerId;
    private String status;
    private Double price;
    private String createTime;
    private String updateTime;
}
