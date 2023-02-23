package DTO;

import Constants.ConstantsEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutvioShipDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("API_KEY")
    String apikey = ConstantsEnum.OUTVIO_API_KEY.getConstantValue().toString();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    String orderName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("insured")
    String insured = "false";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("packages")
    OutvioPackaging[] packages;

    public OutvioShipDTO(String orderName, int volumes) {
        this.orderName = orderName;
        OutvioPackaging[] packages = new OutvioPackaging[volumes];
        for (int i = 0; i< volumes; i++){
            packages[i] = new OutvioPackaging();

        }

        this.packages = packages;
    }

    public OutvioShipDTO() {
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
class OutvioPackaging {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("length")
    private int length = 0;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("width")
    private int width = 0;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("height")
    private int height = 0;

    public OutvioPackaging() {
    }
}
