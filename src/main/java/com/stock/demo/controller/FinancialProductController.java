package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stock.demo.mapper.FundEarningsMapper;
import com.stock.demo.mapper.GoldEarningsMapper;
import com.stock.demo.mapper.StockEarningsMapper;
import com.stock.demo.pojo.*;
import com.stock.demo.service.*;
import com.stock.demo.util.UpdateEarn;
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
    FundEarningsMapper fundEarningsMapper;

    @Autowired
    StockEarningsMapper stockEarningsMapper;

    @Autowired
    GoldEarningsMapper goldEarningsMapper;

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

    @Autowired
    UpdateEarn updateEarn;

    @Autowired
    DateOprate dateOprate;


    /**
     * 在市场中，搜索项目
     * @param productCode
     * @param productName
     * @param productType
     * @return 搜索条件为空时，返回值为 []；否则返回实体类
     */
    @GetMapping("/searchMarketProject")
    public List<FinancialProduct> searchMarketProject(@RequestParam(value = "productCode",required = false) String productCode,
                                                @RequestParam(value = "productName",required = false) String productName,
                                                      @RequestParam(value = "productType",required = false) String productType){
        System.out.println("productCode:"+productCode);
        System.out.println("productType:"+productType);
        System.out.println("productName:"+productName);
        // 从前端传过来的参数只能 productCode 或 productName 二选一
        // 当 productCode 不为空时，则判断为通过代码搜索
        // 当 productName 不为空时，则判断为通过名称搜索
        if(!productCode.equals("") && productName.equals("")){
            QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
            queryWrapper.like("productCode",productCode);
            queryWrapper.eq("productType",productType);
            return financialProductService.selectByWrapperReturnList(queryWrapper);
        }else if(productCode.equals("") && !productName.equals("")){
            QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
            queryWrapper.like("productName",productName);
            queryWrapper.eq("productType",productType);
            return financialProductService.selectByWrapperReturnList(queryWrapper);
        }else if(productCode.equals("")&&productName.equals("")){
            /** 当搜索条件为空时，搜索全部 */
            QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("productType",productType);
            return financialProductService.selectByWrapperReturnList(queryWrapper);
        }else{
            return null;
        }
    }

    /**
     * 对比项目（查找项目详情、收益）
     * @param productCode
     * @return
     * @throws ParseException
     */
    @GetMapping("selectCompare")
    public Map<String,Object> selectCompare(@RequestParam(value = "productCode") String productCode) throws ParseException {
        /** 声明 resultMap */
        Map<String,Object> resultMap=new HashMap<String,Object>(10);

        /** 查询 financialProduct （return bean）: productCode */
        QueryWrapper<FinancialProduct> financialProductQueryWrapper=new QueryWrapper<>();
        financialProductQueryWrapper.eq("productCode",productCode);
        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(financialProductQueryWrapper);

        String productType=financialProduct.getProductType();
        int popularity=financialProduct.getPopularity();
//        Date dateOfEstablishment=financialProduct.getDateOfEstablishment();
        String riskType=financialProduct.getRiskType();

        /** 日期格式 -> String标准格式 */
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date oldDate=df.parse(df.format(financialProduct.getDateOfEstablishment()));
        Date newDate=df.parse(df.format(new Date()));

        /** 计算日期差值 */
        Long dayOfLess=dateOprate.dayOfLessDate(oldDate,newDate);

        /** 声明：dayEarn/oneMonthEarn/threeMonthEarn/sixMonthEarn/oneYearEarn/threeYearEarn */
        float dayEarn=0;
        String oneMonthEarn=null;
        String threeMonthEarn=null;
        String sixMonthEarn=null;
        String oneYearEarn=null;
        String threeYearEarn=null;

        /** 声明：天数对应的日期（以月份最大值来计算，保证无空值） */
        int oneMonth=31;
        // 三月最大值：31+31+30 = 62
        int threeMonth=62;
        // 六月最大值：31+30+31+30+31+30 = 183
        int sixMonth=183;
        int oneYear=366;
        // 三年最大值：366+365+365 = 1096
        int threeYear=1096;

        /** 当资产发布 1 个月内的话，显示无任何收益率 */
        if(dayOfLess>oneMonth && dayOfLess<=threeMonth){
            oneMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,1));
        }else if(dayOfLess>threeMonth && dayOfLess<=sixMonth){
            oneMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,1));
            threeMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,3));
        }else if(dayOfLess>sixMonth && dayOfLess<=oneYear){
            oneMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,1));
            threeMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,3));
            sixMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,6));
        }else if(dayOfLess>oneYear && dayOfLess<=threeYear){
            oneMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,1));
            threeMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,3));
            sixMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,6));
            oneYearEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,12));
        }else if(dayOfLess>threeYear){
            oneMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,1));
            threeMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,3));
            sixMonthEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,6));
            oneYearEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,12));
            threeYearEarn=Float.toString(updateEarn.getEarnRate(productCode,productType,36));
        }

        resultMap.put("productName",financialProduct.getProductName());
        resultMap.put("productType",financialProduct.getProductType());
