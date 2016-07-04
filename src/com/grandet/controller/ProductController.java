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
import java.util.List;


@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/api/product", method = RequestMethod.GET)
    public @ResponseBody
    List<Product> getProduct(HttpServletRequest request, HttpServletResponse response){
        String keyword = request.getParameter("keyword");
        String page = request.getParameter("page");
        String typeId = request.getParameter("typeId");
        List<Product> list = null;
        //根据关键字搜索
        if (keyword != null && page !=null){
            list = productService.getProduct(keyword, Integer.parseInt(page));
        }
        //根据类型id搜索
        else if (typeId != null){
            list = productService.getProductByTypeId(Integer.parseInt(typeId));

        }
        //获取全部
        else if (keyword == null && page == null && typeId == null){
            list = productService.getProduct();
        }
        //其他情况请求有误
        else {
            response.setStatus(400);
            return null;
        }

        if (list == null){
            response.setStatus(500);
        }
        else if (list.isEmpty()){
            response.setStatus(404);
        }
        else {
            response.setStatus(200);
        }
        return list;
    }

    @RequestMapping(value = "/api/product/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Product getProduct(@PathVariable(value = "id") long id, HttpServletResponse response){
        Product product = productService.getProduct(id);
        if (product == null){
            response.setStatus(404);
            return null;
        }
        else {
            response.setStatus(200);
            return product;
        }
    }
}
