package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutvioOrderDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("OUTVIO_PARAMS")
    private OutvioParamsDTO outvioParams;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("client")
    private ClientDTO client;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("currency")
    private String currency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("products")
    private List<ProductDTO> products;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("shipping")
    private ShippingDTO shipping;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total")
    private Double total;

    // Getters and setters
    public OutvioParamsDTO getOutvioParams() {
        return outvioParams;
    }

    public void setOutvioParams(OutvioParamsDTO outvioParams) {
        this.outvioParams = outvioParams;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public ShippingDTO getShipping() {
        return shipping;
    }

    public void setShipping(ShippingDTO shipping) {
        this.shipping = shipping;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutvioParamsDTO {
        @JsonProperty("API_KEY")
        private String apiKey;

        @JsonProperty("CMS_ID")
        private String cmsId;

        // Getters and setters
        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getCmsId() {
            return cmsId;
        }

        public void setCmsId(String cmsId) {
            this.cmsId = cmsId;
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClientDTO {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("delivery")
        private DeliveryDTO delivery;

        // Getters and setters
        public DeliveryDTO getDelivery() {
            return delivery;
        }

        public void setDelivery(DeliveryDTO delivery) {
            this.delivery = delivery;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DeliveryDTO {

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("address")
            private String address;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("city")
            private String city;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("countryCode")
            private String countryCode;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("email")
            private String email;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("name")
            private String name;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("phone")
            private String phone;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @JsonProperty("postcode")
            private String postcode;

            // Getters and setters
            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductDTO {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("name")
        private String name;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("price")
        private Double price;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("quantity")
        private Integer quantity;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShippingDTO {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("price")
        private Double price;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("method")
        private String method;

        // Getters and setters
        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
}