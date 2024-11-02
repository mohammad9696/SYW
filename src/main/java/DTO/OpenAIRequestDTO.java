package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIRequestDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("model")
    private String model;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("messages")
    private List<Message> messages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("max_tokens")
    private int maxTokens;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("temperature")
    private double temperature;

    // Constructor, Getters, and Setters
    public OpenAIRequestDTO(String model, List<Message> messages, int maxTokens, double temperature) {
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
    }

    // Inner class for Message
    public static class Message {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("role")
        private String role;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("content")
        private String content;

        // Constructor, Getters, and Setters
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

}
