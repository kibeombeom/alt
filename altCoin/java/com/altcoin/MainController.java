package com.altcoin;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class MainController {
	
    @RequestMapping(value="/mainPage")
    public String home(Model model) {
    	model.addAttribute("msg", "jjdev");

    	return "main";
    }
}
