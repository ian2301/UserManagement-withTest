package com.example.service;

import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.repository.RepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

//@Service
public class UserToUpdate {

    @Autowired
    private RepositoryImp repositoryImp;

    public SqlUser buildUserForUpdate(UserReq userReq, String userName) {

        SqlUser userFrSql = repositoryImp.getById(userName).get();
        userFrSql.setUserName(userReq.getEmail());
        userFrSql.setPassword(userReq.getPassword());
        userFrSql.setFirstName(userReq.getFirstName());
        userFrSql.setLastName(userReq.getLastName());
        userFrSql.setEmail(userReq.getEmail());
        userFrSql.setContactNumber(userReq.getContactNumber());
        userFrSql.setTags((userReq.getTags()).stream().map(Objects::toString).collect(Collectors.joining(":")));
        userFrSql.setUpdated(Instant.now().toString());

        return userFrSql;
    }
}
