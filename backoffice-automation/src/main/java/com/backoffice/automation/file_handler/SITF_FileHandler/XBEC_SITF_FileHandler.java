package com.backoffice.automation.file_handler.SITF_FileHandler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.POJO.broker_POJO.SITF.XBEC_POJOS.SITF_XBEC_POJO;
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

@Component("xbec_sitf_fileHandler")
public class XBEC_SITF_FileHandler implements FileHandler {
    @Autowired
    private Validator validator;

    @Override
    public FileErrorLoggers validateAndGetLogs(File file) throws IOException {
        List<SITF_XBEC_POJO> sitf_xbec_pojos = getBeanList(file, SITF_XBEC_POJO.class);
        FileErrorLoggers fileErrorLoggers = (isNotEmpty(sitf_xbec_pojos) ? validateAndGenerateLogs(validator, sitf_xbec_pojos) : null);

        return fileErrorLoggers;
    }

    private FileErrorLoggers validateAndGenerateLogs(Validator validator, List<SITF_XBEC_POJO> sitf_xbec_pojos) {
        int size = sitf_xbec_pojos.size();
        FileErrorLoggers fileErrorLoggers = new FileErrorLoggers();

        for (int i = 0; i < size; i++) {
            SITF_XBEC_POJO sitf_xbec_pojo = sitf_xbec_pojos.get(i);

            Set<ConstraintViolation<SITF_XBEC_POJO>> constraintViolations = validator.validate(sitf_xbec_pojo);
            List<String> messageList = getAllErrorMessages(constraintViolations);
            FileErrorLogger fileErrorLogger = getFileErrorLogger(messageList, null, null);
            if (fileErrorLogger != null) {
                fileErrorLoggers.getFileErrorLoggerList().add(fileErrorLogger);
            }
        }
        return fileErrorLoggers;

    }

    private List<String> getAllErrorMessages(Set<ConstraintViolation<SITF_XBEC_POJO>> sitf) {
        List<String> skuMessages = new ArrayList<>(sitf.size());
        for (ConstraintViolation<SITF_XBEC_POJO> saSkuConstraintViolation : sitf) {
            String message = saSkuConstraintViolation.getMessage();
            skuMessages.add(message);
        }
        return skuMessages;
    }
}