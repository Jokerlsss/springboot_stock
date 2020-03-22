package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/22
 * Time: 18:18
 * Description:
 */
@TableName("regular")
@Data
public class Regular {
    public Regular(){}

    @TableId(value = "productCode")
    private String productCode;

    // 定期时长
    @TableField("depositDuration")
    private int depositDuration;

    // 利率
    @TableField("interestRate")
    private Float interestRate;
}
