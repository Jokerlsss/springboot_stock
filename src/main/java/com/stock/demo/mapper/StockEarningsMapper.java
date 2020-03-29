package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.StockEarnings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/8
 * Time: 11:44
 * Description:
 */
@Mapper
public interface StockEarningsMapper extends BaseMapper<StockEarnings> {
    @Select("SELECT * FROM stockearnings where productCode=#{productCode} order by earnings_date DESC limit 0,1")
    public StockEarnings selectLastStockEarnings(String productCode);
}
