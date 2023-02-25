package com.example;

import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.service.UserDataFromWeb;
import com.example.service.UserToCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

 @ExtendWith(MockitoExtension.class)
public class UserToCreateTest {

    @Mock
    private UserDataFromWeb userDataFromWeb;
    @InjectMocks
    private UserToCreate userToCreate;

    @Test
    public void UserToAdd(){

        UserReq userReq= new UserReq("322654","stephen","Chao",
                "stephen133@gmail.com","321654987",
                Arrays.asList("a","b","aab"));

        Mockito.when(userDataFromWeb.getAge(userReq.getFirstName())).thenReturn(21);
        Mockito.when(userDataFromWeb.getGender(userReq.getFirstName())).thenReturn("male");
        Mockito.when(userDataFromWeb.getNationCode(userReq.getFirstName())).thenReturn("AU");

        SqlUser userReturn = userToCreate.buildUserToRepository(userReq);

        Assertions.assertTrue(userReturn.getAge()==21);
        Assertions.assertEquals("male",userReturn.getGender());
        Assertions.assertEquals(userReturn.getUserName(),userReq.getEmail());
    }
}
