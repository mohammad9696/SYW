package DTO;

import Utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DraftOrderDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("draft_order")
    private OrderDTO draftOrder;

    public OrderDTO getDraftOrder() {
        return draftOrder;
    }

    public void setDraftOrder(OrderDTO draftOrder) {
        this.draftOrder = draftOrder;
    }

    public DraftOrderDTO(OrderDTO draftOrder) {
        this.draftOrder = draftOrder;
    }

    public DraftOrderDTO() {
    }
}
