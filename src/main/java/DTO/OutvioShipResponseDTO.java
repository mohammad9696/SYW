package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutvioShipResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("success")
    Boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("outvioOrderId")
    String outvioOrderId;

    public Boolean getSuccess() {
        return success;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("urls")
    String[] pdfLabelUrls;


    public String[] getPdfLabelUrls() {
        return pdfLabelUrls;
    }
}
