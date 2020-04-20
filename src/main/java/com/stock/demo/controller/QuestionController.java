package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Answer;
import com.stock.demo.pojo.Question;
import com.stock.demo.service.AnswerService;
import com.stock.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:35
 * Description:
 */
@RestController
@RequestMapping(path={"/question"})
public class QuestionController implements BaseController<Question>{

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @GetMapping("getQA")
    public List getQA(){
        List QAList=new ArrayList();

        /** 获取 questionList */
        List<Question> questionList=questionService.listByWrapper(null);

        for(int i=0;i<questionList.size();i++){
            Map<String,Object> QAmap = new HashMap<String,Object>(10);
            int questionid=questionList.get(i).getQuestionid();

            /** 根据 questionid 查找对应的答案 */
            QueryWrapper<Answer> answerQueryWrapper=new QueryWrapper<>();
            answerQueryWrapper.eq("questionid",questionid);
            List<Answer> answerList=answerService.listByWrapper(answerQueryWrapper);

            QAmap.put("questionid",questionid);
            QAmap.put("questionContent",questionList.get(i).getQuestioncontent());
            QAmap.put("answerList",answerList);

            QAList.add(QAmap);
        }
        return QAList;
        /**
         *
         * 声明 QAList
         * 获取 questionList
         * for(List.size()){
         *     声明 QA map
         *     根据 List.get(i).questionid 查找 answer 表中的答案，return List<Answer>
         *     map.put("questionid",questionid);
         *     ...
         * }
         */
    }

    @Override
    public List<Question> list() {
        return null;
    }

    @Override
    public int insert(Question bean) throws ParseException {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Question bean) {
        return 0;
    }

    @Override
    public Question load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Question> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Question loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Question> pagerByName(Wrapper<Question> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
