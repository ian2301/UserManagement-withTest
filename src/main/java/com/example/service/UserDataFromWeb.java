package com.example.service;

import com.example.pojo.Age;
import com.example.pojo.Gender;
import com.example.pojo.Nation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * get：  age from agify.io,
 * gender from Genderize.io ,
 * nationality from Nationalize.io
 * * @return
 */

//@Service
public class UserDataFromWeb {

    public String getNationCode(String name) {
        try {
            Nation nation = new RestTemplate()
                    .getForObject("https://api.Nationalize.io?name={1}", Nation.class, name);
            String nationId = nation.getCountry().get(0).getCountry_id();
            //System.out.println("nation id is :"+nationId);
            return nationId;
        } catch (Exception e) {
            System.out.println("the name can not be found to find nation code.");
            return null;  //如果找不到name，返回null
        }
    }

    public String getGender(String name) {
        //System.out.println("input name for gender:"+name);
        // String name1 ="ian";
        try {
            Gender gender = new RestTemplate()
                    .getForObject("https://api.Genderize.io?name={1}", Gender.class, name);
            //System.out.println(gender.getGender());
            return gender.getGender();
        } catch (Exception e) {
            System.out.println("the name can not be found to find gender.");
            return null;  //如果找不到name，返回null
        }
    }

    public int getAge(String name) {

        try {
            Age age = new RestTemplate()
                    .getForObject("https://api.agify.io?name={1}", Age.class, name);
            return age.getAge();
        } catch (RestClientException e) {   //catch (RestClientException e)  更换为 catch (Exception e)
            //throw new RuntimeException(e);
            System.out.println("the name can not be found to find age.");
            return 0;  //如果找不到name，返回0
        }
    }
}
