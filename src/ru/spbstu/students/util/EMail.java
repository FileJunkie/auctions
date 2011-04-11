package ru.spbstu.students.util;

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*for mail.ru replace smtps to smtp and, change hosts and port to 25. For gmail port is 465 or 587*/

public class EMail {
    
	private static String mailhost = "smtp.gmail.com";
    static String host = "smtp.gmail.com";
    static String username;
    static String password;    
    
    static void setLP(String user, String pass) {
        username = user;
        password = pass;
    }
    public static synchronized void sendMail(String subject, String body, String sender, String recipients)
                                                                                   throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
 
        Properties props = System.getProperties();
        props.setProperty("mail.transport.protocol", "smtps");
        props.setProperty("mail.smtps.host", mailhost);
        props.put("mail.smtps.port", "465");
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.socketFactory.port", "465");
        props.put("mail.smtps.socketFactory.fallback", "false");
        props.setProperty("mail.smtps.quitwait", "false");
 
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
 
        MimeMessage message = new MimeMessage(session);
        message.setSender(new InternetAddress(sender));
        message.setSubject(subject);
        message.setContent(body, "text/plain");
        Transport t = session.getTransport("smtps");
        try {
            t.connect(host, username, password);
        } finally {
            t.close();
        }
        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        Transport.send(message);
    }
}
