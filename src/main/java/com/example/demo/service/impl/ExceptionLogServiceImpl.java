package com.example.demo.service.impl;

import com.example.demo.entity.ExceptionLog;
import com.example.demo.mapper.ExceptionLogMapper;
import com.example.demo.service.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.example.demo.service.impl
 *
 * @author ypl
 * @create 2020-11-02 10:28
 */


@Service
@Transactional
public class ExceptionLogServiceImpl implements ExceptionLogService {

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Override
    public void insert(ExceptionLog excepLog) {
        exceptionLogMapper.insert(excepLog);
    }
}
