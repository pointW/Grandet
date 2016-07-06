package com.grandet.controller;

import com.grandet.domain.Product;
import com.grandet.service.ClickService;
import com.grandet.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by outen on 16/7/6.
 */

@Controller
public class ClickController {
    @Autowired
    private ClickService clickService;

    @RequestMapping(value = "/api/click/mostClicked", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getMostClicked(){
        Map<String, Object> map = new HashMap<>();
        List<Product> list = clickService.getMostClickedProduct();
        Util.putListToMap(map, list);
        return map;
    }
}
