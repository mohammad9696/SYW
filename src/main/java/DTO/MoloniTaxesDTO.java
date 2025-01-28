package DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniTaxesDTO {

    @JsonProperty("lastmodifiedby")
    private String lastModifiedBy;

    @JsonProperty("lastmodified")
    private String lastModified;

    @JsonProperty("recem_importado")
    private Integer recentlyImported;

    @JsonProperty("tax_id")
    private Integer taxId;

    @JsonProperty("company_id")
    private Integer companyId;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("saft_type")
    private Integer saftType;

    @JsonProperty("vat_type")
    private String vatType;

    @JsonProperty("stamp_tax")
    private String stampTax;

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private double value;

    @JsonProperty("fiscal_zone")
    private String fiscalZone;

    @JsonProperty("active_by_default")
    private Integer activeByDefault;

    @JsonProperty("exemption_reason")
    private String exemptionReason;

    @JsonProperty("country_id")
    private Integer countryId;

    @JsonProperty("visibility")
    private Integer visibility;

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(final String lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getRecentlyImported() {
        return this.recentlyImported;
    }

    public void setRecentlyImported(final Integer recentlyImported) {
        this.recentlyImported = recentlyImported;
    }

    public Integer getTaxId() {
        return this.taxId;
    }

    public void setTaxId(final Integer taxId) {
        this.taxId = taxId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(final Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public Integer getSaftType() {
        return this.saftType;
    }

    public void setSaftType(final Integer saftType) {
        this.saftType = saftType;
    }

    public String getVatType() {
        return this.vatType;
    }

    public void setVatType(final String vatType) {
        this.vatType = vatType;
    }

    public String getStampTax() {
        return this.stampTax;
    }

    public void setStampTax(final String stampTax) {
        this.stampTax = stampTax;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(final double value) {
        this.value = value;
    }

    public String getFiscalZone() {
        return this.fiscalZone;
    }

    public void setFiscalZone(final String fiscalZone) {
        this.fiscalZone = fiscalZone;
    }

    public Integer getActiveByDefault() {
        return this.activeByDefault;
    }

    public void setActiveByDefault(final Integer activeByDefault) {
        this.activeByDefault = activeByDefault;
    }

    public String getExemptionReason() {
        return this.exemptionReason;
    }

    public void setExemptionReason(final String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }

    public Integer getCountryId() {
        return this.countryId;
    }

    public void setCountryId(final Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getVisibility() {
        return this.visibility;
    }

    public void setVisibility(final Integer visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "MoloniTaxAndFeesDTO{" +
                "lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", recentlyImported=" + recentlyImported +
                ", taxId=" + taxId +
                ", companyId=" + companyId +
                ", type=" + type +
                ", saftType=" + saftType +
                ", vatType='" + vatType + '\'' +
                ", stampTax='" + stampTax + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", fiscalZone='" + fiscalZone + '\'' +
                ", activeByDefault=" + activeByDefault +
                ", exemptionReason='" + exemptionReason + '\'' +
                ", countryId=" + countryId +
                ", visibility=" + visibility +
                '}';
    }
}
