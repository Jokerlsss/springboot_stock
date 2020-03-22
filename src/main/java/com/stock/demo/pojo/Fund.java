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
@TableName("fund")
@Data
public class Fund {
    public Fund(){}

    // 产品代码
    @TableId(value = "productCode")
    private String productCode;

    // 基金类型：混合型、股票型、债券型
    @TableField("fundType")
    private String fundType;

    // 基金经理
    @TableField("fundManager")
    private String fundManager;

    // 资产规模
    @TableField("assetSize")
    private String assetSize;

    // 发行价格
    @TableField("issuePrice")
    private Float issuePrice;
}
