package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/20
 * Time: 9:53
 * Description:
 */
@TableName("personalfinancialassets")
@Data
public class PersonalFinancialAssets {
    public PersonalFinancialAssets(){}

    /**
     * 个人理财资产编号 + 用户ID 唯一决定一条记录
     * 个人理财编号为：时间戳 + 个人资产序号
     */
    /* 个人理财资产编号 */
    @TableId(value = "personalFinancialAssetsID")
    private Long personalFinancialAssetsID;

    /* 用户编码 */
    @TableId(value = "userID")
    private Long userID;

    /* 产品代码 */
    @TableField(value = "productCode")
    private String productCode;

    /* 交易平台 */
    @TableField(value = "platform")
    private String platform;

    /* 持有份额 */
    @TableField(value = "amountOfAssets")
    private Float amountOfAssets;

    /* 持仓成本 */
    @TableField(value = "holdingCost")
    private Float holdingCost;

    /* 买入时间 */
    @TableField(value = "buyTime")
    private Date buyTime;

    /* 备注 */
    @TableField(value = "note")
    private String note;

    /* 资产状态 */
    @TableField(value = "status")
    private Long status;

    // 理财产品表
    @Transient
    private String productName;

    @Transient
    private String productType;
}
