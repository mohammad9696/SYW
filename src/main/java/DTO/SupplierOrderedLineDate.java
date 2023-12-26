package DTO;


import Utils.Utils;

public class SupplierOrderedLineDate {

    private String supplier;
    private String sku;
    private String description;
    private String dateOrdered;
    private String dateExpected;
    private String purchasePrice;
    private String quantity;

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(final String supplier) {
        this.supplier = supplier;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(final String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDateOrdered() {
        return this.dateOrdered;
    }

    public void setDateOrdered(final String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getDateExpected() {
        return this.dateExpected;
    }

    public void setDateExpected(final String dateExpected) {
        this.dateExpected = dateExpected;
    }

    public String getPurchasePrice() {
        return this.purchasePrice;
    }

    public void setPurchasePrice(final String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Arriving " + Utils.normalizeStringLenght(10, sku) + "on " + Utils.normalizeStringLenght(10, dateExpected) +
                "     " + Utils.normalizeStringLenght(4, quantity) +" units" + "   from  " + Utils.normalizeStringLenght(25, supplier) +
                "      at " + Utils.normalizeStringLenght(6, purchasePrice) +" Ordered on: " + Utils.normalizeStringLenght(10,dateOrdered);
    }
}
