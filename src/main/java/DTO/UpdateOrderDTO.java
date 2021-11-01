package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOrderDTO {
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("note")
    private String note;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public UpdateOrderDTO(String orderId, String note) {
        this.orderId = orderId;
        this.note = note;
    }
}
