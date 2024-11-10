package DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVariantDTO {

    @JsonProperty("product_id")//deprecated
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

    @JsonProperty("option1") //Deprecated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String option1;

    @JsonProperty("option2") //deprecated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String option2;

    @JsonProperty("option3") //deprecated
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

    @JsonProperty("inventory_policy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String inventoryPolicy;

    @JsonProperty("inventory_item_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String inventoryItemId;

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
    @JsonAlias("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String adminGraphqlApiId;

    @JsonProperty("fulfillment_service")//deprecated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fulfillmentService;

    @JsonProperty("inventory_management")//deprecated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String inventoryManagement;

    @JsonProperty("taxable")//deprecated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String taxable;


    @JsonProperty("inventoryItem")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InventoryItemDTO inventoryItemDTO;

    public InventoryItemDTO getInventoryItemDTO() {
        return this.inventoryItemDTO;
    }

    public void setInventoryItemDTO(final InventoryItemDTO inventoryItemDTO) {
        this.inventoryItemDTO = inventoryItemDTO;
    }

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

    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
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

    public String getFulfillmentService() {
        return fulfillmentService;
    }

    public void setFulfillmentService(String fulfillmentService) {
        this.fulfillmentService = fulfillmentService;
    }

    public String getInventoryManagement() {
        return inventoryManagement;
    }

    public void setInventoryManagement(String inventoryManagement) {
        this.inventoryManagement = inventoryManagement;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
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

    public String getInventoryPolicy() {
        return inventoryPolicy;
    }

    public void setInventoryPolicy(String inventoryPolicy) {
        this.inventoryPolicy = inventoryPolicy;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InventoryItemDTO {

        @JsonProperty("id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String id;

        @JsonProperty("inventoryLevel")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private InventoryLevelQuantitiesDTO inventoryLevelQuantitiesDTO;

        public String getId() {
            return this.id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public InventoryLevelQuantitiesDTO getInventoryLevelQuantitiesDTO() {
            return this.inventoryLevelQuantitiesDTO;
        }

        public void setInventoryLevelQuantitiesDTO(final InventoryLevelQuantitiesDTO inventoryLevelQuantitiesDTO) {
            this.inventoryLevelQuantitiesDTO = inventoryLevelQuantitiesDTO;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InventoryLevelQuantitiesDTO {
        @JsonProperty("quantities")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<Quantities> quantities;

        public List<Quantities> getQuantities() {
            return this.quantities;
        }

        public void setQuantities(final List<Quantities> quantities) {
            this.quantities = quantities;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Quantities {
        @JsonProperty("quantity")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer quantity;

        @JsonProperty("name")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        public Integer getQuantity() {
            return this.quantity;
        }

        public void setQuantity(final Integer quantity) {
            this.quantity = quantity;
        }

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }
}
