package com.backoffice.automation;

import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import com.backoffice.automation.file_cache.FileJsonProcessor;
import com.backoffice.automation.file_cache.FileValidation;
import com.backoffice.automation.file_cache.HtmlProcessor;
import com.backoffice.automation.file_cache.MimeJsonProcessor;
import com.backoffice.automation.file_cache.UniqueFileCache;
import com.backoffice.automation.log_handler.FileLogHandler;
import com.backoffice.automation.mail_Handler.MailContentHolder;
import com.backoffice.automation.mail_Handler.MailProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

import static com.backoffice.automation.utils.Utility.convertAnyStringListToString;

@SpringBootApplication
public class BackofficeAutomationApplication {

    @Autowired
    private FileLogHandler fileLogHandler;
    @Autowired
    private UniqueFileCache uniqueFileCache;
    @Autowired
    private FileValidation fileValidation;
    @Autowired
    private HtmlProcessor htmlProcessor;
    @Autowired
    private FileJsonProcessor fileJsonProcessor;
    @Autowired
    private MimeJsonProcessor mimeJsonProcessor;
    @Autowired
    private MailProcessor mailProcessor;

    private Map<String, Map<String, AllFileDataHandler>> map;

    public static void main(String[] args) {
        SpringApplication.run(BackofficeAutomationApplication.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        map = uniqueFileCache.getFilePerPartnerCachedMap();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {
            fileValidation.validateExistanceOfFilesInMap(map);
            String html = htmlProcessor.getHtml(map);
            fileJsonProcessor.validateJsonAvailabilityAndFileCorrectness(map);
            MailContentHolder mailContentHolder = mimeJsonProcessor.validateJsonAvailabilityAndFileCorrectness(map);
            String body = convertAnyStringListToString(mailContentHolder.getPositiveMessages()) + convertAnyStringListToString(mailContentHolder.getFileErrorMessages());
            mailProcessor.sendMailWithZipFileAttachments(body, html, "Hello Anant", "anant.singhal@pb.com", mailContentHolder.getMailMimeContainers(), "priyanka.gupta2@pb.com");
            System.out.println(html);

//            fileLogHandler.getEveryFileLog(map);
        };
    }


}
