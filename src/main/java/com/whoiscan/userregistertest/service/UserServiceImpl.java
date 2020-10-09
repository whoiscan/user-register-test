package com.whoiscan.userregistertest.service;


import com.whoiscan.userregistertest.entity.Role;
import com.whoiscan.userregistertest.entity.User;
import com.whoiscan.userregistertest.model.Result;
import com.whoiscan.userregistertest.payload.RegisterReq;
import com.whoiscan.userregistertest.repository.RoleRepository;
import com.whoiscan.userregistertest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;

    @Override
    public Result saveUser(RegisterReq registerReq) {
        Result result = new Result();
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getByName("ROLE_USER"));
        Optional<User> optional=userRepository.findByUsernameOrEmail(registerReq.getUsername(),
                registerReq.getEmail());
        System.out.println(optional.isPresent());
        if (optional.isPresent()) {
            result.setSuccess(false);
            result.setMessage("This username or email already exist");
        } else {
            User user=new User(registerReq.getUsername(), registerReq.getEmail(),
                    passwordEncoder.encode(registerReq.getPassword()),
                    registerReq.getFullName(), UUID.randomUUID().toString(), roles);
            userRepository.save(user);
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setSubject("Verify your new account ");
            mailMessage.setText("Click here for activate your account. " +
                    "http://localhost/auth/activate?activCode="
                    + user.getActivCode());
            mailMessage.setTo(user.getEmail());
            mailSender.send(mailMessage);
            result.setSuccess(true);
        }
        return result;
    }}


