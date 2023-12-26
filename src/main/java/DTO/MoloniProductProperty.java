package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniProductProperty {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("property_id")
    private Long propertyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("value")
    private String value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("property")
    private propertyProperty property;

    public MoloniProductProperty() {
    }

    public String getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.property.getTitle();
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private class propertyProperty {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("property_id")
        private Long propertyId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("title")
        private String title;

        public String getTitle() {
            return this.title;
        }

        public propertyProperty() {
        }
    }
}
