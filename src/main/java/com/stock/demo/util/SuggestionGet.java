package com.stock.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/25
 * Time: 22:28
 * Description:
 */
@Component
public class SuggestionGet {

    @Autowired
    private SuggestionGet suggestionGet;

    /**
     * 根据用户的投资性格，返回建议
     * @param character
     * @param low
     * @param middle_low
     * @param middle
     * @param middle_high
     * @return
     */
    public Map<String,Object> suggestionGet(String character,float low, float middle_low, float middle, float middle_high){
        Map<String,Object> resultMap=new HashMap<String,Object>(10);
        if(character.equals("保守")){
            return suggestionGet.conservativeGet(low,middle_low,middle,middle_high);
        }else if(character.equals("谨慎")){
            return suggestionGet.cautiousGet(low,middle_low,middle,middle_high);
        }else if(character.equals("稳健")){
            return suggestionGet.steadyGet(low,middle_low,middle,middle_high);
        }else if(character.equals("积极")){
            return suggestionGet.positiveGet(low,middle_low,middle,middle_high);
        }else if(character.equals("激进")){
            return suggestionGet.radicalGet(low,middle_low,middle,middle_high);
        }else{
            return null;
        }
    }

    /**
     * 保守：conservative
     * 谨慎：cautious
     * 稳健：steady
     * 积极：positive
     * 激进：radical
     *
     * 低风险
     * 中低风险
     * 中风险
     * 中高风险
     */

    /**
     * 保守
     * >>> 建议只拥有低风险
     * @param low
     * @param middle_low
     * @param middle
     * @param middle_high
     * @return
     */
    public Map<String,Object> conservativeGet(float low, float middle_low, float middle, float middle_high){
        /** 声明 判定结果（分配合适 & 过于保守 & 过于激进） */
        String result=null;
        String suggestions=null;

        /** 声明：结果集 */
        Map<String,Object> resultMap=new HashMap<String,Object>(10);
        if(low==100){
            result="分配合适";
            suggestions="保持目前的低风险资产持有率，获取稳健收益";
        }else if(low<100){
            result="过于激进";
            suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险资产的持仓，降低其他更高风险资产的持仓。";
        }
        resultMap.put("result",result);
        resultMap.put("suggestions",suggestions);

        return resultMap;
    }

    /**
     * 谨慎
     * >> 低风险占比应高于80%，中低风险比例不高于20%，低风险占比高于95%：过于保守
     * @param low
     * @param middle_low
     * @param middle
     * @param middle_high
     * @return
     */
    public Map<String,Object> cautiousGet(float low, float middle_low, float middle, float middle_high){
        /** 声明 判定结果（分配合适 & 过于保守 & 过于激进） */
        String result=null;
        String suggestions=null;

        /** 声明：结果集 */
        Map<String,Object> resultMap=new HashMap<String,Object>(10);

        if(low>=80){
            if(low>=95){
                // 低风险资产占比：偏高
                result="过于保守";
                suggestions="    目前资产分配过于保守，建议适当增加中低风险资产占比，提升收益能力。";
            }else if(low<95 && middle==0 && middle_high==0){
                // 低风险资产：80~95 且无 中 & 中高 风险资产：分配合适
                result="分配合适";
                suggestions="    保持目前的资产持有率，稳中求进。";
            }else if(middle>0 || middle_high>0){
                // 中 或 中高风险资产占比：偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险和中低风险资产的持仓，降低其他更高风险资产的持仓占比。";
            }
        }else if(low<80){
            if(middle_low>=20){
                // 中低风险占比：偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险资产的持有占比，降低中低风险资产占比。";
            }else if(middle>0 || middle_high>0){
                // 中 或 中高风险资产占比：偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险和中低风险资产的持仓，降低其他更高风险资产的持仓占比。";
            }
        }
        resultMap.put("result",result);
        resultMap.put("suggestions",suggestions);

        return resultMap;
    }

