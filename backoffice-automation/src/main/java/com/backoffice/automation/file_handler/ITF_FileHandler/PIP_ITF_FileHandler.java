package com.backoffice.automation.file_handler.ITF_FileHandler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Policies;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Policy;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Sku;
import com.backoffice.automation.file_handler.FileHandler;
import com.backoffice.automation.utils.Utility;
import com.backoffice.automation.utils.XstreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by NEX9ZKA on 28/08/2018.
 */

@Component(value = "pip_itf_fileHandler")
public class PIP_ITF_FileHandler implements FileHandler {

    @Autowired
    private Validator validator;

    public FileErrorLoggers validateAndGetLogs(File file) throws IOException {
        Policies policies = getXmlObject(file);
        List<Policy> policiesList = policies.getPolicy();
        FileErrorLoggers fileErrorLoggers = (Utility.isNotEmpty(policiesList) ? validatePolicy(validator, policiesList) : null);
        return fileErrorLoggers;
    }


    private Policies getXmlObject(File xmlFile) throws IOException {
        Policies policies = XstreamUtils.getPoliciesFromFileStream(xmlFile);
        return policies;
    }

    private FileErrorLoggers validatePolicy(Validator validator, List<Policy> policies) {
        int policiesSize = policies.size();

        FileErrorLoggers fileErrorLoggers = new FileErrorLoggers();
        for (int i = 0; i < policiesSize; i++) {
            Policy policy = policies.get(i);
            String policyId = policy.getTransactionID();
            Set<ConstraintViolation<Policy>> constraintViolations = validator.validate(policy);

            List<String> policyErrorMessages = getAllPipPolicyErrorMessages(constraintViolations);
            List<String> skuErrorMessages = validateSku(validator, policy.getSkuList().getSku());
            FileErrorLogger fileErrorLogger = getFileErrorLogger(policyId, policyErrorMessages, skuErrorMessages);
            if (Utility.isNotEmpty(fileErrorLogger.getMessages())) {
                fileErrorLoggers.getFileErrorLoggerList().add(fileErrorLogger);
            }
        }
        return fileErrorLoggers;
    }

    private List<String> validateSku(Validator validator, List<Sku> skuList) {
        List<String> skuErrorMessages = new ArrayList<>(skuList.size());
        for (Sku sku : skuList) {
            Set<ConstraintViolation<Sku>> constraintViolations = validator.validate(sku);
            List<String> skuErrors = getAllSkuErrorMessages(constraintViolations);
            skuErrorMessages.addAll(skuErrors);
        }
        return skuErrorMessages;
    }

    private List<String> getAllSkuErrorMessages(Set<ConstraintViolation<Sku>> skuConstraints) {
        List<String> skuMessages = new ArrayList<>(skuConstraints.size());
        for (ConstraintViolation<Sku> skuConstraint : skuConstraints) {
            String message = skuConstraint.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }

    private List<String> getAllPipPolicyErrorMessages(Set<ConstraintViolation<Policy>> policyConstraints) {
        List<String> skuMessages = new ArrayList<>(policyConstraints.size());
        for (ConstraintViolation<Policy> policyConstraintViolation : policyConstraints) {
            String message = policyConstraintViolation.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }

    private FileErrorLogger getFileErrorLogger(String policyID, List<String>... errorMessages) {
        FileErrorLogger fileErrorLogger = null;
        if (errorMessages != null && errorMessages.length > 0) {
            fileErrorLogger = new FileErrorLogger();
            fileErrorLogger.setPolicyId(policyID);

            addErrorsInFileError(fileErrorLogger, errorMessages);
        }
        return fileErrorLogger;
    }

    private void addErrorsInFileError(FileErrorLogger fileErrorLogger, List<String>[] errorMessages) {
        for (int i = 0; i < errorMessages.length; i++) {
            List<String> errors = errorMessages[i];
            fileErrorLogger.getMessages().addAll(errors);
        }
    }
}