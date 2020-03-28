package com.stock.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.mapper.StockEarningsMapper;
import com.stock.demo.mapper.StockMapper;
import com.stock.demo.mapper.UserMapper;
import com.stock.demo.pojo.FinancialProduct;
import com.stock.demo.pojo.Stock;
import com.stock.demo.pojo.StockEarnings;
import com.stock.demo.pojo.User;
import com.stock.demo.service.FinancialProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.stock.demo.util.SimulateEarnings;
import com.stock.demo.util.DateOprate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockEarningsMapper stockEarningsMapper;

    @Autowired
    private FinancialProductService financialProductService;

    @Test
    void contextLoads() {
    }

    /**
     * 模拟股票涨跌
     * Simulate the rise and fall of stocks
     * TODO: selectLastStockEarnings 参数为：产品代码。查询理财产品总表，获取代码列表，再传进方法中去一一查询并进行操作
     * simulateDailyChange 参数为：上次涨跌幅
     */
    @Test
    public void insertStockEarnings() throws ParseException {
        SimulateEarnings simulateEarnings=new SimulateEarnings();
        // selectLastStockEarnings 返回的是 stockEarnings 类型
        StockEarnings stockEarnings=stockEarningsMapper.selectLastStockEarnings("000000");
        Float lastDailyChange=stockEarnings.getDailyChange();
        // TODO: lastDailyChange 作为参数传递进去模拟算法中
        for (int i=0;i<20;i++){
            System.out.println("今日涨跌幅"+simulateEarnings.simulateDailyChange(-5));
            System.out.println("--------------------------------------------");
        }

//        System.out.println("昨日股价:"+stockEarnings.getStockMarketValue());
//        System.out.println("昨日涨跌幅:"+stockEarnings.getDailyChange());
    }
    /**
     * 判断上次更新收益的日期是否为今天，同时判断是否为工作日
     * @throws ParseException
     */
    // TODO: isWorkWeek(productCode) 传入参数 code、上次收益日期、上次收益涨跌幅
