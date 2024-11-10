package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInventoryDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("location_id")
    private String locationId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("inventory_item_id")
    private String inventoryItemId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("available")
    private int available;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
