package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutvioResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("success")
    private Boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("outvioOrderId")
    private String outvioOrderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("urls")
    private String[] pdfLabelUrls;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("shipments")
    private List<Shipment> shipments;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Shipment {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("courier")
        private String courier;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("label")
        private List<String> label;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("pickupCode")
        private String pickupCode;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("trackingNumber")
        private String trackingNumber;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("secondaryTrackingNumber")
        private String secondaryTrackingNumber;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("estimatedDeliveryDate")
        private String estimatedDeliveryDate;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("otn")
        private String otn;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("shippingRate")
        private Double shippingRate;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("packages")
        private List<Package> packages;

        // Getters and Setters
        public String getCourier() {
            return courier;
        }

        public void setCourier(String courier) {
            this.courier = courier;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }

        public String getPickupCode() {
            return pickupCode;
        }

        public void setPickupCode(String pickupCode) {
            this.pickupCode = pickupCode;
        }

        public String getTrackingNumber() {
            return trackingNumber;
        }

        public void setTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
        }

        public String getSecondaryTrackingNumber() {
            return secondaryTrackingNumber;
        }

        public void setSecondaryTrackingNumber(String secondaryTrackingNumber) {
            this.secondaryTrackingNumber = secondaryTrackingNumber;
        }

        public String getEstimatedDeliveryDate() {
            return estimatedDeliveryDate;
        }

        public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
            this.estimatedDeliveryDate = estimatedDeliveryDate;
        }

        public String getOtn() {
            return otn;
        }

        public void setOtn(String otn) {
            this.otn = otn;
        }

        public Double getShippingRate() {
            return shippingRate;
        }

        public void setShippingRate(Double shippingRate) {
            this.shippingRate = shippingRate;
        }

        public List<Package> getPackages() {
            return packages;
        }

        public void setPackages(List<Package> packages) {
            this.packages = packages;
        }
    }

    public List<Shipment> getShipments() {
        return this.shipments;
    }

    public void setShipments(final List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void setPdfLabelUrls(final String[] pdfLabelUrls) {
        this.pdfLabelUrls = pdfLabelUrls;
    }

    public String getOutvioOrderId() {
        return this.outvioOrderId;
    }

    public void setOutvioOrderId(final String outvioOrderId) {
        this.outvioOrderId = outvioOrderId;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Package {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("_id")
        private String id;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("products")
        private List<String> products;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("packaging")
        private Packaging packaging;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("courierTrackId")
        private String courierTrackId;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("courierCode")
        private String courierCode;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("labelType")
        private String labelType;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getProducts() {
            return products;
        }

        public void setProducts(List<String> products) {
            this.products = products;
        }

        public Packaging getPackaging() {
            return packaging;
        }

        public void setPackaging(Packaging packaging) {
            this.packaging = packaging;
        }

        public String getCourierTrackId() {
            return courierTrackId;
        }

        public void setCourierTrackId(String courierTrackId) {
            this.courierTrackId = courierTrackId;
        }

        public String getCourierCode() {
            return courierCode;
        }

        public void setCourierCode(String courierCode) {
            this.courierCode = courierCode;
        }

        public String getLabelType() {
            return labelType;
        }

        public void setLabelType(String labelType) {
            this.labelType = labelType;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Packaging {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("name")
        private String name;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("length")
        private Double length;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("width")
        private Double width;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("height")
        private Double height;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getLength() {
            return length;
        }

        public void setLength(Double length) {
            this.length = length;
        }

        public Double getWidth() {
            return width;
        }

        public void setWidth(Double width) {
            this.width = width;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }
    }


    public Boolean getSuccess() {
        return success;
    }

    public String[] getPdfLabelUrls() {
        return pdfLabelUrls;
    }
}
