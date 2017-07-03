package com.db_server.MailUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

/**
 * Created by NAVY on 2017/6/19.
 */
public class Email {
    private EmailInfo mail = new EmailInfo();
    private String html;

    private static Email instance;
    public static Email getInstance(){
        if (instance ==null){
            synchronized (Email.class){
                if (instance ==null){
                    try {
                        instance =new Email();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param content
     * @param receiver
     * @return
     */
    private boolean sendHtmlMail(String content,String receiver){

        boolean result = true;

        try{
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

            // 设定mail server
            senderImpl.setHost(mail.MailServer);
            senderImpl.setPort(mail.MailPost);
            senderImpl.setUsername(mail.MailUsername);
            senderImpl.setPassword(mail.MailPassword);
            senderImpl.setDefaultEncoding("UTF-8");
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            senderImpl.setJavaMailProperties(prop);

            // 建立邮件消息,发送简单邮件和html邮件的区别
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

            // 设置收件人，寄件人
            messageHelper.setTo(receiver);
            messageHelper.setFrom(mail.MailUsername);
            messageHelper.setSubject(mail.MailSubject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText(content,true);

            // 发送邮件
            senderImpl.send(mailMessage);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            result = false;
        }

        return result;
    }
    public void SondHtmlMail(String NAME,String Assect, String PWD, String Target){

        html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <style type=\"text/css\">\n" +
                "        span{\n" +
                "            font-family: 楷体;\n" +
                "            font-weight: bold;\n" +
                "            font-size: 20px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"margin-left: 20px;width: 600px;height: 260px;position:absolute;background-color: aqua;border: 2px solid darkgray;border-radius: 20px\">\n" +
                "    <div style=\"width: 560px; height: auto;margin-top:10px;margin-left: 10px\">\n" +
                "        <span style=\"color: blue\">"+NAME+"</span><span> ,您好！</span><br><br>\n" +
                "        <span style=\"color: darkorange; border: 1px solid\">您的账户已成功激活！</span><br><br>\n" +
                "        <span>账号：</span><span style=\"color: green\">"+Assect+"</span><br>\n" +
                "        <span>初始密码：</span><span style=\"color: green\">"+PWD+"</span><br><br>\n" +
                "        <span style=\"color: red\">请及时修改密码，修改密码后请立即删除此邮件，以防泄露！</span>\n" +
                "        <br><br><br><br>\n" +
                "        <span>IMS工作平台</span><br>\n" +
                "        <span>服务器管理：</span><span>NavyXU</span><br>\n" +
                "        <span>联系方式：</span><span>Navy_XU@sina.cn</span>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        sendHtmlMail(html,Target);
    }
}
