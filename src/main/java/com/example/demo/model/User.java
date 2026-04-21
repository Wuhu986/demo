package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId
    private Long id;
    @TableField("nickname")
    private String nickname;
    @TableField("phone")
    private String phone;
    @TableField("password")
    private String password;
//    private String avatar;
//    private String gender;
    @TableField("birth")
    private String birth;
    @TableField("email")
    private String email;
    @TableField("balance")
    private Double balance;
    private String createTime;
    private String updateTime;

}
