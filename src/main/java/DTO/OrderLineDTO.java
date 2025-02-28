package DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("price")
    private Double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sku")
    private String sku;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("variant_id")
    private String variantId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("fulfillable_quantity")
    private int fulfillableQuantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("quantity")
    private int quantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tax_lines")
    private List<ShopifyTaxLineDTO> taxLineDTOS;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("moloniStock")
    private Integer moloniStock;

    public List<ShopifyTaxLineDTO> getTaxLineDTOS() {
        return this.taxLineDTOS;
    }

    public void setTaxLineDTOS(final List<ShopifyTaxLineDTO> taxLineDTOS) {
        this.taxLineDTOS = taxLineDTOS;
    }

    public void setMoloniStock(final Integer moloniStock) {
        this.moloniStock = moloniStock;
    }

    public Integer getMoloniStock() {
        return moloniStock;
    }

    public void setMoloniStock(int moloniStock) {
        this.moloniStock = moloniStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getFulfillableQuantity() {
        return fulfillableQuantity;
    }

    public void setFulfillableQuantity(int fulfillableQuantity) {
        this.fulfillableQuantity = fulfillableQuantity;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }
}
