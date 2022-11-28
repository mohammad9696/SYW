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

    public ProductMetafieldDTO(String key, String value) {
        this.namespace = "custom";
        this.key = key;
        this.value = value;
        this.owner_resource = "product";
    }

    public ProductMetafieldDTO() {
    }
}
