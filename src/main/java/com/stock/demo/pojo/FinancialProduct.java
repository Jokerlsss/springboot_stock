package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/7
 * Time: 17:17
 * Description:
 */
@TableName("financialproduct")
@Data
public class FinancialProduct {
    public FinancialProduct(){}

    @TableId(value = "productCode")
    private String productCode;

    @TableField("productName")
    private String productName;

    @TableField("productType")
    private String productType;

    @TableField("riskType")
    private String riskType;

    @TableField("publisher")
    private String publisher;

    @TableField("popularity")
    private int popularity;

    @TableField("listingStatus")
    private int listingStatus;

    @Transient
    private String dateOfEstablishment;

    @Transient
    private Float issuePrice;
}
