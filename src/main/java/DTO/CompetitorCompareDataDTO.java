package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetitorCompareDataDTO {

    private String competitorName;
    private Double comeptitorPrice;

    public CompetitorCompareDataDTO(final String competitorName, final Double comeptitorPrice) {
        this.competitorName = competitorName;
        this.comeptitorPrice = comeptitorPrice;
    }

    public String getCompetitorName() {
        return this.competitorName;
    }

    public void setCompetitorName(final String competitorName) {
        this.competitorName = competitorName;
    }

    public Double getComeptitorPrice() {
        return this.comeptitorPrice;
    }

    public void setComeptitorPrice(final Double comeptitorPrice) {
        this.comeptitorPrice = comeptitorPrice;
    }
}
