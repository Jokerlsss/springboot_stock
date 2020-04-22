package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.Answer;
import com.stock.demo.pojo.User;
import com.stock.demo.service.AnswerService;
import com.stock.demo.service.UserService;
import com.stock.demo.util.PersonalAssetsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/4/20
 * Time: 10:36
 * Description:
 */
@RestController
@RequestMapping(path={"/answer"})
public class AnswerController implements BaseController<Answer> {

    /**
     * 总分：38分
     * ------------
     * 保守：8-14
     * 谨慎：15-20
     * 稳健：21-28
     * 积极：29-34
     * 激进：35-38
     * ------------
     */

    @Autowired
    AnswerService answerService;

    @Autowired
    UserService userService;

    @Autowired
    PersonalAssetsUtil personalAssetsUtil;

    /**
     * 提交答题问卷，更新用户理财性格
     * @param answerList
     * @param userid
     */
    @GetMapping("commitAnswer")
    public String commitAnswer(@RequestParam String answerList,
                             @RequestParam(value = "userid") Long userid){
        /** 声明：总分 */
        int totalGrade=0;

        /** 按照双引号分割字符串 */
        String[] tempList = answerList.split("\"");

        /** 1,3,5,7..字符串的单数位置为题目答案，双数位置为分割出来的空字符 */
        for (int i=1;i<tempList.length;i=i+2){
            /** 根据 answerid 查询出对应的分数，进行累加 */
            QueryWrapper<Answer> answerQueryWrapper=new QueryWrapper<>();
            answerQueryWrapper.eq("answerid",tempList[i]);
            Answer answer=answerService.beanByWrapper(answerQueryWrapper);

            int grade=answer.getGrade();
            System.out.println("grade"+grade);

            totalGrade=totalGrade+grade;
        }
        /** 根据分数判断对应的投资性格 */
        String character=personalAssetsUtil.getCharacter(totalGrade);

        /** 将投资性格更新进 user 表中 */
        User user=new User();
        user.setInertmentCharacter(character);

        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("userID",userid);
        int flag=userService.updateByWrapper(user,userQueryWrapper);
        if(flag==1){
            return character;
        }else{
            return null;
        }
    }

    @Override
    public List<Answer> list() {
        return null;
    }

    @Override
    public int insert(Answer bean) throws ParseException {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Answer bean) {
        return 0;
    }

    @Override
    public Answer load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<Answer> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public Answer loadByName(String name) {
        return null;
    }

    @Override
    public IPage<Answer> pagerByName(Wrapper<Answer> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
