package com.grandet.controller;

import com.grandet.domain.Price;
import com.grandet.domain.PriceVO;
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
        if (productId == null){
            map.put("msg", "bad request");
            return map;
        }
        List<Price> list = priceService.getPriceByProductId(Long.parseLong(productId));
        Util.putListToMap(map, list);
        return map;
    }

    @RequestMapping(value = "/api/price/history", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getPriceHistory(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String productId = request.getParameter("productId");
        if (productId == null){
            map.put("msg", "bad request");
            return map;
        }
        List<PriceVO> list = priceService.getPriceHistoryAvgByProductId(Long.parseLong(productId));
        Util.putListToMap(map, list);
        return map;
    }
}
