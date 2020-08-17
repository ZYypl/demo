package com.example.demo.service.impl;

import com.example.demo.entity.People;
import com.example.demo.mapper.PeopleMapper;
import com.example.demo.service.PeopleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.example.demo.service.impl
 *
 * @author ypl
 * @create 2020-08-12 16:55
 */

@Service
public class PeopleServiceImpl implements PeopleService {

    @Resource
    private PeopleMapper peopleMapper;
    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public People selectById(Integer id) throws Exception{

        ValueOperations<String, People> operations = redisTemplate.opsForValue();
        String key ="people_" + id;
        Boolean aBoolean = redisTemplate.hasKey(key);
         if(aBoolean){
             return operations.get(key);
         }
        People people = peopleMapper.selectByPrimaryKey(id);
        operations.set(key, people);
        return people;
    }

    @Override
    public int insertPeople(People people) throws Exception{
        return peopleMapper.insertSelective(people);
    }

    @Override
    public List<People> findAllPeople() throws Exception{
        return peopleMapper.findAllPeople();
    }

    @Override
    public int updatePeopleById(People people) throws Exception {
        String key = "people_" + people.getId();
        if (redisTemplate.hasKey(key)) {
            //失效
            redisTemplate.expire(key,5, TimeUnit.SECONDS);
        }

        return peopleMapper.updateByPrimaryKey(people);

    }
}
