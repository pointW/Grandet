package com.grandet.controller;

import com.grandet.domain.Product;
import com.grandet.service.ProductService;
import com.grandet.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/api/product", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getProduct(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String keyword = request.getParameter("keyword");
        String page = request.getParameter("page");
        String typeId = request.getParameter("typeId");
        List<Product> list = null;
        //根据关键字搜索
        if (keyword != null && page !=null){
            try {
                list = productService.getProduct(keyword, Integer.parseInt(page));
            }
            catch (Exception e){
                e.printStackTrace();
                map.put("msg", "bad request");
                return map;
            }
        }
        //根据类型id搜索
        else if (typeId != null && page != null){
            try {
                list = productService.getProductByTypeId(Integer.parseInt(typeId), Integer.parseInt(page));
            }
            catch (Exception e){
                e.printStackTrace();
                map.put("msg", "bad request");
                return map;
            }
        }
        //获取全部
        else if (keyword == null && page == null && typeId == null){
            list = productService.getProduct();
        }
        //其他情况请求有误
        else {
            map.put("msg", "bad request");
            return map;
        }
        Util.putListToMap(map, list);
        return map;
    }

    @RequestMapping(value = "/api/product/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getProduct(@PathVariable(value = "id") long id){
        Map<String, Object> map = new HashMap<String, Object>();
        Product product = productService.getProduct(id);
        Util.putObjectToMap(map, product);
        return map;
    }
}
