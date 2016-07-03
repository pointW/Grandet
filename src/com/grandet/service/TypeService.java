package com.grandet.service;

import com.grandet.domain.Type;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private SqlSession sqlSession;

    public Type getType(int id){
        return sqlSession.selectOne("getType", id);
    }

    public Type getType(String name){
        return sqlSession.selectOne("getTypeByName", name);
    }

    public List<Type> getType(){
        return sqlSession.selectList("getAllType");
    }

    public int addType(Type type){
        return sqlSession.insert("addType", type);
    }

    public int deleteType(int id){
        return sqlSession.delete("deleteType", id);
    }

    public int updateType(Type type){
        return sqlSession.update("updateType", type);
    }
}

