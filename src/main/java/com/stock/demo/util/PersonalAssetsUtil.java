package com.stock.demo.util;

import ch.qos.logback.classic.boolex.GEventEvaluator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.mapper.FundEarningsMapper;
import com.stock.demo.mapper.GoldEarningsMapper;
import com.stock.demo.mapper.StockEarningsMapper;
import com.stock.demo.pojo.*;
import com.stock.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/12
 * Time: 16:33
 * Description:
 */
@Component
public class PersonalAssetsUtil {

    @Autowired
    private PersonalFinancialAssetsService personalFinancialAssetsService;

    @Autowired
    private FinancialProductService financialProductService;

    @Autowired
    private StockEarningsService stockEarningsService;

    @Autowired
    private FundEarningsService fundEarningsService;

    @Autowired
    private GoldEarningsService goldEarningsService;

    @Autowired
    private StockEarningsMapper stockEarningsMapper;

    @Autowired
    private FundEarningsMapper fundEarningsMapper;

    @Autowired
    private GoldEarningsMapper goldEarningsMapper;

    /**
     * 返回对应日期净值
     * @param productType 资产类型
     * @param date 对应日期
     * @param productCode 资产代码
     * @return
     */
    public float getDateOfValue(String productCode,String productType, Date date){
        /** 声明净值/股价/金价 */
        float worth=0;
        /** 根据类型进入不同 service */
        if(productType.equals("股票")){
            QueryWrapper<StockEarnings> stockWrapper=new QueryWrapper<>();
            // 转换：将日期转换为String类型才可以查询到结果
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
            String dateStirng =  formatter.format(date);

            stockWrapper.eq("earnings_date",dateStirng);
            stockWrapper.eq("productCode",productCode);
            StockEarnings stockEarnings=stockEarningsService.selectByWrapperReturnBean(stockWrapper);

            worth=stockEarnings.getStockMarketValue();
            return worth;
        }else if(productType.equals("基金")){
            QueryWrapper<FundEarnings> fundWrapper=new QueryWrapper<>();
            // 转换：将日期转换为String类型才可以查询到结果
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
            String dateStirng =  formatter.format(date);

            fundWrapper.eq("earningsDate",dateStirng);
            fundWrapper.eq("productCode",productCode);
            FundEarnings fundEarnings=fundEarningsService.selectByWrapperReturnBean(fundWrapper);

            worth=fundEarnings.getNetWorth();
            return worth;
        }else if(productType.equals("黄金")){
            QueryWrapper<GoldEarnings> goldWrapper=new QueryWrapper<>();
            // 转换：将日期转换为String类型才可以查询到结果
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
            String dateStirng =  formatter.format(date);

            goldWrapper.eq("earningsDate",dateStirng);
            goldWrapper.eq("productCode",productCode);
            GoldEarnings goldEarnings=goldEarningsService.selectByWrapperReturnBean(goldWrapper);

            worth=goldEarnings.getGoldPrice();
            return worth;
        }else{
            return worth;
        }
    }
}
