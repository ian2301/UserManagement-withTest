package com.example.controller;

import com.example.pojo.UserReq;
import com.example.pojo.entity.SqlUser;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-management")
public class UserController {
    @Autowired
    private UserService userService; //

    @PostMapping("/users/add")
    public ResponseEntity<SqlUser> addUser(@RequestBody @Valid UserReq userReq) { //  @Valid用于检查输入的数据是否合法

        SqlUser newUser = userService.addUserToDB(userReq);
        //return new ResponseEntity<>.( HttpStatus.CREATED);//修改response的返回值为： 201 Created
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/update/{userName}")
    public ResponseEntity<SqlUser> update(@RequestBody UserReq userReq, @PathVariable String userName) {
        SqlUser sqlUser = userService.update(userReq, userName);
        return new ResponseEntity<>(sqlUser, HttpStatus.OK);
    }

    @GetMapping("/users/listAll")
    public ResponseEntity<List<SqlUser>> listUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/users/getById/{userName}")
    public ResponseEntity<SqlUser> getById(@PathVariable String userName) {
        SqlUser user = userService.getOne(userName);
        if (user == null) {
            return ResponseEntity.badRequest().body(user);
        }
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/users/deleteById/{userName}")  //按id删除
    public ResponseEntity<String> deleteById(@PathVariable String userName) {
        if (userService.delete(userName)) {
            //userService.delete(userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
