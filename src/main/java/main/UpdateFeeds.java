package main;

import Constants.ConstantsEnum;
import Constants.ProductMetafieldEnum;
import Constants.ProductSellTypeEnum;
import DTO.*;
import Services.*;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.*;

public class UpdateFeeds {

    private static final Logger logger = LoggerFactory.getLogger(UpdateFeeds.class);

    public static Map<String, MacroProductDTO> getUpdatedProductList() throws GeneralSecurityException, IOException {
        String spreadsheetId = (String) ConstantsEnum.MAIN_SPREADSHEET_ID.getConstantValue();
        SpreadsheetSnippets snippet = new SpreadsheetSnippets(SheetsServiceUtil.getSheetsService());
        return getSheetsProductList(spreadsheetId, snippet);
    }

    public static Map<String, MacroProductDTO> getSheetsProductList (String spreadsheetId, SpreadsheetSnippets spreadsheetSnippets) throws GeneralSecurityException, IOException {

        ValueRange valueRange = spreadsheetSnippets.getValues(spreadsheetId, (String) ConstantsEnum.MAIN_SPREADSHEET_DATA_RANGE.getConstantValue());
        System.out.println(valueRange);
        return SheetsServiceUtil.getProductList(valueRange);

    }

    public static void applyBulkDiscounts (Scanner scanner){
        logger.info("Applying bulk discounts. Please insert brand to offer discount");
        ShopifyProductMetafieldsManager shopifyProductMetafieldsManager = new ShopifyProductMetafieldsManager();
        String brand = scanner.next();
        List<ProductDTO> productDTOS = ShopifyProductService.getShopifyProductList();
        List<ProductDTO> productsToAffect = new ArrayList<>();

        int j= 0;
        for (ProductDTO i : productDTOS){
            if (i.getBrand().equalsIgnoreCase(brand)){
                productsToAffect.add(i);
                logger.info("{}    {} {} {}  {}  {}", j, i.sku(), i.getTitle(), i.getVariants().get(0).getPrice(), i.getVariants().get(0).getCompareAtPrice() );
                j++;
            }
        }

        boolean process = true;

        String dateEnd ="";
        String dateInit ="";
        while (process){
            logger.info("Pretende excluir algum produto? Sim: Escolher número  Não:-1");
            int option = scanner.nextInt();
            if (option <= -1 || productsToAffect.size()<option ){
                process = false;
            } else {
                productsToAffect.remove(option);
                j = 0;
                for (ProductDTO i : productsToAffect){
                    logger.info("{}    {} {} {}  {}  {}", j, i.sku(), i.getTitle(), i.getVariants().get(0).getPrice(), i.getVariants().get(0).getCompareAtPrice() );
                }
            }
        }
        if (productsToAffect.size() == 0) throw new NullPointerException();

        logger.info("Start discount now or at a later date?  0: Start now    1: Later date");
        int option = scanner.nextInt();
        if (option == 1) {
            logger.info("What date to start discount? AAAA-MM-DD");
            dateInit = scanner.next();
        }
        logger.info("What date to end discount? AAAA-MM-DD");
        dateEnd = scanner.next();


        logger.info("What percentage discount to be given for brand {}?", brand);
        int discount = scanner.nextInt();

        for (ProductDTO p : productsToAffect) {
            ProductVariantDTO variant = p.getVariants().get(0);
            if (variant.getCompareAtPrice() == null || variant.getCompareAtPrice() < variant.getPrice()){
                variant.setCompareAtPrice(variant.getPrice());
            }
            shopifyProductMetafieldsManager.createOrUpdateMetafield(true,p, ProductMetafieldEnum.SET_PRICE_DATE_END, dateEnd);
            shopifyProductMetafieldsManager.createOrUpdateMetafield(true,p, ProductMetafieldEnum.SET_PRICE_VALUE_END, variant.getCompareAtPrice());
            double priceToSet = variant.getCompareAtPrice()*.01*(100-discount);
            if (option == 1){
                shopifyProductMetafieldsManager.createOrUpdateMetafield(true,p, ProductMetafieldEnum.SET_PRICE_DATE_INIT, dateInit);
                shopifyProductMetafieldsManager.createOrUpdateMetafield(true,p, ProductMetafieldEnum.SET_PRICE_VALUE_INIT, priceToSet);
            } else {
                ShopifyProductService.updateProductPrice(p, priceToSet, variant.getCompareAtPrice(), true);
            }
            shopifyProductMetafieldsManager.updateMetafields();
        }

    }

