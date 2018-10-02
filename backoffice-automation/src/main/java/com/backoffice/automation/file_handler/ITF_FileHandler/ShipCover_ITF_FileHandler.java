package com.backoffice.automation.file_handler.ITF_FileHandler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.POJO.broker_POJO.ITF.shipCover_POJOS.SCPolicy;
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
@Component("shipCover_itf_fileHandler")
public class ShipCover_ITF_FileHandler implements FileHandler {
    @Autowired
    private Validator validator;

    public FileErrorLoggers validateAndGetLogs(File file) throws IOException {
        List<SCPolicy> scPolicyList = getBeanList(file, SCPolicy.class);
        FileErrorLoggers fileErrorLoggers = (isNotEmpty(scPolicyList) ? validateAndGenerateLogsForScPolicies(validator, scPolicyList) : null);
        return fileErrorLoggers;
    }

    private FileErrorLoggers validateAndGenerateLogsForScPolicies(Validator validator, List<SCPolicy> scPolicies) {
        int scPoliciesSize = scPolicies.size();
        FileErrorLoggers fileErrorLoggers = new FileErrorLoggers();

        for (int i = 0; i < scPoliciesSize; i++) {
            SCPolicy scPolicy = scPolicies.get(i);
            String scPolicyID = scPolicy.getTransactionID().replace("\"", "");
            String trackingNumber = scPolicy.getTrackingNumber().replace("\"", "");


            Set<ConstraintViolation<SCPolicy>> constraintViolations = validator.validate(scPolicy);
            List<String> messageList = getAllScPolicyErrorMessages(constraintViolations);
            FileErrorLogger fileErrorLogger = getFileErrorLogger(messageList, scPolicyID, trackingNumber);
            if (fileErrorLogger != null) {
                fileErrorLoggers.getFileErrorLoggerList().add(fileErrorLogger);
            }
        }
        return fileErrorLoggers;

    }

    private List<String> getAllScPolicyErrorMessages(Set<ConstraintViolation<SCPolicy>> scPolicyConstraints) {
        List<String> skuMessages = new ArrayList<>(scPolicyConstraints.size());
        for (ConstraintViolation<SCPolicy> scSkuConstraintViolation : scPolicyConstraints) {
            String message = scSkuConstraintViolation.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }
}
