package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.StockEarnings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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

    /** 查询倒数第二条收益记录 */
    @Select("SELECT * FROM stockEarnings where productCode=#{productCode} order by earnings_date DESC limit 1,1")
    public StockEarnings selectLastTwoStockEarnings(String productCode);

    /** 趋势图：查找某段时间的收益记录  time：-1,-3 ... */
    @Select("select * from stockearnings where productCode=#{productCode} and earnings_date between (SELECT DATE_ADD(now(),INTERVAL #{time} MONTH)) and now()")
    public List<StockEarnings> selectRecordFromTime(String productCode,int time);

    /** 资产推荐：获取一年/三年前的净值
     *  （time：1,2,3... ;productCode）
     * */
    @Select("SELECT stockMarketValue from stockearnings where productCode=#{productCode} and earnings_date between (SELECT DATE_ADD(now(),INTERVAL -#{time} month)) and now() ORDER BY earnings_date ASC limit 0,1")
    public float getNetWorth(String productCode,int time);
}
