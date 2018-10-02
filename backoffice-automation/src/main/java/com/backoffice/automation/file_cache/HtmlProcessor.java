package com.backoffice.automation.file_cache;

import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import com.backoffice.automation.utils.Utility;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

import static com.backoffice.automation.utils.Utility.getJsonFileNameWithPath;

/**
 * Created by NEX9ZKA on 9/29/2018.
 */
@Component
public class HtmlProcessor {
    public String getHtml(Map<String, Map<String, AllFileDataHandler>> map) throws IOException {
        if (!CollectionUtils.isEmpty(map)) {
            for (Map.Entry entry : map.entrySet()) {
                String key = (String) entry.getKey();
                Map<String, AllFileDataHandler> subMap = (Map<String, AllFileDataHandler>) entry.getValue();

                for (Map.Entry subEntry : subMap.entrySet()) {
                    String subKey = (String) subEntry.getKey();
                    AllFileDataHandler handler = (AllFileDataHandler) subEntry.getValue();

                    String json = handler.getJsonString();
                    writeJsonInSpecifiedLocation(key, subKey, json);
                }
            }
        }
        return getHtmlStringFromPython();
    }

    private void writeJsonInSpecifiedLocation(String key, String subKey, String json) throws IOException {
        if (Utility.isNotEmpty(json)) {
            Utility.saveDataInFile(json, getJsonFileNameWithPath(key, subKey));
        }
    }


    private String getHtmlStringFromPython() throws IOException {
        return Utility.runPythonAndGetData();
    }


} 