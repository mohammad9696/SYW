package DTO;

import Constants.ConstantsEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XlsPorductDataPositionsDTO {

    private Integer ean;
    private Integer sku;
    private Integer name;
    private List<Integer> competitorPositions = new ArrayList<>();
    private Integer smartifyPrice;
    private Integer min;
    private Integer max;

    public Integer getEan() {
        return this.ean;
    }

    public void setEan(final Integer ean) {
        this.ean = ean;
    }

    public Integer getSku() {
        return this.sku;
    }

    public void setSku(final Integer sku) {
        this.sku = sku;
    }

    public Integer getName() {
        return this.name;
    }

    public void setName(final Integer name) {
        this.name = name;
    }

    public Integer getMin() {
        return this.min;
    }

    public void setMin(final Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return this.max;
    }

    public void setMax(final Integer max) {
        this.max = max;
    }

    public Integer getSmartifyPrice() {
        return this.smartifyPrice;
    }

    public void setSmartifyPrice(final Integer smartifyPrice) {
        this.smartifyPrice = smartifyPrice;
    }

    public List<Integer> getCompetitorPositions() {
        return this.competitorPositions;
    }

    public void setCompetitorPositions(final List<Integer> competitorPositions) {
        this.competitorPositions = competitorPositions;
    }
}
