package com.backoffice.automation.log_handler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.file_handler.FileHandler;
import com.backoffice.automation.mail_Handler.MailMimeContainer;
import com.backoffice.automation.mail_Handler.MailProcessor;
import com.backoffice.automation.utils.InsurancePartners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.backoffice.automation.utils.Constants.PLAIN_TEXT;
import static com.backoffice.automation.utils.Utility.*;

/**
 * Created by NEX9ZKA on 04/09/2018.
 */

@Component
public class FileLogHandler {
    @Autowired
    private FileHandler marsh_itf_fileHandler;
    @Autowired
    private FileHandler pip_itf_fileHandler;
    @Autowired
    private FileHandler shipCover_itf_fileHandler;
    @Autowired
    private FileHandler shippingApi_itf_handler;
    @Autowired
    private MailProcessor mailProcessor;

    public void getEveryFileLog(Map<String, Map<String, List<File>>> map) throws IOException {
        List<MailMimeContainer> mailMimeContainers = new ArrayList<>();
//        String xbecMessage = getMessage(map, mailMimeContainers, XBEC, pip_itf_fileHandler, XBEC_ITF_NOT_FOUND, XBEC_ITF_OK_MESSAGE, "PipITFLogs.json");
//        String marsh1Message = getMessage(map, mailMimeContainers, MARSH1, marsh_itf_fileHandler, MARSH1_ITF_NOT_FOUND, MARSH1_ITF_OK_MESSAGE, "Marsh1ItfLogs.json");
//        String marsh3Message = getMessage(map, mailMimeContainers, MARSH3, marsh_itf_fileHandler, MARSH3_ITF_NOT_FOUND, MARSH3_ITF_OK_MESSAGE, "Marsh3ItfLogs.json");
//        String shipCoverMessage = getMessage(map, mailMimeContainers, SHIPCOVER, shipCover_itf_fileHandler, SHIPCOVER_ITF_NOT_FOUND, SHIPCOVER_ITF_OK_MESSAGE, "ShipcoverItfLogs.json");
//        String shippingApiMessage = getMessage(map, mailMimeContainers, SHIPPING_API, shippingApi_itf_handler, SHIPPINGAPI_NOT_FOUND, SHIPPINGAPI_ITF_OK_MESSAGE, "ShippingApiItfLogs.json");
//
//        String pythonString = runPythonAndGetData();
//
//        String consolidatedMessage = getMailBodyMessage(xbecMessage, marsh1Message, marsh3Message, shipCoverMessage, shippingApiMessage);
//
//        mailProcessor.sendMailWithZipFileAttachments(consolidatedMessage, pythonString, "ITF file Mail test", "anant.singhal@pb.com", mailMimeContainers, "");
    }



   /* private String getMessage(Map<InsurancePartners, List<File>> map, List<MailMimeContainer> mailMimeContainers, InsurancePartners insurancePartners, ITF_FileHandler itf_fileHandler, String fileNotFound, String ITF_OK_Message, String fileName) throws IOException {
        String validateMessage = ValidateItfExists(map, insurancePartners, fileNotFound);
        String outputMessage;
        if (isNotEmpty(validateMessage)) {
            outputMessage = validateMessage;
        } else {
            String json = getJson(map, insurancePartners, itf_fileHandler);
            saveDataInFile(json, ITF_JSON_FILE_LOCATION + "\\" + fileName);
            outputMessage = addJsonToMimeAndGenerateMessage(mailMimeContainers, json, fileName, ITF_OK_Message);

        }
        return outputMessage;
    }*/

    private String ValidateItfExists(Map<InsurancePartners, List<File>> map, InsurancePartners insurancePartners, String message) {
        List<File> fileList = map.get(insurancePartners);

        if (!isNotEmpty(fileList) || fileList.get(0) == null) {
            return message;
        } else {
            return null;
        }
    }

    private String getMailBodyMessage(String... messages) {
        StringBuilder builder = null;
        if (isArrayNotEmpty(messages)) {
            builder = new StringBuilder();
            for (int i = 0; i < messages.length; i++) {
                String message = messages[i];
                if (isNotEmpty(message)) {
                    builder.append(message).append("\n");
                }
            }
        }
        return builder.toString();
    }

   /* private String getJson(Map<InsurancePartners, List<File>> map, InsurancePartners insurancePartners, ITF_FileHandler fileHandler) throws IOException {
        List<File> fileList = map.get(insurancePartners);
        String json = null;
        if (isNotEmpty(fileList)) {
            File file = fileList.get(0);
            FileErrorLoggers fileErrorLoggers = fileHandler.validateITFAndGetLogs(file);
            setFileNameInFileLoggers(fileErrorLoggers, file.getName());
            json = fileErrorLoggersToJson(fileErrorLoggers);
        }

        return json;
    }*/


    private void setFileNameInFileLoggers(FileErrorLoggers fileLoggers, String fileName) {
        if (isNotNull(fileLoggers)) {
            fileLoggers.setFileName(fileName);
        }
    }

    private String addJsonToMimeAndGenerateMessage(List<MailMimeContainer> mailMimeContainers, String json, String fileName, String message) {
        if (isNotEmpty(json)) {
            mailMimeContainers.add(getFileMimeDetails(json, fileName, PLAIN_TEXT));
        } else {
            return message;
        }
        return null;
    }

    public void saveDataInFile(String data, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] strToBytes = data.getBytes();

        Files.write(path, strToBytes);
    }


    /*private String fileErrorLoggersToJson(FileErrorLoggers fileErrorLoggers) throws JsonProcessingException {
        String jsonString = null;
        if (isNotNull(fileErrorLoggers)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            jsonString = objectMapper.writeValueAsString(fileErrorLoggers);
        }

        return jsonString;
    }*/

    private MailMimeContainer getFileMimeDetails(String fileText, String fileName, String fileContentType) {
        MailMimeContainer mailMimeContainer = new MailMimeContainer();
        mailMimeContainer.setFileText(fileText);
        mailMimeContainer.setFileContentType(fileContentType);
        mailMimeContainer.setFileName(fileName);
        return mailMimeContainer;
    }
}