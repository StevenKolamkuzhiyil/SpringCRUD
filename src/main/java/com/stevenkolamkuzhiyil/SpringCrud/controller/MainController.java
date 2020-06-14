package com.stevenkolamkuzhiyil.SpringCrud.controller;

import com.stevenkolamkuzhiyil.SpringCrud.model.dto.BranchDTO;
import com.stevenkolamkuzhiyil.SpringCrud.model.dto.EmployeeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping(path = {"", "/home"})
    public String index() {
        return "home";
    }

    @GetMapping(path = "user/login")
    public String login() {
        return "login";
    }

    @GetMapping("employees")
    public String employees(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employee";
    }

    @GetMapping("branches")
    public String branches(Model model) {
        model.addAttribute("branch", new BranchDTO());
        return "branch";
    }

}
