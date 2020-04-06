package com.stock.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.demo.pojo.Gold;
import com.stock.demo.pojo.GoldEarnings;
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
public interface GoldEarningsMapper extends BaseMapper<GoldEarnings> {
    /** 查询最后一条收益记录 */
    @Select("SELECT * FROM goldearnings where productCode=#{productCode} order by earningsDate DESC limit 0,1")
    public GoldEarnings selectLastOneEarnings(String productCode);

    /** 查询倒数第二条收益记录 */
    @Select("SELECT * FROM goldEarnings where productCode=#{productCode} order by earningsDate DESC limit 1,1")
    public GoldEarnings selectLastTwoEarnings(String productCode);
}
