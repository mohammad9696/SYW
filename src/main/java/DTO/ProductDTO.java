package DTO;

import Constants.ConstantsEnum;
import Constants.ProductMetafieldEnum;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty("title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonProperty("body_html")
    @JsonAlias("descriptionHtml")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bodyHtml;

    @JsonProperty("vendor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String brand;

    @JsonProperty("product_type")
    @JsonAlias("productType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productType;

    @JsonProperty("created_at")
    @JsonAlias("createdAt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdAt;

    @JsonProperty("handle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String handle;

    @JsonProperty("updated_at")
    @JsonAlias("updatedAt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedAt;

    @JsonProperty("published_at")
    @JsonAlias("publishedAt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publishedAt;

    @JsonProperty("template_suffix")
    @JsonAlias("templateSuffix")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String templateSuffix;

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    @JsonProperty("published_scope") //deprecated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publishedScope;

    @JsonIgnore
    private String[] tags;

    @JsonProperty("admin_graphql_api_id") //id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String adminGraphqlApiId;

    @JsonProperty("variants")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductVariantDTO> variants;

    @JsonProperty("images")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductImageDTO> images;

    @JsonProperty("image")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductImageDTO image;

    @JsonProperty("metafields")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductMetafieldDTO> metafields;

    public List<ProductMetafieldDTO> getMetafields() {
        return this.metafields;
    }

    public void setMetafields(final List<ProductMetafieldDTO> metafields) {
        this.metafields = metafields;
    }

    public Integer getDeliveryMaxDays() {
        return this.deliveryMaxDays;
    }

    public void setDeliveryMaxDays(final Integer deliveryMaxDays) {
        this.deliveryMaxDays = deliveryMaxDays;
    }

    private Integer deliveryMinDays;
    private Integer deliveryMaxDays;

    public String getId() {
        return id;
    }

    public String barcode(){
        return this.getVariants().get(0).getBarcode();
    }

    public double priceWithoutVat() {
        return this.getVariants().get(0).getPrice()/Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString());
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

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTemplateSuffix() {
        return templateSuffix;
    }

    public void setTemplateSuffix(String templateSuffix) {
        this.templateSuffix = templateSuffix;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishedScope() {
        return publishedScope;
    }

    public void setPublishedScope(String publishedScope) {
        this.publishedScope = publishedScope;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String tags[]) {
        this.tags = tags;
    }

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }

    public List<ProductVariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariantDTO> variants) {
        this.variants = variants;
    }

    public List<ProductImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ProductImageDTO> images) {
        this.images = images;
    }

    public ProductImageDTO getImage() {
        return image;
    }

    public void setImage(ProductImageDTO image) {
        this.image = image;
    }

    public String sku(){
        try {
            return this.getVariants().get(0).getSku();
        } catch (Exception e){
            return null;
        }

    }
    @Override
    public String toString() {
        return "ProductDTO{" +
                "title='" + title + '\'' +
                '}';
    }

    public Integer getDeliveryMinDays() {
        return this.deliveryMinDays;
    }

    public void setDeliveryMinDays(final Integer deliveryMinDays) {
        this.deliveryMinDays = deliveryMinDays;
    }

    public Integer getMaxDays() {
        return this.deliveryMaxDays;
    }

    public void setMaxDays(final Integer maxDays) {
        this.deliveryMaxDays = maxDays;
    }

    public ProductMetafieldDTO getMetafield (ProductMetafieldEnum metafield){
        for (ProductMetafieldDTO i : metafields){
            if (i.getProductMetafieldEnum()==metafield){
                return i;
            }
        }
        return  null;
    }
}
