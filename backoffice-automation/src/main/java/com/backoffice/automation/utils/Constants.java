package com.backoffice.automation.utils;

import java.text.SimpleDateFormat;

/**
 * Created by NEX9ZKA on 31/08/2018.
 */
public class Constants {
    public static final String DEFAULT_MESSAGE = "Validation passed for file: ";
    public static final String GENERIC_NULL_MESSAGE = " Should not be null or empty";
    public static final String regexForTransactionTime = "^\\d{2}[\\.-]\\d{2}[\\.-]\\d{4}\\s\\d{2}[\\.:]\\d{2}[\\.:]\\d{2}$";
    public static final String PLAIN_TEXT = "text/plain; charset=UTF-8";
    public static final String REGEX_FOR_NEWLINE_REMOVAL = "(?m)^[ \t]*\r?\n";
    public static final String ENV_VAR_ITF_BO_AUTOMATION = "BO_Automation_ITF";
    public static final String ENV_VAR_ITF_BO_AUTOMATION_JSON = "BO_Automation_ITF_Json";
    public static final String ITF_MARSH_PIP_FILE_LOCATION = System.getenv(ENV_VAR_ITF_BO_AUTOMATION);
    public static final String ITF_JSON_FILE_LOCATION = System.getenv(ENV_VAR_ITF_BO_AUTOMATION_JSON);
    public static final SimpleDateFormat EIGHT_CHAR_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat FOURTEEN_CHAR_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final String XBEC_ITF_OK_MESSAGE = "No problems were found in XBEC'S ITF file.";
    public static final String MARSH1_ITF_OK_MESSAGE = "No problems were found in MARSH1 ITF file.";
    public static final String MARSH3_ITF_OK_MESSAGE = "No problems were found in MARSH3 ITF file.";
    public static final String SHIPCOVER_ITF_OK_MESSAGE = "No problems were found in SHIPCOVER ITF file.";
    public static final String SHIPPINGAPI_ITF_OK_MESSAGE = "No problems were found in SHIPPINGAPI ITF file.";

    public static final String XBEC_ITF_NOT_FOUND = "Today's ITF file for XBEC not found";
    public static final String MARSH1_ITF_NOT_FOUND = "Today's ITF file for MARSH1 not found";
    public static final String MARSH3_ITF_NOT_FOUND = "Today's ITF file for MARSH3 not found";
    public static final String SHIPCOVER_ITF_NOT_FOUND = "Today's ITF file for SHIPCOVER not found";
    public static final String SHIPPINGAPI_NOT_FOUND = "Today's ITF file for SHIPPINGAPI not found";

    public static final String FILE_NOT_EXISTS_ERROR = "1 File For 2 Does Not Exists";
}
