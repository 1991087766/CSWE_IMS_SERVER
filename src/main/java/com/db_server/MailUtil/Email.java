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
    EmailInfo mail = new EmailInfo();
    public boolean sendHtmlMail(String subject,String content,String receiver){

        boolean result = true;

        try{
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

            // 设定mail server
            senderImpl.setHost(mail.MailServer);
            senderImpl.setPort(mail.MailPost);
            senderImpl.setUsername(mail.MailUsername);                             // 根据自己的情况,设置发件邮箱地址
            senderImpl.setPassword(mail.MailPassword);                    // 根据自己的情况, 设置password
            senderImpl.setDefaultEncoding("UTF-8");
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");                                 // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            senderImpl.setJavaMailProperties(prop);

            // 建立邮件消息,发送简单邮件和html邮件的区别
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

            // 设置收件人，寄件人
            messageHelper.setTo(receiver);
            messageHelper.setFrom(mail.MailUsername);
            messageHelper.setSubject(subject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText(content,true);

            // 发送邮件
            senderImpl.send(mailMessage);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            result = false;
//            logger.info("EmailUtils.method [sendHtmlMail]: email send result-" + result +",error message-" + e);
        }

        return result;
    }
//    public static String getHtmlStr(JsonArray data, String receiver, int day){
//
//        JsonObject json = data.get(0).getAsJsonObject();
//        String title = json.get("task_tittle").getAsString();
        // NO.1、获取收件人名
//        String receiverName = getEmailStr(receiver);
        // NO.2、获取邮件表格
//        String emailTable = getTableStr(data);
        // NO.3、获取邮件时间
//        String emailDate = Date2Str.getCurrentDate2();
//
//        String html = "<html><head></head><body>"
//                + "<p>" +receiverName+" 您好,</p>"
//                + "<p>&nbsp;&nbsp;&nbsp;&nbsp;生产日程-<font size=\"4\" color=\"#FF0000\" face=\"Verdana\" >"+title+"</font>中的下列任务将于<font size=\"4\" color=\"#FF0000\" face=\"Verdana\" >"+day+"</font>天后开始启动，请关注:</p>"
//                + "<div style=\"padding-left:20px\">"
//                + "<table border=\"1px\" cellspacing=\"0px\" style=\"border-collapse:collapse\">"
//                + "<tr>"
//                + "<td>任务名</td><td>启动时间</td><td>结束时间</td>"
//                + "</tr>"
//                + emailTable
//                + "</table></div>"
//                + "<p>&nbsp;&nbsp;&nbsp;&nbsp;每项任务的详细情况,请登陆 "
//                + "<a href=\"http://100.78.205.244:8080\"><font size=\"4\" color=\"#FF0000\">项目综合管理&评价系统</font></a> 查看!</p>"
//                + "<p>项目综合管理&评价系统</br>"+emailDate+"</p>"
//                + "</body></html>";
//
//        return html;
//    }
}
