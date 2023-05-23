package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniChildProductDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product_composed_id")
    private Long productComposedId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("qty")
    private Integer quantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("price")
    private Double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product_parent_id")
    private Long productParentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product_child_id")
    private Long productChildId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private MoloniProductDTO productDTO;

    public Long getProductComposedId() {
        return this.productComposedId;
    }

    public void setProductComposedId(final Long productComposedId) {
        this.productComposedId = productComposedId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Long getProductParentId() {
        return this.productParentId;
    }

    public void setProductParentId(final Long productParentId) {
        this.productParentId = productParentId;
    }

    public Long getProductChildId() {
        return this.productChildId;
    }

    public void setProductChildId(final Long productChildId) {
        this.productChildId = productChildId;
    }

    public MoloniProductDTO getProductDTO() {
        return this.productDTO;
    }

    public void setProductDTO(final MoloniProductDTO productDTO) {
        this.productDTO = productDTO;
    }
}
