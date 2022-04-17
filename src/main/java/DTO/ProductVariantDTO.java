package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVariantDTO {

    @JsonProperty("product_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productId;

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty("title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonProperty("price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double price;

    @JsonProperty("sku")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sku;

    @JsonProperty("position")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long position;

    @JsonProperty("barcode")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String barcode;

    @JsonProperty("option1")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String option1;

    @JsonProperty("option2")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String option2;

    @JsonProperty("option3")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String option3;

    @JsonProperty("grams")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double grams;

    @JsonProperty("weight")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double weight;

    @JsonProperty("weight_unit")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String weightUnit;

    @JsonProperty("inventory_item_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long inventoryItemId;

    @JsonProperty("inventory_quantity")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer inventoryQuantity;

    @JsonProperty("old_inventory_quantity")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer oldInventoryQuantity;

    @JsonProperty("requires_shipping")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean requiresShipping;

    @JsonProperty("compare_at_price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double compareAtPrice;

    @JsonProperty("admin_graphql_api_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String adminGraphqlApiId;

    @Override
    public String toString() {
        return "DTO.ProductVariantDTO{" +
                "productId='" + productId + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                ", position=" + position +
                ", barcode='" + barcode + '\'' +
                ", grams=" + grams +
                ", weight=" + weight +
                ", weightUnit='" + weightUnit + '\'' +
                ", inventoryItemId=" + inventoryItemId +
                ", inventoryQuantity=" + inventoryQuantity +
                ", oldInventoryQuantity=" + oldInventoryQuantity +
                ", requiresShipping=" + requiresShipping +
                ", adminGraphqlApiId='" + adminGraphqlApiId + '\'' +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getGrams() {
        return grams;
    }

    public void setGrams(Double grams) {
        this.grams = grams;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Long getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(Long inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Integer getOldInventoryQuantity() {
        return oldInventoryQuantity;
    }

    public void setOldInventoryQuantity(Integer oldInventoryQuantity) {
        this.oldInventoryQuantity = oldInventoryQuantity;
    }

    public Boolean getRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(Boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    public Double getCompareAtPrice() {
        return compareAtPrice;
    }

    public void setCompareAtPrice(Double compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }
}
