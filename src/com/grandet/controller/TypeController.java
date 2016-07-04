package com.grandet.controller;

import com.grandet.domain.Type;
import com.grandet.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/api/type", method = RequestMethod.GET)
    public @ResponseBody
    List<Type> getType(){
        return typeService.getType();
    }
}
