package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniCountryDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("country_id")
    private Integer countryId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("iso_3166_1")
    private String iso31661;

    public String getIso31661() {
        return this.iso31661;
    }

    public void setIso31661(final String iso31661) {
        this.iso31661 = iso31661;
    }

    public Integer getCountryId() {
        return this.countryId;
    }

    public void setCountryId(final Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}