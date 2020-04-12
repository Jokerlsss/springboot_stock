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
        public void updateDayEarn(Long financialAssetsID){
            DecimalFormat dfTwo =new DecimalFormat("#0.00");
            DecimalFormat dfFour =new DecimalFormat("#0.0000");
        /** 查找：个人资产表所有记录（无ID参数时）  个人资产表单记录（有ID参数时） */
        QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
        // 0：在市
            /** 如果ID为null，则是更新全部记录 */
            if(financialAssetsID==null){
                wrapper.eq("status",0);
            }else{
                wrapper.eq("personalFinancialAssetsID",financialAssetsID);
            }
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
                System.out.println("这里是份额没被清零的地方，amount值为："+amountOfAssets);
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
            }else if(productType.equals("黄金")){
                GoldEarnings goldEarningsLastOne=goldEarningsMapper.selectLastOneEarnings(productCode);
                GoldEarnings goldEarningsLastTwo=goldEarningsMapper.selectLastTwoEarnings(productCode);

                /** 今日收益 = （最新净值 - 倒数第二条净值） * 份额 */
                float lastOneWorth=goldEarningsLastOne.getGoldPrice();
                float lastTwoWorth=goldEarningsLastTwo.getGoldPrice();
                // 份额
                float amountOfAssets=personalFinancialAssetsList.get(i).getAmountOfAssets();
                System.out.println("这里是份额没被清零的地方，amount值为："+amountOfAssets);
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
                System.out.println("这里是份额没被清零的地方，amount值为："+amountOfAssets);
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
     * 更新 累计收益：totalEarn
     * 更新 持有资产：holdAssets
     *
     * 个人资产ID无值时，为全部更新；有值时，为局部更新
     * @param financialAssetsID
     */
    public void updateHoldEarn(Long financialAssetsID){
        DecimalFormat dfTwo =new DecimalFormat("#0.00");
        DecimalFormat dfFour =new DecimalFormat("#0.0000");
        /** 查找：个人资产表所有记录 */
        QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
        // 0：在市

        /** 如果ID为null，则是更新全部记录 */
        if(financialAssetsID==null){
            wrapper.eq("status",0);
        }else{
            wrapper.eq("personalFinancialAssetsID",financialAssetsID);
        }
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
                float holdEarn=0;
                if(amountOfAssets!=0){
                    holdEarn=Float.parseFloat(dfTwo.format((lastOneWorth-buyWorth)*amountOfAssets));
                }

                System.out.println("这里是持有收益没被清零的地方，holdEarn值为："+holdEarn);

                /** 计算最新拥有资产 */
                float holdAssets=countAssets(lastOneWorth,amountOfAssets);

                /** 更新持有收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
                personalFinancialAssetsList.get(i).setHoldEarn(holdEarn);
                // 更新拥有资产
                personalFinancialAssetsList.get(i).setHoldAssets(holdAssets);

                /** 更新累计收益 */
                // 查询赎回资产
                float redemptionOfAssets=personalFinancialAssetsList.get(i).getRedemptionOfAssets();
                // 计算累计收益
                float totalEarn=updateTotalEarn(holdAssets,redemptionOfAssets,holdingCost);
                // 更新累计收益
                personalFinancialAssetsList.get(i).setTotalEarn(totalEarn);

                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);
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
                float holdEarn=0;
                if(amountOfAssets!=0){
                    holdEarn=Float.parseFloat(dfTwo.format((lastOneWorth-buyWorth)*amountOfAssets));
                }
                System.out.println("这里是持有收益没被清零的地方，holdEarn值为："+holdEarn);
                /** 计算最新拥有资产 */
                float holdAssets=countAssets(lastOneWorth,amountOfAssets);

                /** 更新持有收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
                personalFinancialAssetsList.get(i).setHoldEarn(holdEarn);
                // 更新拥有资产
                personalFinancialAssetsList.get(i).setHoldAssets(holdAssets);

                /** 更新累计收益 */
                // 查询赎回资产
                float redemptionOfAssets=personalFinancialAssetsList.get(i).getRedemptionOfAssets();
                // 计算累计收益
                float totalEarn=updateTotalEarn(holdAssets,redemptionOfAssets,holdingCost);
                // 更新累计收益
                personalFinancialAssetsList.get(i).setTotalEarn(totalEarn);

                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);
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
                float holdEarn=0;
                if(amountOfAssets!=0){
                    holdEarn=Float.parseFloat(dfTwo.format((lastOneWorth-buyWorth)*amountOfAssets));
                }
                System.out.println("这里是持有收益没被清零的地方，holdEarn值为："+holdEarn);
                /** 计算最新拥有资产 */
                float holdAssets=countAssets(lastOneWorth,amountOfAssets);

                /** 更新持有收益 */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
                personalFinancialAssetsList.get(i).setHoldEarn(holdEarn);
                // 更新拥有资产
                personalFinancialAssetsList.get(i).setHoldAssets(holdAssets);

                /** 更新累计收益 */
                // 查询赎回资产
                float redemptionOfAssets=personalFinancialAssetsList.get(i).getRedemptionOfAssets();
                // 计算累计收益
                float totalEarn=updateTotalEarn(holdAssets,redemptionOfAssets,holdingCost);
                // 更新累计收益
                personalFinancialAssetsList.get(i).setTotalEarn(totalEarn);

                personalFinancialAssetsService.updateByWrapper(personalFinancialAssetsList.get(i),personalFinancialAssetsQueryWrapper);
            }
        }
    }

    /**
     * 卖出时调用
     *
     * 计算 赎回资产
     * 新赎回资产 = 旧赎回资产 + 赎回份额 * 赎回时净值
     * @param redemptionOfAssets 旧赎回资产
     * @param sellAmountOfAssets 赎回份额
     * @param sellWorth 赎回时净值
     * @return
     */
    public float updateRedemptionOfAssets(float redemptionOfAssets,float sellAmountOfAssets,float sellWorth){
        DecimalFormat dfTwo =new DecimalFormat("#0.00");
        /** 新赎回资产 = 旧赎回资产 + 赎回份额 * 赎回时净值 */
        float newRedemptionOfAssets=Float.parseFloat(dfTwo.format(redemptionOfAssets+sellAmountOfAssets*sellWorth));
        return newRedemptionOfAssets;
    }

    /**
     * 计算累计收益
     * 累计收益 = 当前拥有资产 + 赎回资产 - 投入成本
     * @param holdAssets 当前拥有资产
     * @param redemptionOfAssets 赎回资产
     * @param holdingCost 投入成本
     * @return
     */
    public float updateTotalEarn(float holdAssets,float redemptionOfAssets,float holdingCost){
        DecimalFormat dfTwo =new DecimalFormat("#0.00");
        /** 累计收益 = 当前资产 + 赎回资产 - 投入成本 */
        float totalEarn=Float.parseFloat(dfTwo.format(holdAssets+redemptionOfAssets-holdingCost));
        return totalEarn;
    }

    /**
     * 更新 持有资产
     * 持有资产 = 当前净值 * 份额
     * @return
     */
    public float countAssets(float lastOneWorth,float amountOfAssets){
        DecimalFormat dfTwo =new DecimalFormat("#0.00");
        float assets=Float.parseFloat(dfTwo.format(lastOneWorth*amountOfAssets));
        return assets;
    }
}
