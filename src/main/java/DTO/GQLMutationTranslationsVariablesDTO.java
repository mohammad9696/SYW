package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GQLMutationTranslationsVariablesDTO {
    public GQLMutationTranslationsVariablesDTO() {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("resourceId1")
    private String resourceId1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("translations1")
    private List<Translation> translations1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("resourceId2")
    private String resourceId2;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("translations2")
    private List<Translation> translations2;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("resourceId3")
    private String resourceId3;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("translations3")
    private List<Translation> translations3;

    public String getResourceId1() {
        return this.resourceId1;
    }

    public void setResourceId1(final String resourceId1) {
        this.resourceId1 = resourceId1;
    }

    public List<Translation> getTranslations1() {
        return this.translations1;
    }

    public void setTranslations1(final List<Translation> translations1) {
        this.translations1 = translations1;
    }

    public String getResourceId2() {
        return this.resourceId2;
    }

    public void setResourceId2(final String resourceId2) {
        this.resourceId2 = resourceId2;
    }

    public List<Translation> getTranslations2() {
        return this.translations2;
    }

    public void setTranslations2(final List<Translation> translations2) {
        this.translations2 = translations2;
    }

    public String getResourceId3() {
        return this.resourceId3;
    }

    public void setResourceId3(final String resourceId3) {
        this.resourceId3 = resourceId3;
    }

    public List<Translation> getTranslations3() {
        return this.translations3;
    }

    public void setTranslations3(final List<Translation> translations3) {
        this.translations3 = translations3;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Translation {
        public Translation() {
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("locale")
        private String locale;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("translatableContentDigest")
        private String digest;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("key")
        private String key;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("value")
        private String value;

        public String getLocale() {
            return this.locale;
        }

        public String getDigest() {
            return this.digest;
        }

        public void setDigest(final String digest) {
            this.digest = digest;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(final String key) {
            this.key = key;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }
    //private enum Locale { en,es,fr,it,de}
}
