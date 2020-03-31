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
}
