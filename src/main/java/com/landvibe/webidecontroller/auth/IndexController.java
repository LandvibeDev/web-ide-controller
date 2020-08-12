package com.landvibe.webidecontroller.auth;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

public class IndexController {
    private final HttpSession httpSession;

    public IndexController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
