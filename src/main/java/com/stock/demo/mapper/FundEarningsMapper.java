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
    /** 查找最后一条收益记录 */
    @Select("SELECT * FROM fundearnings where productCode=#{productCode} order by earningsDate DESC limit 0,1")
    public FundEarnings selectLastOneEarnings(String productCode);

    /** 查找倒数第二条收益记录 */
    @Select("SELECT * FROM fundearnings where productCode=#{productCode} order by earningsDate DESC limit 1,1")
    public FundEarnings selectLastTwoEarnings(String productCode);
}
