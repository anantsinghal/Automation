package com.backoffice.automation.POJO.broker_POJO.VCF;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by NEX9ZKA on 9/28/2018.
 */
@Data
public class VCF_POJO {
    @NotBlank
    private String ClaimID;
    private String VerifiedDate;
    @NotBlank
    private String AccountID;
    @NotBlank
    private String CustomerID;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    @Email
    private String Email;
    private String TrackingNumber;
    private String VerificationType;
    private String ClaimCode;

    private String ShipDate;
    private String ReasonDetails;
    private String Comments;

} 