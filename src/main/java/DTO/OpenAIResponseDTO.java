package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("object")
    private String object;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("created")
    private Long created;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("model")
    private String model;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("choices")
    private List<Choice> choices;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("usage")
    private Usage usage;

    public List<Choice> getChoices() {
        return this.choices;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("index")
        private Integer index;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("message")
        private Message message;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("finish_reason")
        private String finishReason;

        public Message getMessage() {
            return this.message;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Message {

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("role")
            private String role;


            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("content")
            private String content;  // Campo genérico

            public String getContent() {
                return this.content;
            }

        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("completion_tokens")
        private Integer completionTokens;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("total_tokens")
        private Integer totalTokens;

    }
}