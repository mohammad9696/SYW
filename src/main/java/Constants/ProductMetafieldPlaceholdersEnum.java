package Constants;

public enum ProductMetafieldPlaceholdersEnum {

    DATE_MIN("dataMin"),
    DATE_MAX("dataMax");

    private String key;

    ProductMetafieldPlaceholdersEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
