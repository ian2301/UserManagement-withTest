package com.example;

import com.example.pojo.Age;
import com.example.pojo.Country;
import com.example.pojo.Gender;
import com.example.pojo.Nation;
import com.example.service.UserDataFromWeb;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Callable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

/**
 * 测试RestTemplate,
 */

@Slf4j
class UserDataFromWebTest {

    @Mock
    private TestRestTemplate template = new TestRestTemplate();

    @InjectMocks
    private UserDataFromWeb userDataFromWeb = new UserDataFromWeb();

    @BeforeEach
    public void setUp() {
        //初始化
        MockitoAnnotations.openMocks(this);
    }


   // @Test
    @ParameterizedTest
    @ValueSource(strings = {"tony"})
    void getNationCode(String name) {

        doReturn(nation).when(template).
                getForObject("https://api.Nationalize.io?name={1}", Nation.class, name);

//        Mockito.when(template.   //与doReturn().when()功能一样
//                getForObject("https://api.Nationalize.io?name={1}", Nation.class, name)).thenReturn(nation);

        String nationCode = userDataFromWeb.getNationCode(name);

        assertThat(nationCode,isA(String.class)); //是否字符串
        assertThat(nationCode, notNullValue());  //是否空
        assertThat(nationCode.length(), equalTo(2));   //

        MatcherAssert.assertThat(template.
                getForObject("https://api.Nationalize.io?name={1}", Nation.class, name), hasProperty("country"));

        log.info("gen = "+nationCode);
    }

    //@Test  //  not passed
    @ParameterizedTest
    @ValueSource(strings = {"tony"})
    void getGender(String name){

        Mockito.when(template.
                getForObject("https://api.Genderize.io?name={1}", Gender.class, name)).thenReturn(genderObject);

        String gen = userDataFromWeb.getGender(name);

        assertThat(gen,isA(String.class)); //是否字符串
        assertThat(gen, notNullValue());  //是否空
        assertThat(gen, is("male"));   //性别是male
        assertThat(gen, equalToIgnoringCase("male")); // 忽略大小写，判断是否为male
        assertThat(gen, not(equalToIgnoringCase("female")));   //忽略大小写，判断不为female

        MatcherAssert.assertThat(template.
                getForObject("https://api.Genderize.io?name={1}", Gender.class, name), hasProperty("gender"));

        log.info("gen = "+gen);
    }

    //@Test
    @ParameterizedTest
    @ValueSource(strings = {"tony"})
    void getAge(String name) {


        Mockito.when(template.
                getForObject("https://api.agify.io?name={1}", Age.class, name)).thenReturn(ageObject);

       assertThat(template.
                getForObject("https://api.agify.io?name={1}", Age.class, name), hasProperty("age"));


        int age = userDataFromWeb.getAge(name);

        assertThat(age,isA(Integer.class));
        assertThat(age,notNullValue());
        assertTrue(age >= 0, "age not small than 0");  // 大于或等于0为true
        assertFalse(age > 100, "age not big than 100");  //  大于100 为 false
        //assertThat(age, greaterThanOrEqualTo(100));

 /**       assertThat(this.restTemplate.getForObject("http://localhost:" + "port" + "/",
                String.class)).contains("Hello, World");
  */
        log.info("Age = "+age);

    }

    //  Json 数据 from Web.
      private Country country1 = new Country("NZ", 0.059);
       private Country country2 = new Country("AU", 0.058);
       private Country country3 = new Country("GB", 0.050);
       private List<Country> list =  Arrays.asList(country1,country2,country3);
       private Nation nation= new Nation("tony",list);

    private Age ageObject = new Age(59,128604,"tony");
    private Gender genderObject = new Gender(560984,"male","tony",1.0);
}