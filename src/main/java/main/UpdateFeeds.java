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
        ShopifyProductMetafieldsManager shopifyProductMetafieldsManager = new ShopifyProductMetafieldsManager();
        for (ProductDTO p : productDTOS){
            ProductMetafieldDTO minDays = shopifyProductMetafieldsManager.getMetafield(true, p,ProductMetafieldEnum.ETA_MIN_DAYS);
            ProductMetafieldDTO maxDays = shopifyProductMetafieldsManager.getMetafield(true, p,ProductMetafieldEnum.ETA_MAX_DAYS);
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
            snippet.updateValues(ConstantsEnum.DOTT_SPREADSHEET_ID.getConstantValue().toString(), "A1", "RAW", SheetsServiceUtil.getDottSheetValues(sheetsProductList));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
