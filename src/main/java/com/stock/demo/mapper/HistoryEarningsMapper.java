package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.HistoryEarnings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/16
 * Time: 22:05
 * Description:
 */
@Mapper
public interface HistoryEarningsMapper extends BaseMapper<HistoryEarnings> {
    /** 查看历史收益（本月，上月，前月） :  userid & 月份 0：本月  1：上月  2：前月 */
    @Select("select * from historyearnings where userid=#{userid} and month(Now())-#{month}=month(earningsdate) order by earningsdate desc")
    public List<HistoryEarnings> selectMonthOfList(Long userid,int month);

    /** 查看本月盈利次数 */
    @Select("select count(*) from historyearnings where userid=#{userid} and dayEarn>=0 and month(Now())-0=month(earningsdate)")
    public int selectCountOfProfit(Long userid);

    /** 查看本月亏损次数 */
    @Select("select count(*) from historyearnings where userid=#{userid} and dayEarn<0 and month(Now())-0=month(earningsdate)")
    public int selectCountOfLoss(Long userid);

    /** 查看本月收益 */
    @Select("select IFNULL(sum(dayEarn),0) from historyearnings where userid=#{userid} and month(Now())-0=month(earningsdate)")
    public float selectMonthOfEarnings(Long userid);

    /** 查看本月收益最多是哪天 */
    @Select("select * from historyearnings where userid=#{userid} and month(Now())-0=month(earningsdate) ORDER BY dayEarn desc limit 0,1")
    public HistoryEarnings selectMostEarnings(Long userid);
}
