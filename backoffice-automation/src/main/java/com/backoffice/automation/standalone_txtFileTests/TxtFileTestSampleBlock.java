package com.backoffice.automation.standalone_txtFileTests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by NEX9ZKA on 31/08/2018.
 */
public class TxtFileTestSampleBlock {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(""));
        writer.write("");
        writer.close();
    }


    /* private static MimeBodyPart zipAttachment1(List<ByteArrayOutputStream> attachmentList, List<String> reportFileNames) {
         MimeBodyPart messageBodyPart = null;
         try {
             // File file = File.createTempFile( "Reports.zip",".tmp" );
             // FileOutputStream fout = new FileOutputStream(file);
             ByteArrayOutputStream bout = new ByteArrayOutputStream(attachmentList.size());
             ZipOutputStream zos = new ZipOutputStream(bout);
             ZipEntry entry;
             for (int i = 0; i < attachmentList.size(); i++) {
                 ByteArrayOutputStream attachmentFile = attachmentList.get(i);
                 byte[] bytes = attachmentFile.toByteArray();
                 entry = new ZipEntry(reportFileNames.get(i));
                 entry.setSize(bytes.length);
                 zos.putNextEntry(entry);
                 zos.write(bytes);
             }
             messageBodyPart = new MimeBodyPart();
             DataSource source = new ByteArrayDataSource(bout.toByteArray(), "application/zip");
             messageBodyPart.setDataHandler(new DataHandler(source));
             messageBodyPart.setFileName("Reports.zip");
             zos.close();
             zos.closeEntry();

         } catch (Exception e) {
             // TODO: handle exception
         }
         return messageBodyPart;
     }
 */

     /* public static void main(String[] args) throws MessagingException {
        MimeBodyPart mimeBodyPart = zipAttachment(getDummyList());
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        String[] vaStrings = new String[]{"anant.singhal@pb.com"};

        String ccMails = ccArrayToCommaSeparatedString(vaStrings);
        Session session = Session.getInstance(getMailingProperties());
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("anant.singhal@pb.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(senderRec));
        setCC(ccMails, message, vaStrings);
        message.setSubject(subject);
        message.setText("TEXT");
        message.setContent(multipart);

        Transport.send(message);
        LOGGER.log(Level.INFO, "MAIL SENT");

    }*/

  /*  private static List<MailMimeContainer> getDummyList() {
        List<MailMimeContainer> mailMimeContainers = new ArrayList<>();
        MailMimeContainer mailMimeContainer1 = new MailMimeContainer();
        MailMimeContainer mailMimeContainer2 = new MailMimeContainer();
        MailMimeContainer mailMimeContainer3 = new MailMimeContainer();

        mailMimeContainer1.setFileName("Hello1.txt");
        mailMimeContainer2.setFileName("Hello2.txt");
        mailMimeContainer3.setFileName("Hello3.txt");

        mailMimeContainer1.setFileContentType(Constants.PLAIN_TEXT);
        mailMimeContainer2.setFileContentType(Constants.PLAIN_TEXT);
        mailMimeContainer3.setFileContentType(Constants.PLAIN_TEXT);

        mailMimeContainer1.setFileText("odhfsodjfhnsoijdfjowiejfoweijfow");
        mailMimeContainer2.setFileText("odhfsodjfhnsoijdfjowiejfoweijfow");
        mailMimeContainer3.setFileText("odhfsodjfhnsoijdfjowiejfoweijfow");

        mailMimeContainers.add(mailMimeContainer1);
        mailMimeContainers.add(mailMimeContainer2);
        mailMimeContainers.add(mailMimeContainer3);

        return mailMimeContainers;
    }*/
}
