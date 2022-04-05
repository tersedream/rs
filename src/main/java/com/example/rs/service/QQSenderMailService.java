package com.example.rs.service;

import com.example.rs.control.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class QQSenderMailService {
    Logger logger = LoggerFactory.getLogger(A.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;


    public void sendTextMail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        try {
            javaMailSender.send(msg);
        }catch (Exception e){
            logger.error("邮件服务器发送失败!");
            logger.error("目标: " + to);
            logger.error("主题: " + subject);
            logger.error("内容: " + text);
            e.printStackTrace();
            throw e;
        }
    }

    public void sendAttachmentMail(String to,String subject,String text,String path) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // 创建一个multipart格式的message
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
            // 添加附件信息
            FileSystemResource file = new FileSystemResource(new File(path));
            String fileName = path;
            messageHelper.addAttachment(fileName, file);
            // 发送带附件的邮件
            javaMailSender.send(mimeMessage);
            logger.info("邮件发送成功");
        } catch (Exception e) {
            logger.error("带有附件的邮件发送失败。" + e.getMessage());
        }
    }
}
