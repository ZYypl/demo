package com.example.demo.mapper;

import com.example.demo.entity.ExceptionLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * com.example.demo.mapper
 *
 * @author ypl
 * @create 2020-11-02 10:30
 */

@Repository
public interface ExceptionLogMapper extends Mapper<ExceptionLog> {
}
