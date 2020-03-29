package com.stock.demo.util;

import com.alibaba.fastjson.serializer.CalendarCodec;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.pojo.FinancialProduct;
import com.stock.demo.pojo.FundEarnings;
import com.stock.demo.pojo.GoldEarnings;
import com.stock.demo.pojo.StockEarnings;
import com.stock.demo.service.FinancialProductService;
import com.stock.demo.service.FundEarningsService;
import com.stock.demo.service.GoldEarningsService;
import com.stock.demo.service.StockEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/27
 * Time: 12:01
 * Description: About oprate of date
 */
@Component("DateOprate")
public class DateOprate {
    @Autowired
    private FinancialProductService financialProductService;

    @Autowired
    private FundEarningsService fundEarningsService;

    @Autowired
    private GoldEarningsService goldEarningsService;

    @Autowired
    private StockEarningsService stockEarningsService;
    /**
     * 判断是否为工作日
     * @param date (String)
     * @return boolean
     * @throws ParseException
     */
    public boolean isWorkDay(Date date) throws ParseException {
        boolean workDay=true;
        boolean weekDay=false;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断是否为工作日：当不等于周六且不等于周日时
        if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
//            System.out.println(cal.get(Calendar.DAY_OF_WEEK));
            return workDay;
        } else{
//            System.out.println(cal.get(Calendar.DAY_OF_WEEK));
            return weekDay;
        }
    }

    /**
     * 判断日期是否为今天，是今天：返回true，不是今天：返回false
     * @param date
     * @return
     */
    public boolean isTomorrowDay(Date date){
        // 返回 boolean 类型，让调用该方法的地方去进行循环，并加 1 天即可
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 求出明天的日期
        Date todayDate=new Date();
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(todayDate);
        // 在今天的基础上加一天=明天
        gc.add(5,1);
        gc.getTime();

        // 转换当前日期格式为 String 方便比对，格式化如：2020-01-01
        String tomorrowDate = formatter.format(gc.getTime());
        String paramDate=formatter.format(date);

        // 是明天的话，则停止生成，故返回false，反之亦然
        if(tomorrowDate.equals(paramDate)){
            System.out.println(paramDate+"是明天");
            return true;
        }else{
            System.out.println(paramDate+"不是明天");
            return false;
        }
    }


    public void countEarnings(String productCode,Float lastDailyChange,Date lastEarningsDate,Float lastMarketValue,String productType) throws ParseException {
        // 声明：日期操作工具类
        DateOprate dateOprate=new DateOprate();
        // 声明：模拟股票涨跌算法工具类
        SimulateEarnings simulateEarnings=new SimulateEarnings();

        // 查出对应的项目发布日期
        QueryWrapper<FinancialProduct> beanQueryWrapper=new QueryWrapper<>();
        beanQueryWrapper.eq("productCode",productCode);

        // 制定日期格式
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

        // 判断传入日期是否为今天，若不是则继续增加 1 天，直到加到今天则停止
        int addDay=1;

        // 声明：日期类供日期加减
        // ---------------gc.add()----------------
        // value为正则往后,为负则往前
        // field取1加1年,取2加半年,取3加一季度,取4加一周
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(lastEarningsDate);
        gc.add(5,addDay);

        // 把时间进行计算(Long类型) -> 转换成 String 类型 -> 转换成 Date 类型
        // 先加一天进行判断，如果需要继续加天数时，则在 while 末尾加 gc.add(5,addDay) 进行累加
        while(!dateOprate.isTomorrowDay(df.parse(df.format(gc.getTime())))){
            // 将增加后的天数判断是否为工作日
            if(dateOprate.isWorkDay(df.parse(df.format(gc.getTime())))){
                // 调用：模拟算法  参数：上次涨跌幅  返回值：float 类型涨跌幅
                float nowDailyChange=simulateEarnings.simulateDailyChange(lastDailyChange);

                System.out.println(df.format(gc.getTime())+"工作日");
                System.out.println(df.format(gc.getTime())+"涨跌幅为："+nowDailyChange+"%");
                // 生成：本次净值 = 上次净值 + 上次净值 * 本次涨跌幅
                // TODO：本次净值：股票（2位小数）  基金（4位小数）  黄金（2位小数）
                DecimalFormat dfTwo =new DecimalFormat("#0.00");
                DecimalFormat dfFour =new DecimalFormat("#0.0000");
                // 根据类型调用不同的service层来新增记录
                if(productType.equals("股票")){
                    // 保留两位小数
                    float nowMarketValue=Float.parseFloat(dfTwo.format(lastMarketValue+(lastMarketValue*nowDailyChange)/100));

                    // 将各字段信息存进实体类中
                    StockEarnings stockEarnings=new StockEarnings();
                    stockEarnings.setProductCode(productCode);
                    stockEarnings.setEarningsDate(gc.getTime());
                    stockEarnings.setStockMarketValue(nowMarketValue);
                    stockEarnings.setDailyChange(nowDailyChange);

                    System.out.println(stockEarnings);

                    //TODO 空指针异常
                    stockEarningsService.insert(stockEarnings);
                    System.out.println("本次净值："+nowMarketValue);
                    System.out.println("-----------------------------");
                }else if(productType.equals("黄金")){
                    float nowMarketValue=Float.parseFloat(dfTwo.format(lastMarketValue+(lastMarketValue*nowDailyChange)/100));

                    // 将各字段信息存进实体类中
                    GoldEarnings goldEarnings=new GoldEarnings();
                    goldEarnings.setProductCode(productCode);
                    goldEarnings.setEarningsDate(gc.getTime());
                    goldEarnings.setGoldPrice(nowMarketValue);
                    goldEarnings.setDailyChange(nowDailyChange);

                    goldEarningsService.insert(goldEarnings);

                    System.out.println("本次净值："+nowMarketValue);
                    System.out.println("-----------------------------");
                }else if(productType.equals("基金")){
                    float nowMarketValue=Float.parseFloat(dfFour.format(lastMarketValue+(lastMarketValue*nowDailyChange)/100));

                    // 将各字段信息存进实体类中
                    FundEarnings fundEarnings=new FundEarnings();
                    fundEarnings.setProductCode(productCode);
                    fundEarnings.setEarningsDate(gc.getTime());
                    fundEarnings.setNetWorth(nowMarketValue);
                    fundEarnings.setDailyChange(nowDailyChange);

                    fundEarningsService.insert(fundEarnings);

                    System.out.println("本次净值："+nowMarketValue);
                    System.out.println("-----------------------------");
                }
            }else{
                System.out.println(df.format(gc.getTime())+"周末");
                System.out.println("-----------------------------");
            }
            gc.add(5,addDay);
        }
    }
}
