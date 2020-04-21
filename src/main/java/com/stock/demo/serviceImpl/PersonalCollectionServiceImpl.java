package com.stock.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stock.demo.mapper.PersonalCollectionMapper;
import com.stock.demo.pojo.PersonalCollection;
import com.stock.demo.service.PersonalCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘铄
 * Date: 2020/3/23
 * Time: 10:09
 * Description:
 */
@Service
public class PersonalCollectionServiceImpl implements PersonalCollectionService {

    @Autowired
    PersonalCollectionMapper personalCollectionMapper;

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

    @Override
    public List<PersonalCollection> listByWrapper(QueryWrapper<PersonalCollection> wrapper) {
        return personalCollectionMapper.selectList(wrapper);
    }

    /**
     * 删除收藏
     * @param wrapper
     * @return
     */
    @Override
    public int deleteByWrapper(QueryWrapper<PersonalCollection> wrapper) {
        return personalCollectionMapper.delete(wrapper);
    }
}
