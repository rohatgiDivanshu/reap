package com.divanshu.reap.finalProject.services;

import com.divanshu.reap.finalProject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }

    /*public void sendUpdationEmail(String toAddress, String subject, String body) {

        MimeMessage message=sender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message);
        try {
            helper.setFrom(toAddress);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }*/



}
