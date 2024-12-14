package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutvioResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("success")
    private Boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("outvioOrderId")
    private String outvioOrderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("urls")
    private String[] pdfLabelUrls;


    public Boolean getSuccess() {
        return success;
    }

    public String[] getPdfLabelUrls() {
        return pdfLabelUrls;
    }
}
