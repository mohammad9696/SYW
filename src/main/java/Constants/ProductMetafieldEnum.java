package Constants;

public enum ProductMetafieldEnum {

    ETA("eta", ConstantsEnum.ETA_DEFAULT_ETA_MESSAGE.getConstantValue().toString()),
    ETA2("etaAlt",ConstantsEnum.ETA_DEFAULT_ETA_ALTERNATIVE_MESSAGE.getConstantValue().toString()),
    ETA_CART("etaCart",ConstantsEnum.ETA_DEFAULT_ETA_CART_MESSAGE.getConstantValue().toString()),
    ETA_MIN("etaMinDays",ConstantsEnum.ETA_DEFAULT_ETA_MIN_DAYS.getConstantValue().toString()),
    ETA_MAX("etaMaxDays",ConstantsEnum.ETA_DEFAULT_ETA_MAX_DAYS.getConstantValue().toString()),
    ETA_DATE("etaDate",ConstantsEnum.ETA_DEFAULT_ETA_DATE.getConstantValue().toString()),
    ETA_RESULT("etaResult",ConstantsEnum.ETA_DEFAULT_ETA_RESULT.getConstantValue().toString()),
    ETA_CART_RESULT("etaCartResult",ConstantsEnum.ETA_DEFAULT_ETA_CART_RESULT.getConstantValue().toString());

    private String key;
    private String defaultMessage;

    ProductMetafieldEnum(String key, String defaultMessage) {

        this.key = key;
        this.defaultMessage = defaultMessage;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
