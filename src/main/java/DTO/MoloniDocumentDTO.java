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

    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pdfDownloadUrl;

    @JsonProperty("valid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPdfLinkRequestValid;

    public Boolean getPdfLinkRequestValid() {
        return isPdfLinkRequestValid;
    }

    public String getPdfDownloadUrl() {
        return pdfDownloadUrl;
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

    public void setClientOrderNumber(String clientOrderNumber) {
        this.clientOrderNumber = clientOrderNumber;
    }
}
