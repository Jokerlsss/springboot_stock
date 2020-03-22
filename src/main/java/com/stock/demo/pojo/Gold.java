package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/22
 * Time: 18:15
 * Description:
 */
@TableName("gold")
@Data
public class Gold {
    public Gold(){}

    @TableId(value = "productCode")
    private String productCode;

    // 发行价格
    @TableField("issuePrice")
    private Float issuePrice;
}
