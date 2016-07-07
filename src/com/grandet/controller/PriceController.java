package com.grandet.controller;

import com.grandet.domain.Price;
import com.grandet.service.PriceService;
import com.grandet.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by outen on 16/7/6.
 */

@Controller
public class PriceController {
    @Autowired
    private PriceService priceService;

    @RequestMapping(value = "/api/price", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getPrice(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String productId = request.getParameter("productId");
        List<Price> list = priceService.getPriceByProductId(Long.parseLong(productId));
        Util.putListToMap(map, list);
        return map;
    }
}
