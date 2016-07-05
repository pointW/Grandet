package com.grandet.service;

import com.grandet.domain.InsideComment;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by outen on 16/7/5.
 */

@Service
public class InsideCommentService {
    @Autowired
    private SqlSession sqlSession;

    public List<InsideComment> getInsideComment(long productId, int page){
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("index", (page-1)*10);
        return sqlSession.selectList("getInsideCommentByProductId", map);
    }

    public List<InsideComment> getInsideCommentByUserId(int userId, int page){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("index", (page-1)*10);
        return sqlSession.selectList("getInsideCommentByUserId", map);
    }

    public int addInsideComment(InsideComment insideComment){
        return sqlSession.insert("addInsideComment", insideComment);
    }
}
