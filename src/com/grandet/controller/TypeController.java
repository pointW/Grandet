package com.grandet.controller;

import com.grandet.domain.Type;
import com.grandet.service.TypeService;
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
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/api/type", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Type> getType(HttpServletResponse response) {
        List<Type> list = typeService.getType();
        Util.setResponseStatus(list, response);
        return list;
    }

    @RequestMapping(value = "/api/type/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Type getType(@PathVariable(value = "id") int id, HttpServletResponse response){
        Type type = typeService.getType(id);
        Util.setResponseStatus(type, response);
        return type;
    }

    @RequestMapping(value = "/api/type/name/{name}", method = RequestMethod.GET)
    public
    @ResponseBody
    Type getType(@PathVariable(value = "name") String typeName, HttpServletResponse response) {
        Type type = typeService.getType(typeName);
        Util.setResponseStatus(type, response);
        return type;
    }
}
