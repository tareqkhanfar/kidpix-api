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
        message.setFrom("kidpix@kid-pix.com");
        message.setTo(to);
        message.setSubject("Your Security Code");
        message.setText(MESSAGE);
        emailSender.send(message);
        userService.insertSecurityCodeForUserByEmail(to ,code );
    }

    public void sendBook(String userName , String to , String BookPath) {
        BookPath =BookPath.replaceAll("/var/www/html" , "http://kid-pix.com") ;

        System.out.println("to : " + to);
        SimpleMailMessage message = new SimpleMailMessage();
        String recipientName = userName; // Replace with the actual recipient's name
        String bookLink = BookPath; // Replace with the actual link to the book
        String yourName = "KidPix"; // Replace with your name
        String yourContactInfo = "help@kidpix.com"; // Replace with your contact information
        String emailMessage = String.format(
                "Hello %s,\n" +
                        "\n" +
                        "I hope this message finds you well!\n" +
                        "\n" +
                        "We are thrilled to announce that your personalized KidPix StoryBook is ready and waiting for you! This unique book, crafted with your own photos and stories, promises to be a delightful journey through your cherished memories.\n" +
                        "\n" +
                        "To access your StoryBook, simply click on the following link: %s\n" +
                        "\n" +
                        "Warm regards,\n" +
                        "\n" +
                        "%s\n" +
                        "KidPix Team\n" +
                        "%s",
                recipientName, bookLink, yourName, yourContactInfo
        );

        message.setFrom("kidpix@kid-pix.com");
        message.setTo(to);
        message.setSubject("Your KidPix StoryBook is Ready!");
        message.setText(emailMessage);
        emailSender.send(message);
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

    public void NotifyStatusBook(String userName, String to, String status, String themeName) {
        SimpleMailMessage message = new SimpleMailMessage();
        String emailSubject = "Update on Your KidPix StoryBook Order";
        String emailBody = String.format(
                "Dear %s,\n\n" +
                        "We wanted to let you know that the status of your StoryBook '%s' has been updated to: %s.\n\n" +
                        "If you have any questions or need assistance, please feel free to contact us at help@kidpix.com.\n\n" +
                        "Thank you for choosing KidPix!\n\n" +
                        "Best regards,\n" +
                        "The KidPix Team",
                userName, themeName, status
        );

        message.setFrom("kidpix@kid-pix.com"); // Replace with your sender email
        message.setTo(to);
        message.setSubject(emailSubject);
        message.setText(emailBody);
        emailSender.send(message);
    }

    public void sendError(String FirstName , String LastName , String to, String KidName  ) {
        SimpleMailMessage message = new SimpleMailMessage();
        String emailSubject = "<strong>KidPix : Face Not Detected !</strong>";
        String errorMessage = "Dear " + FirstName + " " + LastName + ",\n\n" +
                "We regret to inform you that your face for KID :"+KidName + " was not detected in the uploaded image. " +
                "Please try again by uploading another image that contains your face.\n\n" +
                "Sincerely,\n" +
                "The KidPix Team";


        message.setFrom("kidpix@kid-pix.com");
        message.setTo(to);
        message.setSubject(emailSubject);
        message.setText(errorMessage);
        emailSender.send(message);
    }
}
