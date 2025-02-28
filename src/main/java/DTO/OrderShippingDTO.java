package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tax_lines")
    private List<ShopifyTaxLineDTO> taxLines;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public Boolean getCustom() {
        return this.custom;
    }

    public void setCustom(final Boolean custom) {
        this.custom = custom;
    }

    public List<ShopifyTaxLineDTO> getTaxLines() {
        return this.taxLines;
    }

    public void setTaxLines(final List<ShopifyTaxLineDTO> taxLines) {
        this.taxLines = taxLines;
    }

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
