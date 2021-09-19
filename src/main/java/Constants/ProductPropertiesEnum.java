package Constants;

public enum ProductPropertiesEnum {

    ID ("id", "A", 0),
    TITLE ("title", "B", 1),
    BODY_HTML ("bodyHtml", "C", 2),
    BRAND ("brand", "D", 3),
    PRODUCT_TYPE ("productType", "E", 4),
    CREATED_AT ("createdAt", "F", 5),
    UPDATED_AT ("updatedAt", "G", 6),
    URL("url", "H", 7),
    STATUS ("status", "I", 8),
    TAGS ("tags", "J", 9),
    PRICE_WITHOUT_VAT("priceWithoutVat","K",10),
    PRICE_WITH_VAT("priceWithVAT", "L", 11),
    SKU ("sku", "M", 12),
    BARCODE ("barcode", "N", 13),
    WEIGHT ("weight", "O", 14),
    WEIGHT_UNIT ("weightUnit", "P", 15),
    REQUIRES_SHIPPING ("requiresShipping", "Q", 16),
    IMAGES ("images", "R", 17),
    INVENTORY("inventory","S", 18),
    PRICE_DOTT("priceDottVat","T",19),
    PREVIOUS_PRICE_DOTT("previousPriceDottVat","U",20),
    PRICE_WORTEN("priceWortenVat","V",21),
    PREVIOUS_PRICE_WORTEN("previousPriceWortenVat","W",22),
    PRICE_AMAZON("priceAmazonVat","X",23),
    PREVIOUS_PRICE_AMAZON("previousPriceAmazonVat","Y",24),
    PRICE_FNAC("priceFnacVat","Z",25),
    PREVIOUS_PRICE_FNAC("previousPriceFnacVat","AA",26);

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
