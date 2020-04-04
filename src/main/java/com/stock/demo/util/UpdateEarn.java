package com.stock.demo.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.mapper.FundEarningsMapper;
import com.stock.demo.mapper.GoldEarningsMapper;
import com.stock.demo.mapper.StockEarningsMapper;
import com.stock.demo.mapper.StockMapper;
import com.stock.demo.pojo.*;
import com.stock.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/4
 * Time: 21:33
 * Description:
 */
@Component
public class UpdateEarn {

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
     * 更新：个人资产中的今日收益
     */
    public void updateDayEarn(){
        DecimalFormat dfTwo =new DecimalFormat("#0.00");
        DecimalFormat dfFour =new DecimalFormat("#0.0000");
        /** 查找：个人资产表所有记录 */
        QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
        // 0：在市
        wrapper.eq("status",0);
        List<PersonalFinancialAssets> personalFinancialAssetsList=personalFinancialAssetsService.selectByWrapperReturnList(wrapper);

        /** 遍历：根据 List 中的 productCode 去查找对应的净值 */
        for(int i=0;i<personalFinancialAssetsList.size();i++){
            // 提取 productCode
            String productCode=personalFinancialAssetsList.get(i).getProductCode();
            // 个人项目ID
            Long personalFinancialAssetsID=personalFinancialAssetsList.get(i).getPersonalFinancialAssetsID();

            /** 根据 productCode 查找产品类型以调用不同 service */
            QueryWrapper<FinancialProduct> financialProductQueryWrapper=new QueryWrapper<>();
            financialProductQueryWrapper.eq("productCode",productCode);
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(financialProductQueryWrapper);
            String productType=financialProduct.getProductType();

            if(productType.equals("股票")){
                /** 获取最后一条和倒数第二条收益记录的净值 */
                StockEarnings stockEarningsLastOne=stockEarningsMapper.selectLastStockEarnings(productCode);
                StockEarnings stockEarningsLastTwo=stockEarningsMapper.selectLastTwoStockEarnings(productCode);

                /** 今日收益 = （最新净值 - 倒数第二条净值） * 份额 */
                float lastOneWorth=stockEarningsLastOne.getStockMarketValue();
                float lastTwoWorth=stockEarningsLastTwo.getStockMarketValue();
                // 份额
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                // 今日收益保留两位小数
                float dayEarn=Float.parseFloat(dfTwo.format((lastOneWorth-lastTwoWorth)*amountOfAssets));
                System.out.println("最新净值："+lastOneWorth);
                System.out.println("倒数第二条："+lastTwoWorth);
                System.out.println("股票"+productCode+"dayEarn:"+dayEarn);
                System.out.println("---------------------------------------");
                // TODO： update 个人资产 dayEarn
            }else if(productType.equals("黄金")){
                GoldEarnings goldEarningsLastOne=goldEarningsMapper.selectLastStockEarnings(productCode);
                GoldEarnings goldEarningsLastTwo=goldEarningsMapper.selectLastTwoStockEarnings(productCode);

                /** 今日收益 = （最新净值 - 倒数第二条净值） * 份额 */
                float lastOneWorth=goldEarningsLastOne.getGoldPrice();
                float lastTwoWorth=goldEarningsLastTwo.getGoldPrice();
                // 份额
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                // 今日收益保留两位小数
                float dayEarn=Float.parseFloat(dfTwo.format((lastOneWorth-lastTwoWorth)*amountOfAssets));
                System.out.println("最新净值："+lastOneWorth);
                System.out.println("倒数第二条："+lastTwoWorth);
                System.out.println("黄金"+productCode+"dayEarn:"+dayEarn);
                System.out.println("---------------------------------------");
            }else if(productType.equals("基金")){
                FundEarnings fundEarningsLastOne=fundEarningsMapper.selectLastStockEarnings(productCode);
                FundEarnings fundEarningsLastTwo=fundEarningsMapper.selectLastTwoStockEarnings(productCode);

                /** 今日收益 = （最新净值 - 倒数第二条净值） * 份额 */
                float lastOneWorth=fundEarningsLastOne.getNetWorth();
                float lastTwoWorth=fundEarningsLastTwo.getNetWorth();
                // 份额
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                // 今日收益保留两位小数
                float dayEarn=Float.parseFloat(dfTwo.format((lastOneWorth-lastTwoWorth)*amountOfAssets));
                System.out.println("最新净值："+lastOneWorth);
                System.out.println("倒数第二条："+lastTwoWorth);
                System.out.println("基金"+productCode+"dayEarn:"+dayEarn);
                System.out.println("---------------------------------------");
            }
        }

    }
}
