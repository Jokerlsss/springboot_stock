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
    //TODO:当今日为非工作日时，收益为0
    // TODO: 由于非工作日在收益表中无记录，因此最新净值读不到为0的值，考虑前端名称改为 “最新收益”
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
                System.out.println("股票:"+productCode+" dayEarn:"+dayEarn);
                System.out.println("---------------------------------------");

                /** 赋值：将新的日收益赋值给 bean  条件：personalFinancialAssetsID */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);

                personalFinancialAssetsList.get(i).setDayEarn(dayEarn);
                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);
                // TODO： update 个人资产 dayEarn
            }else if(productType.equals("黄金")){
                GoldEarnings goldEarningsLastOne=goldEarningsMapper.selectLastOneEarnings(productCode);
                GoldEarnings goldEarningsLastTwo=goldEarningsMapper.selectLastTwoEarnings(productCode);

                /** 今日收益 = （最新净值 - 倒数第二条净值） * 份额 */
                float lastOneWorth=goldEarningsLastOne.getGoldPrice();
                float lastTwoWorth=goldEarningsLastTwo.getGoldPrice();
                // 份额
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                // 今日收益保留两位小数
                float dayEarn=Float.parseFloat(dfTwo.format((lastOneWorth-lastTwoWorth)*amountOfAssets));
                System.out.println("最新净值："+lastOneWorth);
                System.out.println("倒数第二条："+lastTwoWorth);
                System.out.println("黄金:"+productCode+" dayEarn:"+dayEarn);
                System.out.println("---------------------------------------");

                /** 更新今日收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);

                personalFinancialAssetsList.get(i).setDayEarn(dayEarn);
                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);

            }else if(productType.equals("基金")){
                FundEarnings fundEarningsLastOne=fundEarningsMapper.selectLastOneEarnings(productCode);
                FundEarnings fundEarningsLastTwo=fundEarningsMapper.selectLastTwoEarnings(productCode);

                /** 今日收益 = （最新净值 - 倒数第二条净值） * 份额 */
                float lastOneWorth=fundEarningsLastOne.getNetWorth();
                float lastTwoWorth=fundEarningsLastTwo.getNetWorth();
                // 份额
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                // 今日收益保留两位小数
                float dayEarn=Float.parseFloat(dfTwo.format((lastOneWorth-lastTwoWorth)*amountOfAssets));
                System.out.println("最新净值："+lastOneWorth);
                System.out.println("倒数第二条："+lastTwoWorth);
                System.out.println("基金:"+productCode+" dayEarn:"+dayEarn);
                System.out.println("---------------------------------------");

                /** 更新今日收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);

                personalFinancialAssetsList.get(i).setDayEarn(dayEarn);
                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);
            }
        }
    }

    /**
     * 更新 持有收益：holdEarn
     */
    public void updateHoldEarn(){
        DecimalFormat dfTwo =new DecimalFormat("#0.00");
        DecimalFormat dfFour =new DecimalFormat("#0.0000");
        /** 查找：个人资产表所有记录 */
        QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
        // 0：在市
        wrapper.eq("status",0);
        List<PersonalFinancialAssets> personalFinancialAssetsList=personalFinancialAssetsService.selectByWrapperReturnList(wrapper);

        for(int i=0;i<personalFinancialAssetsList.size();i++){
            // 获取 productCode
            String productCode=personalFinancialAssetsList.get(i).getProductCode();
            // 获取 个人资产ID
            Long personalFinancialAssetsID=personalFinancialAssetsList.get(i).getPersonalFinancialAssetsID();

            /** 根据 productCode 查找产品类型以调用不同 service */
            QueryWrapper<FinancialProduct> financialProductQueryWrapper=new QueryWrapper<>();
            financialProductQueryWrapper.eq("productCode",productCode);
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(financialProductQueryWrapper);
            String productType=financialProduct.getProductType();

            if(productType.equals("股票")){
                /** 获取最后一条收益记录的净值 */
                StockEarnings stockEarningsLastOne=stockEarningsMapper.selectLastStockEarnings(productCode);
                float lastOneWorth=stockEarningsLastOne.getStockMarketValue();

                /** 获取持仓成本 */
                float holdingCost=personalFinancialAssetsList.get(i).getHoldingCost();
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                // TODO: 将 成本变为每一股的成本(holdingCost/amountOfAssets)
                // 考虑到未来可能买入和卖出，不能取购入当天的净值。而应根据份额和成本来计算
                /** 购入价格 */
                float buyWorth=holdingCost/amountOfAssets;
                /** 持有收益 = （今天净值 - 持仓成本（单价）） * 份额 */
                // 保留两位小数
                float holdEarn=Float.parseFloat(dfTwo.format((lastOneWorth-buyWorth)*amountOfAssets));

                /** 计算最新拥有资产 */
                float holdAssets=countAssets(holdingCost,holdEarn);
                /** 更新持有收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
                personalFinancialAssetsList.get(i).setHoldEarn(holdEarn);
                // 更新拥有资产
                personalFinancialAssetsList.get(i).setHoldAssets(holdAssets);
                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);

                System.out.println("holdEarn:"+holdEarn);
            }else if(productType.equals("基金")){
                FundEarnings fundEarningsLastOne=fundEarningsMapper.selectLastOneEarnings(productCode);
                float lastOneWorth=fundEarningsLastOne.getNetWorth();

                /** 获取持仓成本 */
                float holdingCost=personalFinancialAssetsList.get(i).getHoldingCost();
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();

                // 考虑到未来可能买入和卖出，不能取购入当天的净值。而应根据份额和成本来计算
                /** 购入价格 */
                float buyWorth=holdingCost/amountOfAssets;

                /** 持有收益 = （今天净值 - 持仓成本） * 份额 */
                float holdEarn=Float.parseFloat(dfTwo.format((lastOneWorth-buyWorth)*amountOfAssets));

                /** 计算最新拥有资产 */
                float holdAssets=countAssets(holdingCost,holdEarn);

                /** 更新持有收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
                personalFinancialAssetsList.get(i).setHoldEarn(holdEarn);
                // 更新拥有资产
                personalFinancialAssetsList.get(i).setHoldAssets(holdAssets);
                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);

                System.out.println("holdEarn:"+holdEarn);
            }else if(productType.equals("黄金")){
                GoldEarnings goldEarningsLastOne=goldEarningsMapper.selectLastOneEarnings(productCode);
                float lastOneWorth=goldEarningsLastOne.getGoldPrice();

                /** 获取持仓成本 */
                float holdingCost=personalFinancialAssetsList.get(i).getHoldingCost();
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                // TODO: 将 成本变为每一股的成本(holdingCost/amountOfAssets)
                // 考虑到未来可能买入和卖出，不能取购入当天的净值。而应根据份额和成本来计算
                /** 购入价格 */
                float buyWorth=holdingCost/amountOfAssets;
                /** 持有收益 = （今天净值 - 持仓成本） * 份额 */
                float holdEarn=Float.parseFloat(dfTwo.format((lastOneWorth-buyWorth)*amountOfAssets));

                /** 计算最新拥有资产 */
                float holdAssets=countAssets(holdingCost,holdEarn);

                /** 更新持有收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
                personalFinancialAssetsList.get(i).setHoldEarn(holdEarn);
                // 更新拥有资产
                personalFinancialAssetsList.get(i).setHoldAssets(holdAssets);
                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);

                System.out.println("holdEarn:"+holdEarn);
            }
        }
    }

    /**
     * 更新 资产
     * 资产 = 持有成本 + 持有收益
     * @return
     */
    public float countAssets(float holdingCost,float holdEarn){
        float assets=holdingCost+holdEarn;
        return assets;
    }
}
