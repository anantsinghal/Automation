package com.backoffice.automation;

import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Policies;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Policy;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.Sku;
import com.backoffice.automation.POJO.broker_POJO.ITF.XBEC_POJOS.SkuList;
import com.backoffice.automation.commonDataProcessors.AllFileDataHandler;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by NEX9ZKA on 28/08/2018.
 */
public class StandAlone {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    private static MappingStrategy getMappingStrategy(Class clazz) {
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        //Set mappingStrategy type to Employee Type
        mappingStrategy.setType(clazz);
        //Fields in Employee Bean
        String[] columns = new String[]{"TransactionID",
                "TransactionTime",
                "InsuredAmount",
                "PackageValue",
                "RetailPremium",
                "AccountID",
                "CustomerID",
                "FirstName",
                "MiddleName",
                "LastName",
                "Company",
                "ShipFromState",
                "TrackingNumber",
                "CarrierCode",
                "TransactionCode",
                "TaxOnRetailCommission",
                "TaxOnInsuranceProvisioning",
                "InsuranceCost",
                "WarningShown",
                "ICQSValue",
                "TCQSValue",
                "TransactionType"};
        //Setting the colums for mappingStrategy
        mappingStrategy.setColumnMapping(columns);
        //create instance for CsvToBean class
        return mappingStrategy;
    }


    public static void main(String[] args) throws FileNotFoundException {
        AllFileDataHandler fileDataHandler = new AllFileDataHandler();
//        fileDataHandler.ge
       /* for (int i = 0; i < 1000; i++) {
            long time1 = System.currentTimeMillis();
            File file = new File("D:\\Output\\itf\\temp");
            String[] fileList = file.list();

            long time2 = System.currentTimeMillis();
            System.out.println(time2 - time1);
        }*/
//        List<MarshPolicy> marshPolicies = getBeanList(new File("C:\\Users\\NEX9ZKA\\Desktop\\MARP03Insure00020171026064300000.csv"), MarshPolicy.class,'~');
//        System.out.println("AK");
//        openCSVTesting();
        /*Policies policies = new Policies();
        policies.setPolicy(null);

        Set<ConstraintViolation<Policies>> constraintViolations = validator.validate(policies);
        System.out.println(constraintViolations.iterator().next().getMessage());*/

//        getXmlObject();
    }

    private static void getXmlObject() throws FileNotFoundException {
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("Policies", Policies.class);
        xStream.alias("Policy", Policy.class);
        xStream.alias("Sku", Sku.class);
        xStream.alias("SkuList", SkuList.class);

//        xStream.aliasField("HubID", Policy.class, "HubID");
        xStream.addImplicitCollection(Policies.class, "policyList");

        Policies policies = (Policies) xStream.fromXML(new FileInputStream(new File("C:\\Users\\nex9zka\\Desktop\\PIPP02Insure00020180914082300000.xml")));
        System.out.println(policies);
    }

    /*public void me(){
        CSVParser parser = new CSVParserBuilder()
                .withSeparator('~')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();
    }
*/


    public static <T> List<T> parseCsvFileToBeans(final String filename,
                                                  final char fieldDelimiter,
                                                  final Class<T> beanClass) throws FileNotFoundException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new BufferedReader(new FileReader(filename)),
                    fieldDelimiter);
            final HeaderColumnNameMappingStrategy<T> strategy =
                    new HeaderColumnNameMappingStrategy<T>();
            strategy.setType(beanClass);
            final CsvToBean<T> csv = new CsvToBean<T>();
            return csv.parse(strategy, reader);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // ignore
                }
            }
        }
    }

  /*  private static void openCSVTesting() {
        String filePath = "C:\\Users\\NEX9ZKA\\Desktop\\MARP01Insure00020161215010100000.csv";
        Path myPath = Paths.get(filePath);

        try (CSVReader br = new CSVReader(Files.newBufferedReader(myPath, StandardCharsets.UTF_8))) {

            HeaderColumnNameMappingStrategy<MarshPolicy> strategy
                    = new HeaderColumnNameMappingStrategy<>();

            strategy.setType(MarshPolicy.class);

            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(MarshPolicy.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<MarshPolicy> cars = csvToBean.parse();

            cars.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
