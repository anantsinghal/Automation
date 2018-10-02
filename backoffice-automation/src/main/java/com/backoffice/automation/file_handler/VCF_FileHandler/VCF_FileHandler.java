package com.backoffice.automation.file_handler.VCF_FileHandler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.POJO.broker_POJO.VCF.VCF_POJO;
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
 * Created by NEX9ZKA on 9/29/2018.
 */
@Component("vcf_fileHandler")
public class VCF_FileHandler implements FileHandler {
    @Autowired
    private Validator validator;

    @Override
    public FileErrorLoggers validateAndGetLogs(File file) throws IOException {
        List<VCF_POJO> vcf_pojos = getBeanList(file, VCF_POJO.class);
        FileErrorLoggers fileErrorLoggers = (isNotEmpty(vcf_pojos) ? validateAndGenerateLogs(validator, vcf_pojos) : null);

        return fileErrorLoggers;
    }

    private FileErrorLoggers validateAndGenerateLogs(Validator validator, List<VCF_POJO> vcf_pojos) {
        int size = vcf_pojos.size();
        FileErrorLoggers fileErrorLoggers = new FileErrorLoggers();

        for (int i = 0; i < size; i++) {
            VCF_POJO vcf_pojo = vcf_pojos.get(i);

            Set<ConstraintViolation<VCF_POJO>> constraintViolations = validator.validate(vcf_pojo);
            List<String> messageList = getAllErrorMessages(constraintViolations);
            FileErrorLogger fileErrorLogger = getFileErrorLogger(messageList, null, null);
            if (fileErrorLogger != null) {
                fileErrorLoggers.getFileErrorLoggerList().add(fileErrorLogger);
            }
        }
        return fileErrorLoggers;

    }

    private List<String> getAllErrorMessages(Set<ConstraintViolation<VCF_POJO>> sitf) {
        List<String> skuMessages = new ArrayList<>(sitf.size());
        for (ConstraintViolation<VCF_POJO> saSkuConstraintViolation : sitf) {
            String message = saSkuConstraintViolation.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }
}