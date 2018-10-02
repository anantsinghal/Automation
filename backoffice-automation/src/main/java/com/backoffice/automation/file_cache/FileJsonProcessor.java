package com.backoffice.automation.file_cache;

import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import com.backoffice.automation.utils.Utility;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created by NEX9ZKA on 9/29/2018.
 */
@Component
public class FileJsonProcessor {
    public void validateJsonAvailabilityAndFileCorrectness(Map<String, Map<String, AllFileDataHandler>> map) {
        if (!CollectionUtils.isEmpty(map)) {
            for (Map.Entry entry : map.entrySet()) {
                String key = (String) entry.getKey();
                Map<String, AllFileDataHandler> subMap = (Map<String, AllFileDataHandler>) entry.getValue();

                for (Map.Entry subEntry : subMap.entrySet()) {
                    String subKey = (String) subEntry.getKey();
                    AllFileDataHandler allFileDataHandler = (AllFileDataHandler) subEntry.getValue();
                    validateJsonExistance(allFileDataHandler, key, subKey);
                }
            }
        }
    }

    private void validateJsonExistance(AllFileDataHandler handler, String key, String subKey) {
        if (CollectionUtils.isEmpty(handler.getFileErrorMessages())) {
            if (!Utility.isNotEmpty(handler.getJsonString())) {
                handler.setPositiveDataMessage(getPositiveDataMessage(key, subKey));
            }
        }
    }

    private String getPositiveDataMessage(String key, String subKey) {
        return "No errors found in: " + key + " File for " + subKey;
    }
} 