package DTO;

public class StockDetailsDTO implements  Comparable<StockDetailsDTO> {
    private String sku;
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
        if (getStockDays() > o.getStockDays() ) {
            return 1;
        } else if (getStockDays() < o.getStockDays()){
            return -1;
        } else {
            return 0;
        }
    }
}
