package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductMetafieldDTO {

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty("namespace")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String namespace;

    @JsonProperty("key")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String key;

    @JsonProperty("value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonProperty("owner_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String owner_id;

    @JsonProperty("created_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String created_at;

    @JsonProperty("updated_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updated_at;

    @JsonProperty("owner_resource")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String owner_resource;

    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;

    @JsonProperty("admin_graphql_api_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String admin_graphql_api_id;

    public String getId() {
        return id;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setNamespace(final String namespace) {
        this.namespace = namespace;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setOwner_id(final String owner_id) {
        this.owner_id = owner_id;
    }

    public void setCreated_at(final String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(final String updated_at) {
        this.updated_at = updated_at;
    }

    public void setOwner_resource(final String owner_resource) {
        this.owner_resource = owner_resource;
    }

    public void setAdmin_graphql_api_id(final String admin_graphql_api_id) {
        this.admin_graphql_api_id = admin_graphql_api_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getOwner_resource() {
        return owner_resource;
    }

    public String getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAdmin_graphql_api_id() {
        return admin_graphql_api_id;
    }

    public ProductMetafieldDTO(String key,String namespace, String value) {
        this.namespace = namespace;
        this.key = key;
        this.value = value;
        this.owner_resource = "product";
    }

    public ProductMetafieldDTO() {
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProductMetafieldDTO{" +
                "id='" + id + '\'' +
                ", namespace='" + namespace + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", owner_resource='" + owner_resource + '\'' +
                ", type='" + type + '\'' +
                ", admin_graphql_api_id='" + admin_graphql_api_id + '\'' +
                '}';
    }
}
