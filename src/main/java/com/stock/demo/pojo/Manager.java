package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/22
 * Time: 18:17
 * Description:
 */
@TableName("manager")
@Data
public class Manager {
    public Manager(){}

    @TableId(value = "managerID")
    private Long managerID;

    @TableField("managerName")
    private String managerName;

    @TableField("managerPassword")
    private String managerPassword;
}
