package application;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import javax.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Christian Harris
 */
public class Emailer { 
    public static void sendMessage(ArrayList<String> recipients, PDDocument document){
	if(recipients.size() >0){
            String from = "harris.chrw@gmail.com";
            String password = "SilverAngel3.0";
	
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.endable", "true");
            properties.put("mail.smtp.auth", "true");
	
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });
	
            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                Address[] addresses = new Address[recipients.size()];
                for(int i = 0; i < recipients.size(); i++){
                    addresses[i] = new InternetAddress(recipients.get(i));
                }
                message.setRecipients(Message.RecipientType.TO, addresses);
                message.setSubject("Subject Line");
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText("This is Message Body");
		
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                document.save(outputStream);
                byte[] bytes = outputStream.toByteArray();
                DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
                MimeBodyPart pdfBodyPart = new MimeBodyPart();
                pdfBodyPart.setDataHandler(new DataHandler(dataSource));
                pdfBodyPart.setFileName("test.pdf");
		
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(pdfBodyPart);
		
                message.setContent(multipart);
                System.out.println("Sending...");
                Transport.send(message);
                System.out.println("Sent message successfully...");
                //document.close();
            }
            catch(MessagingException ex){
                ex.printStackTrace();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
