package com.grandet.service;

import com.grandet.domain.Price;
import com.grandet.domain.Product;
import com.grandet.domain.Website;
import com.grandet.util.Util;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by outen on 16/7/6.
 */

@Service ("PriceService")
public class PriceService {
    @Autowired
    private SqlSession sqlSession;

    public List<Price> getPriceByProductId(long productId){
        List<Price> list = sqlSession.selectList("getPriceTodayByProductId", productId);
        if (list.isEmpty()){
            long id = productId;
            Product product = sqlSession.selectOne("getProduct", id);
            try {
                List<Map<String, Object>> mapList = Util.jsonToMapList(Util.getPrice(product.getName()));
                for (Map<String, Object> map : mapList) {
                    Price price = new Price();
                    price.setProductId(productId);
                    price.setNumber(Double.parseDouble((String) map.get("price")));
                    price.setUrl((String) map.get("link"));
                    String websiteName = (String) map.get("mall");
                    Website website = sqlSession.selectOne("getWebsiteByName", websiteName);
                    if (website == null) {
                        website = new Website();
                        website.setName(websiteName);
                        sqlSession.insert("addWebsite", website);
                        website = sqlSession.selectOne("getWebsiteByName", websiteName);
                    }
                    price.setWebsiteId(website.getId());
                    sqlSession.insert("addPrice", price);
                }
                list = sqlSession.selectList("getPriceTodayByProductId", productId);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public void updateAllPrice(){
        List<Product> productList = sqlSession.selectList("getAllProduct");
        for (Product product : productList){
            long productId = product.getId();
            List<Price> priceList = sqlSession.selectList("getPriceTodayByProductId", productId);
            System.out.println("checking price for "+productId);
            if (priceList.isEmpty()){
                System.out.println("updating price for "+productId);
                try {
                    List<Map<String, Object>> mapList = Util.jsonToMapList(Util.getPrice(product.getName()));
                    for (Map<String, Object> map : mapList) {
                        Price price = new Price();
                        price.setProductId(productId);
                        price.setNumber(Double.parseDouble((String) map.get("price")));
                        price.setUrl((String) map.get("link"));
                        String websiteName = (String) map.get("mall");
                        Website website = sqlSession.selectOne("getWebsiteByName", websiteName);
                        if (website == null) {
                            website = new Website();
                            website.setName(websiteName);
                            sqlSession.insert("addWebsite", website);
                            website = sqlSession.selectOne("getWebsiteByName", websiteName);
                        }
                        price.setWebsiteId(website.getId());
                        sqlSession.insert("addPrice", price);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }
}
