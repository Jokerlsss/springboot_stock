package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stock.demo.pojo.FinancialProduct;
import com.stock.demo.service.FinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/7
 * Time: 17:26
 * Description:
 */
@RestController
@RequestMapping(path={"/financialProduct"})
public class FinancialProductController implements BaseController<FinancialProduct>{

    @Autowired
    FinancialProductService financialProductService;

    /**
     * 根据 productCode 条件更新
     * @param bean
     * @return
     */
    @PostMapping("updateByWrapper")
    public int updateByWrapper(@RequestBody(required = false) FinancialProduct bean){
        QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
        String productCode=bean.getProductCode();
        queryWrapper.eq("productCode",productCode);
        return financialProductService.updateByWrapper(bean,queryWrapper);
    }

    /**
     * 根据条件进行查询
     * @param productType
     * @return
     */
    @GetMapping("listByWrapper")
    public List<FinancialProduct> listByWrapper(@RequestParam(value = "productType", required=false ) String productType){
        QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("productType",productType);
        queryWrapper.eq("listingStatus","在市");
        return financialProductService.selectByWrapper(queryWrapper);
    }

    /**
     * 查询所有已有项目
     * @return
     */
    @Override
    @GetMapping("list")
    public List<FinancialProduct> list() {
        return financialProductService.selectStockBaseInfo();
    }

    /**
     * 后台管理系统新增项目
     * @param bean
     * @return
     */
    @PostMapping("insert")
    @Override
    public int insert(@RequestBody(required = false) FinancialProduct bean) {
        System.out.println(bean);
        System.out.println("Insert product");
        return financialProductService.insert(bean);
    }

    /**
     * 后台管理系统分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("listPage")
    public IPage<FinancialProduct> selectPage(@RequestParam(value = "current", required=false ) Long current,
                                              @RequestParam(value = "size", required=false ) Long size){
        Integer total=financialProductService.count();
        System.out.println("select Page");
        IPage<FinancialProduct> pages=new Page<>(current,size,total);
        pages=financialProductService.selectPage(pages,null);
        return pages;
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
        return null;
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
}
