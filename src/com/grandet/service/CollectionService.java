package com.grandet.service;

import com.grandet.domain.Collection;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by outen on 16/7/4.
 */

@Service
public class CollectionService {
    @Autowired
    private SqlSession sqlSession;

    public List<Collection> getCollection(int userId){
        return sqlSession.selectList("getCollectionByUserId", userId);
    }

    public int addCollection(Collection collection){
        if (sqlSession.selectOne("getCollectionByUserIdAndProductId", collection)!=null){
            return -1;
        }
        else if (sqlSession.selectOne("getProduct", collection.getProductId())==null){
            return -2;
        }
        return sqlSession.insert("addCollection", collection);
    }

    public Collection getCollection(Collection collection){
        return sqlSession.selectOne("getCollectionByUserIdAndProductId", collection);
    }
}
