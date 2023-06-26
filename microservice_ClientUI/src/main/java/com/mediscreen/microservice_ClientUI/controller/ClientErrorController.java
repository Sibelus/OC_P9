package com.mediscreen.microservice_ClientUI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientErrorController {


    /**
     * Method that get exception thrown and set its message to the view
     * @param throwable
     * @param model
     * @return
     */
    @GetMapping("/error")
    public String getError(Throwable throwable,Model model) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
