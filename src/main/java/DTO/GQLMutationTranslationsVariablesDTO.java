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
    @JsonProperty("translations")
    private List<Translation> translations;

    public List<Translation> getTranslations() {
        return this.translations;
    }

    public void setTranslations(final List<Translation> translations) {
        this.translations = translations;
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

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("resourceId")
        private String resourceId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("translatedContent")
        private String translatedContent;

        public void setLocale(final String locale) {
            this.locale = locale;
        }

        public String getResourceId() {
            return this.resourceId;
        }

        public void setResourceId(final String resourceId) {
            this.resourceId = resourceId;
        }

        public String getTranslatedContent() {
            return this.translatedContent;
        }

        public void setTranslatedContent(final String translatedContent) {
            this.translatedContent = translatedContent;
        }

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
