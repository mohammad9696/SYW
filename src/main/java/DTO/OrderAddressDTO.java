package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderAddressDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("first_name")
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("last_name")
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("address1")
    private String address1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("city")
    private String city;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("province")
    private String province;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("zip")
    private String postalCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("address2")
    private String address2;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("company")
    private String nipc;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("country_code")
    private String countryCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getNipc() {
        return nipc;
    }

    public void setNipc(String nipc) {
        this.nipc = nipc;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
