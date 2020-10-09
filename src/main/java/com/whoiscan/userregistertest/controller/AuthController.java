package com.whoiscan.userregistertest.controller;

import com.whoiscan.userregistertest.model.Result;
import com.whoiscan.userregistertest.payload.RegisterReq;
import com.whoiscan.userregistertest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.whoiscan.userregistertest.service.UserService;


import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping(value = {"/sign/in"})
    public String getHomePage() {
        return "login";
    }

    @GetMapping(value = {"/sign/up"})
    public String getRegisterPage(Model model, RegisterReq registerReq) {
        model.addAttribute("registerReq", registerReq);
        return "register";
    }

    @PostMapping(value = {"/sign/up"})
    public String registerUser(@Valid RegisterReq registerReq,
                               BindingResult bindingResult,
                               Model model) {
        model.addAttribute("registerReq", registerReq);
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userService.saveUser(registerReq);
            return "login";
        }
    }

    @GetMapping("/activate")
    public String getActivePage(@RequestParam(name = "activCode") String activCode) {
        Result result = authService.activate(activCode);
        if (result.isSuccess()) {
            return "redirect:/cabinet";
        } else {
            return "login";
        }
    }
}

