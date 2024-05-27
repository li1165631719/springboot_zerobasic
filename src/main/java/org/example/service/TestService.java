package org.example.service;

import org.example.mapper.TestMapper;
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
        testMapper.addTestContent("", content);
        return null;
    }

}
