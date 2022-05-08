package com.email.controller;

import com.email.model.EmailRequest;
import com.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/welcome")
    public String welcome()
    {
        return "hello";
    }


    //api to sent email
    @PostMapping("/sentmail")
    public ResponseEntity<?> sentEmail(@RequestBody EmailRequest request)
    {
        System.out.println(request);
        request.setMessage(UUID.randomUUID().toString());
        boolean result= this.emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());
        if(result)
            return ResponseEntity.ok("Email Successfully sent");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent");

    }
}
