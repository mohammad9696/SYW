package Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConstantsEnum {
    PRODUCT_URL_PREFIX("productUrlPrefix", "insertValueInProperties"),
    VAT_EXCLUDE("vat","insertValueInProperties"),
    SHEETS_CLIENT_SECRET("sheetsClientSecret","insertValueInProperties"),
    MAIN_SPREADSHEET_ID ("mainSpreadsheetId","insertValueInProperties"),
    MAIN_SPREADSHEET_DATA_RANGE("mainSpreadsheetDataRange","insertValueInProperties"),
    GET_REQUEST_SHOPIFY_PRODUCTS("getRequestShopifyProducts","insertValueInProperties"),
    KUANTOKUSTA_SPREADSHEET_ID("kuantokustaSpreadSheetId","insertValueInProperties"),
    DOTT_SPREADSHEET_ID("dottSpreadSheetId","insertValueInProperties");


    private String constantName;
    private Object constantValue;

    ConstantsEnum(String constantName, Object constantValue) {
        Properties properties = new Properties();
        InputStream inputStream;
        inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.constantName = constantName;
        this.constantValue = properties.getProperty(constantName, constantValue.toString());
    }

    public String getConstantName() {
        return constantName;
    }

    public Object getConstantValue() {
        return constantValue;
    }
}
