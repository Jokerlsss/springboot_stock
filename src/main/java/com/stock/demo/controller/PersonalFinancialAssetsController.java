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
//            mapBig.put(""+i,map);
//            System.out.println(map);
            moreInfoList.add(map);
            // 将 map 置空防止数据重复
        }
//        list[financialProduct:{...},personalFinancialAssetsList:{},financialProduct:{},{}]
//        list[{{},{}},{{},{}}]
//        System.out.println(moreInfoList);
        return moreInfoList;
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

        QueryWrapper<PersonalFinancialAssets> personalFinancialAssetsQueryWrapper=new QueryWrapper<>();
        personalFinancialAssetsQueryWrapper.eq("userid",bean.getUserid());
        personalFinancialAssetsQueryWrapper.eq("productCode",bean.getProductCode());
        personalFinancialAssetsQueryWrapper.eq("status",0);
        int isExist=personalFinancialAssetsService.selectByWrapperReturnInt(personalFinancialAssetsQueryWrapper);
        System.out.println("isExist:"+isExist);
        /** 判断：某用户的产品代码存在 且 资产状态为持有时，不能新增 */
        if(isExist==0){
            // 根据 productCode 查询对应的类型
            QueryWrapper<FinancialProduct> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("productCode",bean.getProductCode());
            FinancialProduct financialProduct=financialProductService.selectByWrapperReturnBean(queryWrapper);
            String productType=financialProduct.getProductType();

            // 保留四位小数
            DecimalFormat dfFour =new DecimalFormat("#.0000");

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
                /** 在日期原有基础上增加一天，弥补系统少算的一天 */
                GregorianCalendar gc=new GregorianCalendar();
                gc.setTime(bean.getBuyTime());
                // 由于存进mysql时会减少一天，故在今天的基础上加一天=明天
//                gc.add(5,1);
                bean.setBuyTime(gc.getTime());

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
                /** 在日期原有基础上增加一天，弥补系统少算的一天 */
                GregorianCalendar gc=new GregorianCalendar();
                gc.setTime(bean.getBuyTime());
                // 在今天的基础上加一天=明天
                gc.add(5,1);
                bean.setBuyTime(gc.getTime());
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
                /** 在日期原有基础上增加一天，弥补系统少算的一天 */
                GregorianCalendar gc=new GregorianCalendar();
                gc.setTime(bean.getBuyTime());
                // 在今天的基础上加一天=明天
                gc.add(5,1);
                bean.setBuyTime(gc.getTime());
                return personalFinancialAssetsService.insert(bean);
            }else {
                // TODO: 其他类型则返回 0 ，即新增失败
                return 0;
            }
        }else{
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
