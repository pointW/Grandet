package com.grandet.controller;

import com.grandet.domain.User;
import com.grandet.service.UserService;
import com.grandet.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    Map<String, Object> userLogin(User user, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        User u = userService.userLogin(user.getUsername(), user.getPassword());
        if (u != null) {
            session.setAttribute("currentUser", u);
            session.setMaxInactiveInterval(5*60);
            map.put("msg", "success");
            map.put("object", u);
        } else {
            map.put("msg", "fail");
        }
        return map;
    }


    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getAllUser() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> list = userService.getAllUser();
        Util.putListToMap(map, list);
        return map;
    }


    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addUser(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = userService.addUser(user);
        if (result == -1){
            map.put("msg", "existed");
        }
        else if (result == 1) {
            map.put("msg", "success");
        }
        else {
            map.put("msg", "fail");
        }
        return map;
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getUser(@PathVariable(value = "id") int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.getUser(id);
        Util.putObjectToMap(map, user);
        return map;
    }


    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> upDateUser(@PathVariable(value = "id") int id, User user){
        Map<String, Object> map = new HashMap<String, Object>();
        User preUser = userService.getUser(id);
        if (preUser==null){
            map.put("msg", "no result");
        }
        else if (user.getUsername()!=null && preUser.getUsername()!=user.getUsername()){
            map.put("msg", "bad username");
        }
        else {
            if (user.getPassword() == null){
                user.setPassword(preUser.getPassword());
            }
            if (user.getEmail() == null){
                user.setEmail(preUser.getEmail());
            }
            user.setId(id);
            if (userService.updateUser(user) == 1) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
        }
        return map;
    }

    @RequestMapping(value = "/api/user/alter/{id}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateUser(@PathVariable(value = "id") int id, User user){
        Map<String, Object> map = new HashMap<String, Object>();
        User preUser = userService.getUser(id);
        if (preUser==null){
            map.put("msg", "no result");
        }
        else if (user.getUsername()!=null && preUser.getUsername()!=user.getUsername()){
            map.put("msg", "bad username");
        }
        else {
            if (user.getPassword() == null){
                user.setPassword(preUser.getPassword());
            }
            if (user.getEmail() == null){
                user.setEmail(preUser.getEmail());
            }
            user.setId(id);
            if (userService.updateUser(user) == 1) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
        }
        return map;
    }


    @RequestMapping(value = "/api/loginFirst")
    public @ResponseBody
    Map<String, Object> loginFirst(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "no login");
        return map;
    }
}



