package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
    @JsonProperty("unit_id")
    private Long measurementUnitId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("type")
    private Long type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("has_stock")
    private Long hasStock;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("stock")
    private Integer stock;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("at_product_category")
    private String productCategory;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ean")
    private String ean;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("stock")
    private Integer inventory;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String productName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("reference")
    private String sku;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("price")
    private Double priceWithoutVat;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("minimum_stock")
    private String minimumStock;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("taxes")
    private List<MoloniProductTaxesDTO> taxes;


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

    public Integer getInventory() {
        return inventory;
    }

    public String getProductName() {
        return productName;
    }

    public String getSku() {
        return sku;
    }

    public Double getPriceWithoutVat() {
        return priceWithoutVat;
    }

    public String getMinimumStock() {
        return minimumStock;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(Long measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getHasStock() {
        return hasStock;
    }

    public void setHasStock(Long hasStock) {
        this.hasStock = hasStock;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setPriceWithoutVat(double priceWithoutVat) {
        this.priceWithoutVat = priceWithoutVat;
    }

    public void setMinimumStock(String minimumStock) {
        this.minimumStock = minimumStock;
    }

    public List<MoloniProductTaxesDTO> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<MoloniProductTaxesDTO> taxes) {
        this.taxes = taxes;
    }


}
