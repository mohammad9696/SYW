package Constants;

public enum ProductMetafieldEnum {

    ETA("eta","custom","?namespace=custom&key=eta", ConstantsEnum.ETA_DEFAULT_ETA_MESSAGE.getConstantValue().toString()),
    ETA2("etaAlt","custom","?namespace=custom&key=etaAlt",ConstantsEnum.ETA_DEFAULT_ETA_ALTERNATIVE_MESSAGE.getConstantValue().toString()),
    ETA_CART("etaCart","custom","?namespace=custom&key=etaCart",ConstantsEnum.ETA_DEFAULT_ETA_CART_MESSAGE.getConstantValue().toString()),
    ETA_MIN("etaMinDays","custom","?namespace=custom&key=etaMinDays",ConstantsEnum.ETA_DEFAULT_ETA_MIN_DAYS.getConstantValue().toString()),
    ETA_MAX("etaMaxDays","custom","?namespace=custom&key=etaMaxDays",ConstantsEnum.ETA_DEFAULT_ETA_MAX_DAYS.getConstantValue().toString()),
    ETA_MIN_DAYS("etaMinDaysResult","custom","?namespace=custom&key=etaMinDaysResult",ConstantsEnum.ETA_DEFAULT_ETA_MIN_DAYS.getConstantValue().toString()),
    ETA_MAX_DAYS("etaMaxDaysResult","custom","?namespace=custom&key=etaMaxDaysResult",ConstantsEnum.ETA_DEFAULT_ETA_MAX_DAYS.getConstantValue().toString()),
    ETA_DATE("etaDate","custom","?namespace=custom&key=etaDate",ConstantsEnum.ETA_DEFAULT_ETA_DATE.getConstantValue().toString()),
    ETA_RESULT("etaResult","custom","?namespace=custom&key=etaResult",ConstantsEnum.ETA_DEFAULT_ETA_RESULT.getConstantValue().toString()),
    ETA_CART_RESULT("etaCartResult","custom","?namespace=custom&key=etaCartResult",ConstantsEnum.ETA_DEFAULT_ETA_CART_RESULT.getConstantValue().toString()),
    ETA_CART_RESULT_NO_STOCK("etaCartResultNoStock","custom","?namespace=custom&key=etaCartResultNoStock",ConstantsEnum.ETA_DEFAULT_ETA_CART_RESULT.getConstantValue().toString()),
    META_TITLE("title_tag","global","?namespace=global&key=title_tag",""),
    META_DESCRIPTION("description_tag","global","?namespace=global&key=description_tag","");;

    private String key;
    private String namespace;
    private String urlWithKeyAndNameSpace;
    private String defaultMessage;

    ProductMetafieldEnum(String key, String namespace, String urlWithKeyAndNameSpace, String defaultMessage) {

        this.key = key;
        this.namespace = namespace;
        this.defaultMessage = defaultMessage;
        this.urlWithKeyAndNameSpace = urlWithKeyAndNameSpace;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getUrlWithKeyAndNameSpace() {
        return urlWithKeyAndNameSpace;
    }

    public String getNamespace() {
        return namespace;
    }
}
