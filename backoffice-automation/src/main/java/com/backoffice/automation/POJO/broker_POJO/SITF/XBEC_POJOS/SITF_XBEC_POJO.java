package com.backoffice.automation.POJO.broker_POJO.SITF.XBEC_POJOS;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.backoffice.automation.utils.Constants.GENERIC_NULL_MESSAGE;
import static com.backoffice.automation.utils.Constants.regexForTransactionTime;

/**
 * Created by NEX9ZKA on 9/28/2018.
 */
@Data
public class SITF_XBEC_POJO {
    @CsvBindByPosition(position = 1)
    @NotBlank(message = "TransactionTime" + GENERIC_NULL_MESSAGE)
    @Size(min = 19, max = 19, message = "TransactionTime String should have exactly 19 characters")
    @Pattern(regexp = regexForTransactionTime, message = "TransactionTime should be in the following format dd-MM-yyyy HH:mm:ss")
    private String TransactionStartTime;
    private String TransactionEndTime;
    private String TotalPackageValue;
    private String TotalInsuredValue;
    private String TotalRetailPremium;
    private String TotalRecordCount;
    private String TotalVoidPackageValue;
    private String TotalVoidInsuredValue;
    private String TotalVoidRetailPremium;
    private String TotalVoidRecordCount;
    private String TotalTaxOnRetailCommission;
    private String TotalTaxOnInsuranceProvisioning;
    private String TotalInsuranceCost;
    private String TotalVoidTaxOnRetailCommission;
    private String TotalVoidTaxOnInsuranceProvisioning;
    private String TotalVoidInsuranceCost;

}