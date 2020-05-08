package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.PersonalCollectionMapper;
import com.stock.demo.mapper.PersonalFinancialAssetsMapper;
import com.stock.demo.pojo.*;
import com.stock.demo.service.*;
import com.stock.demo.util.PersonalAssetsUtil;
import com.stock.demo.util.SuggestionGet;
import com.stock.demo.util.UpdateEarn;
import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.jnlp.PersistenceService;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/20
 * Time: 10:02
 * Description:
 */
@RestController
@RequestMapping(path={"/personalFinancialAssets"})
public class PersonalFinancialAssetsController implements BaseController<PersonalFinancialAssets>{

    @Autowired
    PersonalFinancialAssetsMapper personalFinancialAssetsMapper;

    @Autowired
    PersonalFinancialAssetsService personalFinancialAssetsService;

    @Autowired
    FinancialProductService financialProductService;

    @Autowired
    StockEarningsService stockEarningsService;

    @Autowired
    FundEarningsService fundEarningsService;

    @Autowired
    GoldEarningsService goldEarningsService;

    @Autowired
    HistoricalOperationService historicalOperationService;

    @Autowired
    UpdateEarn updateEarn;

    @Autowired
    PersonalAssetsUtil personalAssetsUtil;

    @Autowired
    SuggestionGet suggestionGet;

    @Autowired
    UserService userService;

    @GetMapping("getAceOfAssets")
    public List getAceOfAssets(@RequestParam(value="userid",required = false) Long userid){
        /** 分别存放累计收益 & 持有收益 */
        Map<String,Object> totalEarnMap = new HashMap<String,Object>(10);
        Map<String,Object> holdEarnMap = new HashMap<String,Object>(10);
        /** get：累计收益最高的名称 & 收益 */
        List<PersonalFinancialAssets> maxTotalEarnList=personalFinancialAssetsMapper.selectMaxTotalEarn(userid);
        totalEarnMap.put("productCode",maxTotalEarnList.get(0).getProductCode());
        totalEarnMap.put("totalEarn",maxTotalEarnList.get(0).getTotalEarn());
        totalEarnMap.put("productName",maxTotalEarnList.get(0).getProductName());

        /** get：持有收益最高的名称 & 收益 */
        List<PersonalFinancialAssets> maxHoldEarnList=personalFinancialAssetsMapper.selectMaxHoldEarn(userid);
        holdEarnMap.put("productCode",maxHoldEarnList.get(0).getProductCode());
        holdEarnMap.put("holdEarn",maxHoldEarnList.get(0).getHoldEarn());
        holdEarnMap.put("productName",maxHoldEarnList.get(0).getProductName());

        /** 王牌资产（含 累计收益 Max & 持有收益 Max） */
        List aceOfAssets=new ArrayList();
        aceOfAssets.add(totalEarnMap);
        aceOfAssets.add(holdEarnMap);
        System.out.println("aceOfAssets:"+aceOfAssets);

        return aceOfAssets;
    }

    /**
     * >> 按照资产类型划分
     * 资产占比（查询个人拥有各资产总和）
     * @param userid
     * @return
     */
    @GetMapping("getSumOfAssets")
    public List getSumOfAssets(@RequestParam(value="userid",required = false) Long userid){
        DecimalFormat dfOne =new DecimalFormat("#.0");

        float stockAssets=personalFinancialAssetsMapper.selectTypeOfAssets(userid,"股票");
        float fundAssets=personalFinancialAssetsMapper.selectTypeOfAssets(userid,"基金");
        float goldAssets=personalFinancialAssetsMapper.selectTypeOfAssets(userid,"黄金");
        float regular=personalFinancialAssetsMapper.selectTypeOfAssets(userid,"定期");
        float otherAssets=personalFinancialAssetsMapper.selectTypeOfAssets(userid,"其他");
        /** 查询总资产 */
        float sumOfAssets=personalFinancialAssetsMapper.selectSumOfAssets(userid);

        List assetsList=new ArrayList();
        // productQuantity：5种产品
        int productQuantity=5;

        for(int i=0;i<productQuantity;i++){
            Map<String,Object> map = new HashMap<String,Object>(10);
            /**
             * 返回格式
             projectItem: '股票',
             assetItem: '51324',
             progressItem: '51%',（保留一位小数，转换为 String，增加百分号 %）
             proportionItem: '51%'（保留一位小数）
             */
            if(i==0){
                /** 股票资产 */
                String proportionItem=dfOne.format(stockAssets/sumOfAssets*100)+"%";
                map.put("projectItem","股票");
                map.put("assetItem",stockAssets);
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }else if(i==1){
                /** 基金资产 */
                String proportionItem=dfOne.format(fundAssets/sumOfAssets*100)+"%";
                map.put("projectItem","基金");
                map.put("assetItem",fundAssets);
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }else if(i==2){
                /** 黄金资产 */
                String proportionItem=dfOne.format(goldAssets/sumOfAssets*100)+"%";
                map.put("projectItem","黄金");
                map.put("assetItem",goldAssets);
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }else if(i==3){
                /** 定期资产 */
                String proportionItem=dfOne.format(regular/sumOfAssets*100)+"%";
                map.put("projectItem","定期");
                map.put("assetItem",regular);
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }else if(i==4){
                /** 其他资产 */
                String proportionItem=dfOne.format(otherAssets/sumOfAssets*100)+"%";
                map.put("projectItem","其他");
                map.put("assetItem",otherAssets);
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }
            assetsList.add(map);
        }
        return assetsList;
    }

