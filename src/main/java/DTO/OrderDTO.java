package DTO;

import Utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("financial_status")
    private String financialStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String orderNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order_status_url")
    private String orderStatusUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total_price")
    private String totalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total_weight")
    private String totalWeight;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billing_address")
    private OrderAddressDTO billingAdress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("line_items")
    private List<OrderLineDTO> lineItems;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("shipping_address")
    private OrderAddressDTO shippingAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("shipping_lines")
    private List<OrderShippingDTO> shippingLine;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("note")
    private String note;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phone")
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("inventory_behaviour")
    private String inventoryBehaviour = "decrement_obeying_policy";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("buyer_accepts_marketing")
    private boolean buyer_accepts_marketing = true;



    public String getInventoryBehaviour() {
        return inventoryBehaviour;
    }

    public void setInventoryBehaviour(String inventoryBehaviour) {
        this.inventoryBehaviour = inventoryBehaviour;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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

    public OrderDTO() {
    }

    @Override
    public String toString() {
        String fullName = "";
        String shippingMethod = "";
        String city = "";
        String notes = "";
        String postalCode = "";
        if (shippingAddress != null ){
            fullName = Utils.normalizeStringLenght(30, shippingAddress.getFirstName() + " "+ shippingAddress.getLastName());
            city = Utils.normalizeStringLenght(15, shippingAddress.getCity());
            postalCode = Utils.normalizeStringLenght(8, shippingAddress.getPostalCode());
        }
        if (!shippingMethod.isEmpty()){
            shippingMethod = Utils.normalizeStringLenght(20, shippingLine.get(0).getShippingCode());
        }
        notes = Utils.normalizeStringLenght(30, note);

        return
                 orderNumber + "   " + fullName + " " +
                         totalPrice + "   " + postalCode + "   " +  city +
                " " + (totalWeight != null ? Double.parseDouble(totalWeight)/1000 : "") + " Kg  " + shippingMethod + "        " + notes;
    }

}
