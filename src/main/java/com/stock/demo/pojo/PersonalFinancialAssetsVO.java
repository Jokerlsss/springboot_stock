package com.stock.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/3
 * Time: 14:24
 * Description:
 */
@Data
public class PersonalFinancialAssetsVO{
    /** 今日收益 */
    private String dayEarn;

    /** 持仓成本 */
    private float holdingCost;

    /** 持有收益 */
    private String holdEarn;

    /** 项目 Code */
    private String productCode;

    /** 项目名称 */
    private String productName;

    /** 项目类型 */
    private String productType;

    /** 用户ID */
    private String userid;

    /** 拥有资产 */
    private float holdAssets;
}
