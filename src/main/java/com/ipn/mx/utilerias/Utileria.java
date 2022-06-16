package com.ipn.mx.utilerias;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utileria {
    public void enviarMail(String correoDestinatario, String asunto, String textoCorreo){
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDestinatario));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(correoDestinatario));
            message.setSubject(asunto);
            message.setText(textoCorreo);
            
            Transport transporte = session.getTransport("smtp");
            transporte.connect("web3CM12@gmail.com", "web3cm12.");
            transporte.sendMessage(message, message.getAllRecipients());
            transporte.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        Utileria u = new Utileria();
        u.enviarMail("web3CM12@gmail.com", "Hola", "Hola, buenos días");
    }
}
