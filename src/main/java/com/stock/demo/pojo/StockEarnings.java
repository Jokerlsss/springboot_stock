package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/8
 * Time: 11:31
 * Description:
 */
@TableName("stockearnings")
@Data
public class StockEarnings {
    public StockEarnings(){}

    @TableId(value = "productCode")
    private String productCode;

    @TableId(value = "earningsDate")
    private Date earningsDate;

    @TableField("stockMarketValue")
    private Float stockMarketValue;

    @TableField("dailyChange")
    private Float dailyChange;
}
