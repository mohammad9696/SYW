package main;

import Constants.ConstantsEnum;
import DTO.ProductDTO;
import DTO.MacroProductDTO;
import DTO.ProductListDTO;
import Services.HttpRequestExecutor;
import Services.SheetsServiceUtil;
import Services.SpreadsheetSnippets;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

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
        return HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_PRODUCTS.getConstantValue().toString()).getProducts();

    }

    public static void main(String[] args) {
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

    public void service(HttpRequest request, HttpResponse response) throws Exception {
        BufferedWriter writer = response.getWriter();
        writer.write("Executing feeds!");
        main(null);
    }
}
