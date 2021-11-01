package Services;

import Constants.ConstantsEnum;
import Constants.DottPropertiesEnum;
import Constants.KuantoKustaPropertiesEnum;
import Constants.ProductPropertiesEnum;
import DTO.ProductDTO;
import DTO.MacroProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import javax.crypto.Mac;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class SheetsServiceUtil {
    private static final String APPLICATION_NAME = "Smartify your Sheets";

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleAuthorizeUtil.authorize();
        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    public SheetsServiceUtil() {
    }

    public static Map<String, MacroProductDTO> getProductList (ValueRange valueRange){
        Map<String, MacroProductDTO> productList = new LinkedHashMap<>();

        if (valueRange.getValues() != null){
            for (List<Object> product : valueRange.getValues()){
                MacroProductDTO macroProductDTO = new MacroProductDTO(product);
                productList.put(macroProductDTO.getId(), macroProductDTO);
            }
        }
        return productList;
    }

    public static List<List<Object>> getMainSheetValues(Map<String, MacroProductDTO> originalProductList, List<ProductDTO> toUpdateProductsList){
        List<List<Object>> _values = new ArrayList<>();

        _values.add(mainSheetHeaderRow());

        for (ProductDTO product : toUpdateProductsList){
            if (originalProductList.containsKey(product.getId())){
                Map<String, Object> properties = MacroProductDTO.updateProduct(originalProductList.get(product.getId()), new MacroProductDTO(product)).getProductMap();
                _values.add(mainSheetProductRow(properties));
            } else {
                Map<String, Object> properties = new MacroProductDTO(product).getProductMap();
                _values.add(mainSheetProductRow(properties));
            }

        }
        return _values;
    }

    public static List<List<Object>> getKuantokustaSheetValues(Map<String, MacroProductDTO> originalProductList){
        List<List<Object>> _values = new ArrayList<>();
        _values.add(kuantokustaSheetHeaderRow());
        for (Map.Entry<String, MacroProductDTO> obj : originalProductList.entrySet()){
            if(obj.getValue().getStatus().equals("active") && isProductSellable(obj)){
                _values.add(kuantokustaSheetProductRow(obj.getValue()));
            }
        }

        return _values;
    }

    public static List<List<Object>> getDottSheetValues(Map<String, MacroProductDTO> originalProductList){
        List<List<Object>> _values = new ArrayList<>();
        _values.add(dottSheetHeaderRow());
        for (Map.Entry<String, MacroProductDTO> obj : originalProductList.entrySet()){
            if(obj.getValue().getStatus().equals("active") && isProductSellable(obj)){
                _values.add(dottSheetProductRow(obj.getValue()));
            }
        }

        return _values;
    }

    private static boolean isProductSellable (Map.Entry<String, MacroProductDTO> product){
        String[] nonSellableProductsId = ConstantsEnum.PRODUCTS_NOT_FOR_FEED.getConstantValue().toString().split(",");
        if (product.getValue().getTitle().toLowerCase(Locale.ROOT).contains("pré-venda") ||
                product.getValue().getTitle().toLowerCase(Locale.ROOT).contains("pre-venda"))   {
            return false;
        }
        for (String id : nonSellableProductsId){
            if (product.getKey().equals(id)){
                return false;
            }
        }
        return true;
    }

    private static List<Object> dottSheetHeaderRow(){
        List<Object> headers = startDottSheetRow();

        headers.set(DottPropertiesEnum.ID.getColumn_number(), DottPropertiesEnum.ID.getColumn_name());
        headers.set(DottPropertiesEnum.TITLE.getColumn_number(), DottPropertiesEnum.TITLE.getColumn_name());
        headers.set(DottPropertiesEnum.BODY_HTML.getColumn_number(), DottPropertiesEnum.BODY_HTML.getColumn_name());
        headers.set(DottPropertiesEnum.BRAND.getColumn_number(), DottPropertiesEnum.BRAND.getColumn_name());
        headers.set(DottPropertiesEnum.PRODUCT_TYPE.getColumn_number(), DottPropertiesEnum.PRODUCT_TYPE.getColumn_name());
        headers.set(DottPropertiesEnum.CREATED_AT.getColumn_number(), DottPropertiesEnum.CREATED_AT.getColumn_name());
        headers.set(DottPropertiesEnum.UPDATED_AT.getColumn_number(), DottPropertiesEnum.UPDATED_AT.getColumn_name());
        headers.set(DottPropertiesEnum.TAGS.getColumn_number(), DottPropertiesEnum.TAGS.getColumn_name());
        headers.set(DottPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), DottPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_name());
        headers.set(DottPropertiesEnum.PRICE_WITH_VAT.getColumn_number(), DottPropertiesEnum.PRICE_WITH_VAT.getColumn_name());
        headers.set(DottPropertiesEnum.PREVIOUS_PRICE.getColumn_number(), DottPropertiesEnum.PREVIOUS_PRICE.getColumn_name());
        headers.set(DottPropertiesEnum.SKU.getColumn_number(), DottPropertiesEnum.SKU.getColumn_name());
        headers.set(DottPropertiesEnum.BARCODE.getColumn_number(), DottPropertiesEnum.BARCODE.getColumn_name());
        headers.set(DottPropertiesEnum.WEIGHT.getColumn_number(), DottPropertiesEnum.WEIGHT.getColumn_name());
        headers.set(DottPropertiesEnum.WEIGHT_UNIT.getColumn_number(), DottPropertiesEnum.WEIGHT_UNIT.getColumn_name());
        headers.set(DottPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(), DottPropertiesEnum.REQUIRES_SHIPPING.getColumn_name());
        headers.set(DottPropertiesEnum.IMAGE_1_URL.getColumn_number(), DottPropertiesEnum.IMAGE_1_URL.getColumn_name());
        headers.set(DottPropertiesEnum.IMAGE_2_URL.getColumn_number(), DottPropertiesEnum.IMAGE_2_URL.getColumn_name());
        headers.set(DottPropertiesEnum.IMAGE_3_URL.getColumn_number(), DottPropertiesEnum.IMAGE_3_URL.getColumn_name());
        headers.set(DottPropertiesEnum.INVENTORY.getColumn_number(), DottPropertiesEnum.INVENTORY.getColumn_name());
        return headers;
    }

    private static List<Object> dottSheetProductRow(MacroProductDTO macroProductDTO){
        List<Object> productRow = startDottSheetRow();

        if (macroProductDTO.getId() != null) {
            productRow.set(DottPropertiesEnum.ID.getColumn_number(), macroProductDTO.getId());
        }

        if (macroProductDTO.getTitle() != null) {
            productRow.set(DottPropertiesEnum.TITLE.getColumn_number(), macroProductDTO.getTitle());
        }

        if (macroProductDTO.getBodyHtml() != null) {
            productRow.set(DottPropertiesEnum.BODY_HTML.getColumn_number(), macroProductDTO.getBodyHtml());

        }

        if(macroProductDTO.getBrand() != null){
            productRow.set(DottPropertiesEnum.BRAND.getColumn_number(), macroProductDTO.getBrand());
        }

        if(macroProductDTO.getProductType() != null){
            productRow.set(DottPropertiesEnum.PRODUCT_TYPE.getColumn_number(), macroProductDTO.getProductType());
        }

        if(macroProductDTO.getCreatedAt() != null){
            productRow.set(DottPropertiesEnum.CREATED_AT.getColumn_number(), macroProductDTO.getCreatedAt());
        }

        if(macroProductDTO.getUpdatedAt() != null){
            productRow.set(DottPropertiesEnum.UPDATED_AT.getColumn_number(), macroProductDTO.getUpdatedAt());
        }

        if(macroProductDTO.getTags() != null){
            productRow.set(DottPropertiesEnum.TAGS.getColumn_number(), macroProductDTO.getTags());
        }

        if(macroProductDTO.getPriceDottVat() != null){
            productRow.set(DottPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), macroProductDTO.getPriceDottVat()/Double.parseDouble(ConstantsEnum.VAT_EXCLUDE.getConstantValue().toString()));
            productRow.set(DottPropertiesEnum.PRICE_WITH_VAT.getColumn_number(), macroProductDTO.getPriceDottVat());
        } else {
            productRow.set(DottPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), macroProductDTO.getPrice()/Double.parseDouble(ConstantsEnum.VAT_EXCLUDE.getConstantValue().toString()));
            productRow.set(DottPropertiesEnum.PRICE_WITH_VAT.getColumn_number(), macroProductDTO.getPrice());
        }

        if(macroProductDTO.getPreviousPriceDottVat() != null){
            productRow.set(DottPropertiesEnum.PREVIOUS_PRICE.getColumn_number(), macroProductDTO.getPreviousPriceDottVat());
        }

        if(macroProductDTO.getSku() != null){
            productRow.set(DottPropertiesEnum.SKU.getColumn_number(), macroProductDTO.getSku());
        }

        if(macroProductDTO.getBarcode() != null) {
            productRow.set(DottPropertiesEnum.BARCODE.getColumn_number(), macroProductDTO.getBarcode());
        }

        if (macroProductDTO.getWeight() != null){
            productRow.set(DottPropertiesEnum.WEIGHT.getColumn_number(), macroProductDTO.getWeight());
        }

        if (macroProductDTO.getWeightUnit() != null){
            productRow.set(DottPropertiesEnum.WEIGHT_UNIT.getColumn_number(), macroProductDTO.getWeightUnit());
        }

        if (macroProductDTO.getRequiresShipping() != null){
            productRow.set(DottPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(), macroProductDTO.getRequiresShipping());
        }

        if (macroProductDTO.getImages().size() >= 3){
            productRow.set(DottPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
            productRow.set(DottPropertiesEnum.IMAGE_2_URL.getColumn_number(), macroProductDTO.getImages().get(1).getSrc());
            productRow.set(DottPropertiesEnum.IMAGE_3_URL.getColumn_number(), macroProductDTO.getImages().get(2).getSrc());
        } else if (macroProductDTO.getImages().size() == 2) {
            productRow.set(DottPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
            productRow.set(DottPropertiesEnum.IMAGE_2_URL.getColumn_number(), macroProductDTO.getImages().get(1).getSrc());
        } else if (macroProductDTO.getImages().size() == 1) {
            productRow.set(DottPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
        }

        if (macroProductDTO.getInventory() != null){
            productRow.set(DottPropertiesEnum.INVENTORY.getColumn_number(), macroProductDTO.getInventory());
        }

        return productRow;
    }

    private static List<Object> kuantokustaSheetHeaderRow(){
        List<Object> headers = startKuantokustaSheetRow();

        headers.set(KuantoKustaPropertiesEnum.ID.getColumn_number(), KuantoKustaPropertiesEnum.ID.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.TITLE.getColumn_number(), KuantoKustaPropertiesEnum.TITLE.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.BODY_HTML.getColumn_number(), KuantoKustaPropertiesEnum.BODY_HTML.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.BRAND.getColumn_number(), KuantoKustaPropertiesEnum.BRAND.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.PRODUCT_TYPE.getColumn_number(), KuantoKustaPropertiesEnum.PRODUCT_TYPE.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.CREATED_AT.getColumn_number(), KuantoKustaPropertiesEnum.CREATED_AT.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.UPDATED_AT.getColumn_number(), KuantoKustaPropertiesEnum.UPDATED_AT.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.URL.getColumn_number(), KuantoKustaPropertiesEnum.URL.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.TAGS.getColumn_number(), KuantoKustaPropertiesEnum.TAGS.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), KuantoKustaPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.PRICE_WITH_VAT.getColumn_number(), KuantoKustaPropertiesEnum.PRICE_WITH_VAT.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.SKU.getColumn_number(), KuantoKustaPropertiesEnum.SKU.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.BARCODE.getColumn_number(), KuantoKustaPropertiesEnum.BARCODE.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.WEIGHT.getColumn_number(), KuantoKustaPropertiesEnum.WEIGHT.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.WEIGHT_UNIT.getColumn_number(), KuantoKustaPropertiesEnum.WEIGHT_UNIT.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(), KuantoKustaPropertiesEnum.REQUIRES_SHIPPING.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.IMAGE_1_URL.getColumn_number(), KuantoKustaPropertiesEnum.IMAGE_1_URL.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.IMAGE_2_URL.getColumn_number(), KuantoKustaPropertiesEnum.IMAGE_2_URL.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.IMAGE_3_URL.getColumn_number(), KuantoKustaPropertiesEnum.IMAGE_3_URL.getColumn_name());
        headers.set(KuantoKustaPropertiesEnum.INVENTORY.getColumn_number(), KuantoKustaPropertiesEnum.INVENTORY.getColumn_name());
        return headers;
    }

    private static List<Object> kuantokustaSheetProductRow(MacroProductDTO macroProductDTO){
        List<Object> productRow = startKuantokustaSheetRow();

        if (macroProductDTO.getId() != null) {
            productRow.set(KuantoKustaPropertiesEnum.ID.getColumn_number(), macroProductDTO.getId());
        }

        if (macroProductDTO.getTitle() != null) {
            productRow.set(KuantoKustaPropertiesEnum.TITLE.getColumn_number(), macroProductDTO.getTitle());
        }

        if (macroProductDTO.getBodyHtml() != null) {
            productRow.set(KuantoKustaPropertiesEnum.BODY_HTML.getColumn_number(), macroProductDTO.getBodyHtml());

        }

        if(macroProductDTO.getBrand() != null){
            productRow.set(KuantoKustaPropertiesEnum.BRAND.getColumn_number(), macroProductDTO.getBrand());
        }

        if(macroProductDTO.getProductType() != null){
            productRow.set(KuantoKustaPropertiesEnum.PRODUCT_TYPE.getColumn_number(), macroProductDTO.getProductType());
        }

        if(macroProductDTO.getCreatedAt() != null){
            productRow.set(KuantoKustaPropertiesEnum.CREATED_AT.getColumn_number(), macroProductDTO.getCreatedAt());
        }

        if(macroProductDTO.getUpdatedAt() != null){
            productRow.set(KuantoKustaPropertiesEnum.UPDATED_AT.getColumn_number(), macroProductDTO.getUpdatedAt());
        }

        if(macroProductDTO.getUrl() != null){
            productRow.set(KuantoKustaPropertiesEnum.URL.getColumn_number(), macroProductDTO.getUrl());
        }

        if(macroProductDTO.getTags() != null){
            productRow.set(KuantoKustaPropertiesEnum.TAGS.getColumn_number(), macroProductDTO.getTags());
        }

        if(macroProductDTO.getPrice() != null){
            productRow.set(KuantoKustaPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), macroProductDTO.getPrice()/Double.parseDouble(ConstantsEnum.VAT_EXCLUDE.getConstantValue().toString()));
            productRow.set(KuantoKustaPropertiesEnum.PRICE_WITH_VAT.getColumn_number(), macroProductDTO.getPrice());
        }

        if(macroProductDTO.getSku() != null){
            productRow.set(KuantoKustaPropertiesEnum.SKU.getColumn_number(), macroProductDTO.getSku());
        }

        if(macroProductDTO.getBarcode() != null) {
            productRow.set(KuantoKustaPropertiesEnum.BARCODE.getColumn_number(), macroProductDTO.getBarcode());
        }

        if (macroProductDTO.getWeight() != null){
            productRow.set(KuantoKustaPropertiesEnum.WEIGHT.getColumn_number(), macroProductDTO.getWeight());
        }

        if (macroProductDTO.getWeightUnit() != null){
            productRow.set(KuantoKustaPropertiesEnum.WEIGHT_UNIT.getColumn_number(), macroProductDTO.getWeightUnit());
        }

        if (macroProductDTO.getRequiresShipping() != null){
            productRow.set(KuantoKustaPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(), macroProductDTO.getRequiresShipping());
        }

        if (macroProductDTO.getImages().size() >= 3){
            productRow.set(KuantoKustaPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
            productRow.set(KuantoKustaPropertiesEnum.IMAGE_2_URL.getColumn_number(), macroProductDTO.getImages().get(1).getSrc());
            productRow.set(KuantoKustaPropertiesEnum.IMAGE_3_URL.getColumn_number(), macroProductDTO.getImages().get(2).getSrc());
        } else if (macroProductDTO.getImages().size() == 2) {
            productRow.set(KuantoKustaPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
            productRow.set(KuantoKustaPropertiesEnum.IMAGE_2_URL.getColumn_number(), macroProductDTO.getImages().get(1).getSrc());
        } else if (macroProductDTO.getImages().size() == 1) {
            productRow.set(KuantoKustaPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
        }

        if (macroProductDTO.getInventory() != null){
            productRow.set(KuantoKustaPropertiesEnum.INVENTORY.getColumn_number(), macroProductDTO.getInventory());
        }

        return productRow;
    }

    private static List<Object> mainSheetHeaderRow(){
        List<Object> headers = startMainSheetRow();

        headers.set(ProductPropertiesEnum.ID.getColumn_number(),ProductPropertiesEnum.ID.getColumn_name());
        headers.set(ProductPropertiesEnum.VARIANT_ID.getColumn_number(), ProductPropertiesEnum.VARIANT_ID.getColumn_name());
        headers.set(ProductPropertiesEnum.TITLE.getColumn_number(),ProductPropertiesEnum.TITLE.getColumn_name());
        headers.set(ProductPropertiesEnum.BODY_HTML.getColumn_number(),ProductPropertiesEnum.BODY_HTML.getColumn_name());
        headers.set(ProductPropertiesEnum.BRAND.getColumn_number(),ProductPropertiesEnum.BRAND.getColumn_name());
        headers.set(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_number(),ProductPropertiesEnum.PRODUCT_TYPE.getColumn_name());
        headers.set(ProductPropertiesEnum.CREATED_AT.getColumn_number(),ProductPropertiesEnum.CREATED_AT.getColumn_name());
        headers.set(ProductPropertiesEnum.UPDATED_AT.getColumn_number(),ProductPropertiesEnum.UPDATED_AT.getColumn_name());
        headers.set(ProductPropertiesEnum.URL.getColumn_number(),ProductPropertiesEnum.URL.getColumn_name());
        headers.set(ProductPropertiesEnum.STATUS.getColumn_number(),ProductPropertiesEnum.STATUS.getColumn_name());
        headers.set(ProductPropertiesEnum.TAGS.getColumn_number(),ProductPropertiesEnum.TAGS.getColumn_name());
        headers.set(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_number(),ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name());
        headers.set(ProductPropertiesEnum.SKU.getColumn_number(),ProductPropertiesEnum.SKU.getColumn_name());
        headers.set(ProductPropertiesEnum.BARCODE.getColumn_number(),ProductPropertiesEnum.BARCODE.getColumn_name());
        headers.set(ProductPropertiesEnum.WEIGHT.getColumn_number(),ProductPropertiesEnum.WEIGHT.getColumn_name());
        headers.set(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_number(),ProductPropertiesEnum.WEIGHT_UNIT.getColumn_name());
        headers.set(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(),ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_name());
        headers.set(ProductPropertiesEnum.IMAGES.getColumn_number(),ProductPropertiesEnum.IMAGES.getColumn_name());
        headers.set(ProductPropertiesEnum.INVENTORY.getColumn_number(),ProductPropertiesEnum.INVENTORY.getColumn_name());
        headers.set(ProductPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), ProductPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_name());
        headers.set(ProductPropertiesEnum.PRICE_DOTT.getColumn_number(), ProductPropertiesEnum.PRICE_DOTT.getColumn_name());
        headers.set(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_number(), ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_name());
        headers.set(ProductPropertiesEnum.PRICE_WORTEN.getColumn_number(), ProductPropertiesEnum.PRICE_WORTEN.getColumn_name());
        headers.set(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_number(), ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_name());
        headers.set(ProductPropertiesEnum.PRICE_AMAZON.getColumn_number(), ProductPropertiesEnum.PRICE_AMAZON.getColumn_name());
        headers.set(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_number(), ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_name());
        headers.set(ProductPropertiesEnum.PRICE_FNAC.getColumn_number(), ProductPropertiesEnum.PRICE_FNAC.getColumn_name());
        headers.set(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_number(), ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_name());

        return headers;
    }

    private static  List<Object> mainSheetProductRow(Map<String, Object> productDTO){
        List<Object> productRow = startMainSheetRow();

        if (productDTO.containsKey(ProductPropertiesEnum.ID.getColumn_name()) && productDTO.get(ProductPropertiesEnum.ID.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.ID.getColumn_number(),productDTO.get(ProductPropertiesEnum.ID.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.VARIANT_ID.getColumn_name()) && productDTO.get(ProductPropertiesEnum.VARIANT_ID.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.VARIANT_ID.getColumn_number(),productDTO.get(ProductPropertiesEnum.VARIANT_ID.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.TITLE.getColumn_name()) && productDTO.get(ProductPropertiesEnum.TITLE.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.TITLE.getColumn_number(),productDTO.get(ProductPropertiesEnum.TITLE.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.BODY_HTML.getColumn_name()) && productDTO.get(ProductPropertiesEnum.BODY_HTML.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.BODY_HTML.getColumn_number(),productDTO.get(ProductPropertiesEnum.BODY_HTML.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.BRAND.getColumn_name()) && productDTO.get(ProductPropertiesEnum.BODY_HTML.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.BRAND.getColumn_number(),productDTO.get(ProductPropertiesEnum.BRAND.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_number(),productDTO.get(ProductPropertiesEnum.PRODUCT_TYPE.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.CREATED_AT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.CREATED_AT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.CREATED_AT.getColumn_number(),productDTO.get(ProductPropertiesEnum.CREATED_AT.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.UPDATED_AT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.UPDATED_AT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.UPDATED_AT.getColumn_number(),productDTO.get(ProductPropertiesEnum.UPDATED_AT.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.URL.getColumn_name()) && productDTO.get(ProductPropertiesEnum.URL.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.URL.getColumn_number(),productDTO.get(ProductPropertiesEnum.URL.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.STATUS.getColumn_name()) && productDTO.get(ProductPropertiesEnum.STATUS.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.STATUS.getColumn_number(),productDTO.get(ProductPropertiesEnum.STATUS.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.TAGS.getColumn_name()) && productDTO.get(ProductPropertiesEnum.TAGS.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.TAGS.getColumn_number(),productDTO.get(ProductPropertiesEnum.TAGS.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_number(),productDTO.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.SKU.getColumn_name()) && productDTO.get(ProductPropertiesEnum.SKU.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.SKU.getColumn_number(),productDTO.get(ProductPropertiesEnum.SKU.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.BARCODE.getColumn_name()) && productDTO.get(ProductPropertiesEnum.BARCODE.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.BARCODE.getColumn_number(),productDTO.get(ProductPropertiesEnum.BARCODE.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.WEIGHT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.WEIGHT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.WEIGHT.getColumn_number(),productDTO.get(ProductPropertiesEnum.WEIGHT.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_number(),productDTO.get(ProductPropertiesEnum.WEIGHT_UNIT.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_name()) && productDTO.get(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(),productDTO.get(ProductPropertiesEnum.REQUIRES_SHIPPING.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.IMAGES.getColumn_name()) && productDTO.get(ProductPropertiesEnum.IMAGES.getColumn_name()) != null){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                productRow.set(ProductPropertiesEnum.IMAGES.getColumn_number(),objectMapper.writeValueAsString(productDTO.get(ProductPropertiesEnum.IMAGES.getColumn_name())));
            } catch (JsonProcessingException e) {
                productRow.set(ProductPropertiesEnum.IMAGES.getColumn_number(),"");
            }
        }

        if (productDTO.containsKey(ProductPropertiesEnum.INVENTORY.getColumn_name()) && productDTO.get(ProductPropertiesEnum.INVENTORY.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.INVENTORY.getColumn_number(),productDTO.get(ProductPropertiesEnum.INVENTORY.getColumn_name()));
        }

        //calcular preço sem iva
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
        if(productDTO.containsKey(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()) != null){
            try {
                productRow.set(ProductPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(),numberFormat.parse(productDTO.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()).toString()).doubleValue()/ Double.parseDouble(ConstantsEnum.VAT_EXCLUDE.getConstantValue().toString()));
            } catch (ParseException e) {
            }
        }

        if (productDTO.containsKey(ProductPropertiesEnum.PRICE_DOTT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_DOTT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PRICE_DOTT.getColumn_number(),productDTO.get(ProductPropertiesEnum.PRICE_DOTT.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_number(),productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_DOTT.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PRICE_WORTEN.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_WORTEN.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PRICE_WORTEN.getColumn_number(),productDTO.get(ProductPropertiesEnum.PRICE_WORTEN.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_number(),productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_WORTEN.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PRICE_DOTT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_DOTT.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PRICE_DOTT.getColumn_number(),productDTO.get(ProductPropertiesEnum.PRICE_DOTT.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PRICE_AMAZON.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_AMAZON.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PRICE_AMAZON.getColumn_number(),productDTO.get(ProductPropertiesEnum.PRICE_AMAZON.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_number(),productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_AMAZON.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PRICE_FNAC.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_FNAC.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PRICE_FNAC.getColumn_number(),productDTO.get(ProductPropertiesEnum.PRICE_FNAC.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_number(),productDTO.get(ProductPropertiesEnum.PREVIOUS_PRICE_FNAC.getColumn_name()));
        }
        productRow.add(ProductPropertiesEnum.values().length,";");

        return productRow;
    }

    private static List<Object> startMainSheetRow() {
        List<Object> row = new ArrayList<>();
        for(int i = 0; i<ProductPropertiesEnum.values().length; i++){
            row.add("");
        }
        return row;
    }

    private static List<Object> startKuantokustaSheetRow() {
        List<Object> row = new ArrayList<>();
        for(int i = 0; i< KuantoKustaPropertiesEnum.values().length; i++){
            row.add("");
        }
        return row;
    }

    private static List<Object> startDottSheetRow() {
        List<Object> row = new ArrayList<>();
        for(int i = 0; i< DottPropertiesEnum.values().length; i++){
            row.add("");
        }
        return row;
    }

}
