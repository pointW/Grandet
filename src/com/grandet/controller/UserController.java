package com.grandet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import com.google.gson.*;
import com.grandet.domain.User;
import com.grandet.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class UserController {
    @Autowired
    private UserService userService;

//    200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
//    201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
//    202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
//    204 NO CONTENT - [DELETE]：用户删除数据成功。
//    400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
//    401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
//    403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
//    404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
//    406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
//    410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
//    422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
//    500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。

    @RequestMapping(value = "/api/userLogin", method = RequestMethod.POST)
    public
    @ResponseBody
    User userLogin(@ModelAttribute User user, HttpServletResponse response, HttpSession session) {
        User u = userService.userLogin(user.getUsername(), user.getPassword());
        if (u != null) {
            session.setAttribute("user", u);
            session.setMaxInactiveInterval(5*60);
            response.setStatus(200);
            return user;
        } else {
            response.setStatus(404);
            return null;
        }
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getAllUser(HttpServletResponse response) {
        List<User> list = userService.getAllUser();
        if (list.isEmpty()){
            response.setStatus(404);
            return null;
        }
        else {
            response.setStatus(200);
            return list;
        }
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public
    @ResponseBody
    String addUser(User user, HttpServletResponse response) {
        if (userService.addUser(user) == 1) {
            response.setStatus(201);
        } else {
            response.setStatus(400);
        }
        return null;
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUser(@PathVariable(value = "id") int id, HttpServletResponse response) {
        User user = userService.getUser(id);
        if (user != null) {
            response.setStatus(200);
            return user;
        } else {
            response.setStatus(404);
            return null;
        }
    }


    @RequestMapping(value = "api/user/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    String upDateUser(@PathVariable(value = "id") int id, User user, HttpServletResponse response){
        if (userService.getUser(id)==null){
            response.setStatus(404);
            return null;
        }
        else if (userService.getUser(id).getUsername()!=user.getUsername()){
            response.setStatus(400);
            return null;
        }
        else {
            if (userService.updateUser(user) == 1){
                response.setStatus(201);
                return null;
            }
            else {
                response.setStatus(400);
                return null;
            }
        }
    }

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    String deleteUser(@PathVariable(value = "id") int id, HttpServletResponse response) {
        if (userService.deleteUser(id) == 1) {
            response.setStatus(204);
            return null;
        } else {
            response.setStatus(404);
            return null;
        }
    }
}



