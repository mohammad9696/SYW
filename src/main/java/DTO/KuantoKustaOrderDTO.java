package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KuantoKustaOrderDTO {

    @JsonProperty("orderId")
    private String kuantoKustaOrderId;

    @JsonProperty("deliveryAddress")
    private KuantoKustaOrderAddressDTO shippingAddress;

    @JsonProperty("billingAddress")
    private KuantoKustaOrderAddressDTO billingAddress;

    @JsonProperty("products")
    private List<KuantoKustaProductDTO> products;

    @JsonProperty("shippingPrice")
    private Double shippingPrice;

    @JsonProperty("orderState")
    private String orderStatus;

    @JsonProperty("email")
    private String email;

    @JsonProperty("referring_site")
    private String referringSite;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferringSite() {
        return referringSite;
    }

    public void setReferringSite(String referringSite) {
        this.referringSite = referringSite;
    }

    public String getKuantoKustaOrderId() {
        return kuantoKustaOrderId;
    }

    public void setKuantoKustaOrderId(String kuantoKustaOrderId) {
        this.kuantoKustaOrderId = kuantoKustaOrderId;
    }

    public KuantoKustaOrderAddressDTO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(KuantoKustaOrderAddressDTO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public KuantoKustaOrderAddressDTO getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(KuantoKustaOrderAddressDTO billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<KuantoKustaProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<KuantoKustaProductDTO> products) {
        this.products = products;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
