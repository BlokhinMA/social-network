package ru.sstu.socialnetworkbackend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    @Async
    public void send(String to, String message) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject("Подтвердите ваш email");
            helper.setText(message, true);
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Не удалось отправить письмо", e);
        }
    }

}
