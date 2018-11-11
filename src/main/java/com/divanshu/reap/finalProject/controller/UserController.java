package com.divanshu.reap.finalProject.controller;


import com.divanshu.reap.finalProject.entity.User;
import com.divanshu.reap.finalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;


@RestController
public class UserController {


    @Autowired
    UserService userService;


    @GetMapping(value = "/")
    public ModelAndView show() {
        ModelAndView modelAndView = new ModelAndView("user_dashboard");
        return modelAndView;
    }

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("user_dashboard");
        return modelAndView;
    }

/*
    @PostMapping(value = "/dashboard")
    public ModelAndView dashboardPage(){
        ModelAndView modelAndView = new ModelAndView("user_dashboard");
        return modelAndView;
    }
*/


    @GetMapping(value = "/login")
    public ModelAndView getlogin() {
        ModelAndView modelAndView = new ModelAndView("user_login");
        return modelAndView;
    }


    @PostMapping(value = "/login")
    public ModelAndView postlogin(User user) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findByEmail(user.getEmail());

        if (userExists != null) {
            if (!userExists.getPassword().isEmpty() && userExists.getPassword().equals(user.getPassword())) {
                modelAndView.setViewName("user_dashboard");
            } else {
                modelAndView.addObject("msg", "Password don't match");
                modelAndView.setViewName("user_login");
            }
        } else {
            modelAndView.addObject("msg", "Email & Password not found");
            modelAndView.setViewName("user_login");
        }
        return modelAndView;
    }


    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("user_login");
    }


    @GetMapping("/register")
    public ModelAndView registerUser(Model model) {

        ModelAndView modelAndView = new ModelAndView("user_registration");
        model.addAttribute("user", new User());
        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView postRegister(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user_registration");
            return modelAndView;
        }

        if (userService.isUserPresent(user.getEmail())) {
            model.addAttribute("exist", true);

            modelAndView.setViewName("user_registration");
            return modelAndView;
        }

        userService.createUser(user);
        modelAndView.addObject("msg", "Registration successfully done! Proceed to login.");
        modelAndView.setViewName("user_registration");
        return modelAndView;
    }

/*

    @GetMapping("/forgotpassword")
    public ModelAndView resetPass(){
        ModelAndView modelAndView = new ModelAndView("forgot_password");
        return modelAndView;
    }


    @PostMapping("/forgotpassword")
    public ModelAndView updateNewPass(User user){
        ModelAndView modelAndView = new ModelAndView("user_login");
       return modelAndView;
    }
*/


    @GetMapping("/admin")
    public ModelAndView adminLoginPage() {
        return new ModelAndView("admin");
    }

    @PostMapping("/admin")
    public ModelAndView adminPage(User user, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findByEmail(user.getEmail());


        if (userExists != null) {
            if (user.getEmail().equals(user.getEmail()) && user.getPassword().equals(user.getPassword())) {
                modelAndView.setViewName("admin_dashboard");
            } else {
                modelAndView.addObject("msg", "You're not authorized");
                modelAndView.setViewName("admin");
            }
        } else {
            modelAndView.addObject("msg", "Email & Password not found");
            modelAndView.setViewName("admin");
        }
        return modelAndView;
    }

    @GetMapping("/guideline/index")
    public ModelAndView guidelines() {

        ModelAndView modelAndView = new ModelAndView("guideline");
        return modelAndView;

    }

    @GetMapping("/notification/index")
    public ModelAndView notification() {
        ModelAndView modelAndView = new ModelAndView("notification");
        return modelAndView;

    }


    @GetMapping("/badge/index")
    public ModelAndView badge() {
        ModelAndView modelAndView = new ModelAndView("badges");
        return modelAndView;

    }

    @GetMapping("/admin/list")
    public ModelAndView adminDash(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return new ModelAndView("admin_dashboard");
    }


    @GetMapping("/admin/list/{id}/edit")
    public ModelAndView editUser(@PathVariable Integer id, Model model) {

        User user = userService.findOneUser(id);
        model.addAttribute("user", user);
        return new ModelAndView("user_edit");
    }


    @GetMapping("/admin/list/{id}/delete")
    public ModelAndView deleteUser(@PathVariable Integer id) {
        userService.deleteOneUser(id);
        return new ModelAndView("redirect:/admin/list");
    }


    @RequestMapping(value = "/admin/list/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("user_id") Integer id,
                               @RequestParam Map<String, String> userMap) {

        String firstname = userMap.get("firstname");
        String lastname = userMap.get("lastname");
        String status = userMap.get("status");

//        List<Role> roles = Arrays.asList(new Role("ADMIN"),new Role("USER"),new Role("MANAGER"));

        User user = userService.findOneUser(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setStatus(status);
//        user.setRoles(roles);

        userService.saveUser(user);
        return new ModelAndView("redirect:/admin/list");
    }


    /*@RequestMapping(value="search",method = RequestMethod.GET,
            produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> searchUser(){
        try{

            User user = new User();
            ResponseEntity<List<User>> responseEntity =
                    new ResponseEntity<List<User>>(userService.findAll(),HttpStatus.OK);
            return responseEntity;
        }catch (Exception e){
            return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
        }
    }*/


    @GetMapping("/all")
    public String allUsers() {
        return userService.findAll().toString();
    }


}