    /**
     * >> 按照资产风险划分
     * 资产占比（查询个人拥有各资产总和）
     * 生成投资建议
     * @param userid
     * @return
     */
    @GetMapping("getAssetsFromRisk")
    public Map<String,Object> getAssetsFromRisk(@RequestParam(value="userid",required = false) Long userid){
        /** 声明最终结果集 */
        Map<String,Object> resultMap=new HashMap<String,Object>(10);

        DecimalFormat dfOne =new DecimalFormat("#.0");
        DecimalFormat dfTwo =new DecimalFormat("#.00");

        /** 查询总资产 */
        float sumOfAssets=personalFinancialAssetsMapper.selectSumOfAssets(userid);

        /** 声明各风险资产金额 */
        float lowAssets=0;
        float middleLowAssets=0;
        float middleAssets=0;
        float middleHighAssets=0;

        /** 声明：最终结果列表 */
        List assetsList=new ArrayList();

        /** 查该用户拥有的资产 */
        QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
        personalFinancialAssetsQueryWrapper.eq("userid",userid);
        personalFinancialAssetsQueryWrapper.eq("status",0);
        List<PersonalFinancialAssets> personalFinancialAssetsList=personalFinancialAssetsService.selectByWrapperReturnList(personalFinancialAssetsQueryWrapper);

        for(int i=0;i<personalFinancialAssetsList.size();i++){
            /** 根据 productCode，遍历查询 financialProduct 中的 riskType */
            String productCode=personalFinancialAssetsList.get(i).getProductCode();

            QueryWrapper<FinancialProduct> financialProductQueryWrapper=new QueryWrapper<>();
            financialProductQueryWrapper.eq("productCode",productCode);
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(financialProductQueryWrapper);

            /** 根据风险类型，进行资产累加 */
            if(financialProduct.getRiskType().equals("低风险")){
                lowAssets=personalFinancialAssetsList.get(i).getHoldAssets()+lowAssets;
            }else if(financialProduct.getRiskType().equals("中低风险")){
                middleLowAssets=personalFinancialAssetsList.get(i).getHoldAssets()+middleLowAssets;
            }else if(financialProduct.getRiskType().equals("中风险")){
                middleAssets=personalFinancialAssetsList.get(i).getHoldAssets()+middleAssets;
            }else if(financialProduct.getRiskType().equals("中高风险")){
                middleHighAssets=personalFinancialAssetsList.get(i).getHoldAssets()+middleHighAssets;
            }
        }

        /** 生成占比值并返回 List */
        // typeQuantity：4种风险类型
        int typeQuantity=4;

        /** 声明：四个风险占比数值，用于生成建议用 */
        float lowProportion=0;
        float middleLowProportion=0;
        float middleProportion=0;
        float middleHighProportion=0;

        /** get：user 的 investmentCharacter：投资性格，用于生成建议用 */
        User user=userService.load(userid);
        String investmentCharacter=user.getInvestmentCharacter();

        for(int i=0;i<typeQuantity;i++){
            Map<String,Object> map = new HashMap<String,Object>(10);
            /**
             * 返回格式
             projectItem: '低风险',
             assetItem: '51324',
             progressItem: '51%',（保留一位小数，转换为 String，增加百分号 %）
             proportionItem: '51%'（保留一位小数）
             */
            if(i==0){
                lowProportion=Float.parseFloat(dfOne.format(lowAssets/sumOfAssets*100));
                /** 低风险资产 */
                String proportionItem=dfOne.format(lowAssets/sumOfAssets*100)+"%";
                map.put("projectItem","低级");
                /** 保留两位小数，为 0 时会显示为 .00，故进行格式转换 */
                map.put("assetItem",dfTwo.format(lowAssets).equals(".00")?"0":dfTwo.format(lowAssets));
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }else if(i==1){
                // 占比数值
                middleLowProportion=Float.parseFloat(dfOne.format(middleLowAssets/sumOfAssets*100));
                /** 中低风险资产 */
                // 占比数值 + %
                String proportionItem=dfOne.format(middleLowAssets/sumOfAssets*100)+"%";
                map.put("projectItem","中低");
                map.put("assetItem",dfTwo.format(middleLowAssets).equals(".00")?"0":dfTwo.format(middleLowAssets));
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }else if(i==2){
                middleProportion=Float.parseFloat(dfOne.format(middleAssets/sumOfAssets*100));
                /** 中风险资产 */
                String proportionItem=dfOne.format(middleAssets/sumOfAssets*100)+"%";
                map.put("projectItem","中等");
                map.put("assetItem",dfTwo.format(middleAssets).equals(".00")?"0":dfTwo.format(middleAssets));
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }else if(i==3){
                middleHighProportion=Float.parseFloat(dfOne.format(middleHighAssets/sumOfAssets*100));
                /** 中高资产 */
                String proportionItem=dfOne.format(middleHighAssets/sumOfAssets*100)+"%";
                map.put("projectItem","中高");
                map.put("assetItem",dfTwo.format(middleHighAssets).equals(".00")?"0":dfTwo.format(middleHighAssets));
                map.put("progressItem",proportionItem);
                map.put("proportionItem",proportionItem);
            }
            assetsList.add(map);
        }

        /** 生成投资建议 */
        Map<String,Object> suggestionMap=suggestionGet.suggestionGet(investmentCharacter,lowProportion,middleLowProportion,middleProportion,middleHighProportion);

        /** 将 List 和 suggestionMap 存进 resultMap 中 并返回 */
        resultMap.put("assetsList",assetsList);
        resultMap.put("suggestion",suggestionMap);

        System.out.println("resultMap:"+resultMap);
        return resultMap;
    }

    /**
     * 获取累计收益
     * @param userid
     * @return
     */
    @GetMapping("getTotalEarn")
    public float getTotalEarn(@RequestParam(value="userid",required = false) Long userid){
        return personalFinancialAssetsMapper.selectTotalEarn(userid);
    }

    // TODO: 限制用户不能在周末操作

    // TODO：！修改持仓成本为 单价，当要使用持仓成本时，用 单价 * 份额即可
    /**
     * 卖出产品份额
     * 在卖出更新了份额 & 赎回资产后，需要同步更新 今日收益、
     * @param bean
     * @return
     */
    @PostMapping("sellPro")
    public int sellPro(@RequestBody(required = false) PersonalFinancialAssets bean) throws ParseException {
        DecimalFormat dfTwo =new DecimalFormat("#.00");
        DecimalFormat dfFour =new DecimalFormat("#.0000");
        // 是否卖出成功标志
        int flag = 0;
        /** 读取旧资产份额 */
        QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
        personalFinancialAssetsQueryWrapper.eq("userid",bean.getUserid());
        personalFinancialAssetsQueryWrapper.eq("productCode",bean.getProductCode());
        personalFinancialAssetsQueryWrapper.eq("status",0);
        PersonalFinancialAssets personalFinancialAssets=personalFinancialAssetsService.selectByWrapperReturnBean(personalFinancialAssetsQueryWrapper);
        // 读取旧资产份额
        float amountOfAssets=personalFinancialAssets.getAmountOfAssets();

        /** 判断 资产份额 是否与bean中资产份额一致，若是：全仓卖出（修改资产状态） & 更新 持有资产、赎回资产、持有收益、累计收益 */
        /** 累计收益 = 当前拥有资产 + 赎回资产 - 投入成本 */
        if(amountOfAssets==bean.getAmountOfAssets()){
            Long personalFinancialAssetsID=personalFinancialAssets.getPersonalFinancialAssetsID();

            /** 新赎回资产 = 旧赎回资产 + 赎回份额 * 赎回时净值 */
            // get：旧赎回资产
            float redemptionOfAssets=personalFinancialAssets.getRedemptionOfAssets();

            /** select：产品 bean（参数：productCode） */
            QueryWrapper<FinancialProduct> financialProductQueryWrapper= new QueryWrapper<>();
            financialProductQueryWrapper.eq("productCode",bean.getProductCode());
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(financialProductQueryWrapper);

            // 工具类参数
            String productType=financialProduct.getProductType();
            String productCode=financialProduct.getProductCode();
            Date sellTime=bean.getBuyTime();
            /** 工具类：获取对应日期净值 */
            float worth=personalAssetsUtil.getDateOfValue(productCode,productType,sellTime);

            /** 持有资产清零 & 更新赎回资产 */
            // get：赎回份额
            float redemptionAmount=bean.getAmountOfAssets();
            // 获得新的赎回资产（参数：旧赎回资产 & 赎回份额 & 净值）
            float newRedemption=updateEarn.updateRedemptionOfAssets(redemptionOfAssets,redemptionAmount,worth);
            // 全仓卖出，持有资产清零：0
            personalFinancialAssets.setHoldAssets(0);
            personalFinancialAssets.setRedemptionOfAssets(newRedemption);
            /**新份额 = 旧份额 - 卖出份额 */
            // 份额清零
            float newAmountOfAssets=personalFinancialAssets.getAmountOfAssets()-bean.getAmountOfAssets();
            personalFinancialAssets.setAmountOfAssets(newAmountOfAssets);
            // 将状态更改为：1（已卖出）
            personalFinancialAssets.setStatus(1);

            /** 更新：资产状态 */
            QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
            wrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
            flag=personalFinancialAssetsService.updateByWrapper(personalFinancialAssets,wrapper);

            try {
                /** 在份额、赎回资产更新后，对以下参数进行更新 */
                /** 今日收益保持原有份额的收益 */
                /** 更新累计收益、持有收益、持有资产（参数：personalFinancialAssetsID） */
                updateEarn.updateHoldEarn(personalFinancialAssetsID);

//                /** 更新今日收益 */
//                updateEarn.updateDayEarn(personalFinancialAssetsID);

                /********************** 新增操作记录 *********************/
                // 获取当前时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                //获取剩余持有资产
                float holdAssets=personalFinancialAssetsService.selectByWrapperReturnBean(wrapper).getHoldAssets();

                // 将参数保存进 HistoricalOperation 实体类中
                HistoricalOperation historicalOperation=new HistoricalOperation();

                /**
                 * 个人资产ID: personalFinancialAssetsID
                 * 资产ID: productCode
                 * 用户ID: bean.getUserid()
                 * 操作时间：new DateTime()
                 * 操作名称：卖出
                 * 资产名称：bean.getProductName()
                 * 操作金额：newRedemption（赎回资产）
                 * 剩余持有资产：personalFinancialAssetsService.selectByWrapperReturnBean(wrapper).getHoldAssets();
                 */
                historicalOperation.setPersonalFinancialAssetsID(personalFinancialAssetsID);
                historicalOperation.setProductCode(productCode);
                historicalOperation.setUserid(bean.getUserid());
                historicalOperation.setOperatingdate(oprateTime);
                historicalOperation.setOperationName("卖出");
                historicalOperation.setProductName(bean.getProductName());
                historicalOperation.setOprateAmount(newRedemption);
                historicalOperation.setHoldAssets(holdAssets);
                // 添加记录
                historicalOperationService.insert(historicalOperation);
            }catch (Exception e){
                throw e;
            }
        }else if(amountOfAssets<bean.getAmountOfAssets()){
            /** 卖出份额超出限制，错误码：401 */
            flag=401;
            return flag;
        }else{
            Long personalFinancialAssetsID=personalFinancialAssets.getPersonalFinancialAssetsID();
            /** 非全仓卖出，则更新赎回资产、持有收益、日收益、累计收益、份额、持有资产 */

            // get：旧赎回资产
            float redemptionOfAssets=personalFinancialAssets.getRedemptionOfAssets();

            /** select：产品 bean（参数：productCode） */
            QueryWrapper<FinancialProduct> financialProductQueryWrapper= new QueryWrapper<>();
            financialProductQueryWrapper.eq("productCode",bean.getProductCode());
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(financialProductQueryWrapper);

            // 工具类参数
            String productType=financialProduct.getProductType();
            String productCode=financialProduct.getProductCode();
            Date sellTime=bean.getBuyTime();
            /** 工具类：获取对应日期净值 */
            float worth=personalAssetsUtil.getDateOfValue(productCode,productType,sellTime);

            /** 获得新的赎回资产（参数：旧赎回资产 & 赎回份额 & 赎回时净值） */
            // get：赎回份额
            float redemptionAmount=bean.getAmountOfAssets();
            float newRedemption=updateEarn.updateRedemptionOfAssets(redemptionOfAssets,redemptionAmount,worth);

            /**新份额 = 旧份额 - 卖出份额 */
            float newAmountOfAssets=personalFinancialAssets.getAmountOfAssets()-bean.getAmountOfAssets();

            /** 旧单价 = 旧持仓成本 / 旧持有份额 */
            float oldUnitPrice=personalFinancialAssets.getHoldingCost()/personalFinancialAssets.getAmountOfAssets();

            /** 新持仓成本 = 旧单价 * 新持有份额 */
            float newHoldingCost=Float.parseFloat(dfTwo.format(oldUnitPrice*newAmountOfAssets));

            personalFinancialAssets.setRedemptionOfAssets(newRedemption);
            personalFinancialAssets.setAmountOfAssets(newAmountOfAssets);
            personalFinancialAssets.setHoldingCost(newHoldingCost);

            /** 在份额、赎回资产更新后，对以下参数进行更新 */
            /** 更新累计收益、持有收益、持有资产（参数：personalFinancialAssetsID） */
            try {

                /** 更新：资产状态 */
                QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
                wrapper.eq("personalFinancialAssetsID",personalFinancialAssetsID);
                flag=personalFinancialAssetsService.updateByWrapper(personalFinancialAssets,wrapper);

                updateEarn.updateHoldEarn(personalFinancialAssetsID);

//                /** 更新今日收益 */
//                updateEarn.updateDayEarn(personalFinancialAssetsID);

                /*********************** 新增操作记录 ********************/
                // 获取当前时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                //获取剩余持有资产
                float holdAssets=personalFinancialAssetsService.selectByWrapperReturnBean(wrapper).getHoldAssets();

                // 声明 HistoricalOperation 实体类供存数据
                HistoricalOperation historicalOperation=new HistoricalOperation();

                /**
                 * 个人资产ID: personalFinancialAssetsID
                 * 资产ID: productCode
                 * 用户ID: bean.getUserid()
                 * 操作时间：new DateTime()
                 * 操作名称：卖出
                 * 资产名称：bean.getProductName()
                 * 操作金额：newRedemption（赎回资产）
                 * 剩余持有资产：personalFinancialAssetsService.selectByWrapperReturnBean(wrapper).getHoldAssets();
                 */
                historicalOperation.setPersonalFinancialAssetsID(personalFinancialAssetsID);
                historicalOperation.setProductCode(productCode);
                historicalOperation.setUserid(bean.getUserid());
                historicalOperation.setOperatingdate(oprateTime);
                historicalOperation.setOperationName("卖出");
                historicalOperation.setProductName(bean.getProductName());
                historicalOperation.setOprateAmount(newRedemption);
                historicalOperation.setHoldAssets(holdAssets);
                // 添加记录
                historicalOperationService.insert(historicalOperation);

            }catch (Exception e){
                throw e;
            }
        }
        /**
         * service 读取资产份额
         * 判断资产份额是否与bean中资产份额一致
         * if（是）
         *     则修改资产状态为1（卖出）
         * else
         *     TODO：卖出日期应为当天（不然会发生：1号和3号买入，2号卖出全仓，这时候该资产应该已经不存在了）
         *     读取卖出日期的当天净值
         *     新持有份额 = 旧份额 - 卖出份额
         *     新持仓资产 = 旧持仓资产 - 卖出份额 * 当天净值
         *     旧单价 = 旧持仓成本 / 旧持有份额
         *     新持仓成本 = 旧单价 * 新持有份额
         *
         *     更新新持仓收益
         *
         */
        return flag;
    }

    /**
     * 查找单个对应资产
     * @param userid
     * @param productCode
     * @return
     */
    @GetMapping("selectOne")
    public PersonalFinancialAssets selectOne(@RequestParam(value="userid",required = false) Long userid,
                                             @RequestParam(value="productCode",required = false) Long productCode){
        /** 查找资产（代码 & 用户ID & 状态：0【持有】） */
        QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
        personalFinancialAssetsQueryWrapper.eq("productCode",productCode);
        personalFinancialAssetsQueryWrapper.eq("userid",userid);
        personalFinancialAssetsQueryWrapper.eq("status",0);

        return personalFinancialAssetsService.selectByWrapperReturnBean(personalFinancialAssetsQueryWrapper);
    }

    /**
     * 查询用户持有资产
     * status：0 持有  1：卖出
     * @param userID
     * @return List<PersonalFinancialAssets>
     */
    @GetMapping("listById")
    public List listById(@RequestParam(value="userID",required = false) Long userID){
        Map<String,Object> mapBig = new HashMap<String,Object>(10);

        /** 通过个人资产中的 productCode 对应查询该资产的信息 */
        List<PersonalFinancialAssets> personalFinancialAssetsList=personalFinancialAssetsService.selectUserHoldProduct(userID);
        List moreInfoList=new ArrayList();

        for(int i=0;i<personalFinancialAssetsList.size();i++){
            /** 将 map 放在循环中新建，可以防止 map 带着旧值 add 进 list 中 */
            Map<String,Object> map = new HashMap<String,Object>(10);

            // 根据 productCode 查询对应的产品信息
            String productCode=personalFinancialAssetsList.get(i).getProductCode();
            QueryWrapper<FinancialProduct> wrapper=new QueryWrapper<>();
            wrapper.eq("productCode",productCode);
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(wrapper);
            /** 将查询到的两个对象放在 map 中 */

            map.put("productName",financialProduct.getProductName());
            map.put("productCode",financialProduct.getProductCode());
            map.put("productType",financialProduct.getProductType());
            map.put("holdingCost",personalFinancialAssetsList.get(i).getHoldingCost());
            map.put("dayEarn",personalFinancialAssetsList.get(i).getDayEarn());
            map.put("holdEarn",personalFinancialAssetsList.get(i).getHoldEarn());

            /** 将 map 存进 ArrayList 中 */
            moreInfoList.add(map);
        }
        return moreInfoList;
    }

    // TODO 加仓后更新日收益、累计收益、持有收益
    /**
     * 加仓（更新个人资产中的信息： 持有资产 & 持有成本 & 持有份额）
     * @param bean
     * @return
     */
    @PostMapping("addPositions")
    public int addPositions (@RequestBody(required = false) PersonalFinancialAssets bean) throws ParseException {
        int flag=0;
        /** 查找出已存在的对应的资产记录(userid,productCode,status) */
        QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
        String productCode=bean.getProductCode();
        Long userid=bean.getUserid();

        wrapper.eq("productCode",productCode);
        wrapper.eq("userid",userid);
        // 资产状态  0：持有  1：已卖出
        wrapper.eq("status",0);

        PersonalFinancialAssets personalFinancialAssets=personalFinancialAssetsService.selectByWrapperReturnBean(wrapper);

        /** 查看产品类型（参数：productCode） */
        QueryWrapper<FinancialProduct> financialProductQueryWrapper= new QueryWrapper<>();
        financialProductQueryWrapper.eq("productCode",productCode);
        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(financialProductQueryWrapper);
        // 查询类型
        String productType=financialProduct.getProductType();

        if(productType.equals("股票")){
            try{
                // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
                QueryWrapper<StockEarnings> stockWrapper=new QueryWrapper<>();

                // 转换：将日期转换为String类型才可以查询到结果
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
                String dateStirng =  formatter.format(bean.getBuyTime());

                stockWrapper.eq("earnings_date",dateStirng);
                stockWrapper.eq("productCode",bean.getProductCode());
                StockEarnings stockEarnings=stockEarningsService.selectByWrapperReturnBean(stockWrapper);

                // holdingUnitPrice：持有单价
                float holdingUnitPrice=stockEarnings.getStockMarketValue();

                /**
                 * 新购入金额 = 新购入份额 * 对应日期净值
                 * 新份额 = 新购入份额 + 旧份额
                 * 新持有资产 = 新购入金额 + 旧持仓资产
                 * 新持仓成本 = 新购入金额 + 旧持仓成本
                 */
                float newBuyAssets=bean.getAmountOfAssets()*holdingUnitPrice;
                float newAmountOfAssets=bean.getAmountOfAssets()+personalFinancialAssets.getAmountOfAssets();
                float newHoldAssets=newBuyAssets+personalFinancialAssets.getHoldAssets();
                float newHoldingCost=newBuyAssets+personalFinancialAssets.getHoldingCost();

                System.out.println("newAmountOfAssets"+newAmountOfAssets);
                System.out.println("holdAssets"+newHoldAssets);
                System.out.println("newBuyAssets"+newBuyAssets);
                System.out.println("newHoldingCost"+newHoldingCost);
                /** 将更新的值赋给该条记录 */
                personalFinancialAssets.setAmountOfAssets(newAmountOfAssets);
                personalFinancialAssets.setHoldAssets(newHoldAssets);
                personalFinancialAssets.setHoldingCost(newHoldingCost);

                /** 更新数值（bean & 资产ID） */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssets.getPersonalFinancialAssetsID());
                flag=personalFinancialAssetsService.updateByWrapper(personalFinancialAssets,personalFinancialAssetsQueryWrapper);

                /******************** 新增操作记录 ********************/
                // 获取当前时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                // 声明 HistoricalOperation 实体类供存数据
                HistoricalOperation historicalOperation=new HistoricalOperation();

                /**
                 * 个人资产ID: personalFinancialAssets.getPersonalFinancialAssetsID();
                 * 资产ID: productCode
                 * 用户ID: userid
                 * 操作时间：new DateTime()
                 * 操作名称：加仓
                 * 资产名称：bean.getProductName()
                 * 操作金额：newBuyAssets（购入金额）
                 * 剩余持有资产：newHoldAssets
                 */
                historicalOperation.setPersonalFinancialAssetsID(personalFinancialAssets.getPersonalFinancialAssetsID());;
                historicalOperation.setProductCode(productCode);
                historicalOperation.setUserid(userid);
                historicalOperation.setOperatingdate(oprateTime);
                historicalOperation.setOperationName("加仓");
                historicalOperation.setProductName(bean.getProductName());
                historicalOperation.setOprateAmount(bean.getHoldingCost());
                historicalOperation.setHoldAssets(newHoldAssets);
                // 新增操作记录
                historicalOperationService.insert(historicalOperation);
            }catch (Exception e){
                throw e;
            }
        }else if(productType.equals("基金")){
            try{
                // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
                QueryWrapper<FundEarnings> fundEarningsQueryWrapper=new QueryWrapper<>();

                // 转换：将日期转换为String类型才可以查询到结果
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
                String dateStirng =  formatter.format(bean.getBuyTime());

                fundEarningsQueryWrapper.eq("earningsDate",dateStirng);
                fundEarningsQueryWrapper.eq("productCode",bean.getProductCode());
                FundEarnings fundEarnings=fundEarningsService.selectByWrapperReturnBean(fundEarningsQueryWrapper);

                // 根据当天净值，作为购入的单价  holdingUnitPrice：持有单价
                float holdingNetWorth=fundEarnings.getNetWorth();

                /**
                 * （购入金额）
                 * 新持仓成本 = 新购入金额 + 旧持仓成本
                 * 新持有资产 = 新购入金额 + 旧持仓资产
                 * 新份额 = 新购入金额 / 对应日期净值 + 旧份额
                 */
                float newHoldingCost=bean.getHoldingCost()+personalFinancialAssets.getHoldingCost();
                float newHoldAssets=bean.getHoldingCost()+personalFinancialAssets.getHoldAssets();
                float newAmountOfAssets=bean.getHoldingCost()/holdingNetWorth+personalFinancialAssets.getAmountOfAssets();

                // 赋值
                personalFinancialAssets.setAmountOfAssets(newAmountOfAssets);
                personalFinancialAssets.setHoldAssets(newHoldAssets);
                personalFinancialAssets.setHoldingCost(newHoldingCost);
                /** 更新数值（bean & 资产ID） */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssets.getPersonalFinancialAssetsID());
                flag= personalFinancialAssetsService.updateByWrapper(personalFinancialAssets,personalFinancialAssetsQueryWrapper);

                /******************** 新增操作记录 ********************/
                // 获取当前时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                // 声明 HistoricalOperation 实体类供存数据
                HistoricalOperation historicalOperation=new HistoricalOperation();


                /**
                 * 个人资产ID: personalFinancialAssets.getPersonalFinancialAssetsID();
                 * 资产ID: productCode
                 * 用户ID: userid
                 * 操作时间：new DateTime()
                 * 操作名称：加仓
                 * 资产名称：bean.getProductName()
                 * 操作金额：bean.getHoldingCost()（购入金额）
                 * 剩余持有资产：newHoldAssets
                 */
                historicalOperation.setPersonalFinancialAssetsID(personalFinancialAssets.getPersonalFinancialAssetsID());;
                historicalOperation.setProductCode(productCode);
                historicalOperation.setUserid(userid);
                historicalOperation.setOperatingdate(oprateTime);
                historicalOperation.setOperationName("加仓");
                historicalOperation.setProductName(bean.getProductName());
                historicalOperation.setOprateAmount(bean.getHoldingCost());
                historicalOperation.setHoldAssets(newHoldAssets);
                // 新增操作记录
                historicalOperationService.insert(historicalOperation);
            }catch (Exception e){
                throw e;
            }
        }else if(productType.equals("黄金")){
            try {
                // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
                QueryWrapper<GoldEarnings> goldEarningsQueryWrapper=new QueryWrapper<>();

                // 转换：将日期转换为String类型才可以查询到结果
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
                String dateStirng =  formatter.format(bean.getBuyTime());

                goldEarningsQueryWrapper.eq("earningsDate",dateStirng);
                goldEarningsQueryWrapper.eq("productCode",bean.getProductCode());
                GoldEarnings goldEarnings=goldEarningsService.selectByWrapperReturnBean(goldEarningsQueryWrapper);

                // 根据当天净值，作为购入的单价  holdingUnitPrice：持有单价
                float holdingNetWorth=goldEarnings.getGoldPrice();

                /**
                 * （购入金额）
                 * 新持仓成本 = 新购入金额 + 旧持仓成本
                 * 新持有资产 = 新购入金额 + 旧持仓资产
                 * 新份额 = 新购入金额 / 对应日期净值 + 旧份额
                 */
                float newHoldingCost=bean.getHoldingCost()+personalFinancialAssets.getHoldingCost();
                float newHoldAssets=bean.getHoldingCost()+personalFinancialAssets.getHoldAssets();
                float newAmountOfAssets=bean.getHoldingCost()/holdingNetWorth+personalFinancialAssets.getAmountOfAssets();

                // 赋值
                personalFinancialAssets.setAmountOfAssets(newAmountOfAssets);
                personalFinancialAssets.setHoldAssets(newHoldAssets);
                personalFinancialAssets.setHoldingCost(newHoldingCost);
                /** 更新数值（bean & 资产ID） */
                QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
                personalFinancialAssetsQueryWrapper.eq("personalFinancialAssetsID",personalFinancialAssets.getPersonalFinancialAssetsID());
                flag= personalFinancialAssetsService.updateByWrapper(personalFinancialAssets,personalFinancialAssetsQueryWrapper);

                /******************** 新增操作记录 ********************/
                // 获取当前时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                // 声明 HistoricalOperation 实体类供存数据
                HistoricalOperation historicalOperation=new HistoricalOperation();


                /**
                 * 个人资产ID: personalFinancialAssets.getPersonalFinancialAssetsID();
                 * 资产ID: productCode
                 * 用户ID: userid
                 * 操作时间：new DateTime()
                 * 操作名称：加仓
                 * 资产名称：bean.getProductName()
                 * 操作金额：bean.getHoldingCost()（购入金额）
                 * 剩余持有资产：newHoldAssets
                 */
                historicalOperation.setPersonalFinancialAssetsID(personalFinancialAssets.getPersonalFinancialAssetsID());;
                historicalOperation.setProductCode(productCode);
                historicalOperation.setUserid(userid);
                historicalOperation.setOperatingdate(oprateTime);
                historicalOperation.setOperationName("加仓");
                historicalOperation.setProductName(bean.getProductName());
                historicalOperation.setOprateAmount(bean.getHoldingCost());
                historicalOperation.setHoldAssets(newHoldAssets);
                // 新增操作记录
                historicalOperationService.insert(historicalOperation);
            }catch (Exception e){
                throw e;
            }
        }else{
            return 0;
        }
        return flag;
    }

    /**
     * 根据 userID、个人资产ID 删除
     * @param bean
     * @return
     */
    // TODO: 异常处理
    @PostMapping("deleteByWrapper")
    public int deleteByWrapper(@RequestBody(required = false) PersonalFinancialAssets bean){
        QueryWrapper<PersonalFinancialAssets> queryWrapper=new QueryWrapper<>();
        Long userID=bean.getUserid();
        Long PersonalFinancialAssetsID=bean.getPersonalFinancialAssetsID();
        queryWrapper.eq("personalFinancialAssetsID",PersonalFinancialAssetsID);
        queryWrapper.eq("userID",userID);
        return personalFinancialAssetsService.deleteByWrapper(queryWrapper);
    }

    /**
     * 查看是否存在某资产
     * @param userID
     * @param productCode
     * @return
     */
    @GetMapping("isExist")
    public int isExist(@RequestParam(value="userID",required = false) Long userID,
                       @RequestParam(value="productCode",required = false) String productCode){
        /** 查询项目是否存在（参数：userid & produtCode） */
        QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
        personalFinancialAssetsQueryWrapper.eq("userid",userID);
        personalFinancialAssetsQueryWrapper.eq("productCode",productCode);
        personalFinancialAssetsQueryWrapper.eq("status",0);
        int isExist=personalFinancialAssetsService.selectByWrapperReturnInt(personalFinancialAssetsQueryWrapper);
        /** 返回 isExist 0：不存在  1：存在 */
        return isExist;
    }

    @Override
    public List<PersonalFinancialAssets> list() {
        return null;
    }

    // TODO 添加产品后更新 日收益、持有收益、持有资产、累计收益
    /**
     * 添加个人产品
     * @param bean
     * @return
     */
    @Override
    @PostMapping("insert")
    public int insert(@RequestBody(required = false) PersonalFinancialAssets bean) throws ParseException {
        int flag=0;
        /** 查询产品是否存在的条件 */
        QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
        personalFinancialAssetsQueryWrapper.eq("userid",bean.getUserid());
        personalFinancialAssetsQueryWrapper.eq("productCode",bean.getProductCode());
        personalFinancialAssetsQueryWrapper.eq("status",0);

        // 方法：判断产品是否存在
        int isExist=personalFinancialAssetsService.selectByWrapperReturnInt(personalFinancialAssetsQueryWrapper);
        System.out.println("isExist:"+isExist);
        /** 判断：某用户的产品代码存在（isExist：1） 且 资产状态为持有时，不能新增 */
        if(isExist==0){
            // 根据 productCode 查询对应的类型
            QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("productCode",bean.getProductCode());
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(queryWrapper);

            String productType=financialProduct.getProductType();

            // 保留四位小数
            DecimalFormat dfFour =new DecimalFormat("#.0000");

            if(productType.equals("股票")){
               try{
                   // 获取持有份额
                   float amountOfAssets=bean.getAmountOfAssets();

                   // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
                   QueryWrapper<StockEarnings> stockWrapper=new QueryWrapper<>();

                   // 转换：将日期转换为String类型才可以查询到结果
                   SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
                   String dateStirng =  formatter.format(bean.getBuyTime());

                   stockWrapper.eq("earnings_date",dateStirng);
                   stockWrapper.eq("productCode",bean.getProductCode());
                   StockEarnings stockEarnings=stockEarningsService.selectByWrapperReturnBean(stockWrapper);

                   // holdingUnitPrice：持有单价
                   float holdingUnitPrice=stockEarnings.getStockMarketValue();
                   // holdingCost：持仓成本
                   /** 持仓成本 = 持有单价 * 持仓份额(int) */
                   float holdingCost=holdingUnitPrice*amountOfAssets;
                   /** 计算之后的 holdingCost 存进 bean中 */
                   bean.setHoldingCost(holdingCost);
                   /** 在日期原有基础上增加一天，弥补系统少算的一天 */
                   GregorianCalendar gc=new GregorianCalendar();
                   gc.setTime(bean.getBuyTime());
                   // 由于存进mysql时会减少一天，故在今天的基础上加一天=明天
//                gc.add(5,1);
                   bean.setBuyTime(gc.getTime());

                   int insertFlag=personalFinancialAssetsService.insert(bean);

                   // 获取资产ID供更新收益
                   QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
                   wrapper.eq("productCode",bean.getProductCode());
                   wrapper.eq("userid",bean.getUserid());
                   wrapper.eq("status",0);
                   PersonalFinancialAssets personalFinancialAssets=personalFinancialAssetsService.selectByWrapperReturnBean(wrapper);
                   Long financialID=personalFinancialAssets.getPersonalFinancialAssetsID();
                   /** 调用工具类更新个人资产的 持有资产 */
                   // 买入当天不能有日收益
//                   updateEarn.updateDayEarn(financialID);
                   updateEarn.updateHoldEarn(financialID);
                   flag= insertFlag;

                   /********************* 新增操作记录 **********************/
                   // 获取当前时间
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                   Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                   // 声明 HistoricalOperation 实体类供存数据
                   HistoricalOperation historicalOperation=new HistoricalOperation();
                   /**
                    * 个人资产ID: personalFinancialAssets.getPersonalFinancialAssetsID();
                    * 资产ID: bean.getProductCode()
                    * 用户ID: bean.getUserid()
                    * 操作时间：new DateTime()
                    * 操作名称：买入
                    * 资产名称：bean.getProductName()
                    * 操作金额：holdingCost（购入金额）
                    * 剩余持有资产：holdingCost
                    */
                   historicalOperation.setPersonalFinancialAssetsID(financialID);
                   historicalOperation.setProductCode(bean.getProductCode());
                   historicalOperation.setUserid(bean.getUserid());
                   historicalOperation.setOperatingdate(oprateTime);
                   historicalOperation.setOperationName("买入");
                   historicalOperation.setProductName(bean.getProductName());
                   historicalOperation.setOprateAmount(holdingCost);
                   /** 持有资产 */
                   historicalOperation.setHoldAssets(holdingCost);

                   historicalOperationService.insert(historicalOperation);

                   /** 在原有人气基础上 +1 */
                   int popularity=financialProduct.getPopularity();
                   int newPopularity=popularity+1;
                   financialProduct.setPopularity(newPopularity);
                   System.out.println(newPopularity);
                   financialProductService.updateByWrapper(financialProduct,queryWrapper);
               }catch (Exception e){
                   throw e;
               }
            }else if(productType.equals("基金")){
                try {
                    // 获取持仓成本（即买入金额）
                    float holdingCost=bean.getHoldingCost();

                    // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
                    QueryWrapper<FundEarnings> fundWrapper=new QueryWrapper<>();
                    // 转换：将日期转换为String类型才可以查询到结果
                    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
                    String dateStirng =  formatter.format(bean.getBuyTime());

                    fundWrapper.eq("earningsDate",dateStirng);
                    fundWrapper.eq("productCode",bean.getProductCode());
                    FundEarnings fundEarnings=fundEarningsService.selectByWrapperReturnBean(fundWrapper);

                    // holdingUnitPrice：持有单价
                    float holdingUnitPrice=fundEarnings.getNetWorth();
                    // holdingCost：持仓成本
                    /** 持仓份额(float) = 持仓成本 / 持有单价 */
                    float amountOfAssets=Float.parseFloat(dfFour.format(holdingCost/holdingUnitPrice));
                    bean.setAmountOfAssets(amountOfAssets);
                    /** 在日期原有基础上增加一天，弥补系统少算的一天 */
                    GregorianCalendar gc=new GregorianCalendar();
                    gc.setTime(bean.getBuyTime());
                    // 在今天的基础上加一天=明天
                    gc.add(5,1);
                    bean.setBuyTime(gc.getTime());
                    // TODO: 当amountOfAssets<1时会变为0

                    int insertFlag=personalFinancialAssetsService.insert(bean);

                    // 获取资产ID供更新收益
                    QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
                    wrapper.eq("productCode",bean.getProductCode());
                    wrapper.eq("userid",bean.getUserid());
                    wrapper.eq("status",0);
                    PersonalFinancialAssets personalFinancialAssets=personalFinancialAssetsService.selectByWrapperReturnBean(wrapper);
                    Long financialID=personalFinancialAssets.getPersonalFinancialAssetsID();

                    /** 调用工具类更新个人资产的今日收益和持有收益 */
                    // 买入当天不能有日收益
//                    updateEarn.updateDayEarn(financialID);
                    updateEarn.updateHoldEarn(financialID);
                    flag= insertFlag;

                    /********************* 新增操作记录 **********************/
                    // 获取当前时间
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                    Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                    // 声明 HistoricalOperation 实体类供存数据
                    HistoricalOperation historicalOperation=new HistoricalOperation();
                    /**
                     * 个人资产ID: personalFinancialAssets.getPersonalFinancialAssetsID();
                     * 资产ID: bean.getProductCode()
                     * 用户ID: bean.getUserid()
                     * 操作时间：new DateTime()
                     * 操作名称：买入
                     * 资产名称：bean.getProductName()
                     * 操作金额：holdingCost（购入金额）
                     * 剩余持有资产：holdingCost
                     */
                    historicalOperation.setPersonalFinancialAssetsID(financialID);
                    historicalOperation.setProductCode(bean.getProductCode());
                    historicalOperation.setUserid(bean.getUserid());
                    historicalOperation.setOperatingdate(oprateTime);
                    historicalOperation.setOperationName("买入");
                    historicalOperation.setProductName(bean.getProductName());
                    historicalOperation.setOprateAmount(holdingCost);
                    historicalOperation.setHoldAssets(holdingCost);

                    historicalOperationService.insert(historicalOperation);

                    /** 在原有人气基础上 +1 */
                    int popularity=financialProduct.getPopularity();
                    int newPopularity=popularity+1;
                    financialProduct.setPopularity(newPopularity);
                    System.out.println(newPopularity);
                    financialProductService.updateByWrapper(financialProduct,queryWrapper);
                }catch (Exception e){
                    throw e;
                }
            }else if(productType.equals("黄金")){
                try {
                    // 获取持仓成本（即买入金额）
                    float holdingCost=bean.getHoldingCost();

                    // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
                    QueryWrapper<GoldEarnings> goldWrapper=new QueryWrapper<>();

                    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
                    String dateStirng =  formatter.format(bean.getBuyTime());

                    goldWrapper.eq("earningsDate",dateStirng);
                    goldWrapper.eq("productCode",bean.getProductCode());
                    GoldEarnings goldEarnings=goldEarningsService.selectByWrapperReturnBean(goldWrapper);

                    // holdingUnitPrice：持有单价
                    float holdingUnitPrice=goldEarnings.getGoldPrice();
                    // holdingCost：持仓成本
                    /** 持仓份额(float) = 持仓成本 / 持有单价 */
                    float amountOfAssets=Float.parseFloat(dfFour.format(holdingCost/holdingUnitPrice));

                    bean.setAmountOfAssets(amountOfAssets);
                    /** 在日期原有基础上增加一天，弥补系统少算的一天 */
                    GregorianCalendar gc=new GregorianCalendar();
                    gc.setTime(bean.getBuyTime());
                    // 在今天的基础上加一天=明天
                    gc.add(5,1);
                    bean.setBuyTime(gc.getTime());


                    int insertFlag=personalFinancialAssetsService.insert(bean);

                    // 获取资产ID供更新收益
                    QueryWrapper<PersonalFinancialAssets> wrapper=new QueryWrapper<>();
                    wrapper.eq("productCode",bean.getProductCode());
                    wrapper.eq("userid",bean.getUserid());
                    wrapper.eq("status",0);
                    PersonalFinancialAssets personalFinancialAssets=personalFinancialAssetsService.selectByWrapperReturnBean(wrapper);
                    Long financialID=personalFinancialAssets.getPersonalFinancialAssetsID();

                    /** 调用工具类更新个人资产的今日收益和持有收益 */
                    // 买入当天不能有日收益
//                    updateEarn.updateDayEarn(financialID);
                    updateEarn.updateHoldEarn(financialID);
                    flag= insertFlag;

                    /********************* 新增操作记录 **********************/
                    // 获取当前时间
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                    Date oprateTime=dateFormat.parse(dateFormat.format(new Date()));

                    // 声明 HistoricalOperation 实体类供存数据
                    HistoricalOperation historicalOperation=new HistoricalOperation();
                    /**
                     * 个人资产ID: personalFinancialAssets.getPersonalFinancialAssetsID();
                     * 资产ID: bean.getProductCode()
                     * 用户ID: bean.getUserid()
                     * 操作时间：new DateTime()
                     * 操作名称：买入
                     * 资产名称：bean.getProductName()
                     * 操作金额：holdingCost（购入金额）
                     * 剩余持有资产：holdingCost
                     */
                    historicalOperation.setPersonalFinancialAssetsID(financialID);
                    historicalOperation.setProductCode(bean.getProductCode());
                    historicalOperation.setUserid(bean.getUserid());
                    historicalOperation.setOperatingdate(oprateTime);
                    historicalOperation.setOperationName("买入");
                    historicalOperation.setProductName(bean.getProductName());
                    historicalOperation.setOprateAmount(holdingCost);
                    historicalOperation.setHoldAssets(holdingCost);

                    historicalOperationService.insert(historicalOperation);

                    /** 在原有人气基础上 +1 */
                    int popularity=financialProduct.getPopularity();
                    int newPopularity=popularity+1;
                    financialProduct.setPopularity(newPopularity);
                    System.out.println(newPopularity);
                    financialProductService.updateByWrapper(financialProduct,queryWrapper);
                }catch (Exception e){
                    throw e;
                }
            }else {
                // TODO: 其他类型则返回 0 ，即新增失败
                return 0;
            }
        }else if(isExist==1){
            /** 当个人资产中存在时（isExist：1），返回：2 */
            return 2;
        }else {
            /** 其他错误，返回：0 */
            return 0;
        }
        return flag;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(PersonalFinancialAssets bean) {
        return 0;
    }

    @Override
    public PersonalFinancialAssets load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssets> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public PersonalFinancialAssets loadByName(String name) {
        return null;
    }

    @Override
    public IPage<PersonalFinancialAssets> pagerByName(Wrapper<PersonalFinancialAssets> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
