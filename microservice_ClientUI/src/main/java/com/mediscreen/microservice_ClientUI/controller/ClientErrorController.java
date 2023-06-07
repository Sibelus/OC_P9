package com.mediscreen.microservice_ClientUI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientErrorController {

    @GetMapping("/error")
    public String getError(Model model) {
        String errorMessage = "personalRecordNotFoundException.toString()";
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
