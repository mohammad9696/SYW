package Constants;

public enum PartnerFeedPropertiesEnum {

    BARCODE ("ean", "A", 0),
    SKU ("sku", "B", 1),
    TITLE ("title", "C", 2),
    AI_DESCRIPTION("description","D",3),
    BRAND ("brand", "E", 4),
    PRECO_TABELA_COM_IVA("global_price","F", 5),
    INVENTORY("stock","G", 6),
    CONDN("condition","H",7),
    IMAGE_1_URL ("image", "I", 8),
    MINQTAL("min_quantity_alert","J",9),
    PCKGQT("package_quantity","K",10),
    DELIVERY_MIN_DAYS ("minDaysDelivery", "L", 11),
    SHPPR("shipping_price","M",12),
    WEIGHT ("weight", "N", 13);

/*
    PRODUCT_TYPE ("productType", "D", 3),
    CREATED_AT ("createdAt", "E", 4),
    UPDATED_AT ("updatedAt", "F", 5),
    URL_PT("urlPT", "G", 6),
    URL_ES("urlES", "H", 7),
    TAGS ("tags", "I", 8),
    PRICE_WITHOUT_VAT("priceWithoutVat","J",9),
    PRICE_WITH_VAT("priceWithVAT", "K", 10),

    WEIGHT_UNIT ("weightUnit", "O", 14),
    REQUIRES_SHIPPING ("requiresShipping", "P", 15),
    DELIVERY_MAX_DAYS ("maxDaysDelivery", "R", 17),

    IMAGE_2_URL ("image2", "T", 19),
    IMAGE_3_URL ("image3", "U", 20),
    MARGEM_COM_PVP_SMARTIFY("margemComPVPdaSMARTIFY","X", 23);
*/
    private String column_name;
    private String column_letter;
    private int column_number;

    PartnerFeedPropertiesEnum(String column_name, String column_letter, int column_number) {
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
