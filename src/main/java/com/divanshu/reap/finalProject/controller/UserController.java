package com.divanshu.reap.finalProject.controller;


import com.divanshu.reap.finalProject.POJO.AppreciatedData;
import com.divanshu.reap.finalProject.entity.User;
import com.divanshu.reap.finalProject.enums.UserRole;
import com.divanshu.reap.finalProject.services.AppreciationService;
import com.divanshu.reap.finalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.print.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class UserController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    UserService userService;

    @Autowired
    AppreciationService appreciationService;

    @GetMapping("/")
    public ModelAndView show() {
        ModelAndView modelAndView = new ModelAndView("user_login");
        return modelAndView;
    }

    @GetMapping(value = "/user/dashboard")
    public ModelAndView dashboardPage(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        User currentUser = (User) request.getSession().getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (currentUser != null) {
            if (!currentUser.getUserRole().equals(UserRole.ADMIN.getValue())) {
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("appreciatedData", new AppreciatedData());
                model.addAttribute("badgeCount", appreciationService.handlingBadge(currentUser));
                model.addAttribute("WallofFameList", appreciationService.findAll());
                model.addAttribute("user", new User());
                modelAndView.setViewName("user_dashboard");
            } else {
                modelAndView.setViewName("redirect:/");
            }
        } else {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }


    @GetMapping(value = "/login")
    public ModelAndView getlogin() {
        ModelAndView modelAndView = new ModelAndView("user_login");
        return modelAndView;
    }


    @PostMapping(value = "/login")
    public ModelAndView postlogin(User user, HttpSession session, RedirectAttributes redirectAttributes) {
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
                if (userExists.getUserRole().equals("ADMIN")) {
                    //add user detail in session(assign session to logged in user)
                    addUserInSession(userExists, session);
                    modelAndView.setViewName("redirect:/admin/list");
                } else {
                    addUserInSession(userExists, session);
                    modelAndView.setViewName("redirect:/user/dashboard");
                }
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
        return new ModelAndView("redirect:/login?=logoutAction");
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
            if (userExists.getUserRole().equals("ADMIN")) {
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
    public ModelAndView guidelines(HttpServletRequest request, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (currentUser != null) {
            if (!currentUser.getUserRole().equals("ADMIN")) {
//                model.addAttribute("currentUser", currentUser);
                modelAndView.setViewName("guideline");
            } else {
                modelAndView.setViewName("redirect:/");
            }

        } else {
            modelAndView.setViewName("redirect:/");
        }

//        ModelAndView modelAndView = new ModelAndView("guideline");
        return modelAndView;

    }

    @GetMapping("/notification/index")
    public ModelAndView notification(HttpServletRequest request, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (currentUser != null) {
            if (!currentUser.getUserRole().equals("ADMIN")) {
//                model.addAttribute("currentUser", currentUser);
                modelAndView.setViewName("notification");
            } else {
                modelAndView.setViewName("redirect:/");
            }

        } else {
            modelAndView.setViewName("redirect:/");
        }

//        ModelAndView modelAndView = new ModelAndView("guideline");
        return modelAndView;


        //        ModelAndView modelAndView = new ModelAndView("notification");
//        return modelAndView;

    }

    @GetMapping("/displayCertificate/display")
    public ModelAndView certificate(HttpServletRequest request, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (currentUser != null) {
            if (!currentUser.getUserRole().equals("ADMIN")) {
                model.addAttribute("currentUser", currentUser);
                modelAndView.setViewName("certificate");
            } else {
                modelAndView.setViewName("redirect:/");
            }

        } else {
            modelAndView.setViewName("redirect:/");
        }

//        ModelAndView modelAndView = new ModelAndView("guideline");
        return modelAndView;


        //        User currentUser = (User) request.getSession().getAttribute("user");
//        ModelAndView modelAndView = new ModelAndView("certificate");
//        model.addAttribute("currentUser", currentUser);
//        return modelAndView;

    }


    @GetMapping("/badge/index")
    public ModelAndView badge(HttpServletRequest request, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (currentUser != null) {
            if (!currentUser.getUserRole().equals("ADMIN")) {
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("badgeCount", appreciationService.handlingBadge(currentUser));
                modelAndView.setViewName("badges");
            } else {
                modelAndView.setViewName("redirect:/");
            }

        } else {
            modelAndView.setViewName("redirect:/");
        }

//        ModelAndView modelAndView = new ModelAndView("guideline");
        return modelAndView;


//        ModelAndView modelAndView = new ModelAndView("badges");
//        return modelAndView;

    }

    @GetMapping("/admin/list")
    public ModelAndView adminDash(HttpServletRequest request,
                                  @SortDefault("id") Pageable pageable, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        if (currentUser != null) {
            if (!currentUser.getUserRole().equals("ADMIN")) {
//                model.addAttribute("currentUser", currentUser);
                modelAndView.setViewName("redirect:/");
            } else {
                request.setAttribute("users", userService.getAllUsers());
                model.addAttribute("users", userService.findAllUserPage(pageable));
                modelAndView.setViewName("admin_dashboard");
            }

        } else {
            modelAndView.setViewName("redirect:/");
        }

//        ModelAndView modelAndView = new ModelAndView("guideline");
        return modelAndView;


//        request.setAttribute("users", userService.getAllUsers());
//        return new ModelAndView("admin_dashboard");
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
//        emailMethod(user);
        return new ModelAndView("redirect:/admin/list");
    }


/*    @GetMapping("/all")
    public List<String> allUsers(@RequestParam(value = "term", defaultValue = "",
            required = false) String term) {
        List<String> sugesstions = new ArrayList<>();
        List<User> allUser = userService.findByFirstName(term);
        for (User user : allUser) {
            sugesstions.add(user.getFirstname()+ "  "+ user.getLastname());
//            sugesstions.add(user.getId().toString());
        }
        return sugesstions;
    }*/


    @GetMapping(value = "/users")
    public List<Map> getUsers(@RequestParam String term) {
        List<User> result = userService.findByFirstName(term);
        return result.stream().map(user -> {
            Map response = new HashMap();
            response.put("label", user.getFirstname());
            response.put("value", user.getFirstname() + " " + user.getLastname());
            response.put("userId", user.getId());
            return response;
        }).collect(Collectors.toList());
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

    @RequestMapping(value = PATH, method = RequestMethod.GET)
    public ModelAndView defaultErrorPage() {
        return new ModelAndView("error");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
