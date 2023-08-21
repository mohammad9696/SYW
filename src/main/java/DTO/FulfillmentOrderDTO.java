package DTO;

import Constants.ConstantsEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FulfillmentOrderDTO {


    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long fulfillmentOrderId;

    @JsonProperty("order_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long orderId;

    @JsonProperty("line_items_by_fulfillment_order")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FulfillmentOrderId[] lineItemsByFulfillmentOrder;

    @JsonProperty("tracking_info")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FulfillmentTrackingInfo trackingInfo;

    @JsonProperty("location_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String locationId = ConstantsEnum.SHOPIFY_MAIN_LOCATION_ID.getConstantValue().toString();

    public FulfillmentOrderDTO(Long fulfillmentOrderId, String trackingNumber, String trackingUrl) {
        FulfillmentOrderId foi = new FulfillmentOrderId(fulfillmentOrderId);
        this.lineItemsByFulfillmentOrder = foi.getOrdersToFulfillIdList(fulfillmentOrderId);
        FulfillmentTrackingInfo tracking = new FulfillmentTrackingInfo(trackingNumber, trackingUrl);
        this.trackingInfo = tracking;
    }

    public FulfillmentOrderId[] getLineItemsByFulfillmentOrder() {
        return this.lineItemsByFulfillmentOrder;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class FulfillmentOrderId {
        @JsonProperty("fulfillment_order_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Long fulfillmentOrderId;

        public FulfillmentOrderId(final Long fulfillmentOrderId) {
            this.fulfillmentOrderId = fulfillmentOrderId;
        }
        public FulfillmentOrderId[] getOrdersToFulfillIdList (Long fulfillmentOrderId){
            FulfillmentOrderId[] f= new FulfillmentOrderId[1];
            f[0] = new FulfillmentOrderId(fulfillmentOrderId);
            return f;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class FulfillmentTrackingInfo {
        @JsonProperty("number")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String number;
        @JsonProperty("url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String url;

        public FulfillmentTrackingInfo(final String number, final String url) {
            this.number = number;
            this.url = url;
        }
    }

    public Long getFulfillmentOrderId() {
        return this.fulfillmentOrderId;
    }

    public void setFulfillmentOrderId(final Long fulfillmentOrderId) {
        this.fulfillmentOrderId = fulfillmentOrderId;
    }


    public void setLineItemsByFulfillmentOrder(final FulfillmentOrderId[] lineItemsByFulfillmentOrder) {
        this.lineItemsByFulfillmentOrder = lineItemsByFulfillmentOrder;
    }

    public FulfillmentTrackingInfo getTrackingInfo() {
        return this.trackingInfo;
    }

    public void setTrackingInfo(final FulfillmentTrackingInfo trackingInfo) {
        this.trackingInfo = trackingInfo;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public FulfillmentOrderDTO() {
    }
}

