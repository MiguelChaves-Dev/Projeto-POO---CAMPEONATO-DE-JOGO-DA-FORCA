import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.io.File;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

public class Mensageiro {

    private static final String REMETENTE = "campeonatodeforca.ifpb@gmail.com";
    private static final String SENHA = "uawgumgkgpdqdgui";

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
            JOptionPane.showMessageDialog(null,"Código Enviado com Sucesso!");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null,"Falha ao enviar para " + destinatario + ": " + e.getMessage());
        }
    }
    public static void enviarComAnexo(String destinatario, String assunto,
                                      String mensagem, File pdf) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session sessao = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA);
            }
        });

        try {
            // parte 1: o texto do e-mail
            MimeBodyPart corpo = new MimeBodyPart();
            corpo.setText(mensagem);

            // parte 2: o PDF
            MimeBodyPart anexo = new MimeBodyPart();
            anexo.setDataHandler(new DataHandler(new FileDataSource(pdf)));
            anexo.setFileName(pdf.getName());

            Multipart partes = new MimeMultipart();
            partes.addBodyPart(corpo);
            partes.addBodyPart(anexo);

            Message email = new MimeMessage(sessao);
            email.setFrom(new InternetAddress(REMETENTE));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            email.setSubject(assunto);
            email.setContent(partes);

            Transport.send(email);
            JOptionPane.showMessageDialog(null, "Relatório enviado com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();   // mostra a causa completa no console
            JOptionPane.showMessageDialog(null, "Falha: " + e.getMessage());
    }
    }
}