package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniDocumentDTO {

    @JsonProperty("company_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyId;

    @JsonProperty("document_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentId;

    @JsonProperty("document_type_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentTypeId;

    @JsonProperty("our_reference")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String shopifyOrderNumber;

    @JsonProperty("your_reference")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientOrderNumber;

    @JsonProperty("number")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentNumber;

    @JsonProperty("date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String date;

    @JsonProperty("entity_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String entityName;

    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pdfDownloadUrl;

    @JsonProperty("document_set_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentSetName;

    @JsonProperty("net_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double documentValueEuros;

    @JsonProperty("valid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPdfLinkRequestValid;

    @JsonProperty("products")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MoloniProductDTO[] productDTOS;

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
        return isPdfLinkRequestValid;
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
        this.isPdfLinkRequestValid = pdfLinkRequestValid;
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

    public String getShopifyOrderNumber() {
        return shopifyOrderNumber;
    }

    public void setShopifyOrderNumber(String shopifyOrderNumber) {
        this.shopifyOrderNumber = shopifyOrderNumber;
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
