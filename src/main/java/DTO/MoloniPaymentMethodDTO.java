package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniPaymentMethodDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payment_method_name")
    private String paymentMethodName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value")
    private Double value;

    @JsonProperty("date")
    private String date;

    public String getPaymentMethodId() {
        return this.paymentMethodId;
    }

    public void setPaymentMethodId(final String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPaymentMethodName() {
        return this.paymentMethodName;
    }

    public void setPaymentMethodName(final String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(final Double value) {
        this.value = value;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
