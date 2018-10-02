package com.backoffice.automation.POJO.broker_POJO.ITF.shipping_API_POJOS;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

/**
 * Created by NEX9ZKA on 9/14/2018.
 */

@Data
public class ShippingApiPolicy {

    @CsvBindByPosition(position = 0)
    private String insuredAmount;

    @CsvBindByPosition(position = 1)
    private String premium;

    @CsvBindByPosition(position = 2)
    private String shipperNumber;

    @CsvBindByPosition(position = 3)
    private String firstName;

    @CsvBindByPosition(position = 4)
    private String lastName;

    @CsvBindByPosition(position = 5)
    private String state;

    @CsvBindByPosition(position = 6)
    private String destinationCountry;

    @CsvBindByPosition(position = 7)
    private String packageID;

    @CsvBindByPosition(position = 8)
    private String carrierCode;

    @CsvBindByPosition(position = 9)
    private String shipDate;

}
