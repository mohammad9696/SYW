package Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConstantsEnum {
    PRODUCT_URL_PREFIX("store.product.url.prefix", "insertValueInProperties"),
    VAT_PT("vat","insertValueInProperties"),
    SHEETS_CLIENT_SECRET("sheets.client.secret.file","insertValueInProperties"),
    SHEETS_PARTNER_MARGIN_MAX("sheets.partner.margin.max","insertValueInProperties"),
    MAIN_SPREADSHEET_ID ("sheets.main.id","insertValueInProperties"),
    MAIN_SPREADSHEET_DATA_RANGE("sheets.main.data.range","insertValueInProperties"),
    GET_REQUEST_SHOPIFY_PRODUCTS("shopify.get.products","insertValueInProperties"),
    GET_REQUEST_TESTSHOPIFY_PRODUCTS("test.shopify.get.products","insertValueInProperties"),
    GET_REQUEST_SHOPIFY_ORDERS("shopify.get.orders","insertValueInProperties"),
    GET_REQUEST_SHOPIFY_ORDERS_ALL_OPEN_UNPAID_AND_PAID("shopify.getAllOpen.orders","insertValueInProperties"),
    CREATE_ORDER_REQUEST_SHOPIFY("shopify.create.order","insertValueInProperties"),
    UPDATE_ORDER_REQUEST_SHOPIFY_PREFIX("shopify.update.order.prefix","insertValueInProperties"),
    UPDATE_ORDER_REQUEST_SHOPIFY_SUFIX("shopify.update.order.sufix","insertValueInProperties"),
    CREATE_DRAFT_ORDER_REQUEST_SHOPIFY("shopify.create.draft.order","insertValueInProperties"),
    COMPLETE_DRAFT_ORDER_REQUEST_SHOPIFY_PREFIX("shopify.complete.draft.order.prefix","insertValueInProperties"),
    COMPLETE_DRAFT_ORDER_REQUEST_SHOPIFY_SUFIX("shopify.complete.draft.order.sufix","insertValueInProperties"),
    KUANTOKUSTA_SPREADSHEET_ID("sheets.kuantokusta.id","insertValueInProperties"),
    PARTNERS_SPREADSHEET_ID("sheets.dott.id","insertValueInProperties"),
    SHOPIFY_MAIN_LOCATION_ID("shopify.location.main.id","insertValueInProperties"),
    FULFILLMENT_REQUEST_URL_PREFIX("shopify.fulfillment.url.prefix","insertValueInProperties"),
    FULFILLMENT_REQUEST_URL_SUFIX("shopify.fulfillment.url.sufix","insertValueInProperties"),
    SHOPIFY_GET_FULFILLMENT_ORDER_ID_URL_PREFIX("shopify.fulfillment.order.get.order.id.prefix","insertValueInProperties"),
    SHOPIFY_GET_FULFILLMENT_ORDER_ID_URL_SUFIX("shopify.fulfillment.order.get.order.id.sufix","insertValueInProperties"),
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
    KUANTOKUSTA_MESSAGE_SHOPIFY("kuantokusta.order.message.shopify","insertValueInProperties"),
    SHOPIFY_CREATE_PRODUCT("shopify.create.product","insertValueInProperties"),
    SHOPIFY_UPDATE_PRODUCT_PREFIX("shopify.update.product.prefix","insertValueInProperties"),
    SHOPIFY_UPDATE_PRODUCT_SUFIX("shopify.update.product.sufix","insertValueInProperties"),
    TESTSHOPIFY_UPDATE_PRODUCT_PREFIX("test.shopify.update.product.prefix","insertValueInProperties"),
    TESTSHOPIFY_UPDATE_PRODUCT_SUFIX("test.shopify.update.product.sufix","insertValueInProperties"),
    OPENAI_REQUEST_URL("openai.request.url","insertValueInProperties"),
    OPENAI_API_KEY("openai.api.key","insertValueInProperties"),
    SHOPIFY_UPDATE_PRODUCT_VARIANT_PRE_SUFIX("shopify.update.product.variant.pre.sufix","insertValueInProperties"),
    SHOPIFY_UPDATE_PRODUCT_VARIANT_POS_PREFIX("shopify.update.product.variant.pos.sufix","insertValueInProperties"),
    SHOPIFY_UPDATE_INVENTORY_PRODUCT_URL("shopify.update.inventory.product.url","insertValueInProperties"),
    SHOPIFY_GRAPHQL_URL("shopify.graphql.url","insertValueInProperties"),
    SHOPIFY_TOKEN("shopify.token","insertValueInProperties"),
    SHOPIFY_GRAPHQL_MUTATION_QUERY_PRODUCT_ETAS_TRANSLATIONS("shopify.graphql.mutation.query.productETATranslations","insertValueInProperties"),
    PRODUCT_PRE_SALE_MESSAGE("product.pre.sale.message","insertValueInProperties"),
    PRODUCT_PRE_SALE_TITLE("product.pre.sale.title","insertValueInProperties"),
    GET_CUSTOMER_BY_EMAIL_URL("shopify.customer.get.by.email","insertValueInProperties"),
    GET_CUSTOMER_BY_PHONE_URL("shopify.customer.get.by.phone","insertValueInProperties"),
    CREATE_CUSTOMER_URL("shopify.customer.create.costumer","insertValueInProperties"),
    GET_PRODUCT_METAFIELDS_PREFIX("shopify.product.getMetafields.prefix","insertValueInProperties"),
    GET_PRODUCT_METAFIELDS_SUFIX("shopify.product.getMetafields.sufix","insertValueInProperties"),
    GET_TEST_PRODUCT_METAFIELDS_PREFIX("test.shopify.product.getMetafields.prefix","insertValueInProperties"),
    GET_TEST_PRODUCT_METAFIELDS_SUFIX("test.shopify.product.getMetafields.sufix","insertValueInProperties"),
    ETA_DEFAULT_ETA_MESSAGE("eta.default.eta","insertValueInProperties"),
    ETA_DEFAULT_ETA_CART_MESSAGE("eta.default.eta.cart","insertValueInProperties"),
    ETA_DEFAULT_ETA_ALTERNATIVE_MESSAGE("eta.default.etaAlt","insertValueInProperties"),
    ETA_DEFAULT_ETA_MIN_DAYS("eta.default.etaMinDays","insertValueInProperties"),
    ETA_DEFAULT_ETA_MAX_DAYS("eta.default.etaMaxDays","insertValueInProperties"),
    ETA_DEFAULT_ETA_DATE("eta.default.etaDate","insertValueInProperties"),
    ETA_DEFAULT_ETA_RESULT("eta.default.etaResult","insertValueInProperties"),
    ETA_DEFAULT_ETA_CART_RESULT("eta.default.cart.etaResult","insertValueInProperties"),
    ETA_DEFAULT_UNAVAILABLE("eta.default.unavailable","insertValueInProperties"),
    ETA_CUTOUT_TIME("eta.cutout.time","15"),
    HOLIDAY("holidays","2023-01-01,2022-12-25"),
    MOLONI_GET_TOKEN("moloni.get.token","insertValueInProperties"),
    MOLONI_COMPANY_ID("moloni.company.id","insertValueInProperties"),
    MOLONI_CATEGORY_ID_SHOPIFY("moloni.category.id.shopify","insertValueInProperties"),
    MOLONI_TAX_ID_VATNORMAL("moloni.tax.id.vatnormal","insertValueInProperties"),
    MOLONI_MEASUMENTS_UNIT_ID("moloni.measurements.unit.id","insertValueInProperties"),
    MOLONI_PRODUCT_GET_ONE("moloni.product.get.one","insertValueInProperties"),
    MOLONI_PRODUCT_GET_ALL("moloni.product.get.all","insertValueInProperties"),
    MOLONI_PRODUCT_UPDATE("moloni.product.update","insertValueInProperties"),
    MOLONI_PRODUCT_CREATE("moloni.product.create","insertValueInProperties"),
    MOLONI_DOCUMENT_GET_ALL("moloni.document.get.all","insertValueInProperties"),
    MOLONI_DOCUMENT_GET_ONE("moloni.document.get.one","insertValueInProperties"),
    MOLONI_DOCUMENT_PDF_LINK("moloni.document.get.pdfLink","insertValueInProperties"),
    MOLONI_DOCUMENT_PDF_LINK_DOWNLOAD("moloni.document.get.pdfLink.download","insertValueInProperties"),
    MOLONI_SHIPPING_PRODUCT_ID("moloni.shipping.product.id","insertValueInProperties"),
    MOLONI_STOCKS_GET_ALL("moloni.stocks.get.all","insertValueInProperties"),
    MOLONI_DOCUMENT_GET_TYPES("moloni.document.get.types","insertValueInProperties"),
    MOLONI_CLIENT_GET_ONE_URL("moloni.entity.get.one.client.url","insertValueInProperties"),
    MOLONI_CLIENT_GET_BY_EMAIL_URL("moloni.entity.get.client.email.url","insertValueInProperties"),
    MOLONI_CLIENT_GET_BY_SEARCH_URL("moloni.entity.get.client.search.url","insertValueInProperties"),
    MOLONI_CLIENT_INSERT_URL("moloni.entity.insert.client.url","insertValueInProperties"),
    MOLONI_CLIENT_GET_NEXT_NUMBER_URL("moloni.entity.next.client.number.url","insertValueInProperties"),
    MOLONI_DOCUMENT_GET_DOCUMENT_SET_URL("moloni.document.get.set.url","insertValueInProperties"),
    MOLONI_INSERT_PURCHASE_ORDER_URL("moloni.document.insert.purchaseOrder.url","insertValueInProperties"),
    MOLONI_PURCHASE_ORDER_GET_ONE_URL("moloni.document.get.one.purchaseOrder.url","insertValueInProperties"),
    MOLONI_PURCHASE_ORDER_GET_ALL_URL("moloni.document.get.all.purchaseOrder.url","insertValueInProperties"),
    MOLONI_INVOICE_RECEIPT_INSERT_URL("moloni.document.insert.invoiceReceipt.url","insertValueInProperties"),
    MOLONI_COUNTRIES_GET_ALL("moloni.countries.get.all","insertValueInProperties"),
    MOLONI_TAXES_GET_ALL("moloni.taxes.get.all","insertValueInProperties"),
    MOLONI_PAYMENT_METHODS_GET_ALL("moloni.payment.methods.get.all","insertValueInProperties"),
    SYSTEM_PRINTER_LABEL("system.printer.label","insertValueInProperties"),
    SYSTEM_PRINTER_PAPER("system.printer.paper","insertValueInProperties"),
    OUTVIO_SHIP_URL("outvio.ship.url","insertValueInProperties"),
    OUTVIO_CREATE_ORDER_URL("outvio.create.order.url","insertValueInProperties"),
    OUTVIO_API_KEY("outvio.apikey","insertValueInProperties"),
    SHOPIFY_ORDER_SHIPPING_CODE_PICKUP("shopify.order.shipping.code.pickup","insertValueInProperties");


    private String constantName;
    private Object constantValue;

    ConstantsEnum(String constantName, Object constantValue) {
        Properties properties = new Properties();
        InputStream inputStream;
        inputStream = getClass().getClassLoader().getResourceAsStream("sywapp.properties");
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
