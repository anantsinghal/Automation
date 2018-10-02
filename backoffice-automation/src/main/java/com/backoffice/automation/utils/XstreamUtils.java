package com.backoffice.automation.utils;

import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Policies;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Policy;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Sku;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.SkuList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by NEX9ZKA on 9/16/2018.
 */
public class XstreamUtils {

    public static Policies getPoliciesFromFileStream(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            XStream xStream = xStreamForPipITFFile();
            xStream.setClassLoader(Thread.currentThread().getContextClassLoader());
            Policies policies = (Policies) xStream.fromXML(fileInputStream);
            return policies;
        }
    }

    private static XStream xStreamForPipITFFile() {
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("Policies", Policies.class);
        xStream.alias("Policy", Policy.class);
        xStream.alias("Sku", Sku.class);
        xStream.alias("SkuList", SkuList.class);

        xStream.addImplicitCollection(Policies.class, "policyList");
        xStream.addImplicitCollection(SkuList.class, "SkuArrayList");

        return xStream;
    }


} 