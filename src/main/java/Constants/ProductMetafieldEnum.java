package Constants;

public enum ProductMetafieldEnum {

    ETA("eta","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD, ConstantsEnum.ETA_DEFAULT_ETA_MESSAGE.getConstantValue().toString()),
    ETA2("etaAlt","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_ALTERNATIVE_MESSAGE.getConstantValue().toString()),
    ETA_CART("etaCart","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_CART_MESSAGE.getConstantValue().toString()),
    ETA_MIN("etaMinDays","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_MIN_DAYS.getConstantValue().toString()),
    ETA_MAX("etaMaxDays","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_MAX_DAYS.getConstantValue().toString()),
    ETA_MIN_DAYS("etaMinDaysResult","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_MIN_DAYS.getConstantValue().toString()),
    ETA_MAX_DAYS("etaMaxDaysResult","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_MAX_DAYS.getConstantValue().toString()),
    ETA_DATE("etaDate","custom",MetafieldTypeEnum.DATE,ConstantsEnum.ETA_DEFAULT_ETA_DATE.getConstantValue().toString()),
    ETA_RESULT("etaResult","custom",MetafieldTypeEnum.MULTI_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_RESULT.getConstantValue().toString()),
    ETA_CART_RESULT("etaCartResult","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_CART_RESULT.getConstantValue().toString()),
    ETA_CART_RESULT_NO_STOCK("etaCartResultNoStock","custom",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,ConstantsEnum.ETA_DEFAULT_ETA_CART_RESULT.getConstantValue().toString()),
    META_TITLE("title_tag","global",MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD,""),
    META_DESCRIPTION("description_tag","global",MetafieldTypeEnum.MULTI_LINE_TEXT_FIELD,""),
    SET_PRICE_DATE_INIT("pricesetdateinit","custom",MetafieldTypeEnum.DATE,""),
    SET_PRICE_VALUE_INIT("pricetosetinit","custom",MetafieldTypeEnum.NUMBER_DECIMAL,""),
    SET_PRICE_DATE_END("pricesetdateend","custom",MetafieldTypeEnum.DATE,""),
    SET_PRICE_VALUE_END("pricetosetend","custom",MetafieldTypeEnum.NUMBER_DECIMAL,"");

    private String key;
    private String namespace;
    private MetafieldTypeEnum metafieldTypeEnum;
    private String defaultMessage;

    ProductMetafieldEnum(String key, String namespace, MetafieldTypeEnum metafieldTypeEnum, String defaultMessage) {

        this.key = key;
        this.namespace = namespace;
        this.defaultMessage = defaultMessage;
        this.metafieldTypeEnum = metafieldTypeEnum;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public MetafieldTypeEnum getMetafieldTypeEnum() {
        return this.metafieldTypeEnum;
    }

    public String getNamespace() {
        return namespace;
    }
}
