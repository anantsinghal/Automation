package com.backoffice.automation.POJO.broker_POJO.SITF.shipCover_POJOS;

import lombok.Data;

/**
 * Created by NEX9ZKA on 9/28/2018.
 */
@Data
public class SITF_Shipcover_POJO {
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
    private String TotalCASurplusLinesTax;
    private String TotalCAStampingFee;
    private String TotalVoidCASurplusLinesTax;
    private String TotalVoidCAStampingFee;
} 