package com.grandet.controller;

import com.grandet.domain.InsideComment;
import com.grandet.domain.User;
import com.grandet.service.InsideCommentService;
import com.grandet.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by outen on 16/7/5.
 */

@Controller
public class InsideCommentController {
    @Autowired
    private InsideCommentService insideCommentService;

    @RequestMapping(value = "/api/comment", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getComment(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        List<InsideComment> list = new ArrayList<>();
        String productId = request.getParameter("productId");
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        try {
            if (productId != null && page != null) {
                list = insideCommentService.getInsideComment(Long.parseLong(productId), Integer.parseInt(page));
            } else if (userId != null && page != null) {
                list = insideCommentService.getInsideCommentByUserId(Integer.parseInt(userId), Integer.parseInt(page));
            } else {
                map.put("msg", "bad request");
                return map;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("msg", "bad request");
            return map;
        }
        Util.putListToMap(map, list);
        return map;
    }

    @RequestMapping(value = "/api/comment", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addComment(InsideComment insideComment, HttpServletRequest request){
        insideComment.setDate(new Date(System.currentTimeMillis()));
        Map<String, Object> map = new HashMap<>();
        User user = (User)request.getSession().getAttribute("currentUser");
        if (user.getId() != insideComment.getUserId()){
            map.put("msg", "not match");
            return map;
        }
        int result = insideCommentService.addInsideComment(insideComment);
        if (result == 1){
            map.put("msg", "success");
        }
        else {
            map.put("msg", "error");
        }
        return map;
    }

    @RequestMapping(value = "api/comment/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> deleteComment(@PathVariable(value = "id") int id, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        InsideComment insideComment = insideCommentService.getInsideComment(id);
        if (insideComment == null){
            map.put("msg", "not exist");
            return map;
        }
        User user = (User)request.getSession().getAttribute("currentUser");
        if (user.getId() != insideComment.getUserId()){
            map.put("msg", "not match");
            return map;
        }
        int result = insideCommentService.deleteInsideComment(id);
        if (result == 1){
            map.put("msg", "success");
        }
        else {
            map.put("msg", "error");
        }
        return map;
    }
}
