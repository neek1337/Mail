import javax.mail.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class GetMail {

    public static void check(String pop3Host, String storeType, String user,
                              String password)
    {
        try
        {
            // get the session object
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3s.host", pop3Host);
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);
            // emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(pop3Host, user, password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("Новых отчетов" + messages.length);
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Дата: " + message.getSubject());
                System.out.println("Отчет: "+message.getContent().toString());
                String subject = message.getSubject();
                String ans = reader.readLine();
                if ("Y".equals(ans) || "y".equals(ans)) {
                    // set the DELETE flag to true
                    message.setFlag(Flags.Flag.DELETED, true);
                    System.out.println("Marked DELETE for message: " + subject);
                } else if ("n".equals(ans)) {
                    break;
                }
            }
            // expunges the folder to remove messages which are marked deleted
            emailFolder.close(true);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "domqwest@gmail.com";// change accordingly
        String password = "123dom123";// change accordingly

        check(host, mailStoreType, username, password);

    }

}