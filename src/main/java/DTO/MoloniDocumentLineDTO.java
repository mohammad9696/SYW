package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MoloniDocumentLineDTO {

    private String sku;
    private String productName;
    private double sellPrice;
    private double costPrice;
    private int quantity;
    private double marginPerUnit;
    private double profit;

    public String getSku() {
        return this.sku;
    }

    public void setSku(final String sku) {
        this.sku = sku;
    }

    public double getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(final double costPrice) {
        this.costPrice = costPrice;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public double getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(final double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public double getMarginPerUnit() {
        return this.marginPerUnit;
    }

    public void setMarginPerUnit(final double marginPerUnit) {
        this.marginPerUnit = marginPerUnit;
    }

    public double getProfit() {
        return this.profit;
    }

    public void setProfit(final double profit) {
        this.profit = profit;
    }
}
