import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mensageiro {

    private static final String REMETENTE = "klebson.ifpb@gmail.com";
    private static final String SENHA = "ufwkxhusodouynmd";

    public static void enviarMensagem(String destinatario, String assunto, String mensagem) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session sessao = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA);
            }
        });

        try {
            Message email = new MimeMessage(sessao);
            email.setFrom(new InternetAddress(REMETENTE));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            email.setSubject(assunto);
            email.setText(mensagem);

            Transport.send(email);
            System.out.println("E-mail enviado para " + destinatario);

        } catch (MessagingException e) {
            System.out.println("Falha ao enviar para " + destinatario + ": " + e.getMessage());
        }
    }
}