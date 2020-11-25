package com.example.demo.service.impl;

import com.example.demo.entity.People;
import com.example.demo.mapper.PeopleMapper;
import com.example.demo.service.PeopleService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class PeopleServiceImpl implements PeopleService {

    @Resource
    private PeopleMapper peopleMapper;

    @Resource(name = "loneRedisTemplate")
    private RedisTemplate loneRedisTemplate;

//    @Resource
//    private S@@@22 dmfmmmmcmcqlSessionTemplate sqlSessionTemplate;

//    @Resource(name = "clusterRedisTemplate")
//    private RedisTemplate clusterRedisTemplate;

    @Override
    public People selectById(Integer id) throws Exception{

        //redis
        ValueOperations<String, People> operations = loneRedisTemplate.opsForValue();

        //redisTemplate.opsForHash().putAll(ley,Map);

         //redisTemplate.opsForHash().p
        String key ="people_" + id;
        Boolean aBoolean = loneRedisTemplate.hasKey(key);
         if(aBoolean){
             return operations.get(key);
         }
//         sqlSessionTemplate.getSqlSessionFactory().openSession();
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

    //cache design
    @Override
    public int updatePeopleById(People people) throws Exception {
        String key = "people_" + people.getId();
        if (loneRedisTemplate.hasKey(key)) {
            //失效
            loneRedisTemplate.expire(key,5, TimeUnit.SECONDS);
        }

        return peopleMapper.updateByPrimaryKey(people);

    }
}
