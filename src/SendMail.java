import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Crunchify.com
 *
 */

public class SendMail {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public static void main(String args[]) throws AddressException, MessagingException {
        generateAndSendEmail();
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }

    public static void generateAndSendEmail() throws AddressException, MessagingException {

        // Step1
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        // Step2
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.setFrom(new InternetAddress("domqwest@gmail.com"));
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("domqwest@gmail.com"));
        generateMailMessage.setSubject("Test");
        String emailBody = "1";
        generateMailMessage.setContent(emailBody, "text/html");

        // Step3
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "domqwest@gmail.com", "123dom123");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}