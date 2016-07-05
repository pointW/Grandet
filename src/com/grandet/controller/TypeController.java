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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/api/type", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getType() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Type> list = typeService.getType();
        Util.putListToMap(map, list);
        return map;
    }

    @RequestMapping(value = "/api/type/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getType(@PathVariable(value = "id") int id){
        Map<String, Object> map = new HashMap<String, Object>();
        Type type = typeService.getType(id);
        Util.putObjectToMap(map, type);
        return map;
    }

    @RequestMapping(value = "/api/type/name/{name}", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getType(@PathVariable(value = "name") String typeName) {
        Map<String, Object> map = new HashMap<String, Object>();
        Type type = typeService.getType(typeName);
        Util.putObjectToMap(map, type);
        return map;
    }
}
