/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import users.Security;
import users.UserQueries;

/**
 *
 * @author ANOYMASS
 */
public class Mail {

    public static void sendEmail(String subject, String message) {

        Properties props = new Properties();
        final Session session = Session.getInstance(props);
        final Message msg = new MimeMessage(session);

        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.smtp.port", "888");
        
        try {
            Address from = new InternetAddress("pamseuims@gmail.com");
            msg.setContent(message, "text/plain");
            msg.setFrom(from);
            msg.setSubject(subject);
            Security keys = new UserQueries().getEmailKeys();
            
            Task task = new Task() {
              @Override
                protected Object call() throws Exception {

                    Address to = new InternetAddress(keys.getEmail());
                    msg.setRecipient(Message.RecipientType.TO, to);

                    Transport t = null;
                    try {
                        //  msg.setRecipient(Message.RecipientType.TO, to[i]);
                        t = session.getTransport("smtps");
                        t.connect("smtp.gmail.com", "pamseuims", keys.getKey());
                        t.sendMessage(msg, msg.getAllRecipients());

                        updateMessage("Sending Email");

                    } catch (NoSuchProviderException ex) {
                        Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (MessagingException ex) {
                        Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
                        //Logger.getLogger(SendEmailController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (t != null) {

                            try {
                                t.close();
                            } catch (MessagingException ex) {
                                Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                    return null;
                }
            };
            
            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

}