    public static void checkUpdateDates (ProductDTO p, ProductMetafieldEnum dateMetafield, ProductMetafieldEnum priceMetafield){
        Double priceToSet = null;
        LocalDateTime date = ShopifyProductMetafieldsManager.getDateMetafield(p, dateMetafield);
        if (date != null && java.time.LocalDateTime.now().isAfter(date) && !java.time.LocalDateTime.now().minusDays(1).isAfter(date)){
            try {
                logger.info("Updating scheduled init price for {}, scheduled for {} to set price", p.sku(), date);
                priceToSet = Double.parseDouble(p.getMetafield(priceMetafield).getValue());
                if(priceToSet != null && priceToSet>0.00){
                    HttpGraphQLRequestExecutor.removeMetafield(p.getMetafield(priceMetafield));
                    HttpGraphQLRequestExecutor.removeMetafield(p.getMetafield(dateMetafield));
                    ShopifyProductService.updateProductPrice(p, priceToSet, p.getVariants().get(0).getCompareAtPrice(), true);
                }

            } catch (Exception e){
                logger.info("Unable to update scheduled init price for {}, scheduled for {} to set price at {}", p.sku(), date, priceToSet);
            }
        }
    }

    public static void updateScheduledPrices (){
        List<ProductDTO> productDTOS = ShopifyProductService.getShopifyProductList();

        for (ProductDTO p : productDTOS){
            checkUpdateDates(p, ProductMetafieldEnum.SET_PRICE_DATE_INIT, ProductMetafieldEnum.SET_PRICE_VALUE_INIT);
            checkUpdateDates(p, ProductMetafieldEnum.SET_PRICE_DATE_END, ProductMetafieldEnum.SET_PRICE_VALUE_END);
        }
    }
    public static void main(String[] args) {
        logger.info("Initiated");
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Update product feeds");
        System.out.println("2. Pre-sale product");
        System.out.println("3. Remove pre-sale from a product");
        System.out.println("4. Remove product discount");
        System.out.println("5. Remove all product discounts");
        System.out.println("99. Restart");

        int option = scanner.nextInt();
        if (option == 1){
            updateFeeds();
            main(args);
        } else if (option == 2) {
            ShopifyProductService.preSaleProduct();
            main(args);
        } else if (option == 3) {
            ShopifyProductService.removePreSaleProduct();
            main(args);
        } else if (option == 4) {
            ShopifyProductService.removeProductDiscount(ShopifyProductService.getProductToUpdate(ProductSellTypeEnum.DISCOUNTED_PRODUCT));
            main(args);
        } else if (option == 5) {
            ShopifyProductService.removeAllDiscounts();
            main(args);
        } else if (option == 6) {
            main(args);
        } else if (option == 99) {

        } else {
            main(args);
        }
    }

    private static void updateMetafieldsForProducts(List<ProductDTO> productDTOS){
        //ShopifyProductMetafieldsManager shopifyProductMetafieldsManager = new ShopifyProductMetafieldsManager();
        for (ProductDTO p : productDTOS){
            ProductMetafieldDTO minDays = p.getMetafield(ProductMetafieldEnum.ETA_MIN_DAYS);
            ProductMetafieldDTO maxDays = p.getMetafield(ProductMetafieldEnum.ETA_MAX_DAYS);
            if (minDays != null && maxDays != null){
                p.setDeliveryMinDays(Integer.parseInt(minDays.getValue()));
                p.setMaxDays(Integer.parseInt(maxDays.getValue()));
            } else {
                p.setDeliveryMinDays(Integer.parseInt(ProductMetafieldEnum.ETA_MIN_DAYS.getDefaultMessage()));
                p.setMaxDays(Integer.parseInt(ProductMetafieldEnum.ETA_MAX_DAYS.getDefaultMessage()));
            }
        }
    }

    public static void updateFeeds(){
        try {
            String spreadsheetId = (String) ConstantsEnum.MAIN_SPREADSHEET_ID.getConstantValue();
            SpreadsheetSnippets snippet = new SpreadsheetSnippets(SheetsServiceUtil.getSheetsService());

            Map<String, MacroProductDTO> sheetsProductList = getSheetsProductList(spreadsheetId, snippet);
            List<ProductDTO> productsFromShopify = ShopifyProductService.getShopifyProductList();
            updateMetafieldsForProducts(productsFromShopify);

            List<List<Object>> _values = SheetsServiceUtil.getMainSheetValues(sheetsProductList, productsFromShopify);
            snippet.updateValues(spreadsheetId, "A1","RAW", _values);

            //new reading
            sheetsProductList = getSheetsProductList(spreadsheetId, snippet);

            snippet.updateValues(ConstantsEnum.KUANTOKUSTA_SPREADSHEET_ID.getConstantValue().toString(), "A1", "RAW", SheetsServiceUtil.getKuantokustaSheetValues(sheetsProductList));
            snippet.updateValues(ConstantsEnum.PARTNERS_SPREADSHEET_ID.getConstantValue().toString(), "A1", "RAW", SheetsServiceUtil.getPartnerSheetValues(sheetsProductList));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
