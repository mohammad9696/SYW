package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductMetafieldObjectDTO {

    @JsonProperty("metafield")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductMetafieldDTO productMetafieldDTO;

    public ProductMetafieldObjectDTO(ProductMetafieldDTO productMetafieldDTO) {
        this.productMetafieldDTO = productMetafieldDTO;
    }

    public ProductMetafieldObjectDTO(){}

    public ProductMetafieldDTO getProductMetafieldDTO() {
        return productMetafieldDTO;
    }

    public void setProductMetafieldDTO(ProductMetafieldDTO productMetafieldDTO) {
        this.productMetafieldDTO = productMetafieldDTO;
    }

    @Override
    public String toString() {
        return "ProductMetafieldObjectDTO{" +
                "productMetafieldDTO=" + (productMetafieldDTO != null ? productMetafieldDTO.toString() : productMetafieldDTO) +
                '}';
    }
}
