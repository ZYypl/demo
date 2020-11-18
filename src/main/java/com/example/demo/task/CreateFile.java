package com.example.demo.task;

import com.example.demo.entity.People;
import com.example.demo.service.PeopleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * com.example.demo.task
 *
 * @author ypl
 * @create 2020-11-06 10:31
 */
@Component
public class CreateFile {

    @Resource
    private PeopleService peopleServiceImpl;



    @Scheduled(cron = "0 0 10 ? * MON")//每周一上午十点触发0 0 10 ? * MON
    public void corn() throws Exception {

        List<People> allPeople = peopleServiceImpl.findAllPeople();
        File file = new File("D:/date.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        allPeople.stream().forEach(p->{
            try {
                bufferedWriter.write(p.getId()+"/x03"+p.getName()+"/x03"+p.getAddress()+"/x03"+p.getEmail());

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

}
