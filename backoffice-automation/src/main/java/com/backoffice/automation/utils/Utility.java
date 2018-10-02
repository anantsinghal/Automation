package com.backoffice.automation.utils;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLogger;
import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import com.backoffice.automation.mail_Handler.MailMimeContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.backoffice.automation.utils.Constants.ITF_JSON_FILE_LOCATION;

/**
 * Created by NEX9ZKA on 30/08/2018.
 */
public class Utility {

    public static BufferedReader getCSVReader(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return bufferedReader;
    }

    public static String ccArrayToCommaSeparatedString(String[] cc) {
        int ccLength = (cc != null ? cc.length : 0);
        int ccLengthMinusOne = ccLength - 1;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ccLength; i++) {
            builder.append(cc[i]);
            if (i != ccLengthMinusOne) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public static void closeResource(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }


    public static String getFormattedDate(Date date, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(date);
    }

    public static File[] getAllFilesInDirectory(String directoryPath) throws IOException {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    public static List<File> getFilesExcludingDirectories(File[] files, String todayDate) {
        List<File> fileList = null;
        if (isArrayNotEmpty(files)) {
            fileList = new ArrayList<>();
            int filesSize = files.length;
            for (int i = 0; i < filesSize; i++) {
                File file = files[i];
                if (isNotNull(file) && file.isFile() && file.getName().contains(todayDate)) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    public static String getArrayData(String[] array, int number) {
        int arrayLength = array.length;
        String data = (number < arrayLength ? array[number] : null);
        return data;
    }

    public static FileErrorLogger getFileErrorLogger(List<String> errorMessageList, String policyId, String trackingNumber) {
        FileErrorLogger fileErrorLogger = null;
        if (isNotEmpty(errorMessageList)) {
            fileErrorLogger = new FileErrorLogger();
            fileErrorLogger.setPolicyId(policyId);
            fileErrorLogger.setTrackingNumber(trackingNumber);
            fileErrorLogger.getMessages(errorMessageList.size()).addAll(errorMessageList);
            return fileErrorLogger;
        }
        return fileErrorLogger;
    }

    public static String getError(String incompleteString, String... orderWiseStrings) {
        if (isArrayNotEmpty(orderWiseStrings)) {
            for (int i = 0; i < orderWiseStrings.length; i++) {
                String val = orderWiseStrings[i];
                incompleteString = incompleteString.replace(String.valueOf(i + 1), val);
            }
        }
        return incompleteString;
    }

    public static <T> List<T> getBeanList(File file, Class<T> klazz) throws FileNotFoundException {
        FileReader reader = new FileReader(file);
        List<T> beans = new CsvToBeanBuilder(reader)
                .withType(klazz).withSkipLines(1).withOrderedResults(true).withSeparator('~').build().parse();
        return beans;
    }

    public static String fileErrorLoggersToJson(FileErrorLoggers fileErrorLoggers) throws JsonProcessingException {
        String jsonString = null;
        if (isNotNull(fileErrorLoggers)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            jsonString = objectMapper.writeValueAsString(fileErrorLoggers);
        }

        return jsonString;
    }

    public static void saveDataInFile(String data, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] strToBytes = data.getBytes();

        Files.write(path, strToBytes);
    }

    public static String runPythonAndGetData() throws IOException {
        Process p = Runtime.getRuntime().exec("python JsonProcessor.py");
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String pythonStream = in.readLine();
        return pythonStream;
    }

    public static String getJsonFileNameWithPath(String key, String subKey) {
        return ITF_JSON_FILE_LOCATION + "\\" + key + "_" + subKey + "_errorfile.json";
    }

    public static MailMimeContainer getMailMimeProcessorPerJsonFound(AllFileDataHandler handler, String key, String subKey, String fileExtension) {
        MailMimeContainer mailMimeContainer = null;
        String jsonString = handler.getJsonString();
        if (Utility.isNotEmpty(jsonString)) {
            mailMimeContainer = new MailMimeContainer();
            mailMimeContainer.setFileName(key + "_" + subKey + "_errorfile" + fileExtension);
            mailMimeContainer.setFileText(jsonString);
            mailMimeContainer.setFileContentType(Constants.PLAIN_TEXT);
        }
        return mailMimeContainer;
    }

    public static String convertAnyStringListToString(List<String> list) {
        String string = null;
        if (!CollectionUtils.isEmpty(list)) {
            StringBuilder builder = new StringBuilder();
            for (String s : list) {
                if (s != null && !s.equals("null"))
                    builder.append(s).append("\n");
            }
            string = builder.toString();
        }
        return string;
    }

    public static boolean isNotNull(Object o) {
        return o != null;
    }

    public static boolean isNotEmpty(String message) {
        return message != null && !message.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isArrayNotEmpty(Object[] cc) {
        return cc != null && cc.length > 0;
    }
}