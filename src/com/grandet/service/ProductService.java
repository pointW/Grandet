package com.grandet.service;

import com.grandet.domain.Product;
import com.grandet.domain.Type;
import com.grandet.util.Util;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private SqlSession sqlSession;

    public void updateClick(long productId){
        if (sqlSession.selectOne("getClickTodayByProductId", productId) == null){
            sqlSession.insert("addClick", productId);
        }
        sqlSession.update("updateClick", productId);
    }

    public Product getProduct(long id){
        updateClick(id);
        return sqlSession.selectOne("getProduct", id);
    }

    public List<Product> getProduct(){
        return sqlSession.selectList("getAllProduct");
    }

    public List<Product> getProduct(String keyword){
        String jsonString = Util.searchProduct(keyword);
        List<Long> idList = Util.jsonToLongList(jsonString);
        if (idList == null){
            return null;
        }
        List<Product> productList = new ArrayList<Product>();
        for (long id : idList){
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
                product1.setType(type);
                sqlSession.insert("addProduct", product1);
                productList.add(product1);
            }
        }
        return productList;
    }

    public List<Product> getProduct(String keyword, int page){
        String jsonString = Util.searchProduct(keyword, page);
        List<Long> idList = Util.jsonToLongList(jsonString);
        if (idList == null){
            return null;
        }
        List<Product> productList = new ArrayList<Product>();
        for (long id : idList){
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
                product1.setType(type);
                sqlSession.insert("addProduct", product1);
                productList.add(product1);
            }
        }
        return productList;
    }

//    public List<Product> getProductByTypeId(int typeId){
//        return sqlSession.selectList("getProductByTypeId", typeId);
//    }

    public List<Product> getProductByTypeId(int typeId, int page){
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", typeId);
        map.put("index", (page-1)*30);
        return sqlSession.selectList("getProductByTypeId", map);
    }
}
