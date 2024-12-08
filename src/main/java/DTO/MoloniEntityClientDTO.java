package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniEntityClientDTO {

    @JsonProperty("company_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyId;

    @JsonProperty("search")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String search;

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @JsonProperty("website")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String website;

    @JsonProperty("fax")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fax;

    @JsonProperty("customer_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String customerId;

    @JsonProperty("visibility_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer visibilityId;

    @JsonProperty("salesman_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer salesmanId;

    @JsonProperty("number")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientNumber;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonProperty("vat")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vat;

    @JsonProperty("address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @JsonProperty("city")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    @JsonProperty("zip_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String zipCode;

    @JsonProperty("country_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer countryId;

    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;

    @JsonProperty("contact_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactName;

    @JsonProperty("contact_email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactEmail;

    @JsonProperty("contact_phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactPhone;

    @JsonProperty("notes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String notes;

    @JsonProperty("discount")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double discount;

    @JsonProperty("credit_limit")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double creditLimit;

    @JsonProperty("qty_copies_document")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer qtyCopiesDocument;

    @JsonProperty("maturity_date_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maturityDateId;

    @JsonProperty("payment_day")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer paymentDay;

    @JsonProperty("field_notes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fieldNotes;

    @JsonProperty("language_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer languageId;

    @JsonProperty("payment_method_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer paymentMethodId;

    @JsonProperty("delivery_method_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deliveryMethodId;

    @JsonProperty("price_class_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer priceClassId;

    @JsonProperty("document_set_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer documentSetId;

    @JsonProperty("exemption_reason")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String exemptionReason;

    @JsonProperty("print_document_notes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer printDocumentNotes;

    @JsonProperty("country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CountryDTO country;

    @JsonProperty("maturity_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MaturityDateDTO maturityDate;

    @JsonProperty("payment_method")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentMethodDTO paymentMethod;

    @JsonProperty("alternate_addresses")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Object> alternateAddresses;

    @JsonProperty("associated_taxes")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Object> associatedTaxes;

    @JsonProperty("valid")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer valid;

    @JsonProperty("copies")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CopyDTO> copies;

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }

    public Integer getValid() {
        return this.valid;
    }

    public void setValid(final Integer valid) {
        this.valid = valid;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public Integer getVisibilityId() {
        return this.visibilityId;
    }

    public void setVisibilityId(final Integer visibilityId) {
        this.visibilityId = visibilityId;
    }

    public Integer getSalesmanId() {
        return this.salesmanId;
    }

    public void setSalesmanId(final Integer salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getClientNumber() {
        return this.clientNumber;
    }

    public void setClientNumber(final String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getVat() {
        return this.vat;
    }

    public void setVat(final String vat) {
        this.vat = vat;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getCountryId() {
        return this.countryId;
    }

    public void setCountryId(final Integer countryId) {
        this.countryId = countryId;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(final String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public void setContactEmail(final String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(final Double discount) {
        this.discount = discount;
    }

    public Double getCreditLimit() {
        return this.creditLimit;
    }

    public void setCreditLimit(final Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getQtyCopiesDocument() {
        return this.qtyCopiesDocument;
    }

    public void setQtyCopiesDocument(final Integer qtyCopiesDocument) {
        this.qtyCopiesDocument = qtyCopiesDocument;
    }

    public Integer getMaturityDateId() {
        return this.maturityDateId;
    }

    public void setMaturityDateId(final Integer maturityDateId) {
        this.maturityDateId = maturityDateId;
    }

    public Integer getPaymentDay() {
        return this.paymentDay;
    }

    public void setPaymentDay(final Integer paymentDay) {
        this.paymentDay = paymentDay;
    }

    public String getFieldNotes() {
        return this.fieldNotes;
    }

    public void setFieldNotes(final String fieldNotes) {
        this.fieldNotes = fieldNotes;
    }

    public Integer getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(final Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getPaymentMethodId() {
        return this.paymentMethodId;
    }

    public void setPaymentMethodId(final Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Integer getDeliveryMethodId() {
        return this.deliveryMethodId;
    }

    public void setDeliveryMethodId(final Integer deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public Integer getPriceClassId() {
        return this.priceClassId;
    }

    public void setPriceClassId(final Integer priceClassId) {
        this.priceClassId = priceClassId;
    }

    public Integer getDocumentSetId() {
        return this.documentSetId;
    }

    public void setDocumentSetId(final Integer documentSetId) {
        this.documentSetId = documentSetId;
    }

    public String getExemptionReason() {
        return this.exemptionReason;
    }

    public void setExemptionReason(final String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }

    public Integer getPrintDocumentNotes() {
        return this.printDocumentNotes;
    }

    public void setPrintDocumentNotes(final Integer printDocumentNotes) {
        this.printDocumentNotes = printDocumentNotes;
    }

    public CountryDTO getCountry() {
        return this.country;
    }

    public void setCountry(final CountryDTO country) {
        this.country = country;
    }

    public MaturityDateDTO getMaturityDate() {
        return this.maturityDate;
    }

    public void setMaturityDate(final MaturityDateDTO maturityDate) {
        this.maturityDate = maturityDate;
    }

    public PaymentMethodDTO getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(final PaymentMethodDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Object> getAlternateAddresses() {
        return this.alternateAddresses;
    }

    public void setAlternateAddresses(final List<Object> alternateAddresses) {
        this.alternateAddresses = alternateAddresses;
    }

    public List<Object> getAssociatedTaxes() {
        return this.associatedTaxes;
    }

    public void setAssociatedTaxes(final List<Object> associatedTaxes) {
        this.associatedTaxes = associatedTaxes;
    }

    public List<CopyDTO> getCopies() {
        return this.copies;
    }

    public void setCopies(final List<CopyDTO> copies) {
        this.copies = copies;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryDTO {
    @JsonProperty("iso_3166_1")
    private String iso31661;

    @JsonProperty("country_id")
    private Integer countryId;

    @JsonProperty("name")
    private String name;

    // Getters and setters omitted for brevity
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MaturityDateDTO {
    @JsonProperty("maturity_date_id")
    private Integer maturityDateId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("days")
    private Integer days;

    @JsonProperty("associated_discount")
    private Double associatedDiscount;

    // Getters and setters omitted for brevity
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PaymentMethodDTO {
    @JsonProperty("payment_method_id")
    private Integer paymentMethodId;

    @JsonProperty("name")
    private String name;

    // Getters and setters omitted for brevity
}

@JsonIgnoreProperties(ignoreUnknown = true)
class CopyDTO {
    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("document_type_id")
    private Integer documentTypeId;

    @JsonProperty("copies")
    private Integer copies;

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    public Integer getDocumentTypeId() {
        return this.documentTypeId;
    }

    public void setDocumentTypeId(final Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Integer getCopies() {
        return this.copies;
    }

    public void setCopies(final Integer copies) {
        this.copies = copies;
    }
// Getters and setters omitted for brevity

}
