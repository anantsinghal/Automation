package com.backoffice.automation.POJO.JSON_POJO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEX9ZKA on 06/09/2018.
 */
public class FileErrorLoggers {
    @JsonPropertyOrder({"fileName", "fileErrorLoggerList"})
    private String fileName;
    private List<FileErrorLogger> fileErrorLoggerList;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public List<FileErrorLogger> getFileErrorLoggerList() {
        if (fileErrorLoggerList == null) {
            fileErrorLoggerList = new ArrayList<>();
        }
        return fileErrorLoggerList;
    }
}
