package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

    @JsonProperty("delivery_method_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deliveryMethodId;

    @JsonProperty("delivery_datetime")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryDatetime;

    @JsonProperty("delivery_departure_address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryDepartureAddress;

    @JsonProperty("delivery_departure_city")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryDepartureCity;

    @JsonProperty("delivery_departure_zip_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String deliveryDepartureZipCode;

    @JsonProperty("delivery_departure_country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deliveryDepartureCountryId;

    @JsonProperty("vehicle_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer vehicleId;

    @JsonProperty("send_email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SendEmail> sendEmail;

    public List<SendEmail> getSendEmail() {
        return this.sendEmail;
    }

    public void setSendEmail(final List<SendEmail> sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Integer getDeliveryMethodId() {
        return this.deliveryMethodId;
    }

    public void setDeliveryMethodId(final Integer deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getDeliveryDepartureAddress() {
        return this.deliveryDepartureAddress;
    }

    public void setDeliveryDepartureAddress(final String deliveryDepartureAddress) {
        this.deliveryDepartureAddress = deliveryDepartureAddress;
    }

    public String getDeliveryDepartureCity() {
        return this.deliveryDepartureCity;
    }

    public void setDeliveryDepartureCity(final String deliveryDepartureCity) {
        this.deliveryDepartureCity = deliveryDepartureCity;
    }

    public String getDeliveryDepartureZipCode() {
        return this.deliveryDepartureZipCode;
    }

    public void setDeliveryDepartureZipCode(final String deliveryDepartureZipCode) {
        this.deliveryDepartureZipCode = deliveryDepartureZipCode;
    }

    public Integer getDeliveryDepartureCountryId() {
        return this.deliveryDepartureCountryId;
    }

    public void setDeliveryDepartureCountryId(final Integer deliveryDepartureCountryId) {
        this.deliveryDepartureCountryId = deliveryDepartureCountryId;
    }

    public String getDeliveryDatetime() {
        return this.deliveryDatetime;
    }

    public void setDeliveryDatetime(final String deliveryDatetime) {
        this.deliveryDatetime = deliveryDatetime;
    }

    public Integer getVehicleId() {
        return this.vehicleId;
    }

    public void setVehicleId(final Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("reverse_document_id")
    private String reverseDocumentId;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payments")
    private List<MoloniPaymentMethodDTO> payments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("reverse_associated_documents")
    private List<ReverseDocumentDTO> reverseAssociatedDocuments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("associated_documents")
    private List<AssociatedDocumentDTO> associatedDocuments;

    public List<AssociatedDocumentDTO> getAssociatedDocuments() {
        return this.associatedDocuments;
    }

    public void setAssociatedDocuments(final List<AssociatedDocumentDTO> associatedDocuments) {
        this.associatedDocuments = associatedDocuments;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AssociatedDocumentDTO {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("associated_id")
        private String associatedId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("value")
        private Double value;

        public String getAssociatedId() {
            return this.associatedId;
        }

        public void setAssociatedId(final String associatedId) {
            this.associatedId = associatedId;
        }

        public Double getValue() {
            return this.value;
        }

        public void setValue(final Double value) {
            this.value = value;
        }
    }
    public List<ReverseDocumentDTO> getReverseAssociatedDocuments() {
        return this.reverseAssociatedDocuments;
    }

    public void setReverseAssociatedDocuments(final List<ReverseDocumentDTO> reverseAssociatedDocuments) {
        this.reverseAssociatedDocuments = reverseAssociatedDocuments;
    }

    public List<MoloniPaymentMethodDTO> getPayments() {
        return this.payments;
    }

    public void setPayments(final List<MoloniPaymentMethodDTO> payments) {
        this.payments = payments;
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

    public String getReverseDocumentId() {
        return this.reverseDocumentId;
    }

    public void setReverseDocumentId(String reverseDocumentId) {
        this.reverseDocumentId = reverseDocumentId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SendEmail {
        private String email;
        private String name;
        private String msg;

        public String getEmail() {
            return this.email;
        }

        public void setEmail(final String email) {
            this.email = email;
        }

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(final String msg) {
            this.msg = msg;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReverseDocumentDTO {
        @JsonProperty("document_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer documentId;

        @JsonProperty("value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer value;

        @JsonProperty("reverse_associated_document")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private ReverseAssociatedDocumentDTO reverseAssociatedDocument;

        public Integer getDocumentId() {
            return this.documentId;
        }

        public void setDocumentId(final Integer documentId) {
            this.documentId = documentId;
        }

        public Integer getValue() {
            return this.value;
        }

        public void setValue(final Integer value) {
            this.value = value;
        }

        public ReverseAssociatedDocumentDTO getReverseAssociatedDocument() {
            return this.reverseAssociatedDocument;
        }

        public void setReverseAssociatedDocument(final ReverseAssociatedDocumentDTO reverseAssociatedDocument) {
            this.reverseAssociatedDocument = reverseAssociatedDocument;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReverseAssociatedDocumentDTO {

        @JsonProperty("document_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer documentId;

        @JsonProperty("document_type_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer documentTypeId;

        @JsonProperty("document_set_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer documentSetId;

        @JsonProperty("number")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer number;

        @JsonProperty("date")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String date;

        @JsonProperty("expiration_date")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String expirationDate;

        @JsonProperty("your_reference")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String yourReference;

        @JsonProperty("our_reference")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String ourReference;

        @JsonProperty("entity_number")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String entityNumber;

        @JsonProperty("entity_name")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String entityName;

        @JsonProperty("entity_vat")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String entityVat;

        @JsonProperty("entity_address")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String entityAddress;

        @JsonProperty("financial_discount")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double financialDiscount;

        @JsonProperty("gross_value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double grossValue;

        @JsonProperty("comercial_discount_value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double comercialDiscountValue;

        @JsonProperty("financial_discount_value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double financialDiscountValue;

        @JsonProperty("taxes_value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double taxesValue;

        @JsonProperty("deduction_value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double deductionValue;

        @JsonProperty("net_value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double netValue;

        @JsonProperty("status")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer status;

        @JsonProperty("global_guide")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer globalGuide;

        public Integer getDocumentId() {
            return this.documentId;
        }

        public void setDocumentId(final Integer documentId) {
            this.documentId = documentId;
        }

        public Integer getDocumentTypeId() {
            return this.documentTypeId;
        }

        public void setDocumentTypeId(final Integer documentTypeId) {
            this.documentTypeId = documentTypeId;
        }

        public Integer getDocumentSetId() {
            return this.documentSetId;
        }

        public void setDocumentSetId(final Integer documentSetId) {
            this.documentSetId = documentSetId;
        }

        public Integer getNumber() {
            return this.number;
        }

        public void setNumber(final Integer number) {
            this.number = number;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(final String date) {
            this.date = date;
        }

        public String getExpirationDate() {
            return this.expirationDate;
        }

        public void setExpirationDate(final String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getYourReference() {
            return this.yourReference;
        }

        public void setYourReference(final String yourReference) {
            this.yourReference = yourReference;
        }

        public String getOurReference() {
            return this.ourReference;
        }

        public void setOurReference(final String ourReference) {
            this.ourReference = ourReference;
        }

        public String getEntityNumber() {
            return this.entityNumber;
        }

        public void setEntityNumber(final String entityNumber) {
            this.entityNumber = entityNumber;
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

        public Double getFinancialDiscount() {
            return this.financialDiscount;
        }

        public void setFinancialDiscount(final Double financialDiscount) {
            this.financialDiscount = financialDiscount;
        }

        public Double getGrossValue() {
            return this.grossValue;
        }

        public void setGrossValue(final Double grossValue) {
            this.grossValue = grossValue;
        }

        public Double getComercialDiscountValue() {
            return this.comercialDiscountValue;
        }

        public void setComercialDiscountValue(final Double comercialDiscountValue) {
            this.comercialDiscountValue = comercialDiscountValue;
        }

        public Double getFinancialDiscountValue() {
            return this.financialDiscountValue;
        }

        public void setFinancialDiscountValue(final Double financialDiscountValue) {
            this.financialDiscountValue = financialDiscountValue;
        }

        public Double getTaxesValue() {
            return this.taxesValue;
        }

        public void setTaxesValue(final Double taxesValue) {
            this.taxesValue = taxesValue;
        }

        public Double getDeductionValue() {
            return this.deductionValue;
        }

        public void setDeductionValue(final Double deductionValue) {
            this.deductionValue = deductionValue;
        }

        public Double getNetValue() {
            return this.netValue;
        }

        public void setNetValue(final Double netValue) {
            this.netValue = netValue;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(final Integer status) {
            this.status = status;
        }

        public Integer getGlobalGuide() {
            return this.globalGuide;
        }

        public void setGlobalGuide(final Integer globalGuide) {
            this.globalGuide = globalGuide;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tax {
        @JsonProperty("tax_id")
        private Integer taxId;

        @JsonProperty("value")
        private Double value;

        // Getters and Setters
    }

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

    public Boolean checkPdfLinkRequestValid() {
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
