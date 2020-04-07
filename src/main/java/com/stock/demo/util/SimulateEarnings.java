package com.stock.demo.util;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/8
 * Time: 15:03
 * Description: About Simulate earnings's util methods
 */
public class SimulateEarnings {
    private boolean isEarnings;
    /**
     * Simulate daily change
     * When dailyChange between 2% and -2% , up & fall's probability each half(50%)
     * When dailyChange between 2% and 4% , up & fall's probability is 45% & 55%
     * When dailyChange between 4% and 6% , up & fall's probability is 40% & 60%
     * When dailyChange between 6% and 8% , up & fall's probability is 35% & 65%
     * When dailyChange between 8% and 10% , up & fall's probability is 30% & 70%
     * And vice versa
     * @param lastDailyChange
     * @return dailyChange
     */
    // TODO 净值的生成太过于平均且集中，如何加强随机性使得起伏更大呢？（非重点）
    public float simulateDailyChange(float lastDailyChange){
        float nowDailyChange=0;
        DecimalFormat df =new DecimalFormat("#0.00");
        // 设置1-100之间的概率数
        float max=100,min=1;
        float randomNum = (float)(Math.random()*(max-min)+min);
        // DailyChange between -2 and 2
        if(lastDailyChange>-2 && lastDailyChange<=2){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        // DailyChange between 2 and 10
        if(lastDailyChange>2 && lastDailyChange<=4){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange>4 && lastDailyChange<=6){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange>6 && lastDailyChange<=8){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange>8 && lastDailyChange<=10){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        // DailyChange between -2 and -10
        if(lastDailyChange<-2 && lastDailyChange>=-4){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange<-4 && lastDailyChange>=-6){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange<-6 && lastDailyChange>=-8){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange<-8 && lastDailyChange>=-10){
            boolean isEarnings=countProbability(randomNum);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        // 格式化：结果保留两位小数(String) -> 转换成 float 类型
        float nowDailyChangeFloat = Float.parseFloat(df.format(nowDailyChange));
        return nowDailyChangeFloat;
    }

    /**
     * To decide the price up or fall depend on probability
     * @param isEarnings
     * @return nowDailyChange
     */
    public float countNowDailyChange(boolean isEarnings){
        float nowDailyChange=0;
        // 设定涨跌幅最大值为10，最小值为-10
        if(isEarnings){
            float max=10,min=0;
            nowDailyChange = (float)(Math.random()*(max-min)+min);
        }else{
            float max=0,min=-10;
            nowDailyChange = (float)(Math.random()*(max-min)+min);
        }
        return nowDailyChange;
    }

    /**
     * To adjust the price up & fall probability
     * @param probability
     * @return isEarnings
     */
    public boolean countProbability(float probability){
        float max=100,min=1;
        float randomNum = (float)(Math.random()*(max-min)+min);
//        System.out.println("随机概率:"+randomNum);
        if(randomNum<=probability){
            // 盈利
            isEarnings=true;
        }
        else{
            // 亏损
            isEarnings=false;
        }
        return isEarnings;
    }
}
