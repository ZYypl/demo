package com.example.demo.service.impl;

import com.example.demo.entity.OperationLog;
import com.example.demo.mapper.OperationLogMapper;
import com.example.demo.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.example.demo.service.impl
 *
 * @author ypl
 * @create 2020-11-02 10:27
 */
@Service
@Transactional
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void insert(OperationLog operlog) {
        operationLogMapper.insert(operlog);
    }
}
