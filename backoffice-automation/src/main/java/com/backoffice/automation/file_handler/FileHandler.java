package com.backoffice.automation.file_handler;

import com.backoffice.automation.POJO.JSON_POJO.FileErrorLoggers;

import java.io.File;
import java.io.IOException;

/**
 * Created by NEX9ZKA on 9/28/2018.
 */
public interface FileHandler {
    FileErrorLoggers validateAndGetLogs(File file) throws IOException;
} 