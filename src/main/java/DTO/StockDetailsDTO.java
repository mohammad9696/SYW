package DTO;

import java.time.LocalDateTime;
import java.util.List;

public class StockDetailsDTO implements  Comparable<StockDetailsDTO> {
    private String sku;
    private String ean;
    private String productName;
    private Integer moloniStock;
    private Integer shopifyStock;
    private Integer shopifyPaidReservations;
    private Integer shopifyUnpaidReservations;
    private SalesTimePeriodDTO sevenDaysOrFirstPeriod;
    private SalesTimePeriodDTO thirtyDaysOrSecondPeriod;
    private SalesTimePeriodDTO ninetyDaysOrThirdPeriod;
    private Double avgSalesDays;
    private Double stockDays;
    private Integer productActiveForDays;
    private MoloniProductStocksDTO[] stockMovements;
    private List<SupplierOrderedLineDate> supplierOrderedLineDates;
    private LocalDateTime lastSale;
    private Boolean isContinueToSellOutOfStock;

    public Integer getProductActiveForDays() {
        return productActiveForDays;
    }

    public void setProductActiveForDays(Integer productActiveForDays) {
        this.productActiveForDays = productActiveForDays;
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

    public SalesTimePeriodDTO getSevenDaysOrFirstPeriod() {

        return sevenDaysOrFirstPeriod;
    }

    public SalesTimePeriodDTO getThirtyDaysOrSecondPeriod() {
        return thirtyDaysOrSecondPeriod;
    }

    public SalesTimePeriodDTO getNinetyDaysOrThirdPeriod() {
        return ninetyDaysOrThirdPeriod;
    }

    public StockDetailsDTO(String sku) {
        this.sku = sku;
        this.sevenDaysOrFirstPeriod = new SalesTimePeriodDTO();
        this.thirtyDaysOrSecondPeriod = new SalesTimePeriodDTO();
        this.ninetyDaysOrThirdPeriod = new SalesTimePeriodDTO();
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


    public void setSevenDaysOrFirstPeriod(SalesTimePeriodDTO sevenDaysOrFirstPeriod) {
        this.sevenDaysOrFirstPeriod = sevenDaysOrFirstPeriod;
    }

    public void setThirtyDaysOrSecondPeriod(SalesTimePeriodDTO thirtyDaysOrSecondPeriod) {
        this.thirtyDaysOrSecondPeriod = thirtyDaysOrSecondPeriod;
    }

    public void setNinetyDaysOrThirdPeriod(SalesTimePeriodDTO ninetyDaysOrThirdPeriod) {
        this.ninetyDaysOrThirdPeriod = ninetyDaysOrThirdPeriod;
    }

    @Override
    public String toString() {
        return "StockDetailsDTO{" +
                "sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", moloniStock=" + moloniStock +
                ", shopifyStock=" + shopifyStock +
                ", shopifyPaidReservations=" + shopifyPaidReservations +
                ", shopifyUnpaidReservations=" + shopifyUnpaidReservations +
                ", sevenDaysOrFirstPeriod=" + sevenDaysOrFirstPeriod +
                ", thirtyDaysOrSecondPeriod=" + thirtyDaysOrSecondPeriod +
                ", ninetyDaysOrThirdPeriod=" + ninetyDaysOrThirdPeriod +
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
