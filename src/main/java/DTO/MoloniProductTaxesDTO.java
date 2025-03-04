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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value")
    private Integer valueAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tax")
    private Tax tax;

    public Tax getTax() {
        return this.tax;
    }

    public void setTax(final Tax tax) {
        this.tax = tax;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Tax {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("value")
        private Integer value;

        public Tax() {
        }

        public Integer getValue() {
            return this.value;
        }

        public void setValue(final Integer value) {
            this.value = value;
        }
    }

    public MoloniProductTaxesDTO(Long taxId, Long cumulative, Long order) {
        this.taxId = taxId;
        this.cumulative = cumulative;
        this.order = order;
    }

    public MoloniProductTaxesDTO() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getValueAmount() {
        return this.valueAmount;
    }

    public void setValueAmount(final Integer valueAmount) {
        this.valueAmount = valueAmount;
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

    public Double taxPercentageValue(){
        return tax.value*.01;
    }
}