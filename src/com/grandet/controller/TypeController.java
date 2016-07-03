package com.grandet.controller;

import com.grandet.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;
}
