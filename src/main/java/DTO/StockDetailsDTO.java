package DTO;

import java.time.LocalDateTime;
import java.util.List;

public class StockDetailsDTO implements  Comparable<StockDetailsDTO> {
    private String sku;
    private String ean;
    private String productName;
    private Integer availableStock;
    private Integer shopifyStock;
    private Integer shopifyPaidReservations;
    private Integer shopifyUnpaidReservations;
    private Integer moloniPurchaseOrders;
    private Integer moloniBillsOfLading;
    private SalesTimePeriodDTO firstPeriod;
    private SalesTimePeriodDTO secondPeriod;
    private SalesTimePeriodDTO thirdPeriod;
    private Double avgSalesDays;
    private Double stockDays;
    private Integer productActiveForDays;
    private MoloniProductStocksDTO[] stockMovements;
    private List<SupplierOrderedLineDate> supplierOrderedLineDates;
    private LocalDateTime lastSale;
    private Boolean isContinueToSellOutOfStock;
    private Integer recommendedPurchaseUnits;
    private Integer purchaseFrequencyDays;

    public Integer getProductActiveForDays() {
        return productActiveForDays;
    }

    public void setProductActiveForDays(Integer productActiveForDays) {
        this.productActiveForDays = productActiveForDays;
    }

    public Integer getMoloniPurchaseOrders() {
        return this.moloniPurchaseOrders;
    }

    public void setMoloniPurchaseOrders(final Integer moloniPurchaseOrders) {
        this.moloniPurchaseOrders = moloniPurchaseOrders;
    }

    public MoloniProductStocksDTO[] getStockMovements() {
        return this.stockMovements;
    }

    public void setStockMovements(final MoloniProductStocksDTO[] stockMovements) {
        this.stockMovements = stockMovements;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getStockDays() {
        return stockDays;
    }

    public Double getAvgSalesDays() {
        return avgSalesDays;
    }

    public void setAvgSalesDays(Double avgSalesDays) {
        this.avgSalesDays = avgSalesDays;
    }

    public void setStockDays(Double stockDays) {
        this.stockDays = stockDays;
    }

    public SalesTimePeriodDTO getFirstPeriod() {

        return firstPeriod;
    }

    public Integer getMoloniBillsOfLading() {
        return this.moloniBillsOfLading;
    }

    public void setMoloniBillsOfLading(final Integer moloniBillsOfLading) {
        this.moloniBillsOfLading = moloniBillsOfLading;
    }

    public SalesTimePeriodDTO getSecondPeriod() {
        return secondPeriod;
    }

    public SalesTimePeriodDTO getThirdPeriod() {
        return thirdPeriod;
    }

    public StockDetailsDTO(String sku) {
        this.sku = sku;
        this.firstPeriod = new SalesTimePeriodDTO();
        this.secondPeriod = new SalesTimePeriodDTO();
        this.thirdPeriod = new SalesTimePeriodDTO();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getEan() {
        return this.ean;
    }

    public void setEan(final String ean) {
        this.ean = ean;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getPurchaseFrequencyDays() {
        return this.purchaseFrequencyDays;
    }

    public void setPurchaseFrequencyDays(final Integer purchaseFrequencyDays) {
        this.purchaseFrequencyDays = purchaseFrequencyDays;
    }

    public Integer getRecommendedPurchaseUnits() {
        return this.recommendedPurchaseUnits;
    }

    public void setRecommendedPurchaseUnits(final Integer recommendedPurchaseUnits) {
        this.recommendedPurchaseUnits = recommendedPurchaseUnits;
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


    public void setFirstPeriod(SalesTimePeriodDTO firstPeriod) {
        this.firstPeriod = firstPeriod;
    }

    public void setSecondPeriod(SalesTimePeriodDTO secondPeriod) {
        this.secondPeriod = secondPeriod;
    }

    public void setThirdPeriod(SalesTimePeriodDTO thirdPeriod) {
        this.thirdPeriod = thirdPeriod;
    }

    @Override
    public String toString() {
        return "StockDetailsDTO{" +
                "sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", moloniStock=" + availableStock +
                ", shopifyStock=" + shopifyStock +
                ", shopifyPaidReservations=" + shopifyPaidReservations +
                ", shopifyUnpaidReservations=" + shopifyUnpaidReservations +
                ", sevenDaysOrFirstPeriod=" + firstPeriod +
                ", thirtyDaysOrSecondPeriod=" + secondPeriod +
                ", ninetyDaysOrThirdPeriod=" + thirdPeriod +
                ", avgSalesDays=" + avgSalesDays +
                ", stockDays=" + stockDays +
                ", productActiveForDays=" + productActiveForDays +
                '}';
    }

    @Override
    public int compareTo(StockDetailsDTO o) {
        try {
            if (getStockDays() > o.getStockDays() ) {
                return -1;
            } else if (getStockDays() < o.getStockDays()){
                return 1;
            } else {
                if (avgSalesDays > o.getAvgSalesDays()){
                    return 1;
                } else if (avgSalesDays < o.getAvgSalesDays()){
                    return -1;
                }
                return 0;
            }
        } catch (Exception e){
            return 0;
        }

    }

    public LocalDateTime getLastSale() {
        return this.lastSale;
    }

    public void setLastSale(final LocalDateTime lastSale) {
        this.lastSale = lastSale;
    }

    public List<SupplierOrderedLineDate> getSupplierOrderedLineDates() {
        return this.supplierOrderedLineDates;
    }

    public void setSupplierOrderedLineDates(final List<SupplierOrderedLineDate> supplierOrderedLineDates) {
        this.supplierOrderedLineDates = supplierOrderedLineDates;
    }

    public Boolean getContinueToSellOutOfStock() {
        return this.isContinueToSellOutOfStock;
    }

    public void setContinueToSellOutOfStock(final Boolean continueToSellOutOfStock) {
        this.isContinueToSellOutOfStock = continueToSellOutOfStock;
    }
}
