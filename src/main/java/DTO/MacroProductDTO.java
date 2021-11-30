package DTO;

import Constants.ConstantsEnum;
import Constants.ProductPropertiesEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class MacroProductDTO {

    private String id;
    private String variantId;
    private String title;
    private String bodyHtml;
    private String brand;
    private String productType;
    private String createdAt;
    private String updatedAt;
    private String url;
    private String status;
    private String tags;
    private Double price;
    private String sku;
    private String barcode;
    private Double weight;
    private String weightUnit;
    private Boolean requiresShipping;
    private List<ProductImageDTO> images;
    private Integer inventory;
    private Double priceDottVat;
    private Double previousPriceDottVat;
    private Double priceWortenVat;
    private Double previousPriceWortenVat;
    private Double priceAmazonVat;
    private Double previousPriceAmazonVat;
    private Double priceFnacVat;
    private Double previousPriceFnacVat;

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public MacroProductDTO(ProductDTO productDTO){
        this.id = productDTO.getId();
        this.variantId = productDTO.getVariants().get(0).getId();
        this.title = productDTO.getTitle();
        this.bodyHtml = productDTO.getBodyHtml();
        this.brand = productDTO.getBrand();
        this.productType = productDTO.getProductType();
        this.createdAt = productDTO.getCreatedAt();
        this.updatedAt = productDTO.getUpdatedAt();
        this.url = ConstantsEnum.PRODUCT_URL_PREFIX.getConstantValue() + productDTO.getHandle();
        this.status = productDTO.getStatus();
        this.tags = productDTO.getTags();
        this.price = productDTO.getVariants().get(0).getPrice();
        this.sku = productDTO.getVariants().get(0).getSku();
        this.barcode = productDTO.getVariants().get(0).getBarcode();
        this.weight = productDTO.getVariants().get(0).getWeight();
        this.weightUnit = productDTO.getVariants().get(0).getWeightUnit();
        this.requiresShipping = productDTO.getVariants().get(0).getRequiresShipping();
        this.images = productDTO.getImages();
        this.inventory = productDTO.getVariants().get(0).getInventoryQuantity();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MacroProductDTO(List<Object> productObject){
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
        if (productObject.get(ProductPropertiesEnum.ID.getColumn_number()) != null){
            this.id = (String) productObject.get(ProductPropertiesEnum.ID.getColumn_number());
        } else {
            this.id = null;
        }

        if (productObject.get(ProductPropertiesEnum.VARIANT_ID.getColumn_number()) != null){
            this.variantId = (String) productObject.get(ProductPropertiesEnum.VARIANT_ID.getColumn_number());
        } else {
            this.variantId = null;
        }

        if (productObject.get(ProductPropertiesEnum.TITLE.getColumn_number()) != null){
            this.title =(String) productObject.get(ProductPropertiesEnum.TITLE.getColumn_number());
        } else {
            this.title = null;
        }

        if (productObject.get(ProductPropertiesEnum.BODY_HTML.getColumn_number()) != null){
            this.bodyHtml = (String) productObject.get(ProductPropertiesEnum.BODY_HTML.getColumn_number());
        } else {
            this.bodyHtml = null;
        }

        if (productObject.get(ProductPropertiesEnum.BRAND.getColumn_number()) != null){
            this.brand = (String) productObject.get(ProductPropertiesEnum.BRAND.getColumn_number());
        } else {
            this.brand = null;
        }

        if (productObject.get(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_number()) != null){
            this.productType = (String) productObject.get(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_number());
        } else {
            this.productType = null;
        }

        if (productObject.get(ProductPropertiesEnum.CREATED_AT.getColumn_number()) != null){
            this.createdAt = (String) productObject.get(ProductPropertiesEnum.CREATED_AT.getColumn_number());
        } else {
            this.createdAt = null;
        }

        if (productObject.get(ProductPropertiesEnum.UPDATED_AT.getColumn_number()) != null){
            this.updatedAt = (String) productObject.get(ProductPropertiesEnum.UPDATED_AT.getColumn_number());
        } else {
            this.updatedAt = null;
        }

        if (productObject.get(ProductPropertiesEnum.URL.getColumn_number()) != null){
            this.url = (String) productObject.get(ProductPropertiesEnum.URL.getColumn_number());
        } else {
            this.url = null;
        }

        if (productObject.get(ProductPropertiesEnum.STATUS.getColumn_number()) != null){
            this.status = (String) productObject.get(ProductPropertiesEnum.STATUS.getColumn_number());
        } else {
            this.status = null;
        }

        if (productObject.get(ProductPropertiesEnum.TAGS.getColumn_number()) != null){
            this.tags = (String) productObject.get(ProductPropertiesEnum.TAGS.getColumn_number());
        } else {
            this.tags = null;
        }

        if (productObject.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_number()) != null){
            try {
                this.price = numberFormat.parse(productObject.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.price = null;
            }
        } else {
            this.price = null;
        }

        if (productObject.get(ProductPropertiesEnum.SKU.getColumn_number()) != null){
            this.sku = (String) productObject.get(ProductPropertiesEnum.SKU.getColumn_number());
        } else {
            this.sku = null;
        }

        if (productObject.get(ProductPropertiesEnum.BARCODE.getColumn_number()) != null){
            this.barcode = (String) productObject.get(ProductPropertiesEnum.BARCODE.getColumn_number());
        } else {
            this.barcode = null;
        }

        if (productObject.get(ProductPropertiesEnum.WEIGHT.getColumn_number()) != null){
            try {
                this.weight = numberFormat.parse(productObject.get(ProductPropertiesEnum.WEIGHT.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.weight = null;
            }
        } else {
            this.weight = null;
        }

        if (productObject.get(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_number()) != null){
            this.weightUnit = (String) productObject.get(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_number());
        } else {
            this.weightUnit = "g";
        }

        if (productObject.get(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_number()) != null){
            this.requiresShipping = Boolean.parseBoolean(productObject.get(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_number()).toString());
        } else {
            this.requiresShipping = true;
        }

        if (productObject.get(ProductPropertiesEnum.IMAGES.getColumn_number()) != null){
            String jsonObject = (String) productObject.get(ProductPropertiesEnum.IMAGES.getColumn_number());
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<ProductImageDTO>> typeReference = new TypeReference<List<ProductImageDTO>>(){};
            try {
                this.images = objectMapper.readValue(jsonObject,typeReference);
            } catch (JsonProcessingException e) {
                this.images = null;
            }
        } else {
            this.images = null;
        }

        if (productObject.get(ProductPropertiesEnum.INVENTORY.getColumn_number()) != null){
            try {
                this.inventory = numberFormat.parse(productObject.get(ProductPropertiesEnum.INVENTORY.getColumn_number()).toString()).intValue();
            } catch (ParseException e) {
                this.inventory = 0;
            }
        } else {
            this.inventory = 0;
        }

        if (productObject.get(ProductPropertiesEnum.PRICE_DOTT.getColumn_number()) != null){
            try {
                this.priceDottVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PRICE_DOTT.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.priceDottVat = null;
            }
        } else {
            this.priceDottVat = null;
        }

        if (productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_number()) != null){
            try {
                this.previousPriceDottVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.previousPriceDottVat = null;
            }
        } else {
            this.previousPriceDottVat = null;
        }

        if (productObject.get(ProductPropertiesEnum.PRICE_WORTEN.getColumn_number()) != null){
            try {
                this.priceWortenVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PRICE_WORTEN.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.priceWortenVat = null;
            }
        } else {
            this.priceWortenVat = null;
        }

        if (productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_number()) != null){
            try {
                this.previousPriceWortenVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.previousPriceWortenVat = null;
            }
        } else {
            this.previousPriceWortenVat = null;
        }

        if (productObject.get(ProductPropertiesEnum.PRICE_AMAZON.getColumn_number()) != null){
            try {
                this.priceAmazonVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PRICE_AMAZON.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.priceAmazonVat = null;
            }
        } else {
            this.priceAmazonVat = null;
        }

        if (productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_number()) != null){
            try {
                this.previousPriceAmazonVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.previousPriceAmazonVat = null;
            }
        } else {
            this.previousPriceAmazonVat = null;
        }

        if (productObject.get(ProductPropertiesEnum.PRICE_FNAC.getColumn_number()) != null){
            try {
                this.priceFnacVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PRICE_FNAC.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.priceFnacVat = null;
            }
        } else {
            this.priceFnacVat = null;
        }

        if (productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_number()) != null){
            try {
                this.previousPriceFnacVat = numberFormat.parse(productObject.get(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_number()).toString()).doubleValue();
            } catch (ParseException e) {
                this.previousPriceFnacVat = null;
            }
        } else {
            this.previousPriceFnacVat = null;
        }

    }
    public Map<String, Object> getProductMap(){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ProductPropertiesEnum.ID.getColumn_name(), id);
        map.put(ProductPropertiesEnum.VARIANT_ID.getColumn_name(), variantId);
        map.put(ProductPropertiesEnum.TITLE.getColumn_name(), title);
        map.put(ProductPropertiesEnum.BODY_HTML.getColumn_name(), bodyHtml);
        map.put(ProductPropertiesEnum.BRAND.getColumn_name(), brand);
        map.put(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_name(), productType);
        map.put(ProductPropertiesEnum.CREATED_AT.getColumn_name(), createdAt);
        map.put(ProductPropertiesEnum.UPDATED_AT.getColumn_name(), updatedAt);
        map.put(ProductPropertiesEnum.URL.getColumn_name(), url);
        map.put(ProductPropertiesEnum.STATUS.getColumn_name(), status);
        map.put(ProductPropertiesEnum.TAGS.getColumn_name(), tags);
        map.put(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name(), price);
        map.put(ProductPropertiesEnum.SKU.getColumn_name(), sku);
        map.put(ProductPropertiesEnum.BARCODE.getColumn_name(), barcode);
        map.put(ProductPropertiesEnum.WEIGHT.getColumn_name(), weight);
        map.put(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_name(), weightUnit);
        map.put(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_name(), requiresShipping);
        map.put(ProductPropertiesEnum.IMAGES.getColumn_name(), images);
        map.put(ProductPropertiesEnum.INVENTORY.getColumn_name(), inventory);
        map.put(ProductPropertiesEnum.PRICE_DOTT.getColumn_name(), priceDottVat);
        map.put(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_name(), previousPriceDottVat);
        map.put(ProductPropertiesEnum.PRICE_WORTEN.getColumn_name(), priceWortenVat);
        map.put(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_name(), previousPriceWortenVat);
        map.put(ProductPropertiesEnum.PRICE_AMAZON.getColumn_name(), priceAmazonVat);
        map.put(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_name(), previousPriceAmazonVat);
        map.put(ProductPropertiesEnum.PRICE_FNAC.getColumn_name(), priceFnacVat);
        map.put(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_name(), previousPriceFnacVat);


        return map;
    }

    public MacroProductDTO(String id, String variantId, String title, String bodyHtml, String brand, String productType, String createdAt, String updatedAt, String url, String status, String tags, Double price, String sku, String barcode, Double weight, String weightUnit, Boolean requiresShipping, List<ProductImageDTO> images, Integer inventory, Double priceDottVat, Double previousPriceDottVat, Double priceWortenVat, Double previousPriceWortenVat, Double priceAmazonVat, Double previousPriceAmazonVat, Double priceFnacVat, Double previousPriceFnacVat) {
        this.id = id;
        this.variantId = variantId;
        this.title = title;
        this.bodyHtml = bodyHtml;
        this.brand = brand;
        this.productType = productType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.url = url;
        this.status = status;
        this.tags = tags;
        this.price = price;
        this.sku = sku;
        this.barcode = barcode;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.requiresShipping = requiresShipping;
        this.images = images;
        this.inventory = inventory;
        this.priceDottVat = priceDottVat;
        this.previousPriceDottVat = previousPriceDottVat;
        this.priceWortenVat = priceWortenVat;
        this.previousPriceWortenVat = previousPriceWortenVat;
        this.priceAmazonVat = priceAmazonVat;
        this.previousPriceAmazonVat = previousPriceAmazonVat;
        this.priceFnacVat = priceFnacVat;
        this.previousPriceFnacVat = previousPriceFnacVat;
    }

    public static MacroProductDTO updateProduct (MacroProductDTO original, MacroProductDTO update ){
        String id = update.getId();
        String variantId = original.getVariantId().equals(update.getVariantId()) ? original.getVariantId() : update.getVariantId();
        String title = original.getTitle().equals(update.getTitle()) ? original.getTitle() : update.getTitle();
        String bodyHtml = original.getBodyHtml().equals(update.getBodyHtml()) ? original.getBodyHtml() : update.getBodyHtml();
        String brand = original.getBrand().equals(update.getBrand()) ? original.getBrand() : update.getBrand();
        String productType = original.getProductType().equals(update.getProductType()) ? original.getProductType() : update.getProductType();
        String createdAt = original.getCreatedAt().equals(update.getCreatedAt()) ? original.getCreatedAt() : update.getCreatedAt();
        String updatedAt = original.getUpdatedAt().equals(update.getUpdatedAt()) ? original.getUpdatedAt() : update.getUpdatedAt();
        String handle = original.getUrl().equals(update.getUrl()) ? original.getUrl() : update.getUrl();
        String status = original.getStatus().equals(update.getStatus()) ? original.getStatus() : update.getTitle();
        String tags = original.getTags().equals(update.getTags()) ? original.getTags() : update.getTags();
        Double price = original.getPrice() == update.getPrice() ? original.getPrice() : update.getPrice();
        String sku = original.getSku().equals(update.getSku()) ? original.getSku() : update.getSku();
        String barcode = original.getBarcode().equals(update.getBarcode()) ? original.getBarcode() : update.getBarcode();
        Double weight = original.getWeight() == update.getWeight() ? original.getWeight() : update.getWeight();
        String weightUnit = original.getWeightUnit().equals(update.getWeightUnit()) ? original.getWeightUnit() : update.getWeightUnit();
        Boolean requiresShipping = original.getRequiresShipping() == update.getRequiresShipping() ? original.getRequiresShipping() : update.getRequiresShipping();
        List<ProductImageDTO> images = original.getImages() == update.getImages() ? original.getImages() : update.getImages();
        Integer inventory = original.getInventory() == update.getInventory() ? original.getInventory() : update.getInventory();
        Double priceDottVat = original.getPriceDottVat();
        Double previousPriceDottVat = original.getPreviousPriceDottVat();
        Double priceWortenVat = original.getPriceWortenVat();
        Double previousPriceWortenVat = original.getPreviousPriceWortenVat();
        Double priceAmazonVat = original.getPriceAmazonVat();
        Double previousPriceAmazonVat = original.getPreviousPriceAmazonVat();
        Double priceFnacVat = original.getPriceFnacVat();
        Double previousPriceFnacVat = original.getPreviousPriceFnacVat();
        return new MacroProductDTO(id, variantId, title, bodyHtml, brand, productType, createdAt, updatedAt, handle, status, tags, price, sku, barcode,weight, weightUnit, requiresShipping, images, inventory, priceDottVat, previousPriceDottVat, priceWortenVat, previousPriceWortenVat, priceAmazonVat, previousPriceAmazonVat, priceFnacVat, previousPriceFnacVat);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Boolean getRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(Boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    public List<ProductImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ProductImageDTO> images) {
        this.images = images;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Double getPriceDottVat() {
        return priceDottVat;
    }

    public void setPriceDottVat(Double priceDottVat) {
        this.priceDottVat = priceDottVat;
    }

    public Double getPreviousPriceDottVat() {
        return previousPriceDottVat;
    }

    public void setPreviousPriceDottVat(Double previousPriceDottVat) {
        this.previousPriceDottVat = previousPriceDottVat;
    }

    public Double getPriceWortenVat() {
        return priceWortenVat;
    }

    public void setPriceWortenVat(Double priceWortenVat) {
        this.priceWortenVat = priceWortenVat;
    }

    public Double getPreviousPriceWortenVat() {
        return previousPriceWortenVat;
    }

    public void setPreviousPriceWortenVat(Double previousPriceWortenVat) {
        this.previousPriceWortenVat = previousPriceWortenVat;
    }

    public Double getPriceAmazonVat() {
        return priceAmazonVat;
    }

    public void setPriceAmazonVat(Double priceAmazonVat) {
        this.priceAmazonVat = priceAmazonVat;
    }

    public Double getPreviousPriceAmazonVat() {
        return previousPriceAmazonVat;
    }

    public void setPreviousPriceAmazonVat(Double previousPriceAmazonVat) {
        this.previousPriceAmazonVat = previousPriceAmazonVat;
    }

    public Double getPriceFnacVat() {
        return priceFnacVat;
    }

    public void setPriceFnacVat(Double priceFnacVat) {
        this.priceFnacVat = priceFnacVat;
    }

    public Double getPreviousPriceFnacVat() {
        return previousPriceFnacVat;
    }

    public void setPreviousPriceFnacVat(Double previousPriceFnacVat) {
        this.previousPriceFnacVat = previousPriceFnacVat;
    }


}
