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

    public int addUser(User user){
        String username = user.getUsername();
        if (sqlSession.selectOne("getUserByUsername", username)!=null){
            return -1;
        }
        return sqlSession.insert("addUser", user);
    }

    public int deleteUser(int id){
        return sqlSession.delete("deleteUser", id);
    }

    public int updateUser(User user){
        return sqlSession.update("updateUser", user);
    }
}
