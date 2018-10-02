package com.backoffice.automation.file_handler.ITF_FileHandler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.POJO.broker_POJO.ITF.shipping_API_POJOS.ShippingApiPolicy;
import com.backoffice.automation.file_handler.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.backoffice.automation.utils.Utility.*;

/**
 * Created by NEX9ZKA on 9/14/2018.
 */
@Component("shippingApi_itf_handler")
public class ShippingApi_ITF_Handler implements FileHandler {

    @Autowired
    private Validator validator;


    public FileErrorLoggers validateAndGetLogs(File file) throws IOException {
        List<ShippingApiPolicy> shippingApiPolicies = getBeanList(file, ShippingApiPolicy.class);
        FileErrorLoggers fileErrorLoggers = (isNotEmpty(shippingApiPolicies) ? validateAndGenerateLogsForMarshPolicies(validator, shippingApiPolicies) : null);

        return fileErrorLoggers;
    }

    private FileErrorLoggers validateAndGenerateLogsForMarshPolicies(Validator validator, List<ShippingApiPolicy> shippingApiPolicies) {
        int saPoliciesSizeSize = shippingApiPolicies.size();
        FileErrorLoggers fileErrorLoggers = new FileErrorLoggers();

        for (int i = 0; i < saPoliciesSizeSize; i++) {
            ShippingApiPolicy shippingApiPolicy = shippingApiPolicies.get(i);
            String trackingNumber = shippingApiPolicy.getPackageID().replace("\"", "");

            Set<ConstraintViolation<ShippingApiPolicy>> constraintViolations = validator.validate(shippingApiPolicy);
            List<String> messageList = getAllShippingApiPolicyErrorMessages(constraintViolations);
            FileErrorLogger fileErrorLogger = getFileErrorLogger(messageList, null, trackingNumber);
            if (fileErrorLogger != null) {
                fileErrorLoggers.getFileErrorLoggerList().add(fileErrorLogger);
            }
        }
        return fileErrorLoggers;

    }

    private List<String> getAllShippingApiPolicyErrorMessages(Set<ConstraintViolation<ShippingApiPolicy>> shippingApiConstraints) {
        List<String> skuMessages = new ArrayList<>(shippingApiConstraints.size());
        for (ConstraintViolation<ShippingApiPolicy> saSkuConstraintViolation : shippingApiConstraints) {
            String message = saSkuConstraintViolation.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }
}