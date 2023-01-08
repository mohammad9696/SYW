package main;

import Constants.ConstantsEnum;
import Constants.ProductSellTypeEnum;
import DTO.*;
import Services.HttpRequestExecutor;
import Services.SheetsServiceUtil;
import Services.SpreadsheetSnippets;
import Services.UpdateProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class UpdateFeeds {

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

    public static List<ProductDTO> getShopifyProductList(){

        TypeReference<ProductListDTO> typeReference = new TypeReference<ProductListDTO>() {};
        Map<String, Object> params = new HashMap<>();
        List<ProductDTO> result = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_PRODUCTS.getConstantValue().toString(), params).getProducts();
        while (!params.isEmpty()){
            String newReqUrl = params.get("newReqUrl").toString();
            params.remove("newReqUrl");
            List<ProductDTO> resultNext = HttpRequestExecutor.getObjectRequest(typeReference, newReqUrl, params).getProducts();
            for (ProductDTO i : resultNext){
                result.add(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
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
            UpdateProductService.preSaleProduct();
            main(args);
        } else if (option == 3) {
            UpdateProductService.removePreSaleProduct();
            main(args);
        } else if (option == 4) {
            UpdateProductService.removeProductDiscount(UpdateProductService.getProductToUpdate(ProductSellTypeEnum.DISCOUNTED_PRODUCT));
            main(args);
        } else if (option == 5) {
            UpdateProductService.removeAllDiscounts();
            main(args);
        } else if (option == 6) {
            main(args);
        } else if (option == 99) {

        } else {
            main(args);
        }
    }

    public static void updateFeeds(){
        try {
            String spreadsheetId = (String) ConstantsEnum.MAIN_SPREADSHEET_ID.getConstantValue();
            SpreadsheetSnippets snippet = new SpreadsheetSnippets(SheetsServiceUtil.getSheetsService());

            Map<String, MacroProductDTO> sheetsProductList = getSheetsProductList(spreadsheetId, snippet);
            List<ProductDTO> productsFromShopify = getShopifyProductList();

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
