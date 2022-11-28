package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductMetafieldListDTO {


    @JsonProperty("metafields")
    private List<ProductMetafieldDTO> metafields;

    public List<ProductMetafieldDTO> getMetafields() {
        return metafields;
    }
}
