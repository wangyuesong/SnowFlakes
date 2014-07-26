<%@page import="java.util.Random"%>
<%@page import=" java.util.Properties"%>
<%@page import=" java.util.Date"%>
<%@page import=" java.util.Properties"%>
<%@page import=" java.util.logging.Level"%>
<%@page import=" java.util.logging.Logger"%>
<%@page import=" javax.mail.Address"%>
<%@page import=" javax.mail.Authenticator"%>
<%@page import=" javax.mail.BodyPart"%>
<%@page import=" javax.mail.Message"%>
<%@page import=" javax.mail.MessagingException"%>
<%@page import=" javax.mail.Multipart"%>
<%@page import=" javax.mail.PasswordAuthentication"%>
<%@page import="javax.mail.Session"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeBodyPart"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.internet.MimeMultipart"%>
<%@page import="javax.mail.*"%>
<%@page import="com.tongji.collaborationteam.util.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%

    String address = (String) request.getParameter("email");
    MailSenderInfo mailInfo = new MailSenderInfo();
    mailInfo.setMailServerHost("smtp.gmail.com");
    mailInfo.setMailServerPort("587");
    mailInfo.setValidate(true);
    mailInfo.setUserName("kjamvictory@gmail.com");
    mailInfo.setPassword("jk2010106164");//您的邮箱密码    
    mailInfo.setFromAddress("kjamvictory@gmail.com");
    mailInfo.setToAddress(address);
    mailInfo.setSubject("SnowFlakes Registeration");
    Random random = new Random();
    String rd = "";
    for (int i = 0; i < 4; i++) {
        rd += random.nextInt(10) + "";
    }

    mailInfo.setContent("Hi Guest,\n\t Welcome to Snowflakes, here is the authentication code for register: "+rd+".");
    //这个类主要来发送邮件   
    SimpleMailSender sms = new SimpleMailSender();
    boolean flag = false;

    flag = sms.sendTextMail(mailInfo);//发送文体格式
    SimpleMailSender sm = new SimpleMailSender();

    if (flag) {
        out.clearBuffer();
        out.write(rd);
    } else {
        out.clearBuffer();
        out.write("false");
    }


%>