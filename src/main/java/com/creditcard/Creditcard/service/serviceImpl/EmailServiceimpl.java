package com.creditcard.Creditcard.service.serviceImpl;

import com.creditcard.Creditcard.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * This class implements emailService interface and provides the logic for email sending.
 */
@Service
public class EmailServiceimpl implements EmailService {
    private final JavaMailSender mailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceimpl.class);

    /**
     * Constructor.
     * @param mailSender JavaMailSender.
     */
    public EmailServiceimpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Method is used to send email.
     * @param to Email id provided by the user.
     * @param email The body of the email.
     */
    @Override
    @Async
    public boolean send(String to, String email) {
        try{
            MimeMessage mimeMessage =  mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("CreditCard Bill Payment");
            helper.setFrom("creditcardservices12345@gmail.com");
            mailSender.send(mimeMessage);
            return true;
        }
        catch (MessagingException e){
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send");
        }
    }
}
