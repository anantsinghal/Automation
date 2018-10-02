package com.backoffice.automation.file_cache;

import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import com.backoffice.automation.mail_Handler.MailContentHolder;
import com.backoffice.automation.mail_Handler.MailMimeContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static com.backoffice.automation.utils.Utility.*;

/**
 * Created by NEX9ZKA on 9/30/2018.
 */
@Component
public class MimeJsonProcessor {
    public MailContentHolder validateJsonAvailabilityAndFileCorrectness(Map<String, Map<String, AllFileDataHandler>> map) {
        MailContentHolder mailContentHolder = null;
        if (!CollectionUtils.isEmpty(map)) {
            mailContentHolder = new MailContentHolder();

            processSubMap(map, mailContentHolder);
        }

        return mailContentHolder;
    }

    private void processSubMap(Map<String, Map<String, AllFileDataHandler>> map, MailContentHolder mailContentHolder) {
        for (Map.Entry entry : map.entrySet()) {
            String key = (String) entry.getKey();
            Map<String, AllFileDataHandler> subMap = (Map<String, AllFileDataHandler>) entry.getValue();

            for (Map.Entry subEntry : subMap.entrySet()) {
                String subKey = (String) subEntry.getKey();
                AllFileDataHandler allFileDataHandler = (AllFileDataHandler) subEntry.getValue();
                MailMimeContainer mailMimeContainer = getMailMimeProcessorPerJsonFound(allFileDataHandler, key, subKey, ".json");
                addMailMimeContainerToList(mailContentHolder.getMailMimeContainers(), mailMimeContainer);
                addFileErrorsToMailContent(mailContentHolder.getFileErrorMessages(), allFileDataHandler.getFileErrorMessages(), key, subKey);
                addFilePositiveMessageToMailContent(mailContentHolder.getPositiveMessages(), allFileDataHandler.getPositiveDataMessage(), key, subKey);
            }
        }
    }

    private void addFileErrorsToMailContent(List<String> fileErrorList, List<String> mapFileErrList, String key, String subKey) {
        if (isNotEmpty(mapFileErrList)) {
            fileErrorList.add("\n" + key + "->" + subKey + "\n");
            fileErrorList.addAll(mapFileErrList);
        }
    }

    private void addFilePositiveMessageToMailContent(List<String> positiveMessage, String mapPosMessage, String key, String subKey) {
        if (isNotEmpty(mapPosMessage)) {
            positiveMessage.add("\n" + key + "->" + subKey + "\n");
            positiveMessage.add(mapPosMessage);
        }
    }

    private void addMailMimeContainerToList(List<MailMimeContainer> mailMimeContainers, MailMimeContainer mailMimeContainer) {
        if (isNotNull(mailMimeContainer)) {
            mailMimeContainers.add(mailMimeContainer);
        }
    }


} 