package ru.sstu.socialnetwork.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    @GetMapping
    public String index(Principal principal) {
        return redirect(principal, "index");
    }

    @GetMapping("/sign_up")
    public String signUp(Principal principal) {
        return redirect(principal, "sign_up");
    }

    @GetMapping("/sign_in")
    public String signIn(Principal principal) {
        return redirect(principal, "sign_in");
    }

    @GetMapping("/my_profile")
    public String myProfile() {
        return "my_profile";
    }

    private String redirect(Principal principal, String page) {
        if (principal != null)
            return "redirect:/my_profile";
        return page;
    }

}
