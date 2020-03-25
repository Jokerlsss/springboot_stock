package com.stock.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.FinancialProduct;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/7
 * Time: 17:23
 * Description:
 */
@Service
public interface FinancialProductService extends BaseService<FinancialProduct>{
    public List<FinancialProduct> selectStockBaseInfo();
    public IPage<FinancialProduct> selectPage(IPage<FinancialProduct> ipage, Wrapper<FinancialProduct> wrapper);
    public List<FinancialProduct> selectByWrapperReturnList(Wrapper<FinancialProduct> wrapper);
    public FinancialProduct selectByWrapperReturnBean(Wrapper<FinancialProduct> wrapper);
    public int updateByWrapper(FinancialProduct bean,Wrapper<FinancialProduct> wrapper);
}
