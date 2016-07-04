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
    List<Product> getProduct(HttpServletResponse response){
        List<Product> list = productService.getProduct();
        if (list.isEmpty()){
            response.setStatus(404);
            return null;
        }
        else {
            response.setStatus(200);
            return list;
        }
    }

    @RequestMapping(value = "/api/product/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Product getProduct(@PathVariable(value = "id") int id, HttpServletResponse response){
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

    @RequestMapping(value = "api/product/typeId", method = RequestMethod.GET)
    public @ResponseBody
    List<Product> getProductByTypeId(HttpServletRequest request, HttpServletResponse response){
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        List<Product> list = productService.getProductByTypeId(typeId);
        if (list.isEmpty()){
            response.setStatus(404);
            return null;
        }
        else {
            response.setStatus(200);
            return list;
        }
    }

    @RequestMapping(value = "api/product/search", method = RequestMethod.GET)
    public @ResponseBody
    List<Product> getProduct(HttpServletRequest request, HttpServletResponse response){
        String keyword = request.getParameter("keyword");
        System.out.println(Util.searchProduct(keyword));
        List<Product> list = productService.getProduct(keyword);
        if (list == null){
            response.setStatus(500);
            return null;
        }
        else if (list.isEmpty()){
            response.setStatus(404);
            return null;
        }
        else {
            response.setStatus(200);
            return list;
        }
    }
}
