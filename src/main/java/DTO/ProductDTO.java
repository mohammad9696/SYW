package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bodyHtml;

    @JsonProperty("vendor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String brand;

    @JsonProperty("product_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productType;

    @JsonProperty("created_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdAt;

    @JsonProperty("handle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String handle;

    @JsonProperty("updated_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedAt;

    @JsonProperty("published_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publishedAt;

    @JsonProperty("template_suffix")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String templateSuffix;

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    @JsonProperty("published_scope")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publishedScope;

    @JsonProperty("tags")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tags;

    @JsonProperty("admin_graphql_api_id")
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
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

    public String getSku(){
        return this.getVariants().get(0).getSku();
    }
    @Override
    public String toString() {
        return "ProductDTO{" +
                "title='" + title + '\'' +
                '}';
    }
}
