package application;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Christian Harris
 */
public class Emailer implements Runnable{
    private String recipient;
    private PDDocument document;
    
    public Emailer(){
        this.recipient = null;
        this.document = null;
    }
    
    public Emailer(String recipient, PDDocument document){
        this.recipient = recipient;
        this.document = document;
    }
    
    public void setRecipient(String recipient){
        this.recipient = recipient;
    }
    
    public void setDocument(PDDocument document){
        this.document = document;
    }
    
    @Override
    public void run(){
        this.sendMessage(this.recipient, this.document);
    }
    
    private void sendMessage(String recipient, PDDocument document){
        String from = "harris.chrw@gmail.com";
        String password = "SilverAngel3.0";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        //properties.put("mail.smtp.ssl.endable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};

        /*Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });*/
        Session session = Session.getInstance(properties, auth);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            /*Address[] addresses = new Address[recipients.size()];
            for(int i = 0; i < recipients.size(); i++){
                addresses[i] = new InternetAddress(recipients.get(i));
            }*/
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
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
            //System.out.println("Sending...");
            Transport.send(message);
            //System.out.println("Sent message successfully...");
        }
        catch(MessagingException | IOException ex){
            ex.printStackTrace();
        }
    }
}
