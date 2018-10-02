package com.backoffice.automation.file_handler.SITF_FileHandler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.POJO.broker_POJO.SITF.shipCover_POJOS.SITF_Shipcover_POJO;
import com.backoffice.automation.file_handler.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.backoffice.automation.utils.Utility.*;

/**
 * Created by NEX9ZKA on 9/28/2018.
 */

@Component("shipcover_sitf_fileHandler")
public class Shipcover_SITF_FileHandler implements FileHandler {
    @Autowired
    private javax.validation.Validator validator;

    @Override
    public FileErrorLoggers validateAndGetLogs(File file) throws IOException {
        List<SITF_Shipcover_POJO> sitf_shipcover_pojos = getBeanList(file, SITF_Shipcover_POJO.class);
        FileErrorLoggers fileErrorLoggers = (isNotEmpty(sitf_shipcover_pojos) ? validateAndGenerateLogs(validator, sitf_shipcover_pojos) : null);

        return fileErrorLoggers;
    }

    private FileErrorLoggers validateAndGenerateLogs(javax.validation.Validator validator, List<SITF_Shipcover_POJO> sitf_shipcover_pojos) {
        int size = sitf_shipcover_pojos.size();
        FileErrorLoggers fileErrorLoggers = new FileErrorLoggers();

        for (int i = 0; i < size; i++) {
            SITF_Shipcover_POJO sitf_shipcover_pojo = sitf_shipcover_pojos.get(i);

            Set<ConstraintViolation<SITF_Shipcover_POJO>> constraintViolations = validator.validate(sitf_shipcover_pojo);
            List<String> messageList = getAllErrorMessages(constraintViolations);
            FileErrorLogger fileErrorLogger = getFileErrorLogger(messageList, null, null);
            if (fileErrorLogger != null) {
                fileErrorLoggers.getFileErrorLoggerList().add(fileErrorLogger);
            }
        }
        return fileErrorLoggers;

    }

    private List<String> getAllErrorMessages(Set<ConstraintViolation<SITF_Shipcover_POJO>> sitf) {
        List<String> skuMessages = new ArrayList<>(sitf.size());
        for (ConstraintViolation<SITF_Shipcover_POJO> saSkuConstraintViolation : sitf) {
            String message = saSkuConstraintViolation.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }
}