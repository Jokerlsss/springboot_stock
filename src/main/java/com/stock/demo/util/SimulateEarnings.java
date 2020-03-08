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
    public float simulateDailyChange(float lastDailyChange){
        float nowDailyChange=0;
        // DailyChange between -2 and 2
        if(lastDailyChange>-2 && lastDailyChange<=2){
            boolean isEarnings=countProbability(50);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        // DailyChange between 2 and 10
        if(lastDailyChange>2 && lastDailyChange<=4){
            boolean isEarnings=countProbability(45);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange>4 && lastDailyChange<=6){
            boolean isEarnings=countProbability(40);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange>6 && lastDailyChange<=8){
            boolean isEarnings=countProbability(35);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange>8 && lastDailyChange<=10){
            boolean isEarnings=countProbability(30);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        // DailyChange between -2 and -10
        if(lastDailyChange<-2 && lastDailyChange>=-4){
            boolean isEarnings=countProbability(45);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange<-4 && lastDailyChange>=-6){
            boolean isEarnings=countProbability(40);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange<-6 && lastDailyChange>=-8){
            boolean isEarnings=countProbability(35);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        if(lastDailyChange<-8 && lastDailyChange>=-10){
            boolean isEarnings=countProbability(30);
            nowDailyChange = countNowDailyChange(isEarnings);
        }
        return nowDailyChange;
    }

    /**
     * To decide the price up or fall depend on probability
     * TODO: Use df.format to change the grammar of data
     * @param isEarnings
     * @return nowDailyChange
     */
    public float countNowDailyChange(boolean isEarnings){
        float nowDailyChange=0;
        DecimalFormat df =new DecimalFormat("#0.00");

        if(isEarnings){
            float max=10,min=0;
            nowDailyChange = (float)(Math.random()*(max-min)+min);
            System.out.println("收入:"+df.format(nowDailyChange));
        }else{
            float max=0,min=-10;
            nowDailyChange = (float)(Math.random()*(max-min)+min);
            System.out.println("亏损:"+df.format(nowDailyChange));
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
        System.out.println("随机概率:"+randomNum);
        if(randomNum<=probability){
            isEarnings=true;
        }
        else{
            isEarnings=false;
        }
        return isEarnings;
    }
}
