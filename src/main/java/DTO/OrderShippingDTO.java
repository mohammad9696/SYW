package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderShippingDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("code")
    private String shippingCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("title")
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("price")
    private String price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custom")
    private Boolean custom;

    public String getShippingCode() {
        return shippingCode;
    }

    public OrderShippingDTO(String title, String price, Boolean custom) {
        this.title = title;
        this.price = price;
        this.custom = custom;
    }

    public OrderShippingDTO() {
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }
}
