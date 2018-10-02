package com.backoffice.automation.POJO.broker_POJO.ITF.marsh_POJOS;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.backoffice.automation.utils.Constants.GENERIC_NULL_MESSAGE;
import static com.backoffice.automation.utils.Constants.regexForTransactionTime;

/**
 * Created by NEX9ZKA on 30/08/2018.
 */
@Data
public class MarshPolicy {
    @CsvBindByPosition(position = 0)
    @NotBlank(message = "TransactionID" + GENERIC_NULL_MESSAGE)
    @Size(min = 20, max = 20, message = "TransactionID should have only 20 characters")
    private String transactionID;

    @CsvBindByPosition(position = 1)
    @NotBlank(message = "TransactionTime" + GENERIC_NULL_MESSAGE)
    @Size(min = 19, max = 19, message = "TransactionTime String should have exactly 19 characters")
    @Pattern(regexp = regexForTransactionTime, message = "TransactionTime should be in the following format dd-MM-yyyy HH:mm:ss")
    private String transactionTime;

    @CsvBindByPosition(position = 2)
    private String insuredAmount;

    @CsvBindByPosition(position = 3)
    private String packageValue;

    @CsvBindByPosition(position = 4)
    private String retailPremium;

    @CsvBindByPosition(position = 5)
    @NotBlank(message = "AccountID" + GENERIC_NULL_MESSAGE)
    private String accountID;

    @CsvBindByPosition(position = 6)
    private String customerID;

    @CsvBindByPosition(position = 7)
    @NotBlank(message = "FirstName" + GENERIC_NULL_MESSAGE)
    private String firstName;

    @CsvBindByPosition(position = 8)
    private String middleName;

    @CsvBindByPosition(position = 9)
    private String lastName;

    @CsvBindByPosition(position = 10)
    private String company;

    @CsvBindByPosition(position = 11)
    private String shipFromState;

    @CsvBindByPosition(position = 12)
    private String trackingNumber;//yes

    @CsvBindByPosition(position = 13)
    private String carrierCode;//yes

    @CsvBindByPosition(position = 14)
    private String transactionCode;

    @CsvBindByPosition(position = 15)
    private String taxOnRetailCommission;

    @CsvBindByPosition(position = 16)
    private String taxOnInsuranceProvisioning;

    @CsvBindByPosition(position = 17)
    private String insuranceCost;

    @CsvBindByPosition(position = 18)
    private String warningShown;

    @CsvBindByPosition(position = 19)
    private String ICQSValue;

    @CsvBindByPosition(position = 20)
    private String TCQSValue;

    @CsvBindByPosition(position = 21)
    private String transactionType;


}
