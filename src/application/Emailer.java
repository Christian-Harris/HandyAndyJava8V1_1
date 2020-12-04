package application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import javax.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Christian Harris
 */
public class Emailer {
    public static void sendMessage(/*String recipient, PDDocument document*/){
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        try{
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setLeading(14);
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("This is a pdf");
            contentStream.endText();
            contentStream.close();
            //document.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
        String to = "harris.chrw@gmail.com";
        String from = "harris.chrw@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("harris.chrw@gmail.com", "SilverAngel3.0");
            }
        });
        // Used to debug SMTP issues
        //session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("This is the Subject Line!");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("This is message body");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            //messageBodyPart = new MimeBodyPart();
            //messageBodyPart.setDataHandler(new DataHandler(document, "application/pdf"));
            
            //now write the PDF content to the output stream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            //writePdf(outputStream);
            byte[] bytes = outputStream.toByteArray();
            
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("test.pdf");
            
            messageBodyPart = new MimeBodyPart();
            multipart.addBodyPart(pdfBodyPart);
            
            message.setText("This is actual message");
            message.setContent(multipart);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        try{
            document.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
        
        /*
        String smtpHost = "smtp.gmail.com"; //replace this with a valid host
        int smtpPort = 465; //replace this with a valid port
                 
        String sender = "harris.chrw@gmail.com"; //replace this with a valid sender email address
        String recipient = "harris.chrw@gmail.com"; //replace this with a valid recipient email address
        String content = "dummy content"; //this will be the text of the email
        String subject = "dummy subject"; //this will be the subject of the email
         
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);     
        //Session session = Session.getDefaultInstance(properties, null);
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("harris.chrw@gmail.com", "SilverAngel3.0");
            }
        });
         
        ByteArrayOutputStream outputStream = null;
         
        try {           
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);
             
            //now write the PDF content to the output stream
            outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            //writePdf(outputStream);
            byte[] bytes = outputStream.toByteArray();
             
            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("test.pdf");
                         
            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);
             
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
             
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport.send(mimeMessage);
             
            System.out.println("sent from " + sender + 
                    ", to " + recipient + 
                    "; server = " + smtpHost + ", port = " + smtpPort);         
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            //clean off
            if(null != outputStream) {
                try { outputStream.close(); outputStream = null; }
                catch(Exception ex) { }
            }
        }*/
    }
}
