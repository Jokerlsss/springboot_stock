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
import java.util.*;

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

    @Autowired
    StockEarningsService stockEarningsService;

    @Autowired
    GoldEarningsService goldEarningsService;

    @Autowired
    FundEarningsService fundEarningsService;

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
        // TODO: 在新增项目的同时，在 earnings 表中加入：代码、收益日期（等于发布日期）、净值（发布价格）、涨跌幅（第一天为 0）
        // TODO 股票收益：产品代码(bean.getCode)、收益日期(bean.getDate)、股票市值(stock.selectone(code).getPrice)、日涨跌幅(0)
        // TODO 基金收益：产品代码、收益日期、净值(fund.selectone(code).getPrice)、日涨跌幅
        // TODO 黄金收益：产品代码、收益日期、金价(gold.selectone(code).getPrice)、日涨跌幅

        // 增加 financialProduct 表记录
        financialProductService.insert(bean);

        String productType=bean.getProductType();
        if(productType.equals("股票")){
         // 获取产品代码、收益日期（第一天发布日期）、初始涨跌幅
         String productCode=bean.getProductCode();
         Date earningsDate=bean.getDateOfEstablishment();
         float dailyChange=0;

         // 获取发行价格
         QueryWrapper<Stock> queryWrapper=new QueryWrapper<>();
         queryWrapper.eq("productCode",productCode);
         Stock stock=stockService.selectByWrapperReturnBean(queryWrapper);
         float issuePrice=stock.getIssuePrice();

         // 赋值给 Earnings 表
         StockEarnings stockEarnings=new StockEarnings();
         stockEarnings.setProductCode(productCode);
         stockEarnings.setEarningsDate(earningsDate);
         stockEarnings.setStockMarketValue(issuePrice);
         stockEarnings.setDailyChange(dailyChange);

         // 增加 Earnings 表记录
         return stockEarningsService.insert(stockEarnings);
        }else if(productType.equals("基金")){
            // 获取产品代码、收益日期（第一天发布日期）、初始涨跌幅
            String productCode=bean.getProductCode();
            Date earningsDate=bean.getDateOfEstablishment();
            float dailyChange=0;

            // 获取发行价格
            QueryWrapper<Fund> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("productCode",productCode);
            Fund fund=fundService.selectByWrapperReturnBean(queryWrapper);
            // TODO: 有时候会空指针异常
            float issuePrice=fund.getIssuePrice();

            // 赋值给 Earnings 表
            FundEarnings fundEarnings=new FundEarnings();
            fundEarnings.setProductCode(productCode);
            fundEarnings.setEarningsDate(earningsDate);
            fundEarnings.setNetWorth(issuePrice);
            fundEarnings.setDailyChange(dailyChange);

            // 增加 Earnings 表记录
            return fundEarningsService.insert(fundEarnings);
        }else if(productType.equals("黄金")){
            // 获取产品代码、收益日期（第一天发布日期）、初始涨跌幅
            String productCode=bean.getProductCode();
            Date earningsDate=bean.getDateOfEstablishment();
            float dailyChange=0;

            // 获取发行价格
            QueryWrapper<Gold> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("productCode",productCode);
            Gold gold=goldService.selectByWrapperReturnBean(queryWrapper);
            float issuePrice=gold.getIssuePrice();

            // 赋值给 Earnings 表
            GoldEarnings goldEarnings=new GoldEarnings();
            goldEarnings.setProductCode(productCode);
            goldEarnings.setEarningsDate(earningsDate);
            goldEarnings.setGoldPrice(issuePrice);
            goldEarnings.setDailyChange(dailyChange);

            // 增加 Earnings 表记录
            return goldEarningsService.insert(goldEarnings);
        }else{
            return financialProductService.insert(bean);
        }
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
     * 查询近几月的净值  参数：productCode & 时间（-1，-3,-6,-12,-36）
     * @param productCode
     * @param time
     * @return
     */
    @GetMapping("selectToTrend")
    public Map<String,Object> selectToTrend(@RequestParam(value = "productCode",required = false) String productCode,
                                            @RequestParam(value = "time",required = false) int time){
        Map<String,Object> map = new HashMap<String,Object>(10);

        QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("productCode",productCode);
        /** 根据 productCode 查得 productType */
        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(queryWrapper);

        // 数组：净值
        List worthList=new ArrayList();

        if(financialProduct.getProductType().equals("基金")){
            List<FundEarnings> fundEarningsList=fundEarningsService.selectRecordFromTime(productCode,time);
            /** 用两个数组来存放数据，一个净值，一个日期，最终将数组合并为 Map */

            for(int i=0;i<fundEarningsList.size();i++){
                // 将净值提取出来，存进 worthList
                worthList.add(fundEarningsList.get(i).getNetWorth());
            }
            map.put("worthList",worthList);
            System.out.println(productCode);
        }else if(financialProduct.getProductType().equals("股票")){
            List<StockEarnings> stockEarningsList=stockEarningsService.selectRecordFromTime(productCode,time);
            /** 用两个数组来存放数据，一个净值，一个日期，最终将数组合并为 Map */

            for(int i=0;i<stockEarningsList.size();i++){
                // 将净值提取出来，存进 worthList
                worthList.add(stockEarningsList.get(i).getStockMarketValue());
            }
            map.put("worthList",worthList);
            System.out.println(productCode);
        }else if(financialProduct.getProductType().equals("黄金")){
            List<GoldEarnings> goldEarningsList=goldEarningsService.selectRecordFromTime(productCode,time);
            /** 用两个数组来存放数据，一个净值，一个日期，最终将数组合并为 Map */

            for(int i=0;i<goldEarningsList.size();i++){
                // 将净值提取出来，存进 worthList
                worthList.add(goldEarningsList.get(i).getGoldPrice());
            }
            map.put("worthList",worthList);
            System.out.println(productCode);
        }
        return map;
    }

    /**
     * 查找项目详情（多对象查询）
     * @param productCode
     * @return map
     */
    @GetMapping("selectProjectDetail")
    public Map<String,Object> selectDetails(@RequestParam(value = "productCode",required = false) String productCode){
        Map<String,Object> map = new HashMap<String,Object>(10);

        QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("productCode",productCode);
        /** 根据 productCode 查得 productType */
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
