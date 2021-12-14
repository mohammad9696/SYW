package Services;

import Constants.ConstantsEnum;
import DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;

public class CreateOrderService {

    private static String getCustomerId(String email, String phone){
        phone = phone.replace("+","");
        String id = null;
        TypeReference<CustomerListDTO> typeReference = new TypeReference<CustomerListDTO>(){};
        CustomerListDTO objectDTO = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.GET_CUSTOMER_BY_EMAIL_URL.getConstantValue().toString()+email);

        if (!objectDTO.getCustomerDTO().isEmpty()){
            id = objectDTO.getCustomerDTO().get(0).getId();
        } else {
            objectDTO = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.GET_CUSTOMER_BY_PHONE_URL.getConstantValue().toString()+phone);
            if (!objectDTO.getCustomerDTO().isEmpty()) {
                id = objectDTO.getCustomerDTO().get(0).getId();
            }
        }
        return id;
    }

    private static String createCustomer (String firstName, String lastName, String email, String phone){
        CustomerDTO customerDTO = new CustomerDTO(firstName, lastName, email, phone);
        CustomerObjectDTO d = HttpRequestExecutor.sendRequest(CustomerObjectDTO.class, new CustomerObjectDTO(customerDTO), ConstantsEnum.CREATE_CUSTOMER_URL.getConstantValue().toString());
        return d.getCustomerDTO().getId();
    }

    public static OrderDTO createDraftOrder(OrderDTO draftOrder){
        String customerId = getCustomerId(draftOrder.getEmail(), draftOrder.getPhone());
        if (customerId == null){
            customerId = createCustomer(draftOrder.getShippingAddress().getFirstName(), draftOrder.getShippingAddress().getLastName(),
                    draftOrder.getEmail(), draftOrder.getPhone());
        }
        draftOrder.setCustomerDTO(new CustomerDTO(customerId));
        DraftOrderDTO result = HttpRequestExecutor.sendRequest(DraftOrderDTO.class, new DraftOrderDTO(draftOrder), ConstantsEnum.CREATE_DRAFT_ORDER_REQUEST_SHOPIFY.getConstantValue().toString());

        OrderDTO updateOrder = new OrderDTO(result.getDraftOrder().getId(), "paid");
        result = HttpRequestExecutor.updateRequest(DraftOrderDTO.class, new DraftOrderDTO(updateOrder), getUpdateDraftOrderRequestUrl(result.getDraftOrder().getId()));
        return result.getDraftOrder();
    }

    private static String getUpdateDraftOrderRequestUrl (String orderId){
        return ConstantsEnum.COMPLETE_DRAFT_ORDER_REQUEST_SHOPIFY_PREFIX.getConstantValue().toString() +
                orderId + ConstantsEnum.COMPLETE_DRAFT_ORDER_REQUEST_SHOPIFY_SUFIX.getConstantValue().toString();
    }
}
