package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GQLMutationMetafieldsVariablesDTO {
    @JsonProperty("metafields")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductMetafieldDTO> metafields;

    public GQLMutationMetafieldsVariablesDTO(final List<ProductMetafieldDTO> metafields) {
        this.metafields = metafields;
    }
}
