package com.email.controller;

import com.email.Dao.UserRepository;
import com.email.model.User;
import com.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Random;
@RestController
public class ForgetController {
    Random random = new Random(1000);
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sent-otp/{email}")
    public String sentOTP(@PathVariable("email") String email, HttpSession session)
    {

        System.out.println("Email"+email);
        //generating 4 digit otp

        int otp = random.nextInt(999999);
        System.out.println("OTP"+otp);

        //code for sent otp to email
        String subject="OTP From SCM";
        String message=""
                +"<div style='border:1px solid #e2e2e2; padding:20px'>"
                + "<h1>"
                +"OTP is "
                + "<b>" +otp
                + "</n>"
                + "</h1>"
                + "</div>";

        String to=email;

        boolean flag=this.emailService.sendEmail(subject,message,to);
        if(flag)
        {
            session.setAttribute("myotp",otp);
            session.setAttribute("email",email);
            return "OTP is sent successfully";
        }
        else
        {
         session.setAttribute("Message","check your email id !! ");
         return "verify your email-id";
        }


    }

    //verify otp
    @PostMapping("/verify-otp/{otp}")
    public String verifyOtp(@PathVariable("otp") String otp,HttpSession session)
    {
        System.out.println("this works");
        try {
            int myOtp = (int) session.getAttribute("myotp");
            String email = (String) session.getAttribute("email");
            if(myOtp==Integer.parseInt(otp))
            {
                //password change form
                User user = this.userRepository.getUserByEmail(email);
                if(user==null)
                {
                    //sent error message
                    session.setAttribute("message","User does not exist with this mail!!");
                    return "forgot_email_form";
                }
                else
                {
                    //sent change password form
                    return  "otp is valid";
                }
            }
            else
            {
                session.setAttribute("message","You have entered wrong otp");
                return "verify_otp";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "wrong details";
    }

}

