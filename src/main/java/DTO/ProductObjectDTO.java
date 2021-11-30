package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductObjectDTO {
    @JsonProperty("product")
    private ProductDTO order;

    public ProductDTO getOrder() {
        return order;
    }

    public void setOrder(ProductDTO order) {
        this.order = order;
    }

    public ProductObjectDTO(ProductDTO order) {
        this.order = order;
    }
}
