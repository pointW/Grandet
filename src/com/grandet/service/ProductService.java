package com.grandet.service;

import com.grandet.domain.Product;
import com.grandet.domain.Type;
import com.grandet.util.Util;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Product> getProduct(String keyword){
        String jsonString = Util.searchProduct(keyword);
        List<Integer> idList = Util.jsonToIntList(jsonString);
        if (idList == null){
            return null;
        }
        List<Product> productList = new ArrayList<Product>();
        for (int id : idList){
            Product product = sqlSession.selectOne("getProduct", id);
            if (product != null){
                productList.add(product);
            }
            else {
                Map<String, Object> map = Util.jsonToMap(Util.getProductById(id));
                Product product1 = new Product();
                product1.setId(id);
                product1.setName((String)map.get("name"));
                product1.setPic((String)map.get("pic"));
                String name = (String)map.get("type");
                Type type = sqlSession.selectOne("getTypeByName", name);
                if (type == null){
                    type = new Type();
                    type.setName(name);
                    sqlSession.insert("addType", type);
                    type = sqlSession.selectOne("getTypeByName", name);
                }
                product1.setTypeId(type.getId());
                sqlSession.insert("addProduct", product1);
                productList.add(product1);
            }
        }
        return productList;
    }

    public List<Product> getProductByTypeId(int typeId){
        return sqlSession.selectList("getProductByTypeId", typeId);
    }
}
