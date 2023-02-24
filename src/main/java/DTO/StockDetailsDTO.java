package DTO;

public class StockDetailsDTO {
    private String sku;
    private Integer moloniStock;
    private Integer shopifyStock;
    private Integer shopifyPaidReservations;
    private Integer shopifyUnpaidReservations;

    public StockDetailsDTO(String sku) {
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getMoloniStock() {
        return moloniStock;
    }

    public void setMoloniStock(Integer moloniStock) {
        this.moloniStock = moloniStock;
    }

    public Integer getShopifyStock() {
        return shopifyStock;
    }

    public void setShopifyStock(Integer shopifyStock) {
        this.shopifyStock = shopifyStock;
    }

    public Integer getShopifyPaidReservations() {
        return shopifyPaidReservations;
    }

    public void setShopifyPaidReservations(Integer shopifyPaidReservations) {
        this.shopifyPaidReservations = shopifyPaidReservations;
    }

    public Integer getShopifyUnpaidReservations() {
        return shopifyUnpaidReservations;
    }

    public void setShopifyUnpaidReservations(Integer shopifyUnpaidReservations) {
        this.shopifyUnpaidReservations = shopifyUnpaidReservations;
    }
}
