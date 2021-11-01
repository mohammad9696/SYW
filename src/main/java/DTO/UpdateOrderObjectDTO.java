package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOrderObjectDTO {
    @JsonProperty("order")
    private UpdateOrderDTO order;

    public UpdateOrderObjectDTO(String orderId, String note) {
        UpdateOrderDTO order = new UpdateOrderDTO(orderId, note);
        this.order = order;
    }
}
