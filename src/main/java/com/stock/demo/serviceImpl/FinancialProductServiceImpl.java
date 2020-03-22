package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.FinancialProductMapper;
import com.stock.demo.pojo.FinancialProduct;
import com.stock.demo.service.FinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/7
 * Time: 17:25
 * Description:
 */
@Service
public class FinancialProductServiceImpl implements FinancialProductService {

    @Autowired
    FinancialProductMapper financialProductMapper;

    @Override
    public List<FinancialProduct> list() {
        return financialProductMapper.selectList(null);
    }

    @Override
    public int insert(FinancialProduct bean) {
        return financialProductMapper.insert(bean);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(FinancialProduct bean) {
        return 0;
    }

    @Override
    public FinancialProduct load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return financialProductMapper.selectCount(null);
    }

    @Override
    public IPage<FinancialProduct> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public FinancialProduct loadByName(String name) {
        return null;
    }

    @Override
    public IPage<FinancialProduct> pagerByName(Wrapper<FinancialProduct> wrapper, Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public List<FinancialProduct> selectStockBaseInfo() {
        return financialProductMapper.selectStockBaseInfo();
    }

    @Override
    public IPage<FinancialProduct> selectPage(IPage<FinancialProduct> ipage, Wrapper<FinancialProduct> wrapper) {
        return financialProductMapper.selectPage(ipage,wrapper);
    }

    @Override
    public List<FinancialProduct> selectByWrapper(Wrapper<FinancialProduct> wrapper) {
        return financialProductMapper.selectList(wrapper);
    }

    /**
     * 根据条件来更新
     * @param bean
     * @param wrapper
     * @return
     */
    @Override
    public int updateByWrapper(FinancialProduct bean, Wrapper<FinancialProduct> wrapper) {
        return financialProductMapper.update(bean,wrapper);
    }
}
