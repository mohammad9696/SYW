package DTO;

import Constants.ConstantsEnum;
import Constants.CourierExpeditionEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierOauthDTO {

    @JsonProperty("grant_type")
    private final String grantType =ConstantsEnum.COURIER_OAUTH_GRANT_TYPE.getConstantValue().toString();

    @JsonProperty("client_id")
    private final String clientId = ConstantsEnum.COURIER_OAUTH_CLIENT_ID.getConstantValue().toString();

    @JsonProperty("client_secret")
    private final String clientSecret= ConstantsEnum.COURIER_OAUTH_CLIENT_SECRET.getConstantValue().toString();

    @JsonProperty("username")
    private final String username = ConstantsEnum.COURIER_OAUTH_USERNAME.getConstantValue().toString();

    @JsonProperty("password")
    private final String password = ConstantsEnum.COURIER_OAUTH_PASSWORD.getConstantValue().toString();

    public CourierOauthDTO() {
    }
}
