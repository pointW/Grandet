package com.grandet.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grandet.domain.Type;
import com.grandet.domain.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private SqlSession sqlSession;

    public Product getProduct(int id){
        return sqlSession.selectOne("getProduct", id);
    }

    public List<Product> getProduct(){
        return sqlSession.selectList("getAllProduct");
    }

    public List<Product> getProduct(String name){
        return sqlSession.selectList("getProductByName", name);
    }

    public List<Product> getProductByTypeId(int typeId){
        return sqlSession.selectList("getProductByTypeId", typeId);
    }
}
