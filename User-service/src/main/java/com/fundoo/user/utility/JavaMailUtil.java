package com.fundoo.user.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class JavaMailUtil {

    @Autowired
    JavaMailSender javaMailSender;

    public SimpleMailMessage sendMail(String email, String jwtToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link = "http://localhost:8081/fundoo/user/verify?token=";
        message.setTo(email);
        message.setSubject("Account verification mail");
        message.setText("Registration Successful to activate your account click on this link   " + (link + jwtToken));
        javaMailSender.send(message);
        return message;
    }

    public SimpleMailMessage resetPwMail(String email, String jwtToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link="http://localhost:4200/update-password";
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link : " + (link +";token="+ jwtToken));
        javaMailSender.send(message);
        return message;
    }


}
