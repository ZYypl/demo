package com.example.demo.controller;

import com.example.demo.annotation.OperLog;
import com.example.demo.comment.ApiResult;
import com.example.demo.constant.OperLogConstant;
import com.example.demo.entity.People;
import com.example.demo.service.PeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * com.example.demo.controller
 *
 * @author ypl
 * @create 2020-06-16 09:37
 */
@RestController
@RequestMapping("/test/demo/")
public class DemoController {

    @Resource
    private PeopleService peopleServiceImpl;


    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 通过查询用户
     * @param id 用户id
     */
    @GetMapping("/{id}")
    @OperLog(operModul = "用户管理",operType = OperLogConstant.OPER_SELECT,operDesc = "通过用户id查询用户")
    public ApiResult testDemo(@PathVariable Integer id) {

        if(StringUtils.isEmpty(id)){
            return new ApiResult(false, "id为空");
        }
        System.out.println(id);
        People people = null;
        try {
            people = peopleServiceImpl.selectById(id);
        } catch (Exception e) {
            logger.error("查询数据报错",e);
        }
        return new ApiResult(true, people, "SUCCESS");
    }

    @GetMapping("findAll")
    @OperLog(operModul = "用户管理",operType = OperLogConstant.OPER_SELECT,operDesc = "查询所有用户")
    public ApiResult findAll() {
        List<People> allPeople =null;
        try {
              allPeople = peopleServiceImpl.findAllPeople();
        } catch (Exception e) {
            logger.error("select error",e);
        }
        return new ApiResult(true, allPeople, "SUCCESS");
    }




    @PostMapping("/modify")
    @OperLog(operModul = "用户管理",operType = OperLogConstant.OPER_UPDATE,operDesc = "修改用户信息")
    public ApiResult modifyPeople(@RequestBody People p) {
        int i=0;
        try {
              i = peopleServiceImpl.updatePeopleById(p);
        } catch (Exception e) {
            logger.error("update error ",e);
        }

        return new ApiResult(true, i , "SUCCESS");
    }


    @PostMapping("/insert")
    @OperLog(operModul = "用户管理",operType = OperLogConstant.OPER_ADD,operDesc = "新增用户")
    public ApiResult insertPeople(@RequestBody People p) {
        int i=0;
        try {
            i = peopleServiceImpl.insertPeople(p);
        } catch (Exception e) {
            logger.error("insert error ",e);
        }

        return new ApiResult(true, i , "SUCCESS");
    }




    /**
     *japidocs 不好使
     */


//    @GetMapping("/hbase/selectAllTable")
//    public ApiResult selectAllTable() {
//
//
//        List<String> strings = HbaseUtil.selectAllTables();
//
//
//        return new ApiResult(true ,strings, "SUCCESS");
//    }


}
