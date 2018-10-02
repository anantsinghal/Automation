package com.backoffice.automation.file_handler.ITF_FileHandler;


import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.POJO.broker_POJO.ITF.marsh_POJOS.MarshPolicy;
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
 * Created by NEX9ZKA on 30/08/2018.
 */
@Component("marsh_itf_fileHandler")
public class Marsh_ITF_FileHandler implements FileHandler {

    @Autowired
    private Validator validator;

    public FileErrorLoggers validateAndGetLogs(File file) throws IOException {
        List<MarshPolicy> marshPolicies = getBeanList(file, MarshPolicy.class);
        FileErrorLoggers fileErrorLoggers = (isNotEmpty(marshPolicies) ? validateAndGenerateLogsForMarshPolicies(validator, marshPolicies) : null);

        return fileErrorLoggers;
    }

    private FileErrorLoggers validateAndGenerateLogsForMarshPolicies(Validator validator, List<MarshPolicy> marshPolicies) {
        int marshPoliciesSize = marshPolicies.size();
        FileErrorLoggers fileErrorLoggers = new FileErrorLoggers();

        for (int i = 0; i < marshPoliciesSize; i++) {
            MarshPolicy marshPolicy = marshPolicies.get(i);
            String marshPolicyID = marshPolicy.getTransactionID().replace("\"", "");
            String trackingNumber = marshPolicy.getTrackingNumber().replace("\"", "");

            Set<ConstraintViolation<MarshPolicy>> constraintViolations = validator.validate(marshPolicy);
            List<String> messageList = getAllMarshPolicyErrorMessages(constraintViolations);
            FileErrorLogger fileErrorLogger = getFileErrorLogger(messageList, marshPolicyID, trackingNumber);
            if (fileErrorLogger != null) {
                fileErrorLoggers.getFileErrorLoggerList().add(fileErrorLogger);
            }
        }
        return fileErrorLoggers;

    }

    private List<String> getAllMarshPolicyErrorMessages(Set<ConstraintViolation<MarshPolicy>> marshPolicyConstraints) {
        List<String> skuMessages = new ArrayList<>(marshPolicyConstraints.size());
        for (ConstraintViolation<MarshPolicy> marSkuConstraintViolation : marshPolicyConstraints) {
            String message = marSkuConstraintViolation.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }
}