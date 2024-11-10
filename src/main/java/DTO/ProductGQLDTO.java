package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductGQLDTO {
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty("products")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Products products;

        public Products getProducts() {
            return products;
        }

        public void setProducts(Products products) {
            this.products = products;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Products {

        @JsonProperty("edges")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<ProductEdge> edges;

        @JsonProperty("pageInfo")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private PageInfo pageInfo;

        public List<ProductEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<ProductEdge> edges) {
            this.edges = edges;
        }

        public PageInfo getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metafields {

        @JsonProperty("edges")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<MetafieldEdge> edges;

        public List<MetafieldEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<MetafieldEdge> edges) {
            this.edges = edges;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetafieldEdge {

        @JsonProperty("node")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private MetafieldNode node;

        public MetafieldNode getNode() {
            return node;
        }

        public void setNode(MetafieldNode node) {
            this.node = node;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetafieldNode {

        @JsonProperty("id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String id;

        @JsonProperty("jsonValue")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String jsonValue;

        @JsonProperty("key")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String key;

        @JsonProperty("namespace")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String namespace;

        @JsonProperty("value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String value;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJsonValue() {
            return jsonValue;
        }

        public void setJsonValue(String jsonValue) {
            this.jsonValue = jsonValue;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductEdge {

        @JsonProperty("node")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private ProductNode node;

        @JsonProperty("cursor")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String cursor;

        public ProductNode getNode() {
            return node;
        }

        public void setNode(ProductNode node) {
            this.node = node;
        }

        public String getCursor() {
            return cursor;
        }

        public void setCursor(String cursor) {
            this.cursor = cursor;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductNode {

        @JsonProperty("id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String id;

        @JsonProperty("title")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String title;

        @JsonProperty("descriptionHtml")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String descriptionHtml;

        @JsonProperty("vendor")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String vendor;

        @JsonProperty("productType")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String productType;

        @JsonProperty("createdAt")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String createdAt;

        @JsonProperty("handle")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String handle;

        @JsonProperty("updatedAt")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String updatedAt;

        @JsonProperty("publishedAt")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String publishedAt;

        @JsonProperty("templateSuffix")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String templateSuffix;

        @JsonProperty("status")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String status;

        @JsonProperty("tags")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<String> tags;

        @JsonProperty("variants")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Variants variants;

        @JsonProperty("media")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Media media;

        @JsonProperty("metafields")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Metafields metafields;

        public Metafields getMetafields() {
            return this.metafields;
        }

        public void setMetafields(final Metafields metafields) {
            this.metafields = metafields;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescriptionHtml() {
            return descriptionHtml;
        }

        public void setDescriptionHtml(String descriptionHtml) {
            this.descriptionHtml = descriptionHtml;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getHandle() {
            return handle;
        }

        public void setHandle(String handle) {
            this.handle = handle;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getTemplateSuffix() {
            return templateSuffix;
        }

        public void setTemplateSuffix(String templateSuffix) {
            this.templateSuffix = templateSuffix;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Variants getVariants() {
            return variants;
        }

        public void setVariants(Variants variants) {
            this.variants = variants;
        }

        public Media getMedia() {
            return media;
        }

        public void setMedia(Media media) {
            this.media = media;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageInfo {

        @JsonProperty("hasNextPage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private boolean hasNextPage;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Variants {

        @JsonProperty("edges")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<VariantEdge> edges;

        public List<VariantEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<VariantEdge> edges) {
            this.edges = edges;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VariantEdge {

        @JsonProperty("node")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private VariantNode node;

        public VariantNode getNode() {
            return node;
        }

        public void setNode(VariantNode node) {
            this.node = node;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VariantNode {

        @JsonProperty("id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String id;

        @JsonProperty("title")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String title;

        @JsonProperty("price")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String price;

        @JsonProperty("compareAtPrice")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String compareAtPrice;

        @JsonProperty("taxable")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String taxable;

        @JsonProperty("sku")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String sku;

        @JsonProperty("position")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private int position;

        @JsonProperty("barcode")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String barcode;

        @JsonProperty("inventoryItem")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private InventoryItem inventoryItem;

        @JsonProperty("inventoryPolicy")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String inventoryPolicy;

        @JsonProperty("inventoryQuantity")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer inventoryQuantity;

        public Integer getInventoryQuantity() {
            return this.inventoryQuantity;
        }

        public void setInventoryQuantity(final Integer inventoryQuantity) {
            this.inventoryQuantity = inventoryQuantity;
        }

        public String getInventoryPolicy() {
            return this.inventoryPolicy;
        }

        public void setInventoryPolicy(final String inventoryPolicy) {
            this.inventoryPolicy = inventoryPolicy;
        }

        public String getId() {
            return id;
        }

        public String getCompareAtPrice() {
            return this.compareAtPrice;
        }

        public void setCompareAtPrice(final String compareAtPrice) {
            this.compareAtPrice = compareAtPrice;
        }

        public String getTaxable() {
            return this.taxable;
        }

        public void setTaxable(final String taxable) {
            this.taxable = taxable;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public InventoryItem getInventoryItem() {
            return inventoryItem;
        }

        public void setInventoryItem(InventoryItem inventoryItem) {
            this.inventoryItem = inventoryItem;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InventoryItem {

        @JsonProperty("id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String id;

        @JsonProperty("inventoryLevel")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private InventoryLevel inventoryLevel;

        @JsonProperty("measurement")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Measurement measurement;

        @JsonProperty("requiresShipping")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private boolean requiresShipping;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public InventoryLevel getInventoryLevel() {
            return inventoryLevel;
        }

        public void setInventoryLevel(InventoryLevel inventoryLevel) {
            this.inventoryLevel = inventoryLevel;
        }

        public Measurement getMeasurement() {
            return measurement;
        }

        public void setMeasurement(Measurement measurement) {
            this.measurement = measurement;
        }

        public boolean isRequiresShipping() {
            return requiresShipping;
        }

        public void setRequiresShipping(boolean requiresShipping) {
            this.requiresShipping = requiresShipping;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InventoryLevel {

        @JsonProperty("quantities")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<Quantity> quantities;

        public List<Quantity> getQuantities() {
            return quantities;
        }

        public void setQuantities(List<Quantity> quantities) {
            this.quantities = quantities;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Quantity {

        @JsonProperty("quantity")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer quantity;

        @JsonProperty("name")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Measurement {

        @JsonProperty("weight")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Weight weight;

        public Weight getWeight() {
            return weight;
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weight {

        @JsonProperty("unit")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String unit;

        @JsonProperty("value")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private double value;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Media {

        @JsonProperty("edges")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<MediaEdge> edges;

        public List<MediaEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<MediaEdge> edges) {
            this.edges = edges;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MediaEdge {

        @JsonProperty("node")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private MediaNode node;

        public MediaNode getNode() {
            return node;
        }

        public void setNode(MediaNode node) {
            this.node = node;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MediaNode {

        @JsonProperty("alt")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String alt;

        @JsonProperty("id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String id;

        @JsonProperty("status")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String status;


        @JsonProperty("mediaContentType")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String mediaContentType;

        @JsonProperty("preview")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Preview preview;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMediaContentType() {
            return mediaContentType;
        }

        public void setMediaContentType(String mediaContentType) {
            this.mediaContentType = mediaContentType;
        }

        public Preview getPreview() {
            return preview;
        }

        public void setPreview(Preview preview) {
            this.preview = preview;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Preview {

        @JsonProperty("image")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Image image;

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {

        @JsonProperty("url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}