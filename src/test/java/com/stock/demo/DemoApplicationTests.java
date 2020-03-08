package com.stock.demo;

import com.stock.demo.mapper.StockEarningsMapper;
import com.stock.demo.mapper.StockMapper;
import com.stock.demo.mapper.UserMapper;
import com.stock.demo.pojo.Stock;
import com.stock.demo.pojo.StockEarnings;
import com.stock.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.stock.demo.util.SimulateEarnings;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockEarningsMapper stockEarningsMapper;

    @Test
    void contextLoads() {
    }

    /**
     * 模拟股票涨跌
     * Simulate the rise and fall of stocks
     * TODO:查询理财产品总表，获取代码列表，再传进方法中去一一查询并进行操作
     */
    @Test
    public void insertStockEarnings(){
        SimulateEarnings simulateEarnings=new SimulateEarnings();
        StockEarnings stockEarnings=stockEarningsMapper.selectLastStockEarnings("000000");
        for (int i=0;i<20;i++){
            System.out.println("今日涨跌幅"+simulateEarnings.simulateDailyChange(5));
            System.out.println("--------------------------------------------");
        }

//        System.out.println("昨日股价:"+stockEarnings.getStockMarketValue());
//        System.out.println("昨日涨跌幅:"+stockEarnings.getDailyChange());
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
