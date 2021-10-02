package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FulfillmentObject {

    @JsonProperty("fulfillment")
    private FulfillmentDTO fulfillmentDTO;

    public FulfillmentDTO getFulfillmentDTO() {
        return fulfillmentDTO;
    }

    public void setFulfillmentDTO(FulfillmentDTO fulfillmentDTO) {
        this.fulfillmentDTO = fulfillmentDTO;
    }

    public FulfillmentObject(FulfillmentDTO fulfillmentDTO) {
        this.fulfillmentDTO = fulfillmentDTO;
    }
}
