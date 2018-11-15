package com.divanshu.reap.finalProject.controller;


import com.divanshu.reap.finalProject.entity.User;
import com.divanshu.reap.finalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/")
    public ModelAndView show() {
        ModelAndView modelAndView = new ModelAndView("user_login");
        return modelAndView;
    }
/*

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("user_dashboard");
        return modelAndView;
    }
*/

    @GetMapping(value = "/user/dashboard")
    public ModelAndView dashboardPage() {
        ModelAndView modelAndView = new ModelAndView("user_dashboard");
        return modelAndView;
    }


    @GetMapping(value = "/login")
    public ModelAndView getlogin() {
        ModelAndView modelAndView = new ModelAndView("user_login");
        return modelAndView;
    }


    @PostMapping(value = "/login")
    public ModelAndView postlogin(User user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findByEmail(user.getEmail());

        if (userExists == null) {
            modelAndView.addObject("msg", "Email and Password not found");
            modelAndView.setViewName("user_login");
        } else if (userExists.getStatus().equals("Inactive")) {
            modelAndView.addObject("msg", "User is Inactive");
            modelAndView.setViewName("user_login");
        } else {
            if (!userExists.getPassword().isEmpty() && userExists.getPassword().equals(user.getPassword())) {
                modelAndView.setViewName("redirect:/user/dashboard");
            } else {
                modelAndView.addObject("msg", "Password don't match");
                modelAndView.setViewName("user_login");
            }

        }

        return modelAndView;
    }



    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login?=logout");
    }


    @GetMapping("/register")
    public ModelAndView registerUser(Model model) {
        return new ModelAndView("user_registration", "user", new User());
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@Valid @ModelAttribute User user,
                                     BindingResult bindingResult,
                                     Model model) {

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


    @GetMapping("/admin")
    public ModelAndView adminLoginPage() {
        return new ModelAndView("admin");
    }

    @PostMapping("/admin")
    public ModelAndView adminPage(User user, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findByEmail(user.getEmail());


        if (userExists != null) {
            if (user.getEmail().equals("admin@ttn.com") && user.getPassword().equals("admin12")) {
                modelAndView.setViewName("redirect:/admin/list");
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
    public ModelAndView adminDash(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {

        request.setAttribute("users", userService.getAllUsers());
        return new ModelAndView("admin_dashboard");
    }


    @GetMapping("/admin/list/{id}/edit")
    public ModelAndView editUser(@PathVariable Integer id, Model model, String role) {


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
        String email = userMap.get("email");
        String status = userMap.get("status");
        String roles = userMap.get("userRole");

        User user = userService.findOneUser(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setStatus(status);
        user.setUserRole(roles);
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin/list");
    }


    @GetMapping("/all")
    public List<String> allUsers(@RequestParam(value = "term", defaultValue = "",
            required = false) String term) {
        List<String> sugesstions = new ArrayList<>();
        List<User> allUser = userService.findByFirstName(term);
        for (User user : allUser) {
            sugesstions.add(user.getFirstname());
        }
        return sugesstions;
    }


    @RequestMapping(value = "/admin/list/{firstName}", method = RequestMethod.GET)
    public ModelAndView loadByName(@PathVariable("firstName") String firstName, ModelMap model) {
        model.put("users", userService.findByFirstName(firstName));
        return new ModelAndView("/search", model);
    }

    private void addUserInSession(User u, HttpSession httpSession) {
        httpSession.setAttribute("user", u);
        httpSession.setAttribute("id", u.getId());
        httpSession.setAttribute("role", u.getUserRole());
    }

}