//        resultMap.put("dayEarn",dayEarn);
        resultMap.put("oneMonthEarn",oneMonthEarn);
        resultMap.put("threeMonthEarn",threeMonthEarn);
        resultMap.put("sixMonthEarn",sixMonthEarn);
        resultMap.put("oneYearEarn",oneYearEarn);
        resultMap.put("threeYearEarn",threeYearEarn);
        resultMap.put("riskType",riskType);
        resultMap.put("dateOfEstablishment",oldDate);
        resultMap.put("popularity",popularity);

        return resultMap;
    }

    /**
     * 资产推荐（股票、基金、黄金）
     * @param productType
     * @return
     */
    @GetMapping("selectRecommendAssets")
    public List selectRecommendAssets(@RequestParam(value = "productType", required=false ) String productType) throws ParseException {
        /** 声明结果列表、存放发布日期一年（三年）以上的项目 */
        List resultList=new ArrayList();
        List oneYearList=new ArrayList();
        List threeYearList=new ArrayList();

        /** 声明 Map */
        Map<String,Object> oneMap = new HashMap<String,Object>(10);
        Map<String,Object> threeMap = new HashMap<String,Object>(10);

        /** 声明：变量存放一年收益最高的资产信息 */
        String oneProductCode=null;
        String oneProductName=null;
        float oneProductEarnRate=-999;

        /** 声明：变量存放三年收益最高的资产信息 */
        String threeProductCode=null;
        String threeProductName=null;
        float threeProductEarnRate=-999;

        /** 查询： List<financialProduct>，get：发布日期、productCode（状态：在市、productType：productType） */
        QueryWrapper<FinancialProduct> financialProductQueryWrapper=new QueryWrapper<>();
        financialProductQueryWrapper.eq("listingStatus","在市");
        financialProductQueryWrapper.eq("productType",productType);
        List<FinancialProduct> financialProductList=financialProductService.selectByWrapperReturnList(financialProductQueryWrapper);

        /**
         * 将符合要求的资产按 一年 & 三年 进行分类存进 List
         */
        for(int i=0;i<financialProductList.size();i++){
            System.out.println("-------------");
            /** 日期格式 -> String标准格式 */
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            Date oldDate=df.parse(df.format(financialProductList.get(i).getDateOfEstablishment()));
            Date newDate=df.parse(df.format(new Date()));

            /** 计算两天之差 */
            Long timeDifference=dateOprate.dayOfLessDate(oldDate,newDate);

            // 声明天数
            int oneYear=365;
            int threeYear=1095;

            /** 分年存放符合要求的资产 productCode */
            if(timeDifference>=oneYear && timeDifference<threeYear){
                oneYearList.add(financialProductList.get(i).getProductCode());
            }else if(timeDifference>=threeYear) {
                /** 拥有三年以上的资产也可以在一年收益率中体现 */
                threeYearList.add(financialProductList.get(i).getProductCode());
                oneYearList.add(financialProductList.get(i).getProductCode());
            }

            /**
             * 遍历 一年List
             * 声明 tempProductCode、tempProductName、tempProductEarnRate = -999
             * 一年收益率 = 计算一年收益率的方法（productCode,productType,12）
             * if(收益率 > tempProductEarnRate){
             *     tempProductEarnRate = 收益率
             *     temp... = ...
             * }
             * oneMap.put("tempProductCode",...)
             * oneMap.put(...)
             */

            System.out.println("发布日期："+oldDate);
            System.out.println("今天日期："+newDate);
            System.out.println("相差："+timeDifference+"天");
            System.out.println("oneList"+oneYearList);
            System.out.println("threeList"+threeYearList);
        }

        /** 遍历：List（一年的收益率），进行比较赋值 */
        for(int j=0;j<oneYearList.size();j++){
            System.out.println("(String) oneYearList.get(j)"+oneYearList.get(j));
            float oneYearRate=updateEarn.getEarnRate((String) oneYearList.get(j),productType,12);

            if(oneYearRate>oneProductEarnRate){
                /** 给收益率、produtCode 赋值 */
                oneProductEarnRate=oneYearRate;
                oneProductCode=(String)oneYearList.get(j);
                /** 根据 productCode 查找资产名称 */
                QueryWrapper<FinancialProduct> wrapper=new QueryWrapper<>();
                wrapper.eq("productCode",oneProductCode);
                oneProductName=financialProductService.selectByWrapperReturnBean(wrapper).getProductName();
            }
        }
        /** 一年前收益：将 Max 值的变量存放进 map 中 */
        oneMap.put("oneProductEarnRate",oneProductEarnRate);
        oneMap.put("oneProductCode",oneProductCode);
        oneMap.put("oneProductName",oneProductName);


        /** 遍历：List（三年的收益率），进行比较赋值 */
        for(int z=0;z<threeYearList.size();z++){
            System.out.println("(String) threeYearList.get(z)"+threeYearList.get(z));
            float threeYearRate=updateEarn.getEarnRate((String) threeYearList.get(z),productType,36);

            if(threeYearRate>threeProductEarnRate){
                /** 给收益率、produtCode 赋值 */
                threeProductEarnRate=threeYearRate;
                threeProductCode=(String)threeYearList.get(z);
                /** 根据 productCode 查找资产名称 */
                QueryWrapper<FinancialProduct> wrapper=new QueryWrapper<>();
                wrapper.eq("productCode",threeProductCode);
                threeProductName=financialProductService.selectByWrapperReturnBean(wrapper).getProductName();
            }
        }
        /** 三年前收益：将 Max 值的变量存放进 map 中 */
        threeMap.put("threeProductEarnRate",threeProductEarnRate);
        threeMap.put("threeProductCode",threeProductCode);
        threeMap.put("threeProductName",threeProductName);


        /** 将一年/三年 map 存进 List 中*/
        resultList.add(oneMap);
        resultList.add(threeMap);

        return resultList;
        /**
         * 声明：空 List（一年）
         * 声明：空 List（三年）
         * 遍历查询出 financialProduct ，get：发布日期、productCode（状态：在市、productType：productType）
         * get：现在的系统时间
         * 时间差 = 发布日期 - 系统时间
         * if(时间差 > 1 年){
         *     一年 List.add = 该资产 productCode
         * }else if(时间差 > 3 年){
         *     三年 List.add = 该资产 productCode
         * }
         *
         * 声明 oneMap
         * 声明 threeMap
         * 声明 resultList
         *
         * 遍历 一年List
         * 设置 tempProductCode、tempProductName、tempProductEarnRate = -999、tempProductCode
         * 一年收益率 = 计算一年收益率的方法（productCode,productType,12）
         * if(收益率 > tempProductEarnRate){
         *     tempProductEarnRate = 收益率
         *     temp... = ...
         * }
         * oneMap.put("tempProductCode",...)
         * oneMap.put(...)
         *
         *
         * 遍历 三年List
         * 设置 tempProductCode、tempProductName、tempProductEarnRate = -999、tempProductCode
         * 三年收益率 = 计算三年收益率的方法（productCode,productType,36）
         * -------- 方法：读取一年前的净值（如果是周末则往后顺延一天）-> 收益率 = （最新净值 - 一年前净值）/一年前净值 -> 返回收益率
         * if(收益率 > tempProductEarnRate){
         *     tempProductEarnRate = 收益率
         *     temp... = ...
         * }
         * threeMap.put("tempProductCode",...)
         * threeMap.put(...)
         *
         * resultList.add(oneMap)
         * resultList.add(threeMap)
         *
         * return resultList;
         */
    }

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

    @GetMapping("isExist")
    public boolean isExist(@RequestParam(value = "productName", required=false ) String productName,
                       @RequestParam(value = "productCode", required=false ) String productCode){
        /** 声明两个条件，分别存放 productCode & productName */
        QueryWrapper<FinancialProduct> isExistWrapperByCode=new QueryWrapper<>();
        QueryWrapper<FinancialProduct> isExistWrapperByName=new QueryWrapper<>();

        isExistWrapperByCode.eq("productCode",productCode);
        isExistWrapperByName.eq("productName",productName);

        int countByCode=financialProductService.selectCount(isExistWrapperByCode);
        int countByName=financialProductService.selectCount(isExistWrapperByName);

        /** 查看项目是否在数据库中已经存在 => 参数：产品代码 & 产品名称 */
        if(countByCode==0 && countByName==0){
            System.out.println("true");
            return true;
        }else {
            // 在数据库中有重复
            System.out.println("false");
            return false;
        }
    }

    /**
     * 后台管理系统新增项目
     * @param bean
     * @return
     */
    @PostMapping("insert")
    @Override
    public int insert(@RequestBody(required = false) FinancialProduct bean) {
        String productCode=bean.getProductCode();

            // 增加 financialProduct 表记录
            financialProductService.insert(bean);

            String productType=bean.getProductType();
            if(productType.equals("股票")){
                // 获取产品代码、收益日期（第一天发布日期）、初始涨跌幅
//         String productCode=bean.getProductCode();
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
//            String productCode=bean.getProductCode();
                Date earningsDate=bean.getDateOfEstablishment();
                float dailyChange=0;

                // 获取发行价格
                QueryWrapper<Fund> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("productCode",productCode);
                Fund fund=fundService.selectByWrapperReturnBean(queryWrapper);
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
//            String productCode=bean.getProductCode();
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

            /** 查询最新涨跌幅（productType,productCode） */
            float dailyChange=fundEarningsMapper.selectLastOneEarnings(productCode).getDailyChange();
            map.put("dailyChange",dailyChange);
        }else if(financialProduct.getProductType().equals("黄金")){
            QueryWrapper<Gold> goldQueryWrapper=new QueryWrapper<>();
            goldQueryWrapper.eq("productCode",productCode);
            Gold gold=goldService.selectByWrapperReturnBean(goldQueryWrapper);
            map.put("gold",gold);

            /** 查询最新涨跌幅（productType,productCode） */
            float dailyChange=goldEarningsMapper.selectLastOneEarnings(productCode).getDailyChange();
            map.put("dailyChange",dailyChange);
        }else if(financialProduct.getProductType().equals("定期")){
            QueryWrapper<Regular> regularQueryWrapper=new QueryWrapper<>();
            regularQueryWrapper.eq("productCode",productCode);
            Regular regular=regularService.selectByWrapperReturnBean(regularQueryWrapper);
            map.put("regular",regular);

            // TODO 加上定期的涨跌幅
            /** 查询最新涨跌幅（productType,productCode） */
            map.put("dailyChange",0);
        }else if(financialProduct.getProductType().equals("股票")){
            QueryWrapper<Stock> stockQueryWrapper=new QueryWrapper<>();
            stockQueryWrapper.eq("productCode",productCode);
            Stock stock=stockService.selectByWrapperReturnBean(stockQueryWrapper);
            map.put("stock",stock);

            /** 查询最新涨跌幅（productType,productCode） */
            float dailyChange=stockEarningsMapper.selectLastStockEarnings(productCode).getDailyChange();
            map.put("dailyChange",dailyChange);
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
