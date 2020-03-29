package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.FundEarnings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:40
 * Description:
 */
@Mapper
public interface FundEarningsMapper  extends BaseMapper<FundEarnings> {
    @Select("SELECT * FROM fundearnings where productCode=#{productCode} order by earningsDate DESC limit 0,1")
    public FundEarnings selectLastStockEarnings(String productCode);
}
