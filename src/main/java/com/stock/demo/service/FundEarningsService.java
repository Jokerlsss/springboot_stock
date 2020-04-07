package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.stock.demo.pojo.FundEarnings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 9:54
 * Description:
 */
@Service
public interface FundEarningsService extends BaseService<FundEarnings>{
    public FundEarnings selectByWrapperReturnBean(Wrapper<FundEarnings> wrapper);
    public List<FundEarnings> selectRecordFromTime(String productCode, int time);
}
