package com.grandet.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grandet.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by outen on 16/6/29.
 */

@Service
public class UserService {
    @Autowired
    private SqlSession sqlSession;

    public User userLogin(String username, String password){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        map.put("password", password);
        System.out.println(map.toString());
        User user = sqlSession.selectOne("getUserByUsernameAndPassword", map);
        return user;
    }

    public User getUser(int id){
        return sqlSession.selectOne("getUser", id);
    }

    public List<User> getAllUser(){
        return sqlSession.selectList("getAllUser");
    }

    public int addUser(String username, String password, String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return sqlSession.insert("addUser", user);
    }
}
