package com.example.config;

import com.example.pojo.entity.SqlUser;
import com.example.repository.RepositoryImp;
import com.example.service.UserDataFromWeb;
import com.example.service.UserService;
import com.example.service.UserToCreate;
import com.example.service.UserToUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class ControllerConfig {

    @Bean
    SqlUser sqlUser() {
        return new SqlUser();
    }

    @Bean
    RepositoryImp repositoryImp() {
        return new RepositoryImp();
    }


    @Bean
    UserDataFromWeb userDataFromWeb() {
        return new UserDataFromWeb();
    }


    @Bean
    UserToCreate userToAdd() {
        return new UserToCreate();
    }

    @Bean
    UserToUpdate userToUpdate() {
        return new UserToUpdate();
    }

    @Bean
    UserService userService() {
        return new UserService();
    }
}
