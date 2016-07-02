package com.grandet.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grandet.domain.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeService {
    @Autowired
    private SqlSession sqlSession;

    public Type getType(int id){
        return sqlSession.selectOne("getType", id);
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
