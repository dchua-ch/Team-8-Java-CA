package sg.edu.iss.team8.leaveApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.service.UserService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value="/admin/user")
public class ManageStaff {

    @Autowired
    private UserService uService;


    @RequestMapping(value = "/list")
    public ModelAndView userListPage() {
        ModelAndView mav = new ModelAndView("user-list");
        List<User> userList = uService.findAllUsers();
        mav.addObject("userList", userList);
        return mav;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUserPage(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("user-edit");
        User user = uService.findUser(id);
        mav.addObject("user", user);
        return mav;
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable String id)
            {
        ModelAndView mav = new ModelAndView("forward:/admin/user/list");
        User user = uService.findUser(id);
        uService.removeUser(user);
        String message = "The user " + user.getUserId() + " was successfully deleted.";
        System.out.println(message);
        return mav;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute @Validated User user, BindingResult result) {
        if (result.hasErrors())
            return new ModelAndView("user-new");
        ModelAndView mav = new ModelAndView();
        uService.createUser(user);
        mav.setViewName("forward:/admin/user/list");
        return mav;
    }





}
