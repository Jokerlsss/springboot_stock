package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.HistoricalOperation;
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
public interface HistoricalOperationMapper extends BaseMapper<HistoricalOperation> {
    /** 查看历史操作（本月，上月，前月） :  userid & 月份 0：本月  1：上月  2：前月 */
    @Select("select * from historicaloperation where userid=#{userid} and month(Now())-#{month}=month(operatingdate) order by operatingdate desc")
    public List<HistoricalOperation> selectMonthOfList(Long userid, int month);

    /** 本月总操作次数 */
    @Select("select count(*) from historicaloperation where userid=#{userid} and month(Now())-0=month(operatingdate)")
    public int selectCountOfOperate(Long userid);

    /** 本月买入次数 */
    @Select("select count(*) from historicaloperation where userid=#{userid} and month(Now())-0=month(operatingdate) and operationName='买入'")
    public int selectCountOfBuy(Long userid);

    /** 本月卖出次数 */
    @Select("select count(*) from historicaloperation where userid=#{userid} and month(Now())-0=month(operatingdate) and operationName='卖出'")
    public int selectCountOfSell(Long userid);

    /** 本月加仓次数 */
    @Select("select count(*) from historicaloperation where userid=#{userid} and month(Now())-0=month(operatingdate) and operationName='加仓'")
    public int selectCountOfAdd(Long userid);

    /** 本月操作金额最大的资产 */
    @Select("select * from historicaloperation where userid=#{userid} and month(Now())-0=month(operatingdate) order by oprateAmount desc limit 0,1")
    public HistoricalOperation selectMostOperateAmount(Long userid);
}
