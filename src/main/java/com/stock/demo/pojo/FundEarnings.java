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
 * Time: 18:15
 * Description:
 */
@TableName("fundearnings")
@Data
public class FundEarnings {
    public FundEarnings(){}

    // 产品代码
    @TableId(value = "productCode")
    private String productCode;

    // 收益日期
    @TableField("earningsDate")
    private Date earningsDate;

    // 净值
    @TableField("netWorth")
    private Float netWorth;

    // 涨跌幅
    @TableField("dailyChange")
    private Float dailyChange;
}
