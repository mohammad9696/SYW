package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInventoryDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("location_id")
    private Long locationId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("inventory_item_id")
    private Long inventoryItemId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("available")
    private int available;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(Long inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
