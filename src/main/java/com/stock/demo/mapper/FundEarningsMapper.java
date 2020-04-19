package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.FundEarnings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:40
 * Description:
 */
@Mapper
public interface FundEarningsMapper  extends BaseMapper<FundEarnings> {
    /** 查找最后一条收益记录 */
    @Select("SELECT * FROM fundearnings where productCode=#{productCode} order by earningsDate DESC limit 0,1")
    public FundEarnings selectLastOneEarnings(String productCode);

    /** 查找倒数第二条收益记录 */
    @Select("SELECT * FROM fundearnings where productCode=#{productCode} order by earningsDate DESC limit 1,1")
    public FundEarnings selectLastTwoEarnings(String productCode);

    /** 趋势图：查找某段时间的收益记录  time：-1,-3 ... */
    @Select("select * from fundearnings where productCode=#{productCode} and earningsDate between (SELECT DATE_ADD(now(),INTERVAL #{time} MONTH)) and now()")
    public List<FundEarnings> selectRecordFromTime(String productCode, int time);

    /** 资产推荐：获取一年/三年前的净值
     *  （time：1,2,3... ;productCode）
     * */
    @Select("SELECT netWorth from fundearnings where productCode=#{productCode} and earningsDate between (SELECT DATE_ADD(now(),INTERVAL -#{time} month)) and now() ORDER BY earningsDate ASC limit 0,1")
    public float getNetWorth(String productCode,int time);
}
