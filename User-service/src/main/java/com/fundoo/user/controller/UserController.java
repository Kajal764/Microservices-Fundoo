package com.fundoo.user.controller;

import com.fundoo.user.dto.*;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.model.User;
import com.fundoo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/fundoo/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) throws MessagingException, UnsupportedEncodingException {
        return userService.register(registerUserDto);
    }

    @GetMapping("/verify")
    public Object validateUser(@RequestParam String token) throws UnsupportedEncodingException {
        return userService.verifyUser(token);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        String token = userService.login(loginDto);
        response.setHeader("AuthorizeToken", token);
        return new ResponseDto("Login Successful", 200);
    }

    @PostMapping(value = "/forgot_password")
    public ResponseDto forgotPassword(@RequestBody ForgotPwDto forgotPwDto) throws MessagingException {
        return userService.checkDetails(forgotPwDto);
    }

    @PutMapping("update_password/{token}")
    public ResponseDto updatePassword(@PathVariable("token") String token, @RequestBody UpdatePasswordDto updatePasswordDto) {
        return userService.update(token, updatePasswordDto);
    }

    @GetMapping("/verifiedUser")
    public List<User> VerifiedUser() {
        List<User> userList = userService.verifyAccount();
        if (userList.isEmpty())
            throw new LoginUserException("User Data Not Found");
        return userList;
    }

    @GetMapping("/unVerifiedUser")
    public List<User> unVerifiedUser() {
        List<User> userList = userService.unVerifyAccount();
        if (userList.isEmpty())
            throw new LoginUserException("User Data Not Found");
        return userList;
    }

    @GetMapping("/id/{token}")
    public int getUserId(@PathVariable("token") String token){
        return userService.getUserId(token);
    }

}
