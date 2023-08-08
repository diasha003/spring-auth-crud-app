package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String indexUserPage (Model model){
        model.addAttribute("users", userService.findAll());
        return "user_views/user";
    }


    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute(new User());
        return "user_views/user_form";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model) throws ChangeSetPersister.NotFoundException {
        model.addAttribute("user", userService.findById(id).orElseThrow(()-> new ChangeSetPersister.NotFoundException()));
        model.addAttribute("roles", roleRepository.findAll());
        return "user_views/user_form";
    }

    @PostMapping("/update")
    public String updateUser(User user){
        System.out.println(user);
        userService.createOrUpdate(user);
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String removeUser(@PathVariable(name = "id") Long id){
        userService.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExeptionHandler(ChangeSetPersister.NotFoundException exception){
        ModelAndView modelAndView = new ModelAndView(); //добавить view
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }


}
