package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.PersonalFinancialAssetsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/3
 * Time: 14:27
 * Description:
 */
@Mapper
public interface PersonalFinancialAssetsVOMapper extends BaseMapper<PersonalFinancialAssetsVO> {
@Select("select a.productCode,a.productName,a.productType,b.holdEarn,b.holdingCost,b.dayEarn,b.holdAssets from financialproduct a,personalfinancialassets b where b.userid=#{userid} and b.status=0 and a.productCode=b.productCode")
    public List<PersonalFinancialAssetsVO> selectByWrapper(String userid);
}
