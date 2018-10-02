package com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.backoffice.automation.utils.Constants.GENERIC_NULL_MESSAGE;
import static com.backoffice.automation.utils.Constants.regexForTransactionTime;

/**
 * Created by NEX9ZKA on 28/08/2018.
 */
@Data
public class Policy {

    private String ConsigneeStreet2;

    @NotBlank(message = "ConsigneeShipToCountry" + GENERIC_NULL_MESSAGE)
    private String ConsigneeShipToCountry;

    private String ConsigneeCompany;

    private String SellerRegion;

    @NotBlank(message = "TransactionID" + GENERIC_NULL_MESSAGE)
    @Size(min = 20, max = 20, message = "TransactionID should have only 20 characters")
    private String TransactionID;

    private String BuyerEmail;

    private String BuyerCompany;


    @NotBlank(message = "PartnerID" + GENERIC_NULL_MESSAGE)
    private String PartnerID;

    private String SellerEmail;

    @NotBlank(message = "SellerCity" + GENERIC_NULL_MESSAGE)
    private String SellerCity;


    @NotBlank(message = "TransactionTime" + GENERIC_NULL_MESSAGE)
    @Size(min = 19, max = 19, message = "TransactionTime String should have exactly 19 characters")
    @Pattern(regexp = regexForTransactionTime, message = "TransactionTime should be in the following format dd-MM-yyyy HH:mm:ss")
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private String TransactionTime;

    @NotBlank(message = "ConsigneeStreet1" + GENERIC_NULL_MESSAGE)
    private String ConsigneeStreet1;

    private String BuyerCity;

    @NotBlank(message = "TCQSValue" + GENERIC_NULL_MESSAGE)
    @Size(min = 1, max = 100, message = "TCQSValue should have atleast ZERO as a value")
    private String TCQSValue;

    @NotBlank(message = "InsuranceCost" + GENERIC_NULL_MESSAGE)
    private String InsuranceCost;

    @NotBlank(message = "AccountID" + GENERIC_NULL_MESSAGE)
    private String AccountID;

    private String BuyerPostalCode;

    private String BuyerMiddleName;

    @NotBlank(message = "ProgramID" + GENERIC_NULL_MESSAGE)
    private String ProgramID;

    private SkuList SkuList;

    private String SellerMiddleName;

    private String SellerCompany;

    private String ConsigneeCustomerID;

    @NotBlank(message = "SellerCountry" + GENERIC_NULL_MESSAGE)
    private String SellerCountry;

    private String TrackingNumber;

    private String PackageValue;

    private String SellerLastName;

    private String BuyerID;

    @NotBlank(message = "ConsigneeFirstName" + GENERIC_NULL_MESSAGE)
    private String ConsigneeFirstName;

    @NotBlank(message = "SellerFirstName" + GENERIC_NULL_MESSAGE)
    private String SellerFirstName;

    private String ConsigneePostalCode;

    private String BuyerCountry;

    private String TransactionCurrency;

    private String BuyerRegion;

    private String HubID;

    private String ConsigneeMiddleName;

    private String TransactionType;

    private String ConsigneeEmail;

    private String ConsigneeLastName;

    private String ShipFromRDC;

    private String BuyerCustomerID;

    private String BuyerLastName;

    private String TransactionCode;

    private String SellerStreet2;

    @NotBlank(message = "ConsigneeCity" + GENERIC_NULL_MESSAGE)
    private String ConsigneeCity;

    private String ParcelCostofShipping;

    private String BuyerStreet2;

    private String CarrierCode;

    private String PolicyOwnerID;

    private String ConsigneeShipToRegion;

    private String BuyerFirstName;

    private String InsuredAmount;

    @NotBlank(message = "SellerStreet1" + GENERIC_NULL_MESSAGE)
    private String SellerStreet1;

    private String BuyerStreet1;

    private String ICQSValue;

    private String TransactionTimeOffset;

    private String SellerPostalCode;

}
