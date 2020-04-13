package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.PersonalFinancialAssets;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/20
 * Time: 10:01
 * Description:
 */
@Mapper
public interface PersonalFinancialAssetsMapper extends BaseMapper<PersonalFinancialAssets> {
//    TODO:修改查询方式
    @Select("select a.*,b.productName,b.productType from personalfinancialassets a,financialProduct b WHERE a.userID=#{userID} and a.status=0 and a.productCode=b.productCode")
    public List<PersonalFinancialAssets> selectUserHoldProduct(Long userID);

    /**
     * 查询某个人的累计收益
     */
    @Select("select SUM(totalEarn)from personalfinancialassets where userid=#{userid}")
    public float selectTotalEarn(Long userid);

    /**
     * 查询个人拥有资产某类型的资产和：状态：0（持有）
     * 参数：userid & productType
     * 返回：float 金额总和
     */
    @Select("select IFNULL(sum(holdAssets),0) from personalfinancialassets where userid=#{userid} and productType=#{productType} and status=0")
    public float selectTypeOfAssets(Long userid,String productType);

    /**
     * 查询个人拥有资产总资产：状态：0（持有）
     * @param userid
     * @return
     */
    @Select("select IFNULL(sum(holdAssets),0) from personalfinancialassets where userid=#{userid} and status=0")
    public float selectSumOfAssets(Long userid);
}
