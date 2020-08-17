package com.example.demo.service;

import com.example.demo.entity.People;

import java.util.List;

/**
 * com.example.demo.service
 *
 * @author ypl
 * @create 2020-08-12 16:55
 */
public interface PeopleService {

    People selectById(Integer id) throws Exception;

    int insertPeople(People people) throws Exception;

    List<People> findAllPeople() throws Exception;

    int updatePeopleById(People people) throws Exception;
}
