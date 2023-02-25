package com.example;

import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.repository.RepositoryImp;
import com.example.exception.UserNotFoundException;
import com.example.service.UserService;
import com.example.service.UserToCreate;
import com.example.service.UserToUpdate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @Mock
    private UserToCreate userToCreate = new UserToCreate();
    @Mock
    private UserToUpdate userToUpdate = new UserToUpdate();
    @Mock
    private UserReq userReq = new UserReq();
    @Mock
    private SqlUser sqlUser, sqlUser1 = new SqlUser();
    private List<SqlUser> listUser = new ArrayList<>();

    @Mock
    private RepositoryImp repositoryImp;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {

        //初始化
        MockitoAnnotations.openMocks(this);

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

    @DisplayName("保存user到DB")
    @Test
    public void addUserToDB() {

        Mockito.when(userToCreate.buildUserToRepository(userReq)).thenReturn(sqlUser);
        Mockito.when(repositoryImp.save(sqlUser)).thenReturn(sqlUser);

        SqlUser sqlUserAdd = userService.addUserToDB(userReq);

        Assertions.assertSame(sqlUser, sqlUserAdd);
        Assertions.assertNotNull(sqlUserAdd);
        verify(repositoryImp, times(1)).save(sqlUser);
    }

    @DisplayName("Update user到DB")
    @Test
    public void update() {

        String userName = "stephen133@gmail.com";
        Mockito.when(userToUpdate.buildUserForUpdate(userReq, userName)).thenReturn(sqlUser);
        Mockito.when(repositoryImp.update(sqlUser)).thenReturn(sqlUser);

        SqlUser sqlUserUpdate = userService.update(userReq, userName);
        Assertions.assertSame(sqlUser, sqlUserUpdate);
        Assertions.assertNotNull(sqlUserUpdate);
        verify(repositoryImp, times(1)).update(sqlUser);

    }

    @DisplayName("查询所有User")
    @Test
    public void getAll() {

        Mockito.when(repositoryImp.listAll()).thenReturn(listUser);

        List<SqlUser> listReturned = userService.getAll();

        Assertions.assertNotNull(listReturned);
        //MatcherAssert.assertThat(listReturned.stream().map(),);
        verify(repositoryImp, times(1)).listAll();
    }

    @DisplayName("查询一个user")
    @Test
    public void getOne() {

        String userName = "stephen133@gmail.com";

        Mockito.when(repositoryImp.getById(userName)).thenReturn(Optional.ofNullable(sqlUser));

        SqlUser sqlUserReturned = userService.getOne(userName);

        Assertions.assertNotNull(sqlUserReturned);
        Assertions.assertSame(sqlUser, sqlUserReturned);
        Assertions.assertEquals("stephen133@gmail.com", sqlUserReturned.getUserName());

        verify(repositoryImp, times(1)).getById(userName);
    }

    @DisplayName("查询一个不存在user")
    @Test
    public void getOneButNotFound() {

        String username = "unknown@gmail.com";

        Mockito.when(repositoryImp.getById(username)).thenReturn(Optional.empty());
        //userService.getOne(username).;
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getOne(username));
        verify(repositoryImp, times(1)).getById(username);
    }

    @DisplayName("正常删除 ")
    @Test
    public void delete() {
        String userName = "stephen133@gmail.com";

        Mockito.when(repositoryImp.getById(userName)).thenReturn(Optional.ofNullable(sqlUser));

        boolean result = userService.delete(userName);
        Assertions.assertTrue(result);
        verify(repositoryImp, times(1)).deleteById(userName);
    }

    @DisplayName("User 不存在 ")
    @Test
    public void deleteUserNotFound() {

        String userName = "unknown@gmail.com";

        Mockito.when(repositoryImp.getById(userName)).thenReturn(Optional.empty());

        boolean result = userService.delete(userName);
        Assertions.assertFalse(result);
        verify(repositoryImp, times(0)).deleteById(userName);
    }
}
