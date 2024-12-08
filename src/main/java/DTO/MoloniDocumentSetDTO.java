package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniDocumentSetDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("company_id")
    private String companyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("template_id")
    private Integer templateId;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cash_vat_scheme_indicator")
    private Integer cashVatSchemeIndicator;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_id")
    private String documentSetId;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("active_by_default")
    private Integer activeByDefault;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("image")
    private String image;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("for_invoice_receipt")
    private Integer forInvoiceReceipt;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("is_invisible")
    private Integer isInvisible;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("eac_id")
    private Integer eacId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("eac")
    private EacDTO eac;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_types_numbers")
    private List<DocumentTypeNumberDTO> documentTypesNumbers;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_at_codes")
    private List<DocumentSetAtCodeDTO> documentSetAtCodes;


    public String getDocumentSetId() {
        return this.documentSetId;
    }

    public String getName() {
        return this.name;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class EacDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("eac_id")
    private Integer eacId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("description")
    private String description;

    // Getters and Setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class DocumentTypeNumberDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_id")
    private Integer documentSetId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_type_id")
    private Integer documentTypeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("initial_number")
    private Integer initialNumber;

    // Getters and Setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class DocumentSetAtCodeDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_wsat_id")
    private Integer documentSetWsatId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_id")
    private Integer documentSetId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_type_id")
    private Integer documentTypeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("initial_num")
    private Integer initialNum;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("initial_date")
    private String initialDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_state")
    private Integer documentSetState;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("end_num")
    private Integer endNum;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_at_code")
    private String documentSetAtCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("new_document_set")
    private Integer newDocumentSet;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("can_be_nulled")
    private Integer canBeNulled;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("last_log")
    private LastLogDTO lastLog;

    // Getters and Setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class LastLogDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_wsat_logs_id")
    private Integer documentSetWsatLogsId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_wsat_id")
    private Integer documentSetWsatId;

    @JsonProperty("action")
    private Integer action;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("processing_mode")
    private Integer processingMode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("valid_document_set_at_code")
    private Integer validDocumentSetAtCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("result_code")
    private Integer resultCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("result_msg")
    private String resultMsg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_set_state")
    private Integer documentSetState;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("created_by")
    private String createdBy;

    // Getters and Setters
}
