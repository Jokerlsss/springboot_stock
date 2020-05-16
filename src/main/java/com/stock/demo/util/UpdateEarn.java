package com.stock.demo.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.demo.mapper.*;
import com.stock.demo.pojo.*;
import com.stock.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
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
    private PersonalFinancialAssetsMapper personalFinancialAssetsMapper;

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

    @Autowired
    private HistoryEarningsService historyEarningsService;

    @Autowired
    private HistoryEarningsMapper historyEarningsMapper;

    @Autowired
    private UpdateEarn updateEarn;

    @Autowired
    private DateOprate dateOprate;

    /**
     * 新增收益记录
     */
    public void insertEarnings() throws ParseException {
        // 查询：financialProduct 代码  返回值：List<financialProduct>
        QueryWrapper<FinancialProduct> listQueryWrapper=new QueryWrapper<>();
        listQueryWrapper.eq("listingStatus","在市");
        // 排除定期
        listQueryWrapper.ne("productType","定期");

        List<FinancialProduct> financialProductList=financialProductService.selectByWrapperReturnList(listQueryWrapper);
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

                    System.out.println("产品代码："+productCode+" 类型：股票 开始计算 ->");
                    // 计算 & 生成：本次收益记录
                    dateOprate.countEarnings(productCode,lastDailyChange,lastEarningsDate,lastMarketValue,productType);
                } catch (ParseException e) {
                    throw e;
                }
            }else if(financialProductList.get(i).getProductType().equals("基金")){
                // 上次收益记录 = 查找上次收益记录(产品代码)
                String productCode=financialProductList.get(i).getProductCode();
                try {
                    // 查询：根据代码查找 earnings 表
                    FundEarnings fundEarnings=fundEarningsMapper.selectLastOneEarnings(productCode);

                    // 赋值：查询到的上次收益记录
                    Float lastDailyChange=fundEarnings.getDailyChange();
                    Date lastEarningsDate=fundEarnings.getEarningsDate();
                    Float lastMarketValue=fundEarnings.getNetWorth();
                    // 区分：类型用于在新增时区分调用的 service 层
                    String productType="基金";

                    System.out.println("产品代码："+productCode+" 类型：基金 开始计算 ->");
                    // 计算 & 生成：本次收益记录
                    dateOprate.countEarnings(productCode,lastDailyChange,lastEarningsDate,lastMarketValue,productType);
                } catch (ParseException e) {
                    throw e;
                }
            }else if(financialProductList.get(i).getProductType().equals("黄金")){
                // 上次收益记录 = 查找上次收益记录(产品代码)
                String productCode=financialProductList.get(i).getProductCode();
                try {
                    // 查询：根据代码查找 earnings 表
                    GoldEarnings goldEarnings=goldEarningsMapper.selectLastOneEarnings(productCode);

                    // 赋值：查询到的上次收益记录
                    Float lastDailyChange=goldEarnings.getDailyChange();
                    Date lastEarningsDate=goldEarnings.getEarningsDate();
                    Float lastMarketValue=goldEarnings.getGoldPrice();
                    // 区分：类型用于在新增时区分调用的 service 层
                    String productType="黄金";

                    System.out.println("产品代码："+productCode+" 类型：黄金 开始计算 ->");
                    // 计算 & 生成：本次收益记录
                    dateOprate.countEarnings(productCode,lastDailyChange,lastEarningsDate,lastMarketValue,productType);
                } catch (ParseException e) {
                    throw e;
                }
            }
        }
        updateEarn.updateDayEarn(null);
        updateEarn.updateHoldEarn(null);
    }

    /** 一年收益率 = 计算一年收益率的方法（productCode,productType）：保留两位小数*/
    public float getEarnRate(String productCode,String productType,int month){
        float earnRate=0;
        DecimalFormat dfTwo =new DecimalFormat("#.00");
        // -------- 方法：读取一年前的净值（不用顺延，数据库语句直接取最后一条记录）
        /** 收益率 = （最新净值 - 一年前净值）/一年前净值 -> 返回收益率 */
        if(productType.equals("股票")){
            /** 获取最新净值 */
            StockEarnings stockEarnings=stockEarningsMapper.selectLastStockEarnings(productCode);
            float newValue=stockEarnings.getStockMarketValue();

            /** 获取一年/三年前净值 */
            float oldValue=stockEarningsMapper.getNetWorth(productCode,month);

            /** 获得收益率 */
            earnRate=Float.parseFloat(dfTwo.format((newValue - oldValue)/oldValue));
        }else if(productType.equals("基金")){
            /** 获取最新净值 */
            FundEarnings fundEarnings=fundEarningsMapper.selectLastOneEarnings(productCode);
            float newValue=fundEarnings.getNetWorth();

            /** 获取一年/三年前净值 */
            float oldValue=fundEarningsMapper.getNetWorth(productCode,month);

            /** 获得收益率 */
            earnRate=Float.parseFloat(dfTwo.format((newValue - oldValue)/oldValue));
        }else if(productType.equals("黄金")){
            /** 获取最新净值 */
            GoldEarnings goldEarnings=goldEarningsMapper.selectLastOneEarnings(productCode);
            float newValue=goldEarnings.getGoldPrice();

            /** 获取一年/三年前净值 */
            float oldValue=goldEarningsMapper.getNetWorth(productCode,month);

            /** 获得收益率 */
            earnRate=Float.parseFloat(dfTwo.format((newValue - oldValue)/oldValue));
        }
        return earnRate;
    }

    /**
     * 更新个人的历史收益（userid & earningsDate & totalDayEarn）
     * 变更时机：每日收益更新后（包含：项目买入）、项目删除
     * @param userid
     * @param earningsDate
     */
    public void updateHistoryEarn(Long userid, Date earningsDate) {
        // TODO: 当表中不存在该记录的时候，用insert，存在时用update
        /** 根据 userid 查个人资产表中的 dayEarn 的和（status：0），返回 float */
        QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
        personalFinancialAssetsQueryWrapper.eq("userid",userid);
        // get：个人当天的今日收益之和
        float totalDayEarn=personalFinancialAssetsMapper.selectTotalDayEarn(userid);

        /** 查看某人某天的收益记录是否存在 */
        QueryWrapper<HistoryEarnings> historyEarningsQueryWrapper=new QueryWrapper<>();
        historyEarningsQueryWrapper.eq("userid",userid);
        historyEarningsQueryWrapper.eq("earningsdate",earningsDate);
        int isExist=historyEarningsMapper.selectCount(historyEarningsQueryWrapper);

        /** 根据 userid & earningsDate & dayEarn 调用 service 新增 */
        HistoryEarnings historyEarnings=new HistoryEarnings();
        historyEarnings.setDayEarn(totalDayEarn);
        historyEarnings.setEarningsdate(earningsDate);
        historyEarnings.setUserid(userid);

        /** 若存在则调用 update ，若不存在则调用 insert */
        if(isExist==0){
            historyEarningsService.insert(historyEarnings);
        }else{
            historyEarningsService.updateByWrapper(historyEarnings,historyEarningsQueryWrapper);
        }

    }
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

                /** 历史收益：更新用户的（userid & earningsDate）历史收益 */
                Date earningsDate=stockEarningsLastOne.getEarningsDate();
                Long userid=personalFinancialAssetsList.get(i).getUserid();
                updateEarn.updateHistoryEarn(userid,earningsDate);
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

                /** 历史收益：更新用户的（userid & earningsDate）历史收益 */
                Date earningsDate=goldEarningsLastOne.getEarningsDate();
                Long userid=personalFinancialAssetsList.get(i).getUserid();
                updateEarn.updateHistoryEarn(userid,earningsDate);
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

                /** 历史收益：更新用户的（userid & earningsDate）历史收益 */
                Date earningsDate=fundEarningsLastOne.getEarningsDate();
                Long userid=personalFinancialAssetsList.get(i).getUserid();
                updateEarn.updateHistoryEarn(userid,earningsDate);
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
