package com.backoffice.automation.commonDataProcessors;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEX9ZKA on 9/28/2018.
 */


public class AllFileDataHandler {
    private List<File> boFiles;
    private List<String> fileErrorMessages;

    @Getter
    @Setter
    private String positiveDataMessage;

    @Getter
    @Setter
    private String jsonString;
    @Getter
    @Setter
    private FileErrorLoggers fileErrorLoggers;

    public List<File> getBoFiles() {
        if (boFiles == null) {
            boFiles = new ArrayList<>();
        }
        return boFiles;
    }

    public List<String> getFileErrorMessages() {
        if (fileErrorMessages == null) {
            fileErrorMessages = new ArrayList<>();
        }
        return fileErrorMessages;
    }
}