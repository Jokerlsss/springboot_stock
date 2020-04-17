package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/16
 * Time: 22:03
 * Description:
 */
@TableName("historyearnings")
@Data
public class HistoryEarnings {
    public HistoryEarnings(){}

    // 个人资产ID
    @TableId(value = "earningsdate")
    private Date earningsdate;

    // 用户ID
    @TableId(value = "userid")
    private Long userid;

    // 产品代码
    @TableField("dayEarn")
    private float dayEarn;
}
