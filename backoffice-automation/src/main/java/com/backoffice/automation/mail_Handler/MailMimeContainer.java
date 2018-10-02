package com.backoffice.automation.mail_Handler;

/**
 * Created by NEX9ZKA on 02/09/2018.
 */

public class MailMimeContainer {
    private String fileName;
    private String fileText;
    private String fileContentType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
}
