package Constants;

public enum KuantoKustaPropertiesEnum {

    ID ("id", "A", 0),
    TITLE ("title", "B", 1),
    BODY_HTML ("bodyHtml", "C", 2),
    BRAND ("brand", "D", 3),
    PRODUCT_TYPE ("productType", "E", 4),
    CREATED_AT ("createdAt", "F", 5),
    UPDATED_AT ("updatedAt", "G", 6),
    URL("url", "H", 7),
    TAGS ("tags", "I", 8),
    PRICE_WITHOUT_VAT("priceWithoutVat","J",9),
    PRICE_WITH_VAT("priceWithVAT", "K", 10),
    SKU ("sku", "L", 11),
    BARCODE ("barcode", "M", 12),
    WEIGHT ("weight", "N", 13),
    WEIGHT_UNIT ("weightUnit", "O", 14),
    REQUIRES_SHIPPING ("requiresShipping", "P", 15),
    IMAGE_1_URL ("image1", "Q", 16),
    IMAGE_2_URL ("image2", "R", 17),
    IMAGE_3_URL ("image3", "S", 18),
    INVENTORY("inventory","T", 19);

    private String column_name;
    private String column_letter;
    private int column_number;

    KuantoKustaPropertiesEnum(String column_name, String column_letter, int column_number) {
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
