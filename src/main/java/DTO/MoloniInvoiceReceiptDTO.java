package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniInvoiceReceiptDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_id")
    private String documentSetId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("customer_id")
    private String customerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("company_id")
    private String companyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("date")
    private String date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("entity_name")
    private String entityName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("entity_vat")
    private String entityVat;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("entity_address")
    private String entityAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("entity_city")
    private String entityCity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("entity_zip_code")
    private String entityZipCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("entity_country")
    private String entityCountry;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("entity_country_id")
    private Integer entityCountryId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("notes")
    private String notes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("products")
    private List<MoloniProductDTO> products;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payments")
    private List<Payment> payments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_type_id")
    private Integer documentTypeId;

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }

    public static class Tax {
        @JsonProperty("tax_id")
        private Integer taxId;

        @JsonProperty("value")
        private Double value;

        // Getters and Setters
    }

    public static class Payment {
        @JsonProperty("payment_method_id")
        private String paymentMethodId;

        @JsonProperty("value")
        private Double value;

        @JsonProperty("date")
        private String date;

        // Getters and Setters

        public String getPaymentMethodId() {
            return this.paymentMethodId;
        }

        public void setPaymentMethodId(final String paymentMethodId) {
            this.paymentMethodId = paymentMethodId;
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

    @JsonProperty("status")
    private Integer status;

    public String getDocumentSetId() {
        return this.documentSetId;
    }

    public void setDocumentSetId(final String documentSetId) {
        this.documentSetId = documentSetId;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(final String entityName) {
        this.entityName = entityName;
    }

    public String getEntityVat() {
        return this.entityVat;
    }

    public void setEntityVat(final String entityVat) {
        this.entityVat = entityVat;
    }

    public String getEntityAddress() {
        return this.entityAddress;
    }

    public void setEntityAddress(final String entityAddress) {
        this.entityAddress = entityAddress;
    }

    public String getEntityCity() {
        return this.entityCity;
    }

    public void setEntityCity(final String entityCity) {
        this.entityCity = entityCity;
    }

    public String getEntityZipCode() {
        return this.entityZipCode;
    }

    public void setEntityZipCode(final String entityZipCode) {
        this.entityZipCode = entityZipCode;
    }

    public String getEntityCountry() {
        return this.entityCountry;
    }

    public void setEntityCountry(final String entityCountry) {
        this.entityCountry = entityCountry;
    }

    public Integer getEntityCountryId() {
        return this.entityCountryId;
    }

    public void setEntityCountryId(final Integer entityCountryId) {
        this.entityCountryId = entityCountryId;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public List<MoloniProductDTO> getProducts() {
        return this.products;
    }

    public void setProducts(final List<MoloniProductDTO> products) {
        this.products = products;
    }

    public List<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(final List<Payment> payments) {
        this.payments = payments;
    }

    public Integer getDocumentTypeId() {
        return this.documentTypeId;
    }

    public void setDocumentTypeId(final Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    // Getter e Setter
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}