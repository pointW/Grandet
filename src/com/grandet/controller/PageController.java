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

    @RequestMapping(value = "/search")
    public String searchProduct(HttpServletRequest request, RedirectAttributes attr){
        String keyword = request.getParameter("keyword");
        attr.addAttribute("keyword", keyword);
        return "pages/search.html";
    }

    @RequestMapping(value = "/home")
    public String home(HttpServletRequest request, HttpServletResponse response){
//        try {
//            request.getRequestDispatcher("/pages/index.html").forward(request, response);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        return "index";
    }

    @RequestMapping(value = "/register")
    public String register(){
        return "Register";
    }

}
