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
@TableName("goldEarnings")
@Data
public class GoldEarnings {
    public GoldEarnings(){}

    @TableId(value = "productCode")
    private String productCode;

    @TableField("earningsDate")
    private Date earningsDate;

    @TableField("sevenAnnualizedReturn")
    private Float sevenAnnualizedReturn;

    @TableField("dailyChange")
    private Float dailyChange;
}
