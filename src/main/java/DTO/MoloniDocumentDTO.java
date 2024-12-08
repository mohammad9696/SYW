package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniDocumentDTO {

    @JsonProperty("company_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyId;

    @JsonProperty("customer_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String customerId;

    @JsonProperty("document_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentId;

    @JsonProperty("document_type_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentTypeId;

    @JsonProperty("our_reference")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String internalOrderNumber;

    @JsonProperty("your_reference")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientOrderNumber;

    @JsonProperty("number")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentNumber;

    @JsonProperty("date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String date;

    @JsonProperty("expiration_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String expirationDate;

    @JsonProperty("lastmodified")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastModified;

    @JsonProperty("entity_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String entityName;

    @JsonProperty("document_set_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentSetId;

    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pdfDownloadUrl;

    @JsonProperty("document_set_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentSetName;

    @JsonProperty("net_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double documentValueEuros;

    @JsonProperty("reconciled_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double documentReconciledValueEuros;

    @JsonProperty("valid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean valid;

    @JsonProperty("products")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MoloniProductDTO[] productDTOS;

    @JsonProperty("delivery_destination_address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryDestinationAddress;

    @JsonProperty("delivery_destination_city")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryDestinationCity;

    @JsonProperty("delivery_destination_zip_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryDestinationZipCode;

    @JsonProperty("delivery_destination_country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deliveryDestinationCountryId;

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(final Boolean valid) {
        this.valid = valid;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public String getDeliveryDestinationAddress() {
        return this.deliveryDestinationAddress;
    }

    public void setDeliveryDestinationAddress(final String deliveryDestinationAddress) {
        this.deliveryDestinationAddress = deliveryDestinationAddress;
    }

    public String getDeliveryDestinationCity() {
        return this.deliveryDestinationCity;
    }

    public void setDeliveryDestinationCity(final String deliveryDestinationCity) {
        this.deliveryDestinationCity = deliveryDestinationCity;
    }

    public String getDeliveryDestinationZipCode() {
        return this.deliveryDestinationZipCode;
    }

    public void setDeliveryDestinationZipCode(final String deliveryDestinationZipCode) {
        this.deliveryDestinationZipCode = deliveryDestinationZipCode;
    }

    public Integer getDeliveryDestinationCountryId() {
        return this.deliveryDestinationCountryId;
    }

    public void setDeliveryDestinationCountryId(final Integer deliveryDestinationCountryId) {
        this.deliveryDestinationCountryId = deliveryDestinationCountryId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public String getDocumentSetId() {
        return this.documentSetId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public void setExpirationDate(final String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setDocumentSetId(final String documentSetId) {
        this.documentSetId = documentSetId;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(final String lastModified) {
        this.lastModified = lastModified;
    }

    public Double getDocumentReconciledValueEuros() {
        return this.documentReconciledValueEuros;
    }

    public void setDocumentReconciledValueEuros(final Double documentReconciledValueEuros) {
        this.documentReconciledValueEuros = documentReconciledValueEuros;
    }

    public String getDocumentSetName() {
        return this.documentSetName;
    }

    public Double getDocumentValueEuros() {
        return this.documentValueEuros;
    }

    public void setDocumentValueEuros(final Double documentValueEuros) {
        this.documentValueEuros = documentValueEuros;
    }

    public void setDocumentSetName(final String documentSetName) {
        this.documentSetName = documentSetName;
    }

    public Boolean getPdfLinkRequestValid() {
        return valid;
    }

    public String getPdfDownloadUrl() {
        return pdfDownloadUrl;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public void setDocumentNumber(final String documentNumber) {
        this.documentNumber = documentNumber;
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

    public void setPdfDownloadUrl(final String pdfDownloadUrl) {
        this.pdfDownloadUrl = pdfDownloadUrl;
    }

    public void setPdfLinkRequestValid(final Boolean pdfLinkRequestValid) {
        this.valid = pdfLinkRequestValid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getInternalOrderNumber() {
        return internalOrderNumber;
    }

    public void setInternalOrderNumber(String internalOrderNumber) {
        this.internalOrderNumber = internalOrderNumber;
    }

    public String getClientOrderNumber() {
        return clientOrderNumber;
    }

    public MoloniProductDTO[] getProductDTOS() {
        return this.productDTOS;
    }

    public void setProductDTOS(final MoloniProductDTO[] productDTOS) {
        this.productDTOS = productDTOS;
    }

    public void setClientOrderNumber(String clientOrderNumber) {
        this.clientOrderNumber = clientOrderNumber;
    }
}
