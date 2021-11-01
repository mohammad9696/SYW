package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KuantoKustaOrderAddressDTO {

    @JsonProperty("customerName")
    private String name;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zipCode")
    private String postalCode;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("country")
    private String country;

    @JsonProperty("contact")
    private String phone;

    @JsonProperty("vat")
    private String nipc;

    @JsonProperty("servicePoint")
    private String servicePoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNipc() {
        return nipc;
    }

    public void setNipc(String nipc) {
        this.nipc = nipc;
    }

    public String getServicePoint() {
        return servicePoint;
    }

    public void setServicePoint(String servicePoint) {
        this.servicePoint = servicePoint;
    }

}
