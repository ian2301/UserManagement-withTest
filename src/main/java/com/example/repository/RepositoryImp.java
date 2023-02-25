package com.example.repository;

import com.example.pojo.entity.SqlUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RepositoryImp {
    @Autowired
    private SqlUserRepository sqlUserRepository;
    public List<SqlUser> listAll(){

        return sqlUserRepository.findAll();
    }

    public SqlUser save(SqlUser sqlUser){

        sqlUserRepository.save(sqlUser);
        return sqlUser;
    }

    public SqlUser update(SqlUser sqlUser) {

        sqlUserRepository.save(sqlUser);
        return sqlUser;
    }

    public Optional<SqlUser> getById(String  user_name){

        Optional<SqlUser> sqlUser =sqlUserRepository.findById(user_name);
        //System.out.println("***sqluser  =" +sqlUser);
        return sqlUser;
    }

    public Optional<SqlUser> deleteById(String  user_name){

        Optional<SqlUser> sqlUser =sqlUserRepository.findById(user_name);
        sqlUserRepository.deleteById(user_name);
        return sqlUser;
    }
}
