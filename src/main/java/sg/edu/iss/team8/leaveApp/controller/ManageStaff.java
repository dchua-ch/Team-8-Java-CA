package sg.edu.iss.team8.leaveApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sg.edu.iss.team8.leaveApp.model.*;
import sg.edu.iss.team8.leaveApp.repo.AdminRepo;
import sg.edu.iss.team8.leaveApp.repo.EmployeeRepo;
import sg.edu.iss.team8.leaveApp.repo.ManagerRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.service.UserService;

import java.io.Console;
import java.util.*;

@Controller
@RequestMapping(value="/admin/user")
public class ManageStaff {

    @Autowired
    private UserService uService;

    @Autowired
    UserRepo urepo;

    @Autowired
    EmployeeRepo emrepo;

    @Autowired
    ManagerRepo marepo;

    @Autowired
    AdminRepo adrepo;

    @RequestMapping(value = "/list")
    public ModelAndView userListPage() {
        ModelAndView mav = new ModelAndView("user-list");
        List<User> userList = urepo.getAllUsers();
        mav.addObject("userList", userList);
        return mav;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editEmployeePage(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("user-edit");

        User user = uService.findUser(id);
        String simpleName = user.getClass().getSimpleName();
        System.out.println(simpleName);
        System.out.println("check user type");

        if (simpleName.equals("Employee")){
            Employee myEmployee = emrepo.findById(Integer.valueOf(id)).get();

            Staff staff = new Staff(myEmployee.getUserId(),myEmployee.getName(),myEmployee.getUsername(),myEmployee.getPassword()
                    ,myEmployee.getAnnualLeaveN(),myEmployee.getMedicalLeaveN(),myEmployee.getCompLeaveN(),myEmployee.getReportsTo(),"employee");

            System.out.println(myEmployee.getName() + myEmployee.userId);
            mav.addObject("staff", staff);

        }else if(simpleName.equals("Manager")){

            Manager myMamager = marepo.findById(Integer.valueOf(id)).get();

            Staff staff = new Staff(myMamager.getUserId(),myMamager.getName(),myMamager.getUsername(),myMamager.getPassword()
                    ,myMamager.getAnnualLeaveN(),myMamager.getMedicalLeaveN(),myMamager.getCompLeaveN(),myMamager.getReportsTo(),"manager");

            mav.addObject("staff", staff);


        }else if(simpleName.equals("Admin")){
            // admin
            System.out.println("go to Admin");

            Admin myAdmin = adrepo.findById(Integer.valueOf(id)).get();

            Staff staff = new Staff(myAdmin.getUserId(),myAdmin.getName(),myAdmin.getUsername(),myAdmin.getPassword()
                    ,0,0,0,0,"admin");
//            Staff staff = new Staff(myAdmin.getUserId(),myAdmin.getName(),myAdmin.getUsername(),myAdmin.getPassword()
//                    ,myAdmin.getAnnualLeaveN(),myAdmin.getMedicalLeaveN(),myAdmin.getCompLeaveN(),myAdmin.getReportsTo(),"admin");
            mav.addObject("staff", staff);
        }

        ArrayList<String> eidList = new ArrayList<String>(Arrays.asList("employee","manager","admin"));
        mav.addObject("eidlist", eidList);


        ArrayList<String> reportlist = new ArrayList<>();
        List<User> userList = urepo.getAllUsers();
        for (User currentUser:userList) {
            reportlist.add(String.valueOf(currentUser.userId));
        }
        mav.addObject("reportlist", reportlist);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editEmployee(@ModelAttribute @Validated Staff staff, BindingResult result,
                                     @PathVariable String id) {
        if (result.hasErrors())
            return new ModelAndView("user-edit");

        ModelAndView mav = new ModelAndView("forward:/admin/user/list");
        String message = "Employee was successfully updated.";

        System.out.println(message);


        //get data to update
        System.out.println("get data to update");
        System.out.println(staff.getUserId());
        System.out.println(staff.getUser_type());

        urepo.updateUserType(staff.getUser_type(), Integer.valueOf(id));
        //change user type, can't success
//        urepo.updateUserType(staff.getUser_type(), staff.getUserId());

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encoderPasswod = encoder.encode(staff.getPassword());

        if (staff.getUser_type().equals("employee")){
            Employee updateEmployee = emrepo.findById(Integer.valueOf(id)).get();
            updateEmployee.setAnnualLeaveN(staff.getAnnualLeaveN());
            updateEmployee.setCompLeaveN(staff.getCompLeaveN());
            updateEmployee.setMedicalLeaveN(staff.getMedicalLeaveN());
            updateEmployee.setReportsTo(staff.getReportsTo());
            updateEmployee.setName(staff.getName());
            updateEmployee.setUsername(staff.getUsername());
//            updateEmployee.setPassword(encoderPasswod);

            emrepo.saveAndFlush(updateEmployee);

        }else if(staff.getUser_type().equals("manager")){

            Manager updateManager = marepo.findById(Integer.valueOf(id)).get();
            updateManager.setAnnualLeaveN(staff.getAnnualLeaveN());
            updateManager.setCompLeaveN(staff.getCompLeaveN());
            updateManager.setMedicalLeaveN(staff.getMedicalLeaveN());
            updateManager.setReportsTo(staff.getReportsTo());
            updateManager.setName(staff.getName());
            updateManager.setUsername(staff.getUsername());
//            updateManager.setPassword(encoderPasswod);
            marepo.saveAndFlush(updateManager);

        }else if(staff.getUser_type().equals("admin")){
            // admin
            Admin updateAdmin = adrepo.findById(Integer.valueOf(id)).get();
            updateAdmin.setName(staff.getName());
            updateAdmin.setUsername(staff.getUsername());
//            updateAdmin.setPassword(encoderPasswod);
            adrepo.saveAndFlush(updateAdmin);
        }

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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView newUserPage() {
        ModelAndView mav = new ModelAndView("user-new", "staff", new Staff() {
        });
        ArrayList<String> eidList = new ArrayList<String>(Arrays.asList("employee","manager","admin"));
        mav.addObject("eidlist", eidList);


        ArrayList<String> reportlist = new ArrayList<>();
        List<User> userList = urepo.getAllUsers();
        for (User user:userList) {
            reportlist.add(String.valueOf(user.userId));
        }
        mav.addObject("reportlist", reportlist);

        return mav;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute @Validated Staff staff, BindingResult result) {
        if (result.hasErrors())
            return new ModelAndView("user-new");
        ModelAndView mav = new ModelAndView();
        ArrayList<String> eidList = new ArrayList<String>(Arrays.asList("employee","manager","admin"));
        mav.addObject("eidlist", eidList);

        System.out.println(staff.toString());

        //before encode
        System.out.println("before encode");

        System.out.println(staff.getPassword());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoderPasswod = encoder.encode(staff.getPassword());

        //after encode
        System.out.println("after encode");
        System.out.println(encoderPasswod);

        System.out.println(staff.getUser_type());
        if (staff.getUser_type().equals("employee")){

            Employee myEmployee = new Employee(staff.getName(),staff.getUsername(),encoderPasswod,staff.getAnnualLeaveN(),
                    staff.getMedicalLeaveN(),staff.getCompLeaveN(),staff.getReportsTo());
//            Employee myEmployee = new Employee(staff.getName());
            urepo.saveAndFlush(myEmployee);

        }else if(staff.getUser_type().equals("manager")){

            Manager myManager = new Manager(staff.getName(),staff.getUsername(),encoderPasswod,staff.getAnnualLeaveN(),
                    staff.getMedicalLeaveN(),staff.getCompLeaveN(),staff.getReportsTo());
            urepo.saveAndFlush(myManager);

        }else if(staff.getUser_type().equals("admin")){
            // admin
            Admin myAdmin = new Admin(staff.getName(),staff.getUsername(),encoderPasswod);
            urepo.saveAndFlush(myAdmin);
        }
        mav.setViewName("forward:/admin/user/list");
        return mav;
    }





}