//    @Test
//    public void isWorkWeek() throws ParseException {
//        DateOprate dateOprate=new DateOprate();
//
//        // 查出对应的项目发布日期
//        QueryWrapper<FinancialProduct> beanQueryWrapper=new QueryWrapper<>();
//        beanQueryWrapper.eq("productCode",000000);
//        // TODO：根据传进来的产品代码，去查询并判断 productType ，进入不同的 if 子句再查询 earnings
//        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(beanQueryWrapper);
//        Date earnDate=financialProduct.getDateOfEstablishment();
//
//        // 制定日期格式
//        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//
//        // 判断传入日期是否为今天，若不是则继续增加 1 天，直到加到今天则停止
//        int addDay=1;
//
//        // 把时间进行计算(Long类型) -> 转换成 String 类型 -> 转换成 Date 类型
//        while(!dateOprate.isToday(df.parse(df.format(earnDate.getTime() + addDay * 24 * 60 * 60 * 1000)))){
//            // 将增加后的天数判断是否为工作日
//            if(dateOprate.isWorkDay(df.parse(df.format(earnDate.getTime() + addDay * 24 * 60 * 60 * 1000)))){
//                // !TODO: 调用模拟
//                // 按照 在市、类型 为条件参数查找对应的 productCode
//                // TODO 以下注释的这一步衔接上面的函数 insertStockEarnings 中的通过代码新增 earnings 记录
////
//                // TODO: 如果为工作日的话，读取基金、股票、黄金类型的代码，存为不同的 List
//                // TODO: 通过不同类型的 List，根据 List 长度循环，读取不同的 service 以调用上一次的涨跌幅
//                // TODO: 调用模拟涨跌算法，生成下一天的涨跌幅
//                // TODO: 生成的涨跌幅作为新纪录添加进 Earnings 表里
//                System.out.println(df.parse(df.format(earnDate.getTime() + addDay * 24 * 60 * 60 * 1000))+"是工作日");
//                System.out.println("-----------------");
//            }else{
//                System.out.println(df.parse(df.format(earnDate.getTime() + addDay * 24 * 60 * 60 * 1000))+"是周末");
//            }
//            addDay=addDay+1;
//        }
//    }

    /**
     * 新增收益记录
     */
    @Test
    public void insertEarnings() throws ParseException {
        // 声明：日期判断工具类
        DateOprate dateOprate=new DateOprate();

        // 声明：模拟涨跌算法工具类
        SimulateEarnings simulateEarnings=new SimulateEarnings();

        // 查询：financialProduct 代码  返回值：List<financialProduct>
        QueryWrapper<FinancialProduct> listQueryWrapper=new QueryWrapper<>();
        listQueryWrapper.eq("listingStatus","在市");
        List<FinancialProduct> financialProductList=financialProductService.selectByWrapperReturnList(listQueryWrapper);
        System.out.println("size:"+financialProductList.size());
        for(int i=0;i<financialProductList.size();i++){
            if(financialProductList.get(i).getProductType().equals("股票")){
                // 上次收益记录 = 查找上次收益记录(产品代码)
                String productCode=financialProductList.get(i).getProductCode();
                try {
                    // 查询：根据代码查找 earnings 表
                    StockEarnings stockEarnings=stockEarningsMapper.selectLastStockEarnings(productCode);

                    // 赋值：查询到的上次收益记录
                    Float lastDailyChange=stockEarnings.getDailyChange();
                    Date lastEarningsDate=stockEarnings.getEarningsDate();
                    Float lastMarketValue=stockEarnings.getStockMarketValue();
                    // 区分：类型用于在新增时区分调用的 service 层
                    String productType="股票";

                    System.out.println("产品代码："+productCode+"开始计算 ->");
                    // 计算 & 生成：本次收益记录
                    dateOprate.countEarnings(productCode,lastDailyChange,lastEarningsDate,lastMarketValue,productType);
                } catch (ParseException e) {
                    throw e;
                }
            }else if(financialProductList.get(i).getProductType().equals("基金")){
                // TODO: 基金
            }else if(financialProductList.get(i).getProductType().equals("黄金")){
                // TODO: 黄金
            }
        }
    }

    /**
     * 伪代码：如何新增收益记录
     * 新增收益记录（）{
     *     查询 financialProduct 代码  返回值：List<financialProduct>
     *     for(int i=0;i<productCode.size();i++){
     *         if(financialProduct.get[i].getProductType.equals("股票")){
     *             上次收益记录 = stockService.selectLastEarnings(产品代码)
     *             上次收益日期 = 上次收益记录.上次收益日期
     *             上次涨跌幅 = 上次收益记录.上次涨跌幅
     *             上次净值 = 上次收益记录.上次净值
     *             产品代码 = 上次收益记录.产品代码
     *
     *             countEarnings(产品代码，上次收益日期，上次涨跌幅，上次净值){
     *                 while(){
     *                     本次涨跌幅 = 模拟算法(上次涨跌幅)
     *                     本次净值 = 上次净值 + 上次净值 * 本次涨跌幅
     *                     本次收益日期 = 上次收益日期 + 1
     *                     if(股票){
     *                         本次收益记录 = insertStockEarningsService.insert(产品代码，本次涨跌幅，本次净值，本次收益日期)
     *                     }else if(黄金){
     *                         ...
     *                     }else if(基金){
     *                         ...
     *                     }
     *                 }
     *             }
     *         }else if(...基金){
     *
     *         }else if(...黄金){
     *
     *         }
     *     }
     * }
     */

    /**
     * 添加用户
     */
    @Test
    public void insertUser(){
        User user=new User();
        user.setUserName("Jokerls");
        user.setInertmentCharacter("稳健");

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setUserPassword(encoder.encode("123"));
        userMapper.insert(user);
        System.out.println("成功录入用户");
    }
}
