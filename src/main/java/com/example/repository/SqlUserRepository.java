package com.example.repository;

import com.example.pojo.entity.SqlUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlUserRepository extends JpaRepository<SqlUser ,String > {
}
