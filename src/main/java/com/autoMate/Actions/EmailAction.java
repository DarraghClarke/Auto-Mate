package com.autoMate.Actions;

import com.autoMate.Helpers.Parser;
import com.autoMate.objects.Options;
import com.google.gson.JsonObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

//This could in theory send an email, but its terribly insecure
//and gmail would block it unless the "less secure apps option" is allowed
public class EmailAction implements Action {

    public JsonObject response;
    public String name;
    public Options options;

    public EmailAction(String name) {
        this.name = name;
    }

    public void sendEmail(Map<String, JsonObject> eventLog){

        String to = Parser.format(eventLog, getOptions().getEmailSenderAddress());
        String from = Parser.format(eventLog, getOptions().getEmailReceiverAddress());
        //TODO don't do auth like this
        String auth = Parser.format(eventLog, getOptions().getEmailPassword());

        //TODO don't force gmail, probably move to JSON option
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, auth);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //Todo:: allow to be set from body
            message.setSubject("Subject Line");
            //set the message
            message.setText(Parser.format(eventLog, getOptions().getEmailMessage()));
            // Send email
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public JsonObject getResponse() {
        return response;
    }
}