    /**
     * 稳健
     * >> 低风险占比应高于70%，中风险不高于15%，中高风险为 0
     * >> 低风险占比高于90%，过于保守
     * @param low
     * @param middle_low
     * @param middle
     * @param middle_high
     * @return
     */
    public Map<String,Object> steadyGet(float low, float middle_low, float middle, float middle_high){
        /** 声明 判定结果（分配合适 & 过于保守 & 过于激进） */
        String result=null;
        String suggestions=null;

        /** 声明：结果集 */
        Map<String,Object> resultMap=new HashMap<String,Object>(10);

        if(low>=70){
            if(middle_high>0){
                // 中高风险占比：偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当降低中高风险资产的持仓，保障资金安全。";
            }else if(middle_high==0 && middle>=15){
                // 中风险占比：偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险和中低风险资产的持仓，降低中风险资产的持仓。";
            }else if(middle_high==0 && middle<15){
                // 分配合适
                result="分配合适";
                suggestions="    保持目前的资产持有率，稳中求进。";
            }else if(low>=90){
                // 过于保守
                result="过于保守";
                suggestions="    目前资产风险过于保守，建议适当增加中低风险或中风险资产占比，提升总体资产收益能力。";
            }else{
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险资产的持仓，降低其他更高风险资产的持仓，保障资金安全。";
            }
        }else if(low<70){
            if(middle_high>0){
                // 中高风险占比：偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险和中低风险资产的持仓，降低中高风险资产的持仓。";
            }else if(middle_high==0 && middle_low>=30){
                // 中低风险占比：偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险资产的持仓，降低中低风险资产的持仓，保障资金安全。";
            }else{
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加较低风险资产的持仓，降低其他更高风险资产的持仓，保障资金安全。";
            }
        }
        resultMap.put("result",result);
        resultMap.put("suggestions",suggestions);

        return resultMap;
    }

    /**
     * positive:积极
     * >> 低风险占比应高于50%，中高风险占比不高于30%，低风险占比超过80%：过于保守
     * @param low
     * @param middle_low
     * @param middle
     * @param middle_high
     * @return
     */
    public Map<String,Object> positiveGet(float low, float middle_low, float middle, float middle_high){
        /** 声明 判定结果（分配合适 & 过于保守 & 过于激进） */
        String result=null;
        String suggestions=null;

        /** 声明：结果集 */
        Map<String,Object> resultMap=new HashMap<String,Object>(10);

        if(low>=50){
            if(middle_high<30 && low<70){
                // 低风险占比>=50 & 中高风险<30：分配合适
                result="分配合适";
                suggestions="    保持目前资产持有率，能在保证风险的同时获取更多的收益。";
            }else if(middle_high>=30){
                // 中高风险超过30%：风险偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加较低风险资产的持仓，降低中高风险资产的持仓，以保障资金安全。";
            }else if(low>=70){
                // 低风险持有资产超过70%：过于保守
                result="过于保守";
                suggestions="    目前资产分配过于保守，建议适当增加较高风险资产，以获得更为可观的收益。";
            }
        }else if(low<50){
            if(middle_high>=30){
                // 中高风险超过30%：风险偏高
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加较低风险资产的持仓，降低中高风险资产的持仓，以保障资金安全。";
            }else{
                // 中、中低资产过多
                result="过于激进";
                suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加低风险资产的持仓，降低中等风险资产的持仓，保障资金安全的同时获取可观的收益。";
            }
        }

        resultMap.put("result",result);
        resultMap.put("suggestions",suggestions);

        return resultMap;
    }

    /**
     * radical：激进
     * >> 中高风险不高于50%，低风险资产超过60则过于保守
     * @param low
     * @param middle_low
     * @param middle
     * @param middle_high
     * @return
     */
    public Map<String,Object> radicalGet(float low, float middle_low, float middle, float middle_high){
        /** 声明 判定结果（分配合适 & 过于保守 & 过于激进） */
        String result=null;
        String suggestions=null;

        /** 声明：结果集 */
        Map<String,Object> resultMap=new HashMap<String,Object>(10);

        if(middle_high<50){
            if (low >= 60) {
                // 低风险资产超过60%：过分保守
                result="过于保守";
                suggestions="    目前资产投资占比过于保守，建议适当增加较高风险资产的持仓，以获取更加可观的收益。";
            }else{
                // 中高风险不超过50% 且 不过于保守：分配合适
                result="分配合适";
                suggestions="    保持目前资产占比分配，在可控制的风险范围内获取可观的收益。";
            }
        }else{
            // 中高风险资产超过50%：偏高
            result="过于激进";
            suggestions="    目前资产风险较高，已超出您的风险承受能力，建议适当增加较低风险资产的持仓，降低中高风险资产的持仓，保障资金安全的同时获取可观的收益。";
        }

        resultMap.put("result",result);
        resultMap.put("suggestions",suggestions);

        return resultMap;
    }
}
