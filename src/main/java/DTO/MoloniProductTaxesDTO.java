package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniProductTaxesDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tax_id")
    private Long taxId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cumulative")
    private Long cumulative;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order")
    private Long order;

    public MoloniProductTaxesDTO(Long taxId, Long cumulative, Long order) {
        this.taxId = taxId;
        this.cumulative = cumulative;
        this.order = order;
    }

    public MoloniProductTaxesDTO() {
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getCumulative() {
        return cumulative;
    }

    public void setCumulative(Long cumulative) {
        this.cumulative = cumulative;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}