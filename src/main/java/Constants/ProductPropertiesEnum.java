package Constants;

public enum ProductPropertiesEnum {

    ID ("id", "A", 0),
    VARIANT_ID("variantId","B",1),
    TITLE ("title", "C", 2),
    BODY_HTML ("bodyHtml", "D", 3),
    BRAND ("brand", "E", 4),
    PRODUCT_TYPE ("productType", "F", 5),
    CREATED_AT ("createdAt", "G", 6),
    UPDATED_AT ("updatedAt", "H", 7),
    URL("url", "I", 8),
    STATUS ("status", "J", 9),
    TAGS ("tags", "K", 10),
    PRICE_WITHOUT_VAT("priceWithoutVat","L",11),
    PRICE_WITH_VAT("priceWithVAT", "M", 12),
    SKU ("sku", "N", 13),
    BARCODE ("barcode", "O", 14),
    WEIGHT ("weight", "P", 15),
    WEIGHT_UNIT ("weightUnit", "Q", 16),
    REQUIRES_SHIPPING ("requiresShipping", "R", 17),
    IMAGES ("images", "S", 18),
    INVENTORY("inventory","T", 19),
    INVENTORY_POLICY("inventoryPolicy","U", 20),
    DELIVERY_MIN_DAYS("deliveryMinDays","V", 21),
    DELIVERY_MAX_DAYS("deliveryMaxDays","W", 22),
    PRICE_DOTT("priceDottVat","X",23),
    PREVIOUS_PRICE_DOTT("previousPriceDottVat","Y",24),
    PRICE_WORTEN("priceWortenVat","Z",25),
    PREVIOUS_PRICE_WORTEN("previousPriceWortenVat","AA",26),
    PRICE_AMAZON("priceAmazonVat","AB",27),
    PREVIOUS_PRICE_AMAZON("previousPriceAmazonVat","AC",28),
    PRICE_FNAC("priceFnacVat","AD",29),
    PREVIOUS_PRICE_FNAC("previousPriceFnacVat","AE",30);

    private String column_name;
    private String column_letter;
    private int column_number;

    ProductPropertiesEnum(String column_name, String column_letter, int column_number) {
        this.column_name = column_name;
        this.column_letter = column_letter;
        this.column_number = column_number;
    }

    public String getColumn_name() {
        return column_name;
    }

    public String getColumn_letter() {
        return column_letter;
    }

    public int getColumn_number() {
        return column_number;
    }
}
