package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductListDTO {
    public List<ProductDTO> getProducts() {
        return products;
    }

    @JsonProperty("products")
    private List<ProductDTO> products;
}
