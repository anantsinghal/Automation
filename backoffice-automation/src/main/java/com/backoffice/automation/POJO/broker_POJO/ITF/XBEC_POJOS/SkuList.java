package com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS;

import java.util.List;

/**
 * Created by NEX9ZKA on 28/08/2018.
 */
public class SkuList {
    private List<Sku> SkuArrayList;

    public List<Sku> getSku() {
        return SkuArrayList;
    }

    public void setSku(List<Sku> Sku) {
        this.SkuArrayList = Sku;
    }

    @Override
    public String toString() {
        return "ClassPojo [Sku = " + SkuArrayList + "]";
    }
}
