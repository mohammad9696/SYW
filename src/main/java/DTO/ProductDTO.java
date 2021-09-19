package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body_html")
    private String bodyHtml;

    @JsonProperty("vendor")
    private String brand;

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("handle")
    private String handle;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("published_at")
    private String publishedAt;

    @JsonProperty("template_suffix")
    private String templateSuffix;

    @JsonProperty("status")
    private String status;

    @JsonProperty("published_scope")
    private String publishedScope;

    @JsonProperty("tags")
    private String tags;

    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;

    @JsonProperty("variants")
    private List<ProductVariantDTO> variants;

    @JsonProperty("images")
    private List<ProductImageDTO> images;

    @JsonProperty("image")
    private ProductImageDTO image;

    @Override
    public String toString() {
        return "DTO.ProductDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", bodyHtml='" + bodyHtml + '\'' +
                ", brand='" + brand + '\'' +
                ", productType='" + productType + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", handle='" + handle + '\'' +
                ", updateddAt='" + updatedAt + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", templateSuffix='" + templateSuffix + '\'' +
                ", status='" + status + '\'' +
                ", publishedScope='" + publishedScope + '\'' +
                ", tags='" + tags + '\'' +
                ", adminGraphqlApiId='" + adminGraphqlApiId + '\'' +
                ", variants=" + variants +
                ", images=" + images +
                ", image=" + image +
                '}';
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
}
