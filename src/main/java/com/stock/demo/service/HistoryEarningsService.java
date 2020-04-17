package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.HistoryEarnings;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/16
 * Time: 22:05
 * Description:
 */
@Service
public interface HistoryEarningsService extends BaseService<HistoryEarnings>{
    public int updateByWrapper(HistoryEarnings historyEarnings, QueryWrapper<HistoryEarnings> wrapper);
}
