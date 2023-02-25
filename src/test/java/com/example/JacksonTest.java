package com.example;

import com.example.pojo.UserReq;
import com.example.pojo.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class JacksonTest {

    @Test
    public void jacksonTest() throws JsonProcessingException {
        //jackson 的ObjectMapper: 用于转换对象
        ObjectMapper mapper=new ObjectMapper();

        //创建对象
//                ArrayList<String > arr = (ArrayList<String>) Arrays.asList("a","b","aab");
//                String[] st = {"a","b","aab"};
//                ArrayList<String> arr1 = Lists.newArrayList(st);

                ArrayList<String> arr = Lists.newArrayList("a","b","aab");

        UserReq testUser= UserReq.builder()
                .contactNumber("321654987")
                .email("jess@gmail.com")
                .firstName("Jess")
                .lastName("Chao")
                .password("322654")
                .tags(arr).build();
        //转换成Json字符串
        String userString = mapper.writeValueAsString(testUser);

        System.out.println(userString);
    }
}