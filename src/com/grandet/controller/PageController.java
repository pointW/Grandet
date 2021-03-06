package com.grandet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by outen on 16/7/8.
 */

@Controller
public class PageController {

//    @RequestMapping(value = "/search")
//    public String searchProduct(HttpServletRequest request, RedirectAttributes attr){
//        String keyword = request.getParameter("keyword");
//        attr.addAttribute("keyword", keyword);
//        return "pages/search.html";
//    }

    @RequestMapping(value = "/home")
    public String home(){
        return "homepage";
    }

    @RequestMapping(value = "/register")
    public String register(){
        return "Register";
    }

    @RequestMapping(value = "/product")
    public String product(){
        return "product";
    }

    @RequestMapping(value = "/search")
    public String search(){
        return "search";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/collection")
    public String collection(){
        return "collection";
    }

    @RequestMapping(value = "/alteremail")
    public String alterEmail(){
        return "alteremail";
    }

    @RequestMapping(value = "/alterpassword")
    public String alterPassword(){
        return "alterpassword";
    }

}
