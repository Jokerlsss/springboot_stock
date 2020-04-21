package com.stock.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.pojo.PersonalCollection;
import com.stock.demo.service.PersonalCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:23
 * Description:
 */
@RestController
@RequestMapping(path={"/personalCollection"})
public class PersonalCollectionController implements BaseController<PersonalCollection>{

    @Autowired
    PersonalCollectionService personalCollectionService;

    /**
     * 查询个人收藏项目
     * @param userid
     * @return List<PersonalCollection>
     */
    @GetMapping("listByWrapper")
    public List<PersonalCollection> listByWrapper(@RequestParam(value = "userid") Long userid){
        // TODO 根据查出来的 productCode ，调用查询近一月、近三月、近六月、近一年、近三年收益率方法，存进 map 进行返回
        QueryWrapper<PersonalCollection> personalCollectionQueryWrapper=new QueryWrapper<>();
        personalCollectionQueryWrapper.eq("userid",userid);
        return personalCollectionService.listByWrapper(personalCollectionQueryWrapper);
    }

    @Override
    public List<PersonalCollection> list() {
        return null;
    }

    @Override
    public int insert(PersonalCollection bean) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    /**
     * 删除个人收藏
     * @param userid
     * @param productCode
     * @return
     */
    @PostMapping("deleteByWrapper")
    public int deleteByWrapper(@RequestParam(value = "userid") Long userid,
                      @RequestParam(value = "productCode") Long productCode) {
        int flag=0;
        try {
            QueryWrapper<PersonalCollection> personalCollectionQueryWrapper=new QueryWrapper<>();
            personalCollectionQueryWrapper.eq("productCode",productCode);
            personalCollectionQueryWrapper.eq("userid",userid);
            flag=personalCollectionService.deleteByWrapper(personalCollectionQueryWrapper);
        }catch (Exception e){
            throw e;
        }
        return flag;
    }

    @Override
    public int update(PersonalCollection bean) {
        return 0;
    }

    @Override
    public PersonalCollection load(Long id) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public IPage<PersonalCollection> pager(Long pageNum, Long pageSize) {
        return null;
    }

    @Override
    public PersonalCollection loadByName(String name) {
        return null;
    }

    @Override
    public IPage<PersonalCollection> pagerByName(Wrapper<PersonalCollection> wrapper, Long pageNum, Long pageSize) {
        return null;
    }
}
