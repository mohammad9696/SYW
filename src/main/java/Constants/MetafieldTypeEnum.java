package Constants;

public enum MetafieldTypeEnum {
    SINGLE_LINE_TEXT_FIELD("single_line_text_field"),
    MULTI_LINE_TEXT_FIELD("multi_line_text_field");

    private String key;

    public String getKey() {
        return key;
    }

    MetafieldTypeEnum(String key) {
        this.key = key;
    }
}
