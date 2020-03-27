package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stock.demo.pojo.*;
import com.stock.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stock.demo.util.DateOprate;

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

    @Autowired
    FundService fundService;

    @Autowired
    GoldService goldService;

    @Autowired
    RegularService regularService;

    @Autowired
    StockService stockService;

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
     * 根据 productType 条件查询所有在市项目
     * @param productType
     * @return
     */
    @GetMapping("listByWrapper")
    public List<FinancialProduct> listByWrapper(@RequestParam(value = "productType", required=false ) String productType) throws ParseException {
        QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
// ---------- 从数据库查出日期，转换成 String 格式并判断是否为工作日-----------
//        QueryWrapper<FinancialProduct> queryWrapper1=new QueryWrapper<>();
//        queryWrapper1.eq("productCode",000000);
//        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(queryWrapper1);
//        Date time=financialProduct.getDateOfEstablishment();
//        // Date -> String
//        DateOprate dateOprate=new DateOprate();
//        if(dateOprate.isWorkDay(time)){
//            System.out.println("是工作日");
//        }else{
//            System.out.println("是周末");
//        }
// ---------------------------------------------------------------------
        queryWrapper.eq("productType",productType);
        queryWrapper.eq("listingStatus","在市");
        return financialProductService.selectByWrapperReturnList(queryWrapper);
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

    /**
     * 查找项目详情（多对象查询）
     * @param productCode
     * @return map
     */
    @GetMapping("selectProjectDetail")
    public Map<String,Object> selectDetails(@RequestParam(value = "productCode",required = false) String productCode){
        Map<String,Object> map = new HashMap<String,Object>(10);
        Object object=new Object();

        QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("productCode",productCode);

        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(queryWrapper);

        map.put("financialProduct",financialProduct);
        // 根据不同类型类读取不同实体类的详细页数据
        // 将查询出来的实体类复制给 map，并用 map 作为返回值
        if(financialProduct.getProductType().equals("基金")){
            QueryWrapper<Fund> fundQueryWrapper=new QueryWrapper<>();
            fundQueryWrapper.eq("productCode",productCode);
            Fund fund=fundService.selectByWrapperReturnBean(fundQueryWrapper);
            map.put("fund",fund);
        }else if(financialProduct.getProductType().equals("黄金")){
            QueryWrapper<Gold> goldQueryWrapper=new QueryWrapper<>();
            goldQueryWrapper.eq("productCode",productCode);
            Gold gold=goldService.selectByWrapperReturnBean(goldQueryWrapper);
            map.put("gold",gold);
        }else if(financialProduct.getProductType().equals("定期")){
            QueryWrapper<Regular> regularQueryWrapper=new QueryWrapper<>();
            regularQueryWrapper.eq("productCode",productCode);
            Regular regular=regularService.selectByWrapperReturnBean(regularQueryWrapper);
            map.put("regular",regular);
        }else if(financialProduct.getProductType().equals("股票")){
            QueryWrapper<Stock> stockQueryWrapper=new QueryWrapper<>();
            stockQueryWrapper.eq("productCode",productCode);
            Stock stock=stockService.selectByWrapperReturnBean(stockQueryWrapper);
            map.put("stock",stock);
        }
        return map;
    }

    /**
     * 添加项目时，搜索项目
     * @param productCode
     * @param productName
     * @return 搜索条件为空时，返回值为 []；否则返回实体类
     */
    @GetMapping("/searchProject")
    public List<FinancialProduct> searchProject(@RequestParam(value = "productCode",required = false) String productCode,
                                          @RequestParam(value = "productName",required = false) String productName){
        // 从前端传过来的参数只能 productCode 或 productName 二选一
        // 当 productCode 不为空时，则判断为通过代码搜索
        // 当 productName 不为空时，则判断为通过名称搜索
        if(!productCode.equals("") && productName.equals("")){
            QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
            queryWrapper.like("productCode",productCode);
            return financialProductService.selectByWrapperReturnList(queryWrapper);
        }else if(productCode.equals("") && !productName.equals("")){
            QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
            queryWrapper.like("productName",productName);
            return financialProductService.selectByWrapperReturnList(queryWrapper);
        }else{
            return null;
        }
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
