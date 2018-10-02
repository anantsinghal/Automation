package com.backoffice.automation.older_Automation_Code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class AutomationTest {
    public AutomationTest() {
    }

    public static void main(String[] args) throws IOException {
        Properties prop = getProperties("TestBOConfiguration.properties");

        String Job_base_location = prop.getProperty("BASE_LOCATION");
        System.out.println(Job_base_location);

        File[] files = new File(Job_base_location).listFiles();

        readProcessUpdateProperties(prop, files);

        handleDataFeedServices("DataFeedGenerateERF");
        handleDataFeedServices("DataFeedGenerateICF");
        handleDataFeedServices("DataFeedGenerateITF");
        handleDataFeedServices("DataFeedGenerateSITF");
        handleDataFeedServices("DataFeedGenerateVCF");
        handleDataFeedServices("DataFeedImportBLF");
        handleDataFeedServices("DataFeedImportCSF");
        handleDataFeedServices("DataFeedImportCDF");
    }

    private static void readProcessUpdateProperties(Properties prop, File[] files) {
        for (int i = 1; i <= 9; i++) {

            String mode = prop.getProperty(i + "_mode");

            if (mode.equalsIgnoreCase("ON")) {
                String job = prop.getProperty(i + "_JobName");
                System.out.println(job);

                String UK_cutoff = prop.getProperty(i + "_UKCutOff");
                String SC_cutoff = prop.getProperty(i + "_SCCutOff");
                String WM_cutoff = prop.getProperty(i + "_WMCutOff");
                String US_cutoff = prop.getProperty(i + "_USCutOff");
                String XK_cutoff = prop.getProperty(i + "_XKCutOff");

                String Sch = prop.getProperty(i + "_Sch");

                int filesLength = files.length;
                for (int j = 0; j < filesLength; j++) {
                    File directory = files[j];

                    if (directory.isDirectory() && directory.getAbsolutePath().contains(job)) {
                        String path = directory.getAbsoluteFile().toString();

                        String path1 = path + path.substring(path.lastIndexOf('\\'), path.length() - 4)
                                + "\\pbipdatafeed";

                        File[] partnerfiles = new File(path1).listFiles();

                        int partnerFilesSize = partnerfiles.length;
                        for (int k = 0; k < partnerFilesSize; k++) {
                            File partnerDirectory = partnerfiles[k];

                            String propertyFilePath = partnerDirectory.getAbsolutePath().toString()
                                    + "\\contexts\\Default.properties";

                            Properties props = getLoadedProperties(propertyFilePath);
                            updateProperties(UK_cutoff, SC_cutoff, WM_cutoff, US_cutoff, XK_cutoff, Sch, propertyFilePath, props);
                        }

                    }
                }
            }
        }
    }

    private static Properties getLoadedProperties(String propertyFilePath) {
        Properties props = new Properties();

        try {
            FileInputStream in = new FileInputStream(propertyFilePath);
            props.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    private static void updateProperties(String UK_cutoff, String SC_cutoff, String WM_cutoff, String US_cutoff, String XK_cutoff, String sch, String propertyFilePath, Properties props) {
        try {
//            java.io.
            FileOutputStream out = new FileOutputStream(propertyFilePath);

            if (propertyFilePath.contains("sch_")) {
                props.setProperty("Schedule", sch);
            } else {
                String cutOffTime = "CutOffTime";
                if (propertyFilePath.contains("_uk_")) {
                    props.setProperty(cutOffTime, UK_cutoff);
                } else if (propertyFilePath.contains("_sc_")) {
                    props.setProperty(cutOffTime, SC_cutoff);
                } else if (propertyFilePath.contains("_walmart_")) {
                    props.setProperty(cutOffTime, WM_cutoff);
                } else if (propertyFilePath.contains("_us_")) {
                    props.setProperty(cutOffTime, US_cutoff);
                } else if (propertyFilePath.contains("_xbec_uk_")) {
                    props.setProperty(cutOffTime, XK_cutoff);
                }
            }

            props.store(out, null);

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleDataFeedServices(String dataFeedImportCDF) throws IOException {
        boolean isServiceOn = isServiceOn(dataFeedImportCDF);
        if (isServiceOn) {
            stop_service(dataFeedImportCDF);
            run_service(dataFeedImportCDF);
        } else if (!isServiceOn) {
            run_service(dataFeedImportCDF);
        }
    }

    private static Properties getProperties(String propertyFileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File file = new File(classLoader.getResource(propertyFileName).getFile());
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(file.getAbsoluteFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static boolean isServiceOn(String serviceName) throws IOException {
        String[] script = {"cmd.exe", "/c", "sc", "query", serviceName, "|", "find", "/C", "\"RUNNING\""};

        try {
            Process process = new ProcessBuilder(script).start();

            java.io.InputStream inputStream = process.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String isServiceOn = bufferedReader.readLine();

            if (isServiceOn.equals("1")) {
                return true;
            }
            if (isServiceOn.equals("0")) {
                return false;
            }

        } catch (IOException e) {
            throw e;
        }

        return false;
    }

    public static boolean run_service(String service_name) {
        try {
            String x = "cmd /c net start " + service_name;
            System.out.println(x);
            Process p = Runtime.getRuntime().exec("cmd /c net start " + service_name);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            System.out.println("Done");
            return true;
        } catch (IOException localIOException) {
        } catch (InterruptedException localInterruptedException) {
        }

        System.out.println("Done2");
        return false;
    }

    public static boolean stop_service(String service_name) {
        try {
            String x = "cmd /c net stop " + service_name;
            System.out.println(x);
            Process p = Runtime.getRuntime().exec("cmd /c net stop " + service_name);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            System.out.println("Done");
            return true;
        } catch (IOException localIOException) {
        } catch (InterruptedException localInterruptedException) {
        }

        System.out.println("Done2");
        return false;
    }
}
