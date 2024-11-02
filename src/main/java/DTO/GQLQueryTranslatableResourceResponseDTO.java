package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GQLQueryTranslatableResourceResponseDTO {

    @JsonProperty("data")
    private Data data;

    public Data getData() {
        return this.data;
    }

    public void setData(final Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("translatableResourcesByIds")
        private TranslatableResourcesByIds translatableResourcesByIds;

        public TranslatableResourcesByIds getTranslatableResourcesByIds() {
            return this.translatableResourcesByIds;
        }

        public void setTranslatableResourcesByIds(final TranslatableResourcesByIds translatableResourcesByIds) {
            this.translatableResourcesByIds = translatableResourcesByIds;
        }
    }

    public static class TranslatableResourcesByIds {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("edges")
        private List<Edge> edges;

        public List<Edge> getEdges() {
            return this.edges;
        }

        public void setEdges(final List<Edge> edges) {
            this.edges = edges;
        }
    }

    public static class Edge {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("node")
        private Node node;

        public Node getNode() {
            return this.node;
        }

        public void setNode(final Node node) {
            this.node = node;
        }
    }

    public static class Node {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("resourceId")
        private String resourceId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("translatableContent")
        private List<TranslatableContent> translatableContent;

        public List<TranslatableContent> getTranslatableContent() {
            return this.translatableContent;
        }

        public void setTranslatableContent(final List<TranslatableContent> translatableContent) {
            this.translatableContent = translatableContent;
        }

        public String getResourceId() {
            return this.resourceId;
        }

        public void setResourceId(final String resourceId) {
            this.resourceId = resourceId;
        }
    }

    public static class TranslatableContent {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("key")
        private String key;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("value")
        private String value;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("digest")
        private String digest;


        private String resourceId;

        public String getResourceId() {
            return this.resourceId;
        }

        public void setResourceId(final String resourceId) {
            this.resourceId = resourceId;
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

        public String getDigest() {
            return this.digest;
        }

        public void setDigest(final String digest) {
            this.digest = digest;
        }
    }
}
