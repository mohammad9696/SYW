package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniTokenDTO {

    @JsonProperty("access_token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
