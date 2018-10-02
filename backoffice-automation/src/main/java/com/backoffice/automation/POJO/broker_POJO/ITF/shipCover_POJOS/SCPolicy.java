package com.backoffice.automation.POJO.broker_POJO.ITF.shipCover_POJOS;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.backoffice.automation.utils.Constants.GENERIC_NULL_MESSAGE;
import static com.backoffice.automation.utils.Constants.regexForTransactionTime;

/**
 * Created by NEX9ZKA on 9/14/2018.
 */
@Data
public class SCPolicy {
    @CsvBindByPosition(position = 0)
    @NotBlank(message = "TransactionID" + GENERIC_NULL_MESSAGE)
    @Size(min = 20, max = 20, message = "TransactionID should have only 20 characters")
    private String TransactionID;

    @CsvBindByPosition(position = 1)
    @NotBlank(message = "TransactionTime" + GENERIC_NULL_MESSAGE)
    @Size(min = 19, max = 19, message = "TransactionTime String should have exactly 19 characters")
    @Pattern(regexp = regexForTransactionTime, message = "TransactionTime should be in the following format dd-MM-yyyy HH:mm:ss")
    private String TransactionTime;

    @CsvBindByPosition(position = 2)
    private String InsuredAmount;

    @CsvBindByPosition(position = 3)
    private String PackageValue;

    @CsvBindByPosition(position = 4)
    private String RetailPremium;

    @CsvBindByPosition(position = 5)
    @NotBlank(message = "AccountID" + GENERIC_NULL_MESSAGE)
    private String AccountID;

    @CsvBindByPosition(position = 6)
    private String CustomerID;

    @CsvBindByPosition(position = 7)
    @NotBlank(message = "FirstName" + GENERIC_NULL_MESSAGE)
    private String FirstName;

    @CsvBindByPosition(position = 8)
    private String MiddleName;

    @CsvBindByPosition(position = 9)
    private String LastName;

    @CsvBindByPosition(position = 10)
    private String Company;

    @CsvBindByPosition(position = 11)
    private String ShipFromState;

    @CsvBindByPosition(position = 12)
    private String TrackingNumber;

    @CsvBindByPosition(position = 13)
    private String CarrierCode;

    @CsvBindByPosition(position = 14)
    private String TransactionCode;

    @CsvBindByPosition(position = 15)
    private String TaxOnRetailCommission;

    @CsvBindByPosition(position = 16)
    private String TaxOnInsuranceProvisioning;

    @CsvBindByPosition(position = 17)
    private String InsuranceCost;

    @CsvBindByPosition(position = 18)
    private String WarningShown;

    @CsvBindByPosition(position = 19)
    private String CASurplusLinesTax;

    @CsvBindByPosition(position = 20)
    private String CAStampingFee;
}
