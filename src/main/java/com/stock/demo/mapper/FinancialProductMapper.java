package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.FinancialProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/7
 * Time: 17:23
 * Description:
 */
@Mapper
public interface FinancialProductMapper extends BaseMapper<FinancialProduct> {
    /** 查询股票基本信息 */
    @Select("select a.*,b.dateOfEstablishment,b.issuePrice from financialproduct a,stock b where a.productCode=b.productCode")
    public List<FinancialProduct> selectStockBaseInfo();
}
