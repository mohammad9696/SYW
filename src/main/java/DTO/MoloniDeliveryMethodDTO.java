package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniDeliveryMethodDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("delivery_method_id")
    private String deliveryMethodId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    public String getDeliveryMethodId() {
        return this.deliveryMethodId;
    }

    public void setDeliveryMethodId(final String deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}