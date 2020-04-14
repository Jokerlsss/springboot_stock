package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/22
 * Time: 18:16
 * Description:
 */
@TableName("historicaloperation")
@Data
public class HistoricalOperation {
    public HistoricalOperation(){}

    // 个人资产ID
    @TableId(value = "personalFinancialAssetsID")
    private Long personalFinancialAssetsID;

    // 用户ID
    @TableId(value = "userid")
    private Long userid;

    // 操作时间
    @TableId(value = "operatingdate")
    private Date operatingdate;

    // 产品代码
    @TableField("productCode")
    private String productCode;

    // 操作名称：买入、卖出、加仓
    @TableField("operationName")
    private String operationName;

    // 产品名称
    @TableField("productName")
    private String productName;

    // 操作金额
    @TableField("oprateAmount")
    private float oprateAmount;

    // 剩余持有资产
    @TableField("holdAssets")
    private float holdAssets;



}
