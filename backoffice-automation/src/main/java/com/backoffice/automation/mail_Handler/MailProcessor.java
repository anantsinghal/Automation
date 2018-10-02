package com.backoffice.automation.mail_Handler;

import com.backoffice.automation.utils.Constants;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.backoffice.automation.utils.Utility.*;

/**
 * Created by NEX9ZKA on 31/08/2018.
 */
@Component
public class MailProcessor {
    private static final Logger LOGGER = Logger.getLogger(MailProcessor.class.getName());
    private static final String bodyMessage = "Hi Team,"
            + "\n\n Records reported in file matched to the count in trailer. We are good for today’s recon." +
            "\n\n Thanks and regards" +
            "\n Anant" + "\n FROM SERVER";
    private static final String senderRec = "anant.singhal@pb.com";
    private static final String subject = "Recon File " + "kudhiw" + " status";
    private static final String ANANT = "anant.singhal@pb.com";

    public void sendMailWithTextFileAttachments(List<String> bodyMessage, String subject, String senderRec, List<MailMimeContainer> mailMimeContainers, String... cc) {
        try {
            Multipart multipart = getTextFileMultipart(mailMimeContainers);
            Message message = getMessage(subject, senderRec, multipart, cc);
            Transport.send(message);
            LOGGER.log(Level.INFO, "MAIL SENT");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMailWithZipFileAttachments(String bodyMessage, String html, String subject, String senderRec, List<MailMimeContainer> mailMimeContainers, String... cc) {
        try {
            Multipart multipart = getZipFileMultipart(mailMimeContainers);
            addFileTypesToMultipart(bodyMessage, null, multipart, Constants.PLAIN_TEXT);
            addFileTypesToMultipart(html, "Index.html", multipart, "text/html; charset=utf-8");

            Message message = getMessage(subject, senderRec, multipart, cc);

            Transport.send(message);
            LOGGER.log(Level.INFO, "MAIL SENT");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Multipart addFileTypesToMultipart(String html, String fileName, Multipart multipart, String type) throws MessagingException {
        if (isNotEmpty(html)) {
            BodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(html);
            mimeBodyPart.setContent(html, type);
            if (!type.contains("plain")) {
                mimeBodyPart.setFileName(fileName);
            }
            multipart.addBodyPart(mimeBodyPart);
        }

        return multipart;
    }

   /* private Multipart addMailBodyToMultipart(String bodyMessage, Multipart multipart) throws MessagingException {
        if (isNotEmpty(bodyMessage)) {

            BodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(bodyMessage);
            mimeBodyPart.setContent(bodyMessage, Constants.PLAIN_TEXT);

            multipart.addBodyPart(mimeBodyPart);
        }

        return multipart;
    }*/

    private MimeBodyPart addMessage(String bodyMessage) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyMessage, "text/html; charset=utf-8");
        return mimeBodyPart;
    }


    private Message getMessage(String subject, String sender, Multipart multipart, String[] cc) throws MessagingException, IOException {
        String ccMails = ccArrayToCommaSeparatedString(cc);
        Session session = Session.getInstance(getMailingProperties());
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(senderRec));
        setCC(ccMails, message, cc);
        message.setSubject(subject);
        message.setContent(multipart);
//        message.setText(body);
        return message;
    }

    private void setCC(String ccMails, Message message, String[] cc) throws MessagingException {
        if (isArrayNotEmpty(cc)) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccMails));
        }
    }

    private Multipart getTextFileMultipart(List<MailMimeContainer> mailMimeContainers) throws IOException, MessagingException {
        Multipart multipart = (isNotEmpty(mailMimeContainers)) ? new MimeMultipart() : null;
        //mailMimeContainers != null && !mailMimeContainers.isEmpty()

        for (MailMimeContainer mailMimeContainer : mailMimeContainers) {
            DataSource dataSource = new ByteArrayDataSource(mailMimeContainer.getFileText(), mailMimeContainer.getFileContentType());//"text/plain; charset=UTF-8"
            MimeBodyPart mimeBodyPart = getTextMimeBodyPart(mailMimeContainer.getFileName(), dataSource);
            multipart.addBodyPart(mimeBodyPart);
        }
        return multipart;
    }

    private Multipart getZipFileMultipart(List<MailMimeContainer> mailMimeContainers) throws IOException, MessagingException {
        Multipart multipart = new MimeMultipart();
        ByteArrayOutputStream zipStream = getZipStream(mailMimeContainers);
        MimeBodyPart zipBodyPart = getZipBodyPart(zipStream);
        multipart.addBodyPart(zipBodyPart);
        closeResource(zipStream);
        return multipart;
    }

    private MimeBodyPart getTextMimeBodyPart(String fileName, DataSource dataSource) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        mimeBodyPart.setFileName(fileName);//"ITF_File_Error_Report.txt"
        mimeBodyPart.setDataHandler(new DataHandler(dataSource));
        return mimeBodyPart;
    }


    private Properties getMailingProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.pb.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.ssl.trust", "smtp.pb.com");

        return props;
    }

    private ByteArrayOutputStream getZipStream(List<MailMimeContainer> mailMimeContainers) throws MessagingException, IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(mailMimeContainers.size());
        try (ZipOutputStream zos = new ZipOutputStream(bout)) {
            for (int i = 0; i < mailMimeContainers.size(); i++) {
                MailMimeContainer attachmentFile = mailMimeContainers.get(i);
                byte[] bytes = attachmentFile.getFileText().getBytes();
                ZipEntry entry = new ZipEntry(attachmentFile.getFileName());
                entry.setSize(bytes.length);
                zos.putNextEntry(entry);
                zos.write(bytes);
            }
            zos.finish();
            zos.flush();
            zos.closeEntry();
        }
        return bout;
    }

    private MimeBodyPart getZipBodyPart(ByteArrayOutputStream bout) throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource(bout.toByteArray(), "application/zip");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("Reports.zip");
        return messageBodyPart;
    }


}
