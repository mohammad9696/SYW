package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniProductDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("company_id")
    private Long companyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product_id")
    private Long productId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("category_id")
    private Long categoryId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ean")
    private String ean;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("stock")
    private int inventory;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String productName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("reference")
    private String sku;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("price")
    private double priceWithoutVat;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("minimum_stock")
    private String minimumStock;


    public MoloniProductDTO(Long companyId, String sku) {
        this.companyId = companyId;
        this.sku = sku;
    }

    public MoloniProductDTO(Long companyId, Long productId, String ean, String productName, String sku, double priceWithoutVat) {
        this.companyId = companyId;
        this.productId = productId;
        this.ean = ean;
        this.productName = productName;
        this.sku = sku;
        this.priceWithoutVat = priceWithoutVat;
    }

    public MoloniProductDTO() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getEan() {
        return ean;
    }

    public int getInventory() {
        return inventory;
    }

    public String getProductName() {
        return productName;
    }

    public String getSku() {
        return sku;
    }

    public double getPriceWithoutVat() {
        return priceWithoutVat;
    }

    public String getMinimumStock() {
        return minimumStock;
    }
}
