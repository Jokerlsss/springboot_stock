package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.HistoricalOperationMapper;
import com.stock.demo.pojo.HistoricalOperation;
import com.stock.demo.service.HistoricalOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:09
 * Description:
 */
@Service
public class HistoricalOperationServiceImpl implements HistoricalOperationService {

    @Autowired
    HistoricalOperationMapper historicalOperationMapper;

    @Override
    public List<HistoricalOperation> list() {
        return null;
    }

    /**
     * 添加操作记录
     * @param bean
     * @return
     */
    @Override
    public int insert(HistoricalOperation bean) {
        return historicalOperationMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(HistoricalOperation bean) {
        return 0;
    }

    @Override
    public HistoricalOperation load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<HistoricalOperation> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public HistoricalOperation loadByName(String name) {
        return null;
    }

    @Override
    public IPage<HistoricalOperation> pagerByName(Wrapper<HistoricalOperation> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    /**
     * get：操作记录
     * 参数：wrapper
     * @param wrapper
     * @return
     */
    @Override
    public List<HistoricalOperation> selectByWrapperReturnList(QueryWrapper<HistoricalOperation> wrapper) {
        return historicalOperationMapper.selectList(wrapper);
    }
}
