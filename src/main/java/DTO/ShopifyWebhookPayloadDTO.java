package DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyWebhookPayloadDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("app_id")
    private Long appId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("browser_ip")
    private String browserIp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("buyer_accepts_marketing")
    private Boolean buyerAcceptsMarketing;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cancel_reason")
    private String cancelReason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart_token")
    private String cartToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("checkout_id")
    private Long checkoutId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("checkout_token")
    private String checkoutToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("client_details")
    private ClientDetails clientDetails;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("confirmation_number")
    private String confirmationNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("confirmed")
    private Boolean confirmed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("contact_email")
    private String contactEmail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("currency")
    private String currency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("current_total_price")
    private String currentTotalPrice;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("current_total_tax")
    private String currentTotalTax;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("shipping_address")
    private Address shippingAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("billing_address")
    private Address billingAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("customer")
    private Customer customer;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("line_items")
    private List<LineItem> lineItems;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("shipping_lines")
    private List<ShippingLine> shippingLines;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payment_gateway_names")
    private List<String> paymentGatewayNames;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("created_at")
    private String createdAt; // Propriedade como LocalDateTime

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrentTotalTax() {
        return this.currentTotalTax;
    }

    public void setCurrentTotalTax(final String currentTotalTax) {
        this.currentTotalTax = currentTotalTax;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getBrowserIp() {
        return browserIp;
    }

    public void setBrowserIp(String browserIp) {
        this.browserIp = browserIp;
    }

    public Boolean getBuyerAcceptsMarketing() {
        return buyerAcceptsMarketing;
    }

    public void setBuyerAcceptsMarketing(Boolean buyerAcceptsMarketing) {
        this.buyerAcceptsMarketing = buyerAcceptsMarketing;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public List<String> getPaymentGatewayNames() {
        return this.paymentGatewayNames;
    }

    public void setPaymentGatewayNames(final List<String> paymentGatewayNames) {
        this.paymentGatewayNames = paymentGatewayNames;
    }

    public String getCartToken() {
        return cartToken;
    }

    public void setCartToken(String cartToken) {
        this.cartToken = cartToken;
    }

    public Long getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Long checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getCheckoutToken() {
        return checkoutToken;
    }

    public void setCheckoutToken(String checkoutToken) {
        this.checkoutToken = checkoutToken;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrentTotalPrice() {
        return currentTotalPrice;
    }

    public void setCurrentTotalPrice(String currentTotalPrice) {
        this.currentTotalPrice = currentTotalPrice;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
    }

    public Address getShippingAddress() {
        return this.shippingAddress;
    }

    public void setShippingAddress(final Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClientDetails {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("browser_ip")
        private String browserIp;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("user_agent")
        private String userAgent;

        // Getters and Setters
        public String getBrowserIp() {
            return browserIp;
        }

        public void setBrowserIp(String browserIp) {
            this.browserIp = browserIp;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("first_name")
        private String firstName;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("last_name")
        private String lastName;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("address1")
        private String address1;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("address2")
        private String address2;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("city")
        private String city;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("country")
        private String country;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("country_code")
        private String countryISOCode;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("zip")
        private String postcode;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("company")
        private String vatId;

        public String getCountryISOCode() {
            return this.countryISOCode;
        }

        public void setCountryISOCode(final String countryISOCode) {
            this.countryISOCode = countryISOCode;
        }

        public String getPostcode() {
            return this.postcode;
        }

        public void setPostcode(final String postcode) {
            this.postcode = postcode;
        }

        public String getAddress2() {
            return this.address2;
        }

        public void setAddress2(final String address2) {
            this.address2 = address2;
        }

        public String getVatId() {
            return this.vatId;
        }

        public void setVatId(final String vatId) {
            this.vatId = vatId;
        }

        // Getters and Setters
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Customer {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("id")
        private Long id;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("email")
        private String email;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("first_name")
        private String firstName;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("last_name")
        private String lastName;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("default_address")
        private Address defaultAddress;

        public Address getDefaultAddress() {
            return this.defaultAddress;
        }

        public void setDefaultAddress(final Address defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LineItem {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("admin_graphql_api_id")
        private String adminGraphqlApiId;

        @JsonProperty("attributed_staffs")
        private List<String> attributedStaffs;

        @JsonProperty("current_quantity")
        private Integer currentQuantity;

        @JsonProperty("fulfillable_quantity")
        private Integer fulfillableQuantity;

        @JsonProperty("fulfillment_service")
        private String fulfillmentService;

        @JsonProperty("fulfillment_status")
        private String fulfillmentStatus;

        @JsonProperty("gift_card")
        private Boolean giftCard;

        @JsonProperty("grams")
        private Integer grams;

        @JsonProperty("name")
        private String name;

        @JsonProperty("price")
        private String price;

        @JsonProperty("price_set")
        private PriceSet priceSet;

        @JsonProperty("product_exists")
        private Boolean productExists;

        @JsonProperty("product_id")
        private Long productId;

        @JsonProperty("properties")
        private List<Property> properties;

        @JsonProperty("quantity")
        private Integer quantity;

        @JsonProperty("requires_shipping")
        private Boolean requiresShipping;

        @JsonProperty("sales_line_item_group_id")
        private String salesLineItemGroupId;

        @JsonProperty("sku")
        private String sku;

        @JsonProperty("taxable")
        private Boolean taxable;

        @JsonProperty("title")
        private String title;

        @JsonProperty("total_discount")
        private String totalDiscount;

        @JsonProperty("total_discount_set")
        private PriceSet totalDiscountSet;

        @JsonProperty("variant_id")
        private Long variantId;

        @JsonProperty("variant_inventory_management")
        private String variantInventoryManagement;

        @JsonProperty("variant_title")
        private String variantTitle;

        @JsonProperty("vendor")
        private String vendor;

        @JsonProperty("tax_lines")
        private List<TaxLine> taxLines;

        @JsonProperty("duties")
        private List<Duty> duties;

        @JsonProperty("discount_allocations")
        private List<DiscountAllocation> discountAllocations;

        public Long getId() {
            return this.id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getAdminGraphqlApiId() {
            return this.adminGraphqlApiId;
        }

        public void setAdminGraphqlApiId(final String adminGraphqlApiId) {
            this.adminGraphqlApiId = adminGraphqlApiId;
        }

        public List<String> getAttributedStaffs() {
            return this.attributedStaffs;
        }

        public void setAttributedStaffs(final List<String> attributedStaffs) {
            this.attributedStaffs = attributedStaffs;
        }

        public Integer getCurrentQuantity() {
            return this.currentQuantity;
        }

        public void setCurrentQuantity(final Integer currentQuantity) {
            this.currentQuantity = currentQuantity;
        }

        public Integer getFulfillableQuantity() {
            return this.fulfillableQuantity;
        }

        public void setFulfillableQuantity(final Integer fulfillableQuantity) {
            this.fulfillableQuantity = fulfillableQuantity;
        }

        public String getFulfillmentService() {
            return this.fulfillmentService;
        }

        public void setFulfillmentService(final String fulfillmentService) {
            this.fulfillmentService = fulfillmentService;
        }

        public String getFulfillmentStatus() {
            return this.fulfillmentStatus;
        }

        public void setFulfillmentStatus(final String fulfillmentStatus) {
            this.fulfillmentStatus = fulfillmentStatus;
        }

        public Boolean getGiftCard() {
            return this.giftCard;
        }

        public void setGiftCard(final Boolean giftCard) {
            this.giftCard = giftCard;
        }

        public Integer getGrams() {
            return this.grams;
        }

        public void setGrams(final Integer grams) {
            this.grams = grams;
        }

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(final String price) {
            this.price = price;
        }

        public PriceSet getPriceSet() {
            return this.priceSet;
        }

        public void setPriceSet(final PriceSet priceSet) {
            this.priceSet = priceSet;
        }

        public Boolean getProductExists() {
            return this.productExists;
        }

        public void setProductExists(final Boolean productExists) {
            this.productExists = productExists;
        }

        public Long getProductId() {
            return this.productId;
        }

        public void setProductId(final Long productId) {
            this.productId = productId;
        }

        public List<Property> getProperties() {
            return this.properties;
        }

        public void setProperties(final List<Property> properties) {
            this.properties = properties;
        }

        public Integer getQuantity() {
            return this.quantity;
        }

        public void setQuantity(final Integer quantity) {
            this.quantity = quantity;
        }

        public Boolean getRequiresShipping() {
            return this.requiresShipping;
        }

        public void setRequiresShipping(final Boolean requiresShipping) {
            this.requiresShipping = requiresShipping;
        }

        public String getSalesLineItemGroupId() {
            return this.salesLineItemGroupId;
        }

        public void setSalesLineItemGroupId(final String salesLineItemGroupId) {
            this.salesLineItemGroupId = salesLineItemGroupId;
        }

        public String getSku() {
            return this.sku;
        }

        public void setSku(final String sku) {
            this.sku = sku;
        }

        public Boolean getTaxable() {
            return this.taxable;
        }

        public void setTaxable(final Boolean taxable) {
            this.taxable = taxable;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(final String title) {
            this.title = title;
        }

        public String getTotalDiscount() {
            return this.totalDiscount;
        }

        public void setTotalDiscount(final String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public PriceSet getTotalDiscountSet() {
            return this.totalDiscountSet;
        }

        public void setTotalDiscountSet(final PriceSet totalDiscountSet) {
            this.totalDiscountSet = totalDiscountSet;
        }

        public Long getVariantId() {
            return this.variantId;
        }

        public void setVariantId(final Long variantId) {
            this.variantId = variantId;
        }

        public String getVariantInventoryManagement() {
            return this.variantInventoryManagement;
        }

        public void setVariantInventoryManagement(final String variantInventoryManagement) {
            this.variantInventoryManagement = variantInventoryManagement;
        }

        public String getVariantTitle() {
            return this.variantTitle;
        }

        public void setVariantTitle(final String variantTitle) {
            this.variantTitle = variantTitle;
        }

        public String getVendor() {
            return this.vendor;
        }

        public void setVendor(final String vendor) {
            this.vendor = vendor;
        }

        public List<TaxLine> getTaxLines() {
            return this.taxLines;
        }

        public void setTaxLines(final List<TaxLine> taxLines) {
            this.taxLines = taxLines;
        }

        public List<Duty> getDuties() {
            return this.duties;
        }

        public void setDuties(final List<Duty> duties) {
            this.duties = duties;
        }

        public List<DiscountAllocation> getDiscountAllocations() {
            return this.discountAllocations;
        }

        public void setDiscountAllocations(final List<DiscountAllocation> discountAllocations) {
            this.discountAllocations = discountAllocations;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PriceSet {
        @JsonProperty("shop_money")
        private Money shopMoney;

        @JsonProperty("presentment_money")
        private Money presentmentMoney;

        // Getters and Setters

        public static class Money {
            @JsonProperty("amount")
            private String amount;

            @JsonProperty("currency_code")
            private String currencyCode;

            public String getAmount() {
                return this.amount;
            }

            public void setAmount(final String amount) {
                this.amount = amount;
            }

            public String getCurrencyCode() {
                return this.currencyCode;
            }

            public void setCurrencyCode(final String currencyCode) {
                this.currencyCode = currencyCode;
            }
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Property {
        @JsonProperty("name")
        private String name;

        @JsonProperty("value")
        private String value;

        // Getters and Setters
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TaxLine {
        @JsonProperty("channel_liable")
        private Boolean channelLiable;

        @JsonProperty("price")
        private String price;

        @JsonProperty("price_set")
        private PriceSet priceSet;

        @JsonProperty("rate")
        private Double rate;

        @JsonProperty("title")
        private String title;

        public Boolean getChannelLiable() {
            return this.channelLiable;
        }

        public void setChannelLiable(final Boolean channelLiable) {
            this.channelLiable = channelLiable;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(final String price) {
            this.price = price;
        }

        public PriceSet getPriceSet() {
            return this.priceSet;
        }

        public void setPriceSet(final PriceSet priceSet) {
            this.priceSet = priceSet;
        }

        public Double getRate() {
            return this.rate;
        }

        public void setRate(final Double rate) {
            this.rate = rate;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(final String title) {
            this.title = title;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Duty {
        @JsonProperty("duty_id")
        private Long dutyId;

        @JsonProperty("price")
        private String price;

        public Long getDutyId() {
            return this.dutyId;
        }

        public void setDutyId(final Long dutyId) {
            this.dutyId = dutyId;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(final String price) {
            this.price = price;
        }
// Getters and Setters
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DiscountAllocation {
        @JsonProperty("amount")
        private String amount;

        @JsonProperty("discount_application_index")
        private Integer discountApplicationIndex;

        // Getters and Setters
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShippingLine {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("title")
        private String title;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("price")
        private String price;

        // Getters and Setters
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
    }
}