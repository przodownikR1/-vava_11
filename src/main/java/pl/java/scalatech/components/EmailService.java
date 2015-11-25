package pl.java.scalatech.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private MailSender mailSender;  //fizycznie odpowiada za wysylke 

    public void send(String from, String to, String subject, String message) {
        sendEmail(from, to, subject, message);
    }

    private void sendEmail(String from, String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

}
