package com.backoffice.automation.POJO.JSON_POJO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEX9ZKA on 05/09/2018.
 */
public class FileErrorLogger {
    @JsonPropertyOrder({"policyId", "messages"})

    private String policyId;
    private String trackingNumber;
    private List<String> messages;

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public List<String> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages(int listSize) {
        if (messages == null) {
            messages = new ArrayList<>(listSize);
        }
        return messages;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }


}
