package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductImageDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("position")
    private String position;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updateddAt;

    @JsonProperty("width")
    private Long width;

    @JsonProperty("height")
    private Long height;

    @JsonProperty("src")
    private String src;

    @Override
    public String toString() {
        return "DTO.ProductImageDTO{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", position='" + position + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updateddAt='" + updateddAt + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", src='" + src + '\'' +
                ", adminGraphqlApiId='" + adminGraphqlApiId + '\'' +
                '}';
    }

    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateddAt() {
        return updateddAt;
    }

    public void setUpdateddAt(String updateddAt) {
        this.updateddAt = updateddAt;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }
}
