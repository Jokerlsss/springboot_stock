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
@TableName("regularearnings")
@Data
public class RegularEarnings {
    public RegularEarnings(){}

    @TableId(value = "productCode")
    private String productCode;

    // 收益日期
    @TableField("earningsDate")
    private String earningsDate;

    // 七日年化收益率
    @TableField("sevenAnnualizedReturn")
    private String sevenAnnualizedReturn;
}
