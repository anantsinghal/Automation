package com.backoffice.automation.mail_Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEX9ZKA on 9/30/2018.
 */

public class MailContentHolder {
    private List<String> positiveMessages;
    private List<String> fileErrorMessages;
    private List<MailMimeContainer> mailMimeContainers;

    public List<String> getPositiveMessages() {
        if (positiveMessages == null) {
            positiveMessages = new ArrayList<>();
        }
        return positiveMessages;
    }

    public List<String> getFileErrorMessages() {
        if (fileErrorMessages == null) {
            fileErrorMessages = new ArrayList<>();
        }
        return fileErrorMessages;
    }

    public List<MailMimeContainer> getMailMimeContainers() {
        if (mailMimeContainers == null) {
            mailMimeContainers = new ArrayList<>();
        }
        return mailMimeContainers;
    }
}