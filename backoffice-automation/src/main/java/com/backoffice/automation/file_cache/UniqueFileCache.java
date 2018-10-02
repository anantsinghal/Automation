package com.backoffice.automation.file_cache;

import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.backoffice.automation.utils.Constants.EIGHT_CHAR_DATE_FORMAT;
import static com.backoffice.automation.utils.Constants.ITF_MARSH_PIP_FILE_LOCATION;
import static com.backoffice.automation.utils.Utility.isNotEmpty;

@Component
public class UniqueFileCache {

    @Value("#{'${backoffice.automation.file.DEFAULTPartnerSupported}'}")
    public String DEFAULTPartnerSupported;
    @Value("#{'${backoffice.automation.file.fileNames}'.split(',')}")
    public List<String> fileNames;
    @Autowired
    private Environment environment;

    private static List<String> getTodaysFilesInDirectory(String dirPath, String formattedDate) {
        File file = new File(dirPath);
        String[] fileList = file.list();

        List<String> latestList = new ArrayList<>();
        for (int i = 0; i < fileList.length; i++) {
            String fileName = fileList[i];
            if (fileName.contains(formattedDate)) {
                latestList.add(fileName);
            }
        }
        return latestList;
    }

    public Map<String, Map<String, AllFileDataHandler>> getFilePerPartnerCachedMap() {
        Map<String, Map<String, AllFileDataHandler>> fileMap = getFileMap(fileNames);
        for (Map.Entry entry : fileMap.entrySet()) {
            String fileType = (String) entry.getKey();
            String fileDirectory = ITF_MARSH_PIP_FILE_LOCATION + environment.getProperty(getPartnerFilePathProperty(fileType));
            List<String> todayFiles = getTodaysFilesInDirectory(fileDirectory, EIGHT_CHAR_DATE_FORMAT.format(new Date()));
            Map<String, AllFileDataHandler> value = (Map<String, AllFileDataHandler>) entry.getValue();

            processSubMap(fileType, fileDirectory, todayFiles, value);
        }
        return fileMap;
    }

    private void processSubMap(String fileType, String fileDirectory, List<String> todaysFiles, Map<String, AllFileDataHandler> value) {
        for (Map.Entry subEntry : value.entrySet()) {
            String partner = (String) subEntry.getKey();
            String fileInitialName = environment.getProperty(getPartnerFileNameProperty(fileType, partner));

            AllFileDataHandler allFileDataHandler = (AllFileDataHandler) subEntry.getValue();

            fillSubMapWithLatestModifiedFiles(fileDirectory, todaysFiles, subEntry, fileInitialName, allFileDataHandler);
        }
    }

    private void fillSubMapWithLatestModifiedFiles(String fileDirectory, List<String> todaysFiles, Map.Entry subEntry, String fileInitialName, AllFileDataHandler allFileDataHandler) {
        for (int i = 0; i < todaysFiles.size(); i++) {
            String fileName = todaysFiles.get(i);
            if (fileName.contains(fileInitialName)) {
                allFileDataHandler.getBoFiles().add(new File(fileDirectory + "\\" + fileName));
            }
        }
        addOnlyLatestModifiedFileInMap(subEntry, allFileDataHandler);
    }

    private void addOnlyLatestModifiedFileInMap(Map.Entry subEntry, AllFileDataHandler allFileDataHandler) {
        if (allFileDataHandler.getBoFiles().size() > 1) {
            List<File> filteredLatestModifiedFile = getFilteredLatestModifiedFile(allFileDataHandler);
            allFileDataHandler.getBoFiles().clear();
            allFileDataHandler.getBoFiles().addAll(filteredLatestModifiedFile);
            subEntry.setValue(allFileDataHandler);
        }
    }

    private Map<String, Map<String, AllFileDataHandler>> getFileMap(List<String> fileTypes) {
        Map<String, Map<String, AllFileDataHandler>> map = null;
        if (isNotEmpty(fileTypes)) {
            map = fillFirstMap(fileTypes);
        }
        return map;
    }

    private Map<String, Map<String, AllFileDataHandler>> fillFirstMap(List<String> fileTypes) {
        Map<String, Map<String, AllFileDataHandler>> map;
        map = new LinkedHashMap<>();
        for (int i = 0; i < fileTypes.size(); i++) {
            String fileType = fileTypes.get(i);
            if (!map.containsKey(fileType)) {
                map.put(fileType, new LinkedHashMap<>());
            }
            fillSecondMap(map, fileType);
        }
        return map;
    }

    private void fillSecondMap(Map<String, Map<String, AllFileDataHandler>> map, String fileType) {
        List<String> partnersSupported = getSupportedPartners(fileType);
        for (int i = 0; i < partnersSupported.size(); i++) {
            String supportedPrt = partnersSupported.get(i);
            Map<String, AllFileDataHandler> secondMap = map.get(fileType);
            if (!secondMap.containsKey(supportedPrt)) {
                secondMap.put(supportedPrt, new AllFileDataHandler());
            }
        }
    }

    private List<File> getFilteredLatestModifiedFile(AllFileDataHandler allFileDataHandler) {
        List<File> fileList;
        List<File> boFiles = allFileDataHandler.getBoFiles();
        fileList = (isNotEmpty(boFiles) && boFiles.size() > 1) ? getLastModifiedFileInList(boFiles) : boFiles;
        return fileList;
    }

    private List<File> getLastModifiedFileInList(List<File> todayFiles) {
        File lastModifiedFile = todayFiles.get(0);
        List<File> fileList = new ArrayList<>(1);
        for (int i = 0; i < todayFiles.size(); i++) {
            File file = todayFiles.get(i);
            if (lastModifiedFile.lastModified() < file.lastModified()) {
                lastModifiedFile = file;
            }
        }
        fileList.add(lastModifiedFile);
        return fileList;
    }

    private List<String> getSupportedPartners(String fileType) {
        String property = environment.getProperty(getSupportedPartnerProperty(fileType));
        String finalValue = isNotEmpty(property) ? property : DEFAULTPartnerSupported;
        String[] split = finalValue.split(",");
        return Arrays.asList(split);
    }

    private String getSupportedPartnerProperty(String fileType) {
        String initialProperty = "backoffice.automation.file.";
        String laterProperty = "PartnerSupported";
        String finalProperty = initialProperty + fileType + laterProperty;
        return finalProperty;
    }

    private String getPartnerFileNameProperty(String fileType, String partnerName) {
        String initialProperty = "backoffice.automation.file.";
        String laterProperty = "_FileName";
        String finalProperty = initialProperty + fileType + "_" + partnerName + laterProperty;
        return finalProperty;
    }

    private String getPartnerFilePathProperty(String fileType) {
        String initialProperty = "backoffice.automation.file.";
        String laterProperty = "_FILEPATH";
        String finalProperty = initialProperty + fileType + laterProperty;
        return finalProperty;
    }

    /*

    public Map<InsurancePartners, List<File>> getFilteredFiles() throws IOException {
        playWithMap();
        Map<String, Map<String, List<File>>> fileMap = getFileMap(fileNames);
        File[] files = getAllFilesInDirectory(ITF_MARSH_PIP_FILE_LOCATION);
        List<File> fileList = getFilesExcludingDirectories(files, getFormattedDate(new Date(), EIGHT_CHAR_DATE_FORMAT));
        Map<InsurancePartners, List<File>> map = putfilesInMapByCategory(fileList);
        Map<InsurancePartners, List<File>> map1 = getLatestFileMap(map);
        return map1;
    }

    private Map<InsurancePartners, List<File>> putfilesInMapByCategory(List<File> fileList) {
        Map<InsurancePartners, List<File>> fileMap = getFileMap();
        if (isNotEmpty(fileList)) {
            for (File file : fileList) {
                String fileName = file.getName();
                fillEnumMapWithITFFilesBasedOnFileName(fileMap, file, fileName);
            }
        }
        return fileMap;
    }

    private Map<InsurancePartners, List<File>> getLatestFileMap(Map<InsurancePartners, List<File>> fileMap) {
        if (fileMap != null) {
            for (Map.Entry<InsurancePartners, List<File>> entry : fileMap.entrySet()) {
                List<File> fileList = entry.getValue();
                if (isNotEmpty(fileList)) {
                    File LatestFile = getLatestFileInList(fileList);
                    fileList.clear();
                    fileList.add(LatestFile);
                }
            }
        }
        return fileMap;
    }

    private File getLatestFileInList(List<File> fileList) {
        File lastModifiedFile = null;
        if (isNotEmpty(fileList)) {
            lastModifiedFile = fileList.get(0);
            for (File file : fileList) {
                if (lastModifiedFile.lastModified() < file.lastModified()) {
                    lastModifiedFile = file;
                }
            }
        }
        return lastModifiedFile;
    }

    private void fillEnumMapWithITFFilesBasedOnFileName(Map<InsurancePartners, List<File>> fileMap, File file, String fileName) {
        if (fileName.contains(MARSH1.getFileName())) {
            fillMapByAvailability(fileMap, MARSH1, file);
        } else if (fileName.contains(MARSH3.getFileName())) {
            fillMapByAvailability(fileMap, MARSH3, file);
        } else if (fileName.contains(SHIPCOVER.getFileName())) {
            fillMapByAvailability(fileMap, SHIPCOVER, file);
        } else if (fileName.contains(XBEC.getFileName())) {
            fillMapByAvailability(fileMap, XBEC, file);
        } else if (fileName.contains(SHIPPING_API.getFileName())) {
            fillMapByAvailability(fileMap, SHIPPING_API, file);
        }
    }

    private void fillMapByAvailability(Map<InsurancePartners, List<File>> fileMap, InsurancePartners insurancePartners, File file) {
        if (fileMap.get(insurancePartners) == null) {
            fileMap.replace(insurancePartners, new ArrayList<>());
        }
        fileMap.get(insurancePartners).add(file);
    }

    private Map<InsurancePartners, List<File>> getFileMap() {
        Map<InsurancePartners, List<File>> insMap = new EnumMap<>(InsurancePartners.class);
        insMap.put(MARSH1, null);
        insMap.put(MARSH3, null);
        insMap.put(SHIPCOVER, null);
        insMap.put(XBEC, null);
        insMap.put(SHIPPING_API, null);
        return insMap;
    }*/


} 