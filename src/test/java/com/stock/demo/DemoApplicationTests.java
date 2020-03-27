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
        StockEarnings stockEarnings=stockEarningsMapper.selectLastStockEarnings("000000");
        for (int i=0;i<20;i++){
            System.out.println("今日涨跌幅"+simulateEarnings.simulateDailyChange(-5));
            System.out.println("--------------------------------------------");
        }

//        System.out.println("昨日股价:"+stockEarnings.getStockMarketValue());
//        System.out.println("昨日涨跌幅:"+stockEarnings.getDailyChange());
    }
    /**
     * 判断是否为工作日
     * @throws ParseException
     */
    @Test
    public void isWorkWeek() throws ParseException {
        DateOprate dateOprate=new DateOprate();

        QueryWrapper<FinancialProduct> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("productCode",000000);
        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(queryWrapper1);
        Date time=financialProduct.getDateOfEstablishment();

        // 加天数
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String paramDate=df.format(time.getTime() + 1 * 24 * 60 * 60 * 1000);

        // 判断传入日期是否为今天，若不是则继续增加 1 天，直到加到今天则停止
        int addDay=1;
        while(!dateOprate.isToday(df.format(time.getTime() + addDay * 24 * 60 * 60 * 1000))){
            addDay=addDay+1;
        }
        // Date -> String
        if(dateOprate.isWorkDay(time)){
            System.out.println(time+"是工作日");
        }else{
            System.out.println(time+"是周末");
        }
    }

    @Test
    public void isToday(){

    }

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
