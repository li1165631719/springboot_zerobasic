package org.example.service;

import org.example.mapper.TestMapper;
import org.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李志豪
 * @create 2024/5/27
 */
@Service
public class TestService {

    @Autowired
    TestMapper testMapper;

    public String addTestContent(String content) {
        String id =CommonUtil.generateUUID();
        testMapper.addTestContent(id, content);
        return id;
    }

    public String testMysqlQuery(String id){
        return testMapper.queryTestContentById(id);
    }

}
