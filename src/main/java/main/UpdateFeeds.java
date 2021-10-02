package main;

import Constants.ConstantsEnum;
import DTO.ProductDTO;
import DTO.MacroProductDTO;
import DTO.ProductListDTO;
import Services.HttpRequestExecutor;
import Services.SheetsServiceUtil;
import Services.SpreadsheetSnippets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.List;
import java.util.Map;

public class UpdateFeeds {

    public static void main(String[] args) {
        try {
            SpreadsheetSnippets snippet = new SpreadsheetSnippets(SheetsServiceUtil.getSheetsService());
            String spreadsheetId = (String) ConstantsEnum.MAIN_SPREADSHEET_ID.getConstantValue();
            ValueRange valueRange = snippet.getValues(spreadsheetId, (String) ConstantsEnum.MAIN_SPREADSHEET_DATA_RANGE.getConstantValue());
            System.out.println(valueRange);

            Map<String, MacroProductDTO> sheetsProductList = SheetsServiceUtil.getProductList(valueRange);
            ProductListDTO list = HttpRequestExecutor.getObjectRequest(ProductListDTO.class, ConstantsEnum.GET_REQUEST_SHOPIFY_PRODUCTS.getConstantValue().toString());
            List<ProductDTO> productsFromShopify = list.getProducts();
            SheetsServiceUtil sheetsServiceUtil = new SheetsServiceUtil();
            List<List<Object>> _values = sheetsServiceUtil.getMainSheetValues(sheetsProductList, productsFromShopify);
            snippet.updateValues(spreadsheetId, "A1","RAW", _values);
            //end updates

            //new reading
            valueRange = snippet.getValues(spreadsheetId, ConstantsEnum.MAIN_SPREADSHEET_DATA_RANGE.getConstantValue().toString());
            sheetsProductList = SheetsServiceUtil.getProductList(valueRange);

            snippet.updateValues(ConstantsEnum.KUANTOKUSTA_SPREADSHEET_ID.getConstantValue().toString(), "A1", "RAW", sheetsServiceUtil.getKuantokustaSheetValues(sheetsProductList));
            snippet.updateValues(ConstantsEnum.DOTT_SPREADSHEET_ID.getConstantValue().toString(), "A1", "RAW", sheetsServiceUtil.getDottSheetValues(sheetsProductList));



            System.out.println(valueRange);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
