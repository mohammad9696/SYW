package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("financial_status")
    private String financialStatus;

    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;

    @JsonProperty("name")
    private String orderNumber;

    @JsonProperty("order_status_url")
    private String orderStatusUrl;

    @JsonProperty("total_price")
    private String totalPrice;

    @JsonProperty("total_weight")
    private String totalWeight;

    @JsonProperty("billing_address")
    private OrderAddressDTO billingAdress;

    @JsonProperty("line_items")
    private List<OrderLineDTO> lineItems;

    @JsonProperty("shipping_address")
    private OrderAddressDTO shippingAddress;

    @JsonProperty("shipping_lines")
    private List<OrderShippingDTO> shippingLine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatusUrl() {
        return orderStatusUrl;
    }

    public void setOrderStatusUrl(String orderStatusUrl) {
        this.orderStatusUrl = orderStatusUrl;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public OrderAddressDTO getBillingAdress() {
        return billingAdress;
    }

    public void setBillingAdress(OrderAddressDTO billingAdress) {
        this.billingAdress = billingAdress;
    }

    public List<OrderLineDTO> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<OrderLineDTO> lineItems) {
        this.lineItems = lineItems;
    }

    public OrderAddressDTO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(OrderAddressDTO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderShippingDTO> getShippingLine() {
        return shippingLine;
    }

    public void setShippingLine(List<OrderShippingDTO> shippingLine) {
        this.shippingLine = shippingLine;
    }

    @Override
    public String toString() {
        int spacingName = 30;
        int spacingCity = 15;
        String fullName = shippingAddress.getFirstName() + " "+ shippingAddress.getLastName() ;
        int lenghtToAdd = 0;
        if (fullName.length() < spacingName){
            lenghtToAdd = spacingName - fullName.length();
        }
        String name = (String) fullName.subSequence(0, fullName.length() > spacingName ? spacingName : fullName.length());
        for (int i = 0; i < lenghtToAdd; i++){
            name = name + " ";
        }

        lenghtToAdd = 0;
        if (shippingAddress.getCity().length() < spacingCity){
            lenghtToAdd = spacingCity - shippingAddress.getCity().length();
        }
        String city = (String) shippingAddress.getCity().subSequence(0, shippingAddress.getCity().length() > spacingCity ? spacingCity : shippingAddress.getCity().length());
        for (int i = 0; i < lenghtToAdd; i++){
            city = city + " ";
        }
        return
                 orderNumber + "   " + name + " " +
                         totalPrice + "   " + shippingAddress.getPostalCode() + "   " +  city +
                " " + Double.parseDouble(totalWeight)/1000 + " Kg";
    }
}
