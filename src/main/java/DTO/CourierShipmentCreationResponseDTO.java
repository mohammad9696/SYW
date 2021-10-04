package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierShipmentCreationResponseDTO {

    @JsonProperty("tracking_code")
    private String trackingCode;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    public String getTrackingCode() {
        return trackingCode;
    }

    @Override
    public String toString() {
        return "CourierShipmentCreationResponseDTO{" +
                "trackingCode='" + trackingCode + '\'' +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
