package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserService userService;

    public void sendSecurityCode(String to) {
        System.out.println("to : " + to);
        int code = generateSecurityCode();
        SimpleMailMessage message = new SimpleMailMessage();

        String MESSAGE = "Hello,\n" +
                "\n" +
                "Thank you for using KID pix! To continue with your request, please use the following security code:\n" +
                "\n" +
                "Security Code: ["+code+"]\n" +
                "\n" +
                "This code is part of our security process to ensure your account's safety and integrity. Please enter this code in the designated area on our website to proceed.";
        message.setFrom("tareq@asd.ps");
        message.setTo(to);
        message.setSubject("Your Security Code");
        message.setText(MESSAGE);
        emailSender.send(message);
        userService.insertSecurityCodeForUserByEmail(to ,code );
    }

    private int generateSecurityCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // generates a 6-digit code
        return code;
    }

    public boolean validateSecurityCode(String email, Integer securityCode) {
      UserEntity userEntity =  userService.getUserInfoByEmail(email);
       boolean flag =  userEntity.getSecurity_code().intValue() == securityCode.intValue() ;
       if (flag) {
           userEntity.setStatus_account((byte) 1);
           userService.save(userEntity);
           return true ;
       }
       return false ;
    }
}
