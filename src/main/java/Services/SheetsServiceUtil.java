package Services;

import Constants.ConstantsEnum;
import Constants.PartnerFeedPropertiesEnum;
import Constants.KuantoKustaPropertiesEnum;
import Constants.ProductPropertiesEnum;
import DTO.MoloniProductDTO;
import DTO.ProductDTO;
import DTO.MacroProductDTO;
import DTO.ProductVariantDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

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
                productList.put(macroProductDTO.getVariantId(), macroProductDTO);
            }
        }
        return productList;
    }

    public static List<List<Object>> getMainSheetValues(Map<String, MacroProductDTO> productsFromSheets, List<ProductDTO> productsFromShopify){
        List<List<Object>> _values = new ArrayList<>();

        _values.add(mainSheetHeaderRow());

        for (ProductDTO product : productsFromShopify){
            for(ProductVariantDTO variant : product.getVariants()){
                if (productsFromSheets.containsKey(variant.getId())){
                    Map<String, Object> properties = MacroProductDTO.updateProduct(productsFromSheets.get(variant.getId()), new MacroProductDTO(product, variant)).getProductMap();
                    _values.add(mainSheetProductRow(properties));
                } else {
                    Map<String, Object> properties = new MacroProductDTO(product, variant).getProductMap();
                    _values.add(mainSheetProductRow(properties));
                }
            }
        }
        return _values;
    }

    public static List<List<Object>> getKuantokustaSheetValues(Map<String, MacroProductDTO> originalProductList){
        List<List<Object>> _values = new ArrayList<>();
        _values.add(kuantokustaSheetHeaderRow());
        for (Map.Entry<String, MacroProductDTO> obj : originalProductList.entrySet()){
            if(obj.getValue().getStatus().equalsIgnoreCase("active") && isProductSellable(obj)){
                _values.add(kuantokustaSheetProductRow(obj.getValue()));
            }
        }

        return _values;
    }

    public static List<List<Object>> getPartnerSheetValues(Map<String, MacroProductDTO> originalProductList){
        List<List<Object>> _values = new ArrayList<>();
        _values.add(partnerFeedSheetHeaderRow());
        for (Map.Entry<String, MacroProductDTO> obj : originalProductList.entrySet()){
            if(obj.getValue().getStatus().equalsIgnoreCase("active") && isProductSellable(obj)){
                _values.add(partnerFeedSheetProductRow(obj.getValue()));
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

    private static List<Object> partnerFeedSheetHeaderRow(){
        List<Object> headers = startPartnerSheetRow();
        for (PartnerFeedPropertiesEnum p : PartnerFeedPropertiesEnum.values()){
            headers.set(p.getColumn_number(), p.getColumn_name());
        }
        return headers;
    }

    private static List<Object> partnerFeedSheetProductRow(MacroProductDTO macroProductDTO){
        List<Object> productRow = startPartnerSheetRow();

        MoloniProductDTO moloniProductDTO = MoloniService.getProduct(macroProductDTO.getSku());
        Double tax = moloniProductDTO != null && moloniProductDTO.getTaxes().size() > 0 ? (1+moloniProductDTO.getTaxes().get(0).taxPercentageValue()) : Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString());
        if (macroProductDTO.getId() != null) {
            productRow.set(PartnerFeedPropertiesEnum.ID.getColumn_number(), macroProductDTO.getVariantId());
        }

        if (macroProductDTO.getTitle() != null) {
            productRow.set(PartnerFeedPropertiesEnum.TITLE.getColumn_number(), macroProductDTO.getTitle());
        }

        if(macroProductDTO.getBrand() != null){
            productRow.set(PartnerFeedPropertiesEnum.BRAND.getColumn_number(), macroProductDTO.getBrand());
        }

        if(macroProductDTO.getProductType() != null){
            productRow.set(PartnerFeedPropertiesEnum.PRODUCT_TYPE.getColumn_number(), macroProductDTO.getProductType());
        }

        if(macroProductDTO.getCreatedAt() != null){
            productRow.set(PartnerFeedPropertiesEnum.CREATED_AT.getColumn_number(), macroProductDTO.getCreatedAt());
        }

        if(macroProductDTO.getUpdatedAt() != null){
            productRow.set(PartnerFeedPropertiesEnum.UPDATED_AT.getColumn_number(), macroProductDTO.getUpdatedAt());
        }

        if(macroProductDTO.getUrl() != null){
            productRow.set(PartnerFeedPropertiesEnum.URL_PT.getColumn_number(), macroProductDTO.getUrl());
        }

        if(macroProductDTO.getUrl() != null){
            productRow.set(PartnerFeedPropertiesEnum.URL_ES.getColumn_number(), macroProductDTO.getUrl().replace(".pt",".pt/es"));
        }

        if(macroProductDTO.getTags() != null){
            productRow.set(PartnerFeedPropertiesEnum.TAGS.getColumn_number(), macroProductDTO.getTags());
        }

        if(macroProductDTO.getSku() != null){
            productRow.set(PartnerFeedPropertiesEnum.SKU.getColumn_number(), macroProductDTO.getSku());
        }

        if(macroProductDTO.getBarcode() != null) {
            productRow.set(PartnerFeedPropertiesEnum.BARCODE.getColumn_number(), macroProductDTO.getBarcode());
        }

        if (macroProductDTO.getWeight() != null){
            productRow.set(PartnerFeedPropertiesEnum.WEIGHT.getColumn_number(), macroProductDTO.getWeight());
        }

        if (macroProductDTO.getWeightUnit() != null){
            productRow.set(PartnerFeedPropertiesEnum.WEIGHT_UNIT.getColumn_number(), macroProductDTO.getWeightUnit());
        }

        if (macroProductDTO.getDeliveryMinDays() != null){
            productRow.set(PartnerFeedPropertiesEnum.DELIVERY_MIN_DAYS.getColumn_number(), macroProductDTO.getDeliveryMinDays());
        }

        if (macroProductDTO.getDeliveryMaxDays() != null){
            productRow.set(PartnerFeedPropertiesEnum.DELIVERY_MAX_DAYS.getColumn_number(), macroProductDTO.getDeliveryMaxDays());
        }

        if (macroProductDTO.getRequiresShipping() != null){
            productRow.set(PartnerFeedPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(), macroProductDTO.getRequiresShipping());
        }


        if(macroProductDTO.getPrice() != null){
            productRow.set(PartnerFeedPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), macroProductDTO.getPrice()/tax);
            productRow.set(PartnerFeedPropertiesEnum.PRICE_WITH_VAT.getColumn_number(), macroProductDTO.getPrice());
        }

        if (macroProductDTO.getImages().size() >= 3){
            productRow.set(PartnerFeedPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
            productRow.set(PartnerFeedPropertiesEnum.IMAGE_2_URL.getColumn_number(), macroProductDTO.getImages().get(1).getSrc());
            productRow.set(PartnerFeedPropertiesEnum.IMAGE_3_URL.getColumn_number(), macroProductDTO.getImages().get(2).getSrc());
        } else if (macroProductDTO.getImages().size() == 2) {
            productRow.set(PartnerFeedPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
            productRow.set(PartnerFeedPropertiesEnum.IMAGE_2_URL.getColumn_number(), macroProductDTO.getImages().get(1).getSrc());
        } else if (macroProductDTO.getImages().size() == 1) {
            productRow.set(PartnerFeedPropertiesEnum.IMAGE_1_URL.getColumn_number(), macroProductDTO.getImages().get(0).getSrc());
        }

        if (macroProductDTO.getInventory() != null){
            int inventoryToSet = macroProductDTO.getInventory();
            if (inventoryToSet <= 0){
                if (macroProductDTO.getInventoryPolicy().equalsIgnoreCase("continue")){
                    inventoryToSet = 1;
                }
            } else if (inventoryToSet > 25){
                inventoryToSet = 25;
            }
            productRow.set(PartnerFeedPropertiesEnum.INVENTORY.getColumn_number(), inventoryToSet );
        }

        Double costPrice = StockKeepingUnitsService.getCostPrice(null, macroProductDTO.getSku());
        Double pvp = macroProductDTO.getPrice()/tax;
        Double margin ;
        Double marginPercentage;
        Double sellPrice;
        Double partnerMargin;
        if (costPrice != null && costPrice > 0.0) {
            margin = pvp-costPrice;
            marginPercentage = margin/pvp;
            if (marginPercentage/2 > Double.parseDouble(ConstantsEnum.SHEETS_PARTNER_MARGIN_MAX.getConstantValue().toString())/100 ){
                sellPrice = pvp * (1.0- Double.parseDouble(ConstantsEnum.SHEETS_PARTNER_MARGIN_MAX.getConstantValue().toString())/100);
                partnerMargin = Double.parseDouble(ConstantsEnum.SHEETS_PARTNER_MARGIN_MAX.getConstantValue().toString())/100;
            } else  if (marginPercentage/2 < Double.parseDouble(ConstantsEnum.SHEETS_PARTNER_MARGIN_MIN.getConstantValue().toString())/100 ){
                sellPrice = pvp / (1.0- Double.parseDouble(ConstantsEnum.SHEETS_PARTNER_MARGIN_MIN.getConstantValue().toString())/100);
                partnerMargin = Double.parseDouble(ConstantsEnum.SHEETS_PARTNER_MARGIN_MIN.getConstantValue().toString())/100;
            } else {
                sellPrice = pvp * (1.0 - marginPercentage/2);
                partnerMargin = marginPercentage/2;
            }
            productRow.set(PartnerFeedPropertiesEnum.MARGEM_COM_PVP_SMARTIFY.getColumn_number(), partnerMargin);
            productRow.set(PartnerFeedPropertiesEnum.PRECO_TABELA.getColumn_number(), sellPrice);
        }


        return productRow;
    }

    private static List<Object> kuantokustaSheetHeaderRow(){
        List<Object> headers = startKuantokustaSheetRow();

        for (KuantoKustaPropertiesEnum i : KuantoKustaPropertiesEnum.values()){
            headers.set(i.getColumn_number(), i.getColumn_name());
        }
        return headers;
    }

    private static List<Object> kuantokustaSheetProductRow(MacroProductDTO macroProductDTO){
        List<Object> productRow = startKuantokustaSheetRow();

        if (macroProductDTO.getId() != null) {
            productRow.set(KuantoKustaPropertiesEnum.ID.getColumn_number(), macroProductDTO.getVariantId());
        }

        if (macroProductDTO.getTitle() != null) {
            productRow.set(KuantoKustaPropertiesEnum.TITLE.getColumn_number(), macroProductDTO.getTitle());
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
            productRow.set(KuantoKustaPropertiesEnum.URL_PT.getColumn_number(), macroProductDTO.getUrl());
        }

        if(macroProductDTO.getUrl() != null){
            productRow.set(KuantoKustaPropertiesEnum.URL_ES.getColumn_number(), macroProductDTO.getUrl().replace(".pt",".pt/es"));
        }

        if(macroProductDTO.getTags() != null){
            productRow.set(KuantoKustaPropertiesEnum.TAGS.getColumn_number(), macroProductDTO.getTags());
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

        if (macroProductDTO.getDeliveryMinDays() != null){
            productRow.set(KuantoKustaPropertiesEnum.DELIVERY_MIN_DAYS.getColumn_number(), macroProductDTO.getDeliveryMinDays());
        }

        if (macroProductDTO.getDeliveryMaxDays() != null){
            productRow.set(KuantoKustaPropertiesEnum.DELIVERY_MAX_DAYS.getColumn_number(), macroProductDTO.getDeliveryMaxDays());
        }

        if (macroProductDTO.getRequiresShipping() != null){
            productRow.set(KuantoKustaPropertiesEnum.REQUIRES_SHIPPING.getColumn_number(), macroProductDTO.getRequiresShipping());
        }


        if(macroProductDTO.getPrice() != null){
            productRow.set(KuantoKustaPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(), macroProductDTO.getPrice()/Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString()));
            productRow.set(KuantoKustaPropertiesEnum.PRICE_WITH_VAT.getColumn_number(), macroProductDTO.getPrice());
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
            int inventoryToSet = macroProductDTO.getInventory();
            if (inventoryToSet <= 0){
                if (macroProductDTO.getInventoryPolicy().equals("continue")){
                    inventoryToSet = 3;
                }
            }
            productRow.set(KuantoKustaPropertiesEnum.INVENTORY.getColumn_number(), inventoryToSet );
        }

        return productRow;
    }

    private static List<Object> mainSheetHeaderRow(){
        List<Object> headers = startMainSheetRow();
        for (ProductPropertiesEnum i : ProductPropertiesEnum.values()){
            headers.set(i.getColumn_number(), i.getColumn_name());
        }
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

        if (productDTO.containsKey(ProductPropertiesEnum.INVENTORY_POLICY.getColumn_name()) && productDTO.get(ProductPropertiesEnum.INVENTORY_POLICY.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.INVENTORY_POLICY.getColumn_number(),productDTO.get(ProductPropertiesEnum.INVENTORY_POLICY.getColumn_name()));
        }

        if (productDTO.containsKey(ProductPropertiesEnum.DELIVERY_MIN_DAYS.getColumn_name()) && productDTO.get(ProductPropertiesEnum.DELIVERY_MIN_DAYS.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.DELIVERY_MIN_DAYS.getColumn_number(),productDTO.get(ProductPropertiesEnum.DELIVERY_MIN_DAYS.getColumn_name()));
        }
        if (productDTO.containsKey(ProductPropertiesEnum.DELIVERY_MAX_DAYS.getColumn_name()) && productDTO.get(ProductPropertiesEnum.DELIVERY_MAX_DAYS.getColumn_name()) != null){
            productRow.set(ProductPropertiesEnum.DELIVERY_MAX_DAYS.getColumn_number(),productDTO.get(ProductPropertiesEnum.DELIVERY_MAX_DAYS.getColumn_name()));
        }


        //calcular preço sem iva estático a 23%
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
        if(productDTO.containsKey(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()) && productDTO.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()) != null){
            try {
                productRow.set(ProductPropertiesEnum.PRICE_WITHOUT_VAT.getColumn_number(),numberFormat.parse(productDTO.get(ProductPropertiesEnum.PRICE_WITH_VAT.getColumn_name()).toString()).doubleValue()/ Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString()));
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

    private static List<Object> startPartnerSheetRow() {
        List<Object> row = new ArrayList<>();
        for(int i = 0; i< PartnerFeedPropertiesEnum.values().length; i++){
            row.add("");
        }
        return row;
    }

}
