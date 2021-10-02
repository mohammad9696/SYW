package DTO;

import Constants.ConstantsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FulfillmentDTO {

    @JsonProperty("location_id")
    private final String locationId = ConstantsEnum.SHOPIFY_MAIN_LOCATION_ID.getConstantValue().toString();

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("tracking_url")
    private String trackingUrl;

    @JsonProperty("line_items")
    private List<FulfillmentLineItemDTO> lineItems;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @JsonIgnore
    private String requestUrl;

    public String getLocationId() {
        return locationId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public List<FulfillmentLineItemDTO> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<FulfillmentLineItemDTO> lineItems) {
        this.lineItems = lineItems;
    }

    public FulfillmentDTO(String trackingNumber, String trackingUrl, OrderDTO order) {
        lineItems = new ArrayList<FulfillmentLineItemDTO>();
        for (OrderLineDTO dto :order.getLineItems()){
            if (dto.getFulfillableQuantity()>0){
                lineItems.add(new FulfillmentLineItemDTO(dto.getId()));
            }
        }
        this.trackingNumber = trackingNumber;
        this.trackingUrl = trackingUrl;
        requestUrl = ConstantsEnum.FULFILLMENT_REQUEST_URL_PREFIX.getConstantValue()+
                order.getId()+
                ConstantsEnum.FULFILLMENT_REQUEST_URL_SUFIX.getConstantValue();
    }
}
