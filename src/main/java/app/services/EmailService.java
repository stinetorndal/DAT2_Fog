package app.services;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public void sendEmail(String toEmail, String subject, String bodyText) {
        try {
            Session session = getMailSession();
            Message message = createMimeMessage(session, toEmail, subject, bodyText);
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("Afsendelse af email fejlede: " + e.getMessage());
        }
    }
    private Message createMimeMessage (Session session, String toEmail, String subject, String bodyText) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("donotreply@fog-byggecenter.dk"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(bodyText);
        return  message;
    }

    private Session getMailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", System.getenv("MAILTRAP_HOST"));
        properties.put("mail.smtp.port", "2525");

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(System.getenv("MAILTRAP_USER"), System.getenv("MAILTRAP_PASS"));
            }
        });
    }
}