package com.hrizzon.demo2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // FIXME  -> pourquoi pas de constructeur pour JavaMailSender ?

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailValidationToken(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Validation de votre email");
        message.setText("Pour valider votre email, veuillez cliquer sur le lien suivant : "
                + "http://localhost:4200/validate-email/" + token);
        mailSender.send(message);
    }

}