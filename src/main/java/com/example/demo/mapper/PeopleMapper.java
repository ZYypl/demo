package com.example.demo.mapper;

import com.example.demo.entity.People;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


import java.util.List;

/**
 * com.example.demo.mapper
 *
 * @author ypl
 * @create 2020-08-12 09:54
 */
@Repository
public interface PeopleMapper  extends Mapper<People> {

    List<People> findAllPeople();
}
