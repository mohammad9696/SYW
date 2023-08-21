package DTO;

import Constants.ConstantsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FulfillmentDTO {


    @JsonIgnoreProperties(ignoreUnknown = true)
    private class FulfillmentOrderId {
        @JsonProperty("fulfillment_order_id")
        private Long fulfillmentOrderId;

        public FulfillmentOrderId(final Long fulfillmentOrderId) {
            this.fulfillmentOrderId = fulfillmentOrderId;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class FulfillmentTrackingInfo {
        @JsonProperty("number")
        private String number;
        @JsonProperty("url")
        private String url;
    }

    @JsonProperty("line_items_by_fulfillment_order")
    private FulfillmentOrderId[] lineItemsByFulfillmentOrder;

    @JsonProperty("tracking_info")
    private FulfillmentTrackingInfo trackingInfo;

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

}

