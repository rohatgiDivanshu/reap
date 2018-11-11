package com.divanshu.reap.finalProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


/*
    @GetMapping("/list")
    public ModelAndView adminUserList(Model model, @ModelAttribute User user)
    {
        model.addAttribute("users",userService.findAll());
        return new ModelAndView("admin_dashboard");
    }
*/

/*
    @GetMapping("/list")
    public ModelAndView adminUserSearch(Model model, @RequestParam(defaultValue = "") String firstname, HttpServletRequest request)
    {
        User user = new User();
        user.setFirstname(request.getParameter(firstname));
        model.addAttribute("users",userService.findByFirstNameLike(firstname));
        return new ModelAndView("admin_dashboard");
    }
*/


}
