package DTO;

import Constants.ConstantsEnum;
import Services.HttpRequestExecutor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FulfillmentOrdersDTO {


    @JsonProperty("fulfillment_orders")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FulfillmentOrderDTO[] fulfillmentOrderListDTOS;

    @JsonProperty("fulfillment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FulfillmentOrderDTO fulfillmentOrderDTO;



    public Long getFulfillmentOrderId (OrderDTO order){
        String fulfillmentOrderIdReqUrl = ConstantsEnum.SHOPIFY_GET_FULFILLMENT_ORDER_ID_URL_PREFIX.getConstantValue().toString()+order.getId()+ConstantsEnum.SHOPIFY_GET_FULFILLMENT_ORDER_ID_URL_SUFIX.getConstantValue().toString();
        TypeReference<FulfillmentOrdersDTO> typeReference = new TypeReference<FulfillmentOrdersDTO>(){};
        FulfillmentOrdersDTO fulfillmentOrderDTOS = HttpRequestExecutor.getObjectRequestShopify(typeReference, fulfillmentOrderIdReqUrl, null );
        for (FulfillmentOrderDTO i : fulfillmentOrderDTOS.getFulfillmentOrderListDTOS()){
            return i.getFulfillmentOrderId();
        }
        return null;
    }

    public FulfillmentOrderDTO[] getFulfillmentOrderListDTOS() {
        return this.fulfillmentOrderListDTOS;
    }

    public void setFulfillmentOrderDTO(final FulfillmentOrderDTO fulfillmentOrderDTO) {
        this.fulfillmentOrderDTO = fulfillmentOrderDTO;
    }

    public FulfillmentOrdersDTO() {

    }
}