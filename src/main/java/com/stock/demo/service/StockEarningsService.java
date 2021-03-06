package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.StockEarnings;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/8
 * Time: 11:46
 * Description:
 */
@Service
public interface StockEarningsService extends BaseService<StockEarnings>{
    public StockEarnings selectByWrapperReturnBean(Wrapper<StockEarnings> wrapper);
    public List<StockEarnings> selectRecordFromTime(String productCode, int time);
}
