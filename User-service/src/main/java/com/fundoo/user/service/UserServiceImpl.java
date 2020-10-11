package com.fundoo.user.service;

import com.fundoo.user.dto.*;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.exception.RegistrationException;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public ResponseDto register(RegisterUserDto registerUserDto) {
        registerUserDto.password = bCryptPasswordEncoder.encode(registerUserDto.password);
        User user = new User(registerUserDto);

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent())
            throw new RegistrationException("User already register", 400);
        user.setAccountCreatedDate(LocalDateTime.now());
        User save = userRepository.save(user);
        String jwtToken = jwtUtil.createJwtToken(save.getEmail());
        javaMailUtil.sendMail(save.getEmail(), jwtToken);
        return new ResponseDto("Registration Successful,Check mail to activate your account", 200);
    }

    @Override
    public Object verifyUser(String token) {
        Object validateEmail = jwtUtil.verify(token);
        if (validateEmail != null) {
            Optional<User> data = userRepository.findByEmail(validateEmail.toString());
            if (data.isPresent()) {
                data.get().setVarified(true);
                userRepository.save(data.get());
                return "Verify User Successfully";
            }
        }
        return "Token Expired Register Again";
    }


    @Override
    public String login(LoginDto loginDto) {

        Optional<User> user = userRepository.findByEmail(loginDto.email);
        if (user.isEmpty())
            throw new LoginUserException("No such account found");
        String CLIENT_ID = user.get().getEmail();
        if (encoder.matches(loginDto.password, user.get().getPassword())) {
            if (user.get().isVarified() == true) {
                String token = jwtUtil.createJwtToken(CLIENT_ID);
                // RedisService.setToken(CLIENT_ID, token);
//                RedisService.setToken(user.get().getId(), user.get());
                RedisService.setToken(CLIENT_ID, user.get());
                return token;
            }
            throw new LoginUserException("Please Activate your account");
        }
        throw new LoginUserException("Enter valid password");
    }

    @Override
    public List<User> verifyAccount() {
        List<User> varifiedUser = userRepository.findAll();
        varifiedUser.removeIf(user -> !user.isVarified());
        return varifiedUser;
    }

    @Override
    public List<User> unVerifyAccount() {
        List<User> userList = userRepository.findAll();
        userList.removeIf(user -> user.isVarified());
        return userList;
    }

    @Override
    public ResponseDto checkDetails(ForgotPwDto forgotPwDto) {
        Optional<User> isDetailPresent = userRepository.findByEmail(forgotPwDto.email);
        if (!isDetailPresent.isEmpty()) {
            String jwtToken = jwtUtil.createJwtToken(forgotPwDto.email);
            javaMailUtil.resetPwMail(forgotPwDto.email, jwtToken);
            return new ResponseDto("Email Has been sent to your account", 200);
        }
        throw new LoginUserException("Account not found");
    }

    @Override
    public ResponseDto update(String token, UpdatePasswordDto updatePasswordDto) {
        Object verify = jwtUtil.verify(token);
        Optional<User> userInfo = userRepository.findByEmail(verify.toString());
        if (userInfo.isPresent()) {
            if (updatePasswordDto.password.equals(updatePasswordDto.confirmPassword)) {
                String encodedPassword = bCryptPasswordEncoder.encode(updatePasswordDto.password);
                LocalDateTime time = LocalDateTime.now();
                userRepository.updatePasswordAndTime(encodedPassword, time, userInfo.get().getEmail());
                return new ResponseDto("Password updated succesfully", 200);
            }
            throw new LoginUserException("Password Not match");
        }
        throw new LoginUserException("Account not present");
    }

    @Override
    public int getUserId(String token) {
        Object verify = jwtUtil.verify(token);
        String email = verify.toString();
        User user = RedisService.getToken(email);
        if (user == null)
            throw new LoginUserException("User not present");
        return user.getId();
    }
}
