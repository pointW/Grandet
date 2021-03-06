package com.grandet.controller;

import com.grandet.domain.Collection;
import com.grandet.domain.User;
import com.grandet.service.CollectionService;
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

/**
 * Created by outen on 16/7/4.
 */

@Controller
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/api/collection", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getCollection(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        List<Collection> list = null;
        if (userId != null && page != null){
            try {
                list = collectionService.getCollectionByUserId(Integer.parseInt(userId), Integer.parseInt(page));
            }
            catch (Exception e){
                e.printStackTrace();
                map.put("msg", "bad request");
                return map;
            }
        }
        else {
            map.put("msg", "bad request");
            return map;
        }
        Util.putListToMap(map, list);
        return map;
    }

    @RequestMapping(value = "/api/collection", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> addCollection(Collection collection, HttpServletRequest request){
        Map<String, String > map = new HashMap<String, String>();
        if (collection.getUserId() == 0 || collection.getProductId() == 0){
            map.put("msg", "bad request");
            return map;
        }
//        User user = (User)request.getSession().getAttribute("currentUser");
//        if (user.getId()!=collection.getUserId()){
//            map.put("msg", "not match");
//            return map;
//        }
        int result = collectionService.addCollection(collection);
        if (result == 1){
            map.put("msg", "success");
        }
        else if (result == -1){
            map.put("msg", "existed");
        }
        else if (result == -2){
            map.put("msg", "no product");
        }
        else {
            map.put("msg", "error");
        }
        return map;
    }

    @RequestMapping(value = "/api/collection/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> deleteCollection(@PathVariable(value = "id")int id, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        Collection collection = collectionService.getCollection(id);
        if (collection == null){
            map.put("msg", "not exist");
            return map;
        }
//        User user = (User)request.getSession().getAttribute("currentUser");
//        if (user.getId()!=collection.getUserId()){
//            map.put("msg", "not match");
//            return map;
//        }
        int result = collectionService.deleteCollection(id);
        if (result == 1){
            map.put("msg", "success");
        }
        else {
            map.put("msg", "error");
        }
        return map;
    }
}
