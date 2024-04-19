package DTO;

import Utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCompareDataDTO implements Comparable<ProductCompareDataDTO>{

    private String ean;
    private String sku;
    private String productName;
    private List<CompetitorCompareDataDTO> competitors;
    private Double smartifyPrice;
    private Double minPrice;
    private Double maxPrice;
    private Double costPrice;
    private StockDetailsDTO stockDetailsDTO;
    private Double costAmountInStock;
    private Double marginWithMin;
    private Double marginWithCurrent;

    public List<CompetitorCompareDataDTO> getCompetitors() {
        return this.competitors;
    }

    public Double getCostPrice() {
        return this.costPrice;
    }

    public StockDetailsDTO getStockDetailsDTO() {
        return this.stockDetailsDTO;
    }

    public Double getCostAmountInStock() {
        return this.costAmountInStock;
    }

    public void setCostAmountInStock(final Double costAmountInStock) {
        this.costAmountInStock = costAmountInStock;
    }

    public ProductCompareDataDTO(final String ean, final String sku, final String productName, final List<CompetitorCompareDataDTO> competitors, final Double smartifyPrice, final Double minPrice, final Double maxPrice) {
        this.ean = ean;
        this.sku = sku;
        this.productName = productName;
        this.smartifyPrice = smartifyPrice;
        this.competitors = competitors;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        costAmountInStock = stockDetailsDTO.getMoloniStock() * costPrice;
        String competitorsString = Utils.normalizeStringLenght(8, "Smartify") +  " " +Utils.normalizeStringLenght(5, smartifyPrice+"") + "€   " ;
        for (CompetitorCompareDataDTO i : competitors){
            competitorsString =competitorsString + Utils.normalizeStringLenght(10, i.getCompetitorName()) +  " " +Utils.normalizeStringLenght(5, i.getComeptitorPrice()+"") + " €  ";
        }

        return Utils.normalizeStringLenght(20,sku) + "   " + Utils.normalizeStringLenght(15,ean) + "   " + Utils.normalizeStringLenght(50,productName) + "  " + competitorsString + "\n"+
                "CostPrice: " +  Utils.normalizeStringLenght(5, costPrice+"") + "€   Stock: " + Utils.normalizeStringLenght(3, stockDetailsDTO.getMoloniStock()+"") +  "   Reserved: " + Utils.normalizeStringLenght(3, stockDetailsDTO.getShopifyPaidReservations()+"") + "     SellWithoutStock: " +  Utils.normalizeStringLenght(5,stockDetailsDTO.getContinueToSellOutOfStock()+"") +
                "    Amount in stock: " + Utils.normalizeStringLenght(5, costAmountInStock+"") + "€    Current Margin: " + Utils.normalizeStringLenght(3,  marginWithCurrent*100+"") + "%       Min Margin: " + Utils.normalizeStringLenght(3,  marginWithMin*100+"") + "%\n" + "Last sell was  " + stockDetailsDTO.getLastSale() + " ago\n" ;

    }

    public String getSku() {
        return this.sku;
    }

    public void setCostPrice(final Double costPrice) {
        this.costPrice = costPrice;
    }

    public void setStockDetailsDTO(final StockDetailsDTO stockDetailsDTO) {
        this.stockDetailsDTO = stockDetailsDTO;
    }

    @Override
    public int compareTo(ProductCompareDataDTO o) {
        try {
            if (costAmountInStock == null){
                costAmountInStock = stockDetailsDTO.getMoloniStock() * costPrice;
            }
            if (o.costAmountInStock == null) {
                o.setCostAmountInStock(o.getCostPrice()* o.getStockDetailsDTO().getMoloniStock());
            }

            if (costAmountInStock > o.costAmountInStock ) {
                return -1;
            } else if (costAmountInStock < o.costAmountInStock){
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e){
            return -1;
        }

    }

    public Double getMarginWithMin() {
        return this.marginWithMin;
    }

    public void setMarginWithMin(final Double marginWithMin) {
        this.marginWithMin = marginWithMin;
    }

    public Double getMarginWithCurrent() {
        return this.marginWithCurrent;
    }

    public void setMarginWithCurrent(final Double marginWithCurrent) {
        this.marginWithCurrent = marginWithCurrent;
    }

    public Double getMinPrice() {
        return this.minPrice;
    }

    public String getEan() {
        return this.ean;
    }

    public Double getMaxPrice() {
        return this.maxPrice;
    }

    public String getProductName() {
        return this.productName;
    }

    public Double getSmartifyPrice() {
        return this.smartifyPrice;
    }
}
