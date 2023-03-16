package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniDocumentTypeDTO {

    @JsonProperty("titulo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonProperty("document_type_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String documentTypeId;

    @JsonProperty("saft_code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String saftCode;

    @JsonProperty("language_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String languageId;

    public MoloniDocumentTypeDTO() {
        this.languageId="1";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(String documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getSaftCode() {
        return saftCode;
    }

    public void setSaftCode(String saftCode) {
        this.saftCode = saftCode;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
