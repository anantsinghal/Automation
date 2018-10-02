package com.backoffice.automation.file_cache;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import com.backoffice.automation.file_handler.FileHandler;
import com.backoffice.automation.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.backoffice.automation.utils.Constants.FILE_NOT_EXISTS_ERROR;
import static com.backoffice.automation.utils.Utility.*;

/**
 * Created by NEX9ZKA on 9/27/2018.
 */
@Slf4j
@Component
public class FileValidation {
    @Autowired
    private FileHandler marsh_itf_fileHandler;
    @Autowired
    private FileHandler pip_itf_fileHandler;
    @Autowired
    private FileHandler shipCover_itf_fileHandler;
    @Autowired
    private FileHandler shippingApi_itf_handler;
    @Autowired
    private FileHandler vcf_fileHandler;
    @Autowired
    private FileHandler shipcover_sitf_fileHandler;
    @Autowired
    private FileHandler xbec_sitf_fileHandler;

    public void validateExistanceOfFilesInMap(Map<String, Map<String, AllFileDataHandler>> fileMap) throws IOException {
        if (!CollectionUtils.isEmpty(fileMap)) {
            for (Map.Entry entry : fileMap.entrySet()) {
                String key = (String) entry.getKey();
                Map<String, AllFileDataHandler> value = (Map<String, AllFileDataHandler>) entry.getValue();
                validateSubMap(key, value);
            }

            validatecsv(fileMap);
        }
    }

    private void validateSubMap(String key, Map<String, AllFileDataHandler> value) {
        for (Map.Entry subEntry : value.entrySet()) {
            String subKey = (String) subEntry.getKey();
            AllFileDataHandler allFileDataHandlerMap = (AllFileDataHandler) subEntry.getValue();

            if (!Utility.isNotEmpty(allFileDataHandlerMap.getBoFiles())) {
                allFileDataHandlerMap.getFileErrorMessages().add(getError(FILE_NOT_EXISTS_ERROR, key, subKey));
            }
        }
    }

    private void validatecsv(Map<String, Map<String, AllFileDataHandler>> map) throws IOException {
        for (Map.Entry entry : map.entrySet()) {
            String key = (String) entry.getKey();
            Map<String, AllFileDataHandler> subMap = (Map<String, AllFileDataHandler>) entry.getValue();

            for (Map.Entry subEntry : subMap.entrySet()) {
                String subKey = (String) subEntry.getKey();
                AllFileDataHandler handler = (AllFileDataHandler) subEntry.getValue();
                hierarchy(key, subKey, handler);
            }
        }
    }

    private void hierarchy(String key, String subkey, AllFileDataHandler handler) throws IOException {
        switch (key) {
            case "ITF":
                processITFData(subkey, handler);
                break;
            case "ICF":
                processICFData(subkey);
                break;
            case "VCF":
                processVCFData(subkey, handler);
                break;
            case "SITF":
                processSITFData(subkey, handler);
                break;
        }
    }

    private void processITFData(String subkey, AllFileDataHandler handler) throws IOException {
        switch (subkey) {
            case "SHIPCOVER":
                addLogsToMap(shipCover_itf_fileHandler, handler);
                break;
            case "EBAY_US":
                addLogsToMap(marsh_itf_fileHandler, handler);
                break;
            case "EBAY_UK":
                addLogsToMap(marsh_itf_fileHandler, handler);
                break;
            case "XBEC":
                addLogsToMap(pip_itf_fileHandler, handler);
                break;
            case "SHIPPING_API":
                addLogsToMap(shippingApi_itf_handler, handler);
                break;
        }
    }

    private void processSITFData(String subkey, AllFileDataHandler handler) throws IOException {
        switch (subkey) {
            case "SHIPCOVER":
                addLogsToMap(shipcover_sitf_fileHandler, handler);
                break;
            case "XBEC":
                addLogsToMap(xbec_sitf_fileHandler, handler);
                break;
        }
    }

    private void processVCFData(String subkey, AllFileDataHandler handler) throws IOException {
        switch (subkey) {
            case "SHIPCOVER":
                addLogsToMap(vcf_fileHandler, handler);
                break;
            case "XBEC":
                addLogsToMap(vcf_fileHandler, handler);
                break;
        }
    }

    private void processICFData(String subkey) {
        switch (subkey) {
            case "SHIPCOVER":
                break;
            case "EBAY_US":
                break;
            case "EBAY_UK":
                break;
            case "XBEC":
                break;
            case "SHIPPING_API":
                break;
        }
    }

    private void addLogsToMap(FileHandler fileHandler, AllFileDataHandler handler) throws IOException {
        if (isNotEmpty(handler.getBoFiles())) {
            File file = handler.getBoFiles().get(0);

            FileErrorLoggers fileErrorLoggers = fileHandler.validateAndGetLogs(file);
            setFileNameInFileErrorLoggers(file, fileErrorLoggers);
            String json = fileErrorLoggersToJson(fileErrorLoggers);
            handler.setFileErrorLoggers(fileErrorLoggers);
            handler.setJsonString(json);
        }
    }

    private void setFileNameInFileErrorLoggers(File file, FileErrorLoggers fileErrorLoggers) {
        if (fileErrorLoggers != null) {
            fileErrorLoggers.setFileName(file.getName());
        }
    }
}