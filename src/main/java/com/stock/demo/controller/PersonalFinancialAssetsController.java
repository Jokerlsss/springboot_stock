package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.*;
import com.stock.demo.service.*;
import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jnlp.PersistenceService;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    PersonalFinancialAssetsService personalFinancialAssetsService;

    @Autowired
    FinancialProductService financialProductService;

    @Autowired
    StockEarningsService stockEarningsService;

    @Autowired
    FundEarningsService fundEarningsService;

    @Autowired
    GoldEarningsService goldEarningsService;

    /**
     * 查询用户持有资产
     * @param userID
     * @return List<PersonalFinancialAssets>
     */
    @GetMapping("listById")
    public List<PersonalFinancialAssets> listById(@RequestParam(value="userID",required = false) Long userID){
        return personalFinancialAssetsService.selectUserHoldProduct(userID);
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


    @Override
    public List<PersonalFinancialAssets> list() {
        return null;
    }

    /**
     * 添加个人产品
     * @param bean
     * @return
     */
    @Override
    @PostMapping("insert")
    public int insert(@RequestBody(required = false) PersonalFinancialAssets bean) throws ParseException {
        // TODO: 判断 某用户的产品代码存在 且 资产状态为持有时，不能新增
        // TODO: 股票：份额 -> 金额      基金、黄金：金额 -> 份额

        // 根据 productCode 查询对应的类型
        QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("productCode",bean.getProductCode());
        FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(queryWrapper);
        String productType=financialProduct.getProductType();

        // 保留四位小数
        DecimalFormat dfFour =new DecimalFormat("#0.0000");

        if(productType.equals("股票")){
            // 获取持有份额
            float amountOfAssets=bean.getAmountOfAssets();

            // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
            QueryWrapper<StockEarnings> stockWrapper=new QueryWrapper<>();

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
            return personalFinancialAssetsService.insert(bean);
        }else if(productType.equals("基金")){
            // 获取持仓成本（即买入金额）
            float holdingCost=bean.getHoldingCost();

            // 查询: 当天该产品净值，即为买入成本      参数：买入时间 & productCode
            QueryWrapper<FundEarnings> fundWrapper=new QueryWrapper<>();
            // 转换：将日期转换为String类型才可以查询到结果
            // TODO 用gcDate加上一天
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
            /** 计算之后的 holdingCost 存进 bean中 */
            bean.setAmountOfAssets(amountOfAssets);
            // TODO: 当amountOfAssets<1时会变为0
            return personalFinancialAssetsService.insert(bean);
        }else if(productType.equals("黄金")){
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
            /** 计算之后的 holdingCost 存进 bean中 */
            bean.setAmountOfAssets(amountOfAssets);
            return personalFinancialAssetsService.insert(bean);
        }else {
            // TODO: 其他类型则返回 0 ，即新增失败
            return 0;
        }
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
