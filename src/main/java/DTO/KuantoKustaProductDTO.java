package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KuantoKustaProductDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("id")
    private String id;

    @JsonProperty("offerSku")
    private String offerSku;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferSku() {
        return offerSku;
    }

    public void setOfferSku(String offerSku) {
        this.offerSku = offerSku;
    }
}
