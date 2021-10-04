package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierVolumeDimensionDTO {

    @JsonProperty("width")
    private Double widthCm;

    @JsonProperty("height")
    private Double heightCm;

    @JsonProperty("lenght")
    private Double lenghtCm;


}
