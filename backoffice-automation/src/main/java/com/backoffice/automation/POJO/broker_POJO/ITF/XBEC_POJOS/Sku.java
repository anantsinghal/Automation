package com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by NEX9ZKA on 28/08/2018.
 */
@Data
public class Sku {
    private String SkuID;

    private String SkuQuantity;

    private String SkuUnitSalePrice;

    private String SkuUnitPrice;

    private String SkuTotalPrice;

    @NotBlank(message = "SKU Name Cannot be Null or Empty")
    private String SkuName;


}
