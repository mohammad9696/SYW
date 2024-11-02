package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GQLQueryTranslationsResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    private Data data;

    public Data getData() {
        return this.data;
    }

    public void setData(final Data data) {
        this.data = data;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("product")
        private Product product;

        public Product getProduct() {
            return product;
        }
        public void setProduct(Product product) {
            this.product = product;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("id")
        private String id;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("metafields")
        private Metafields metafields;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Metafields getMetafields() {
            return metafields;
        }

        public void setMetafields(Metafields metafields) {
            this.metafields = metafields;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metafields {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("edges")
        private List<Edge> edges;

        // Getters and Setters
        public List<Edge> getEdges() {
            return edges;
        }

        public void setEdges(List<Edge> edges) {
            this.edges = edges;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Edge {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("node")
        private Node node;

        // Getters and Setters
        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Node {

        @JsonProperty("id")
        private String id;

        @JsonProperty("key")
        private String key;

        @JsonProperty("value")
        private String value;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
