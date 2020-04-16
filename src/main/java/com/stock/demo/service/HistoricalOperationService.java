package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.HistoricalOperation;
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
public interface HistoricalOperationService extends BaseService<HistoricalOperation>{
    /**
     * get：操作记录
     * @param wrapper
     * @return List<bean>
     */
    public List<HistoricalOperation> selectByWrapperReturnList(QueryWrapper<HistoricalOperation> wrapper);
}
