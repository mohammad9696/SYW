package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopifyTaxLineDTO {

    @JsonProperty("channel_liable")
    private Boolean channelLiable;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_set")
    private PriceSetDTO priceSet;

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("title")
    private String title;

    public Boolean getChannelLiable() {
        return this.channelLiable;
    }

    public void setChannelLiable(final Boolean channelLiable) {
        this.channelLiable = channelLiable;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public PriceSetDTO getPriceSet() {
        return this.priceSet;
    }

    public void setPriceSet(final PriceSetDTO priceSet) {
        this.priceSet = priceSet;
    }

    public Double getRate() {
        return this.rate;
    }

    public void setRate(final Double rate) {
        this.rate = rate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PriceSetDTO {
        @JsonProperty("shop_money")
        private MoneyDTO shopMoney;

        @JsonProperty("presentment_money")
        private MoneyDTO presentmentMoney;

        public MoneyDTO getShopMoney() {
            return this.shopMoney;
        }

        public void setShopMoney(final MoneyDTO shopMoney) {
            this.shopMoney = shopMoney;
        }

        public MoneyDTO getPresentmentMoney() {
            return this.presentmentMoney;
        }

        public void setPresentmentMoney(final MoneyDTO presentmentMoney) {
            this.presentmentMoney = presentmentMoney;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MoneyDTO {
        @JsonProperty("amount")
        private String amount;

        @JsonProperty("currency_code")
        private String currencyCode;

        public String getAmount() {
            return this.amount;
        }

        public void setAmount(final String amount) {
            this.amount = amount;
        }

        public String getCurrencyCode() {
            return this.currencyCode;
        }

        public void setCurrencyCode(final String currencyCode) {
            this.currencyCode = currencyCode;
        }
    }
}

