package Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConstantsEnum {
    PRODUCT_URL_PREFIX("store.product.url.prefix", "insertValueInProperties"),
    VAT_EXCLUDE("vat","insertValueInProperties"),
    SHEETS_CLIENT_SECRET("sheets.client.secret.file","insertValueInProperties"),
    MAIN_SPREADSHEET_ID ("sheets.main.id","insertValueInProperties"),
    MAIN_SPREADSHEET_DATA_RANGE("sheets.main.data.range","insertValueInProperties"),
    GET_REQUEST_SHOPIFY_PRODUCTS("shopify.get.products","insertValueInProperties"),
    GET_REQUEST_SHOPIFY_ORDERS("shopify.get.orders","insertValueInProperties"),
    KUANTOKUSTA_SPREADSHEET_ID("sheets.kuantokusta.id","insertValueInProperties"),
    DOTT_SPREADSHEET_ID("sheets.dott.id","insertValueInProperties"),
    SHOPIFY_MAIN_LOCATION_ID("shopify.location.main.id","insertValueInProperties"),
    FULFILLMENT_REQUEST_URL_PREFIX("shopify.fulfillment.url.prefix","insertValueInProperties"),
    FULFILLMENT_REQUEST_URL_SUFIX("shopify.fulfillment.url.sufix","insertValueInProperties"),
    TRACKING_URL_PREFIX("shopify.fulfillment.tracking.url.prefix","insertValueInProperties");


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
