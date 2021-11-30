package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVariantObjectDTO {

    @JsonProperty("variant")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductVariantDTO variant;

    public ProductVariantObjectDTO(ProductVariantDTO variant) {
        this.variant = variant;
    }

    public ProductVariantDTO getVariant() {
        return variant;
    }

    public void setVariant(ProductVariantDTO variant) {
        this.variant = variant;
    }
}
