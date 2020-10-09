package com.whoiscan.userregistertest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.whoiscan.userregistertest.entity.Role;
import com.whoiscan.userregistertest.entity.User;
import com.whoiscan.userregistertest.model.Result;
import com.whoiscan.userregistertest.payload.UserReq;
import com.whoiscan.userregistertest.repository.RoleRepository;
import com.whoiscan.userregistertest.repository.UserRepository;
import com.whoiscan.userregistertest.security.SignedUser;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CabinetController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping({"/", "/cabinet"})
    public String getCabinetPage(Model model) {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        model.addAttribute("user", user);
        return "cabinet";
    }

    @GetMapping("/cabinet/about")
    public String getDeveloper(Model model, @SignedUser User user) {
//        User user = (User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
        model.addAttribute("user", user);
        return "cabinet/about";
    }


    @GetMapping("/cabinet/users/list")
    @ResponseBody
    public List<User> getUsers() {
       return userRepository.findAll();
    }

    @GetMapping("/cabinet/userlist")
    public String getUserList(Model model, @SignedUser User user) {
        model.addAttribute("user", user);
        return "/cabinet/userlist";
    }

    @PutMapping("/cabinet/user/edit/{id}")
    @ResponseBody
    public Result editUser(@Valid @RequestBody UserReq userReq,
                           @PathVariable(value = "id") Integer id){
        Result result=new Result();
        Optional<User> optional=userRepository.findById(id);
        if (optional.isPresent()){
            User editedUser=optional.get();
            editedUser.setFullName(userReq.getFullName());
            List<Role>roles=new ArrayList<>();
            if (userReq.isRoleAdmin()){
                roles.add(roleRepository.getByName("ROLE_ADMIN"));
            }
            if (userReq.isRoleModer()){
                roles.add(roleRepository.getByName("ROLE_MODER"));
            }
            if (userReq.isRoleUser()){
                roles.add(roleRepository.getByName("ROLE_USER"));
            }
            editedUser.setRoles(roles);
            editedUser.setAccountNonLocked(userReq.isAccountNonLocked());
            User savedUser=userRepository.save(editedUser);
            if (savedUser!=null){
                result.setSuccess(true);
                result.setMessage(savedUser.getFullName()+" Successfully updated");
            } else {
                result.setSuccess(false);
                result.setMessage(editedUser.getFullName()+" Error in updating");
            }
        }else {
            result.setSuccess(false);
            result.setMessage(userReq.getFullName()+" Not found");
        }
        return result;
    }
//    @DeleteMapping("/cabinet/user/delete/{id}")
//    @ResponseBody
//    public Result deleteUser(@PathVariable(value = "id") Integer id) {
//        userRepository.deleteById(id);
//        Optional<User> deletedUser =  userRepository.findById(id);
//        Result result = new Result();
//        if (!deletedUser.isPresent()) {
//            result.setSuccess(true);
//            result.setMessage("Successfully deleted");
//        } else {
//            result.setSuccess(false);
//            result.setMessage("Error in deleting");
//        }
//        return result;
//    }
//
//
//
//    @GetMapping("/admin")
//    public String getAdminPage() {
//        return "admin";
//    }
//
//    @GetMapping("/moderator")
//    public String getModerPage() {
//        return "moderator";
//    }
//
//    @GetMapping("/user")
//    public String getUserPage() {
//        return "user";
//    }
}


