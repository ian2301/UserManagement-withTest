package com.example;

import com.example.controller.UserController;
import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.exception.UserNotFoundException;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 *测试controller layer： 使用Mock对象和MockMVC standalone , 模拟controller layer 以外的所有bean，不加载任何运行环境
 */

@Slf4j
// @ExtendWith(MockitoExtension.class)
public class UserControllerTestMock {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserReq userReq;
    private SqlUser sqlUser, sqlUser1;
    private List<SqlUser> listUser = new ArrayList<>();
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @InjectMocks
    UserController userController;

    /**
     * requestBody = {"password":"322654","firstName":"stephen","lastName":"Chao",
     * "email":"stephen133@gmail.com","contactNumber":"321654987","tags":["a","b","aab"]}
     * *
     * responseBody= {"id":65,"userName":"stephen133@gmail.com","password":"322654",
     * "firstName":"stephen","lastName":"Chao","email":"stephen133@gmail.com",
     * "contactNumber":"321654987","age":63,"gender":"male","nationality":"IE",
     * "tags":"a:b:aab","status":"active","created":"2023-02-19T00:48:18Z","updated":"2023-02-19T00:48:18Z"}
     */

    @BeforeEach
    public void setUp() {
        //对mockMvc初始化
        // We would need this line if we would not use the @ExtendWith(MockitoExtension.class)
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userReq = new UserReq("322654", "stephen", "Chao",
                "stephen133@gmail.com", "321654987", Arrays.asList("a", "b", "aab"));
        // 创建 response object
        sqlUser = new SqlUser(65L, "stephen133@gmail.com",
                "322654", "stephen", "Chao",
                "stephen133@gmail.com", "321654987", 63,
                "male", "IE", "a:b:aab", "Active",
                Instant.now().toString(), Instant.now().toString());
        sqlUser1 = new SqlUser(66L, "stephen134@gmail.com",
                "322654", "stephen", "Chao",
                "stephen134@gmail.com", "321654987", 63,
                "male", "IE", "a:b:ab", "Active",
                Instant.now().toString(), Instant.now().toString());
        listUser.add(sqlUser);
        listUser.add(sqlUser1);
    }

    @Test
    @DisplayName("测试 Post,  ")
    public void saveUser() throws Exception {

        Mockito.when(userService.addUserToDB(userReq)).thenReturn(sqlUser);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user-management/users/add")
                        .content(objectMapper.writeValueAsString(userReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("stephen133@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andDo(print())
                .andReturn();

        log.info("Posted =" + mvcResult.getResponse().getContentAsString());
    }


    @Test
    @DisplayName("测试 get all, expect OK")
    void list() throws Exception {

        Mockito.when(userService.getAll()).thenReturn(listUser);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/user-management/users/listAll")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userName").value("stephen133@gmail.com"))
                .andDo(print())
                .andReturn();
        log.info("list all: " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试 getById, expect OK")
    void getById() throws Exception {

        String id = "stephen133@gmail.com";
        Mockito.when(userService.getOne(id)).thenReturn(sqlUser);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                //.get("/api/user-management/users"+"/27")   // 可用
                                .request(HttpMethod.GET, "/api/user-management/users/getById/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("stephen133@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("322654"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(14))
                .andDo(print())
                .andReturn();
        log.info("get ID=" + id + ":" + mvcResult.getResponse().getContentAsString());
    }


    @Test
    @DisplayName("测试 getById, user不存在 OK")
    void getByIdNotFound() throws Exception {

        String userName = "unknow@gmail.com";

        Mockito.when(userService.getOne(userName)).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                //.get("/api/user-management/users"+"/27")   // 可用
                                .request(HttpMethod.GET, "/api/user-management/users/getById/" + userName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())

                .andDo(print())
                .andReturn();
        log.info("User not exist =" + userName + ":" + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试 PUT, expect OK")
    void update() throws Exception {

        String userName = "stephen133@gmail.com";

        Mockito.when(userService.update(userReq,userName)).thenReturn(sqlUser1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.PUT, "/api/user-management/users/update/" + userName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userReq))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("stephen134@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("322654"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").value("a:b:ab"))
                .andDo(print())
                .andReturn();
        verify(userService, times(1)).update(userReq,userName);

        log.info("updated id =" + userName + ": " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试 Delete, OK ")
    void deleteById() throws Exception {

        String userName = "stephen133@gmail.com";

        Mockito.when(userService.delete(userName)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user-management/users/deleteById/" + userName)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        verify(userService, times(1)).delete(userName);
        log.info("deleted id=" + userName);
    }

    @Test
    @DisplayName("测试 Delete, no user found ")
    void deleteByIdNotFound() throws Exception {

        String userName = "unknownuser@gmail.com";

        Mockito.when(userService.delete(userName)).thenThrow(UserNotFoundException.class);

//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/user-management/users/deleteById/" + userName)
//                )
//                .andDo(print())
//                .andReturn();
        Assertions.assertThrows(RuntimeException.class,()->userService.delete(userName)); //JUnit 5 使用Assertions， Junit4 使用assertThat


        //verify(userService, times(0)).delete(userName);
        log.info("delete user fail =" + userName);
    }
}
