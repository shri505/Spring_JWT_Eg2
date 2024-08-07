package com.example.Spring_Login_Eg3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.Spring_Login_Eg3.config.AuthenticationRequest;
import com.example.Spring_Login_Eg3.entity.UserInfo;
import com.example.Spring_Login_Eg3.service.JwtService;
import com.example.Spring_Login_Eg3.service.UserInfoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController
{

    @Autowired
        private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsService")
        private UserInfoService userService;

    @Autowired
        private JwtService jwtService;

    @PostMapping("/addNewUser")
        public String addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
        public String generateToken(@RequestBody AuthenticationRequest authRequest)
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    return jwtService.generateToken(userDetails.getUsername());
        }

    @PostMapping("/createUser")
        public UserInfo createUser(@RequestBody UserInfo userInfo)
        {
        return userService.createUser(userInfo);
        }

    @GetMapping("/getUser/{id}")
        public UserInfo getUserById(@PathVariable int id)
        {
        return userService.getUserById(id);
        }

    @GetMapping("/getAllUsers")
        public List<UserInfo> getAllUsers()
        {
        return userService.getAllUsers();
        }

    @PutMapping("/updateUser/{id}")
        public UserInfo updateUser(@PathVariable int id, @RequestBody UserInfo userInfo)
        {
            return userService.updateUser(id, userInfo);
        }

    @DeleteMapping("/deleteUser/{id}")
         public void deleteUser(@PathVariable int id)
         {
             userService.deleteUser(id);
         }
}
