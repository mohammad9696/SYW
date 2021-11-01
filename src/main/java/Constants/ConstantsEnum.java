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
    CREATE_ORDER_REQUEST_SHOPIFY("shopify.create.order","insertValueInProperties"),
    UPDATE_ORDER_REQUEST_SHOPIFY_PREFIX("shopify.update.order.prefix","insertValueInProperties"),
    UPDATE_ORDER_REQUEST_SHOPIFY_SUFIX("shopify.update.order.sufix","insertValueInProperties"),
    KUANTOKUSTA_SPREADSHEET_ID("sheets.kuantokusta.id","insertValueInProperties"),
    DOTT_SPREADSHEET_ID("sheets.dott.id","insertValueInProperties"),
    SHOPIFY_MAIN_LOCATION_ID("shopify.location.main.id","insertValueInProperties"),
    FULFILLMENT_REQUEST_URL_PREFIX("shopify.fulfillment.url.prefix","insertValueInProperties"),
    FULFILLMENT_REQUEST_URL_SUFIX("shopify.fulfillment.url.sufix","insertValueInProperties"),
    TRACKING_URL_PREFIX("shopify.fulfillment.tracking.url.prefix","insertValueInProperties"),
    COURIER_REQUEST_LAST_HOUR("courier.request.last.hour.pm", "insertValueInProperties"),
    COURIER_SERVICE_CODE_NORMAL("courier.request.service.code.normal", "insertValueInProperties"),
    COURIER_SERVICE_CODE_EXPRESS("courier.request.service.code.express", "insertValueInProperties"),
    COURIER_SERVICE_CODE_URGENT("courier.request.service.code.urgent", "insertValueInProperties"),
    COURIER_SERVICE_CODE_ISLANDS("courier.request.service.code.acores.madeira", "insertValueInProperties"),
    SHOPIFY_COURIER_SERVICE_CODE_NORMAL("shopify.courier.request.service.code.normal", "insertValueInProperties"),
    SHOPIFY_COURIER_SERVICE_CODE_EXPRESS("shopify.courier.request.service.code.express", "insertValueInProperties"),
    SHOPIFY_COURIER_SERVICE_CODE_URGENT("shopify.courier.request.service.code.urgent", "insertValueInProperties"),
    SHOPIFY_COURIER_SERVICE_CODE_ISLANDS("shopify.courier.request.service.code.acores.madeira", "insertValueInProperties"),
    COURIER_REQUEST_SENDER_ADDRESS("courier.request.sender.order.address.object", "insertValueInProperties"),
    COURIER_OAUTH_GRANT_TYPE("courier.oauth.grant.type","insertValueInProperties"),
    COURIER_OAUTH_CLIENT_ID("courier.oauth.client.id","insertValueInProperties"),
    COURIER_OAUTH_CLIENT_SECRET("courier.oauth.client.secret","insertValueInProperties"),
    COURIER_OAUTH_USERNAME("courier.oauth.username","insertValueInProperties"),
    COURIER_OAUTH_PASSWORD("courier.oauth.password","insertValueInProperties"),
    COURIER_GET_OAUTH_URL("courier.oauth.get.token.request.url","insertValueInProperties"),
    COURIER_CREATE_SHIPMENT_URL("courier.oauth.create.shipment.request.url","insertValueInProperties"),
    PRODUCTS_NOT_FOR_FEED("products.not.for.feed","insertValueInProperties"),
    SHIPPING_PRODUCT_VARIANT_ID("shopify.shipping.product.variant.id","insertValueInProperties"),
    KUANTOKUSTA_GET_ORDERS("kuantokusta.get.orders","insertValueInProperties"),
    KUANTOKUSTA_API_KEY("kuantokusta.api.key","insertValueInProperties"),
    KUANTOKUSTA_MESSAGE_SHOPIFY("kuantokusta.order.message.shopify","insertValueInProperties");


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
