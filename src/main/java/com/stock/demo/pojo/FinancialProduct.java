package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
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
public class FinancialProduct implements Serializable {
    private static final long serialVersionUID = 1997544134838742728L;

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
    private String listingStatus;

    @TableField("dateOfEstablishment")
    private Date dateOfEstablishment;

    // TODO:该注解存在的话，在进行分页的controller会报错找不到该属性
//    @Transient
//    private Float issuePrice;
}
