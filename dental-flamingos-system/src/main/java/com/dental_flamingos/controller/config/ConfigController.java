package com.dental_flamingos.controller.config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "config")
public class ConfigController {

    @GetMapping("/")
    public String showConfiguration(Model model) {
        return "config/config";
    }

}
