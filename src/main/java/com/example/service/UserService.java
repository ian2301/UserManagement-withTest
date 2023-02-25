package com.example.service;

import com.example.exception.UserNotFoundException;
import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.repository.RepositoryImp;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * 创建要提供的服务，method form RepositoryImp.
 */
@Transactional
@Slf4j
//@Service
public class UserService {

    @Resource
    private RepositoryImp repositoryImp;
    @Autowired
    private UserToCreate userToCreate;
    @Autowired
    private UserToUpdate userToUpdate;

    //  ****  Add User to DB
    public SqlUser addUserToDB(UserReq userReq) {

        SqlUser sqlUser = userToCreate.buildUserToRepository(userReq);
        //System.out.println("User to be saved=  " +sqlUser);
        repositoryImp.save(sqlUser);
        log.info("user created  =" + sqlUser);
        return sqlUser;
    }

    //  ****  Update  User in DB
    public SqlUser update(UserReq userReq, String userName) {

        SqlUser sqlUser = userToUpdate.buildUserForUpdate(userReq, userName);
        repositoryImp.update(sqlUser);
        log.info("user was updated  =" + sqlUser);
        return sqlUser;
    }

    //  ****  List all User in DB
    public List<SqlUser> getAll() {

        return repositoryImp.listAll();
    }

    //  ****  Get one User from DB
    public SqlUser getOne(String userName) {
        Optional<SqlUser> sqlUser = repositoryImp.getById(userName);
        if (sqlUser.isPresent()) {
            //    repositoryImp.getById(userName);
            return sqlUser.get();
        } else {
            //return null;
            throw new UserNotFoundException("User Not Found withUserName=" + userName);
        }
    }

    //  ****  remove one User from DB
    public boolean delete(String userName) {

        Optional<SqlUser> userFrSql = repositoryImp.getById(userName);

        if (userFrSql.isPresent()) {
            repositoryImp.deleteById(userName);
            log.info(" used was deleted =" + userFrSql);
            return true;
        }
        return false;
    }
}
