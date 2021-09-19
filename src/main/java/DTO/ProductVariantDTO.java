package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVariantDTO {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private double price;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("position")
    private Long position;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("grams")
    private double grams;

    @JsonProperty("weight")
    private double weight;

    @JsonProperty("weight_unit")
    private String weightUnit;

    @JsonProperty("inventory_item_id")
    private Long inventoryItemId;

    @JsonProperty("inventory_quantity")
    private int inventoryQuantity;

    @JsonProperty("old_inventory_quantity")
    private int oldInventoryQuantity;

    @JsonProperty("requires_shipping")
    private boolean requiresShipping;

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

    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;


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

    public double getPrice() {
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

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public int getOldInventoryQuantity() {
        return oldInventoryQuantity;
    }

    public void setOldInventoryQuantity(int oldInventoryQuantity) {
        this.oldInventoryQuantity = oldInventoryQuantity;
    }

    public boolean isRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }
}
