package com.grandet.service;

import com.grandet.domain.Click;
import com.grandet.domain.Product;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by outen on 16/7/6.
 */

@Service
public class ClickService {
    @Autowired
    private SqlSession sqlSession;

    public int getRecentClickTimes(long productId){
        List<Click> list = sqlSession.selectList("getClickByProductId", productId);
        int count = 0;
        for (Click click:list){
            if ((new Date().getTime()-click.getDate().getTime())/24*60*60*1000 <= 7){
                count += click.getTimes();
            }
        }
        return count;
    }

    public List<Product> getMostClickedProduct(){
        List<Long> productIdList = sqlSession.selectList("getMostClicked");
        List<Product> productList = new ArrayList<>();
        for (long productId : productIdList){
            productList.add((Product)sqlSession.selectOne("getProduct", productId));
        }
        return productList;
    }
}
