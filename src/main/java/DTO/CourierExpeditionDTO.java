package DTO;

import Constants.ConstantsEnum;
import Constants.CourierExpeditionEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierExpeditionDTO {

    @JsonProperty("date")
    private String date;

    @JsonProperty("service")
    private String service;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("sender_name")
    private String senderName;

    @JsonProperty("sender_address")
    private String senderAddress;

    @JsonProperty("sender_zip_code")
    private String senderZipCode;

    @JsonProperty("sender_city")
    private String senderCity;

    @JsonProperty("sender_country")
    private String senderCountry;

    @JsonProperty("sender_phone")
    private String senderPhone;

    @JsonProperty("recipient_name")
    private String recipientName;

    @JsonProperty("recipient_address")
    private String recipientAddress;

    @JsonProperty("recipient_zip_code")
    private String recipientZipCode;

    @JsonProperty("recipient_city")
    private String recipientCity;

    @JsonProperty("recipient_country")
    private String recipientCountry;

    @JsonProperty("recipient_phone")
    private String recipientPhone;

    @JsonProperty("volumes")
    private String volumes;

    @JsonProperty("weight")
    private String weight;
/*
    @JsonProperty("dimensions")
    private List<CourierVolumeDimensionDTO> dimensions;
*/



    public CourierExpeditionDTO(String reference, OrderAddressDTO sender, OrderAddressDTO recipient, CourierExpeditionEnum service, String volumes, String weight) {

        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.HOUR);

        if (calendar.get(Calendar.HOUR) >= Integer.parseInt(ConstantsEnum.COURIER_REQUEST_LAST_HOUR.getConstantValue().toString()) && calendar.get(Calendar.AM_PM) == Calendar.PM){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        String month;
        if (calendar.get(Calendar.MONTH)+1 <10){
            month = "0" + (calendar.get(Calendar.MONTH)+1);
        } else {
            month = "" + (calendar.get(Calendar.MONTH)+1);
        }

        String day;
        if (calendar.get(Calendar.DAY_OF_MONTH) <10){
            day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            day = "" + calendar.get(Calendar.DATE);
        }

        this.date = calendar.get(Calendar.YEAR)+ "-"+ month + "-" + day;
        this.service = service.getCourierServiceCode();
        this.reference = reference;
        this.senderName = sender.getFirstName() + " " + sender.getLastName();
        this.senderAddress = sender.getAddress1() + " " + sender.getAddress2();
        this.senderZipCode = sender.getPostalCode();
        this.senderCity = sender.getCity();
        this.senderCountry = sender.getCountryCode();
        this.senderPhone = sender.getPhone().replace(" ","");
        this.recipientName = recipient.getFirstName() + " " + recipient.getLastName();
        this.recipientAddress = recipient.getAddress1() + " " + recipient.getAddress2();
        this.recipientZipCode = recipient.getPostalCode();
        this.recipientCity = recipient.getCity();
        this.recipientCountry = recipient.getCountryCode();
        this.recipientPhone = recipient.getPhone().replace(" ","");
        this.volumes = volumes;
        this.weight = weight;
    }
}
