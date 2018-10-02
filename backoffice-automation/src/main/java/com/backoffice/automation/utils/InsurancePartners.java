package com.backoffice.automation.utils;

/**
 * Created by NEX9ZKA on 9/14/2018.
 */
public enum InsurancePartners {
    MARSH1("MARP01Insure000"),
    MARSH3("MARP03Insure000"),
    SHIPCOVER("PIPInsure000"),
    XBEC("PIPP02Insure000"),
    SHIPPING_API("PIPP05Insure000");

    private String fileName;


    InsurancePartners(String s) {
        this.fileName = s;
    }


    public String getFileName() {
        return fileName;
    }


}
