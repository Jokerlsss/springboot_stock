package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.HistoricalOperationMapper;
import com.stock.demo.mapper.HistoryEarningsMapper;
import com.stock.demo.pojo.HistoricalOperation;
import com.stock.demo.pojo.HistoryEarnings;
import com.stock.demo.service.HistoricalOperationService;
import com.stock.demo.service.HistoryEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/16
 * Time: 22:05
 * Description:
 */
@Service
public class HistoryEarningsServiceImpl implements HistoryEarningsService {

    @Autowired
    HistoryEarningsMapper historyEarningsMapper;

    @Override
    public List<HistoryEarnings> list() {
        return null;
    }

    public List<HistoryEarnings> listByWrapper(QueryWrapper<HistoryEarnings> wrapper){
        return historyEarningsMapper.selectList(wrapper);
    }

    @Override
    public int insert(HistoryEarnings bean) {
        return historyEarningsMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(HistoryEarnings bean) {
        return 0;
    }

    @Override
    public HistoryEarnings load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<HistoryEarnings> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public HistoryEarnings loadByName(String name) {
        return null;
    }

    @Override
    public IPage<HistoryEarnings> pagerByName(Wrapper<HistoryEarnings> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    /**
     * 根据 wrapper 来更新
     * @param historyEarnings
     * @param wrapper
     * @return
     */
    @Override
    public int updateByWrapper(HistoryEarnings historyEarnings, QueryWrapper<HistoryEarnings> wrapper) {
        return historyEarningsMapper.update(historyEarnings,wrapper);
    }
}
