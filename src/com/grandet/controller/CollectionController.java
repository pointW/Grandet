package com.grandet.controller;

import com.grandet.domain.Collection;
import com.grandet.domain.User;
import com.grandet.service.CollectionService;
import com.grandet.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by outen on 16/7/4.
 */

@Controller
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/api/collection", method = RequestMethod.GET)
    public @ResponseBody
    List<Collection> getCollection(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userId");
        List<Collection> list = null;
        if (userId != null){
            list = collectionService.getCollection(Integer.parseInt(userId));
        }
        else {
            response.setStatus(400);
            return null;
        }
        Util.setResponseStatus(list, response);
        return list;
    }

    @RequestMapping(value = "/api/collection", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> addCollection(Collection collection, HttpServletRequest request, HttpServletResponse response){
        User user = (User)request.getSession().getAttribute("currentUser");
        Map<String, String > map = new HashMap<String, String>();
        if (user.getId()!=collection.getUserId()){
            response.setStatus(400);
            map.put("msg", "not match");
            return map;
        }
        int result = collectionService.addCollection(collection);
        if (result == 1){
            response.setStatus(201);
            map.put("msg", "created");
        }
        else if (result == -1){
            response.setStatus(400);
            map.put("msg", "existed");
        }
        else {
            response.setStatus(500);
            map.put("msg", "error");
        }
        return map;
    }
}
