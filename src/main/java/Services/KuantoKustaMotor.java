package Services;

import Constants.ConstantsEnum;
import Constants.HttpRequestAuthTypeEnum;
import Constants.KuantoKustaOrdersEnum;
import DTO.*;
import Utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import main.UpdateFeeds;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KuantoKustaMotor {

    public static void kuantokustaToShopifySync(){
        List<KuantoKustaOrderDTO> ordersToApprove = retrieveOrders();
        for (KuantoKustaOrderDTO kuantoKustaOrderDTO : ordersToApprove){

            OrderDTO order = null;
            try {
                order = createOrderDtoFromKuantoKustaOrderDto(kuantoKustaOrderDTO);
                order = CreateOrderService.createDraftOrder(order);
                if (order == null){
                    throw new Exception("Order not created");
                }

                approveOrderKuantoKusta(kuantoKustaOrderDTO.getKuantoKustaOrderId());
               // updateOrderShopify(orderId, ConstantsEnum.KUANTOKUSTA_MESSAGE_SHOPIFY.getConstantValue() + kuantoKustaOrderDTO.getKuantoKustaOrderId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static List<KuantoKustaOrderDTO> retrieveOrders (){

        TypeReference<List<KuantoKustaOrderDTO>> typeReference = new TypeReference<List<KuantoKustaOrderDTO>>() {};
        List<KuantoKustaOrderDTO> orders = HttpRequestExecutor.getObjectRequest(typeReference , KuantoKustaOrdersEnum.GET_ORDERS_WAITING_APPROVAL.getOrderUrl(), HttpRequestAuthTypeEnum.XXX_API_KEY, ConstantsEnum.KUANTOKUSTA_API_KEY.getConstantValue().toString());

        return orders;
    }

    public static void approveOrderKuantoKusta(String kuantokustaOrderId){
        TypeReference<Object> typeReference = new TypeReference<Object>() {};
        System.out.println("kuantokusta order id " + kuantokustaOrderId );
        HttpRequestExecutor.patchObjectRequest(typeReference, KuantoKustaOrdersEnum.getUrlApproveOrder(kuantokustaOrderId), HttpRequestAuthTypeEnum.XXX_API_KEY, ConstantsEnum.KUANTOKUSTA_API_KEY.getConstantValue().toString());
    }

    public static void updateOrderShopify(String orderId, String note){
        String requestUrl = ConstantsEnum.UPDATE_ORDER_REQUEST_SHOPIFY_PREFIX.getConstantValue() + orderId +
                ConstantsEnum.UPDATE_ORDER_REQUEST_SHOPIFY_SUFIX.getConstantValue();
        UpdateOrderObjectDTO updateOrderObjectDTO = new UpdateOrderObjectDTO(orderId, note);
        HttpRequestExecutor.updateRequest(OrderDTO.class, null, requestUrl);
    }

    public static OrderDTO createOrderDtoFromKuantoKustaOrderDto (KuantoKustaOrderDTO kuantoKustaOrderDTO) throws GeneralSecurityException, IOException {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPhone(kuantoKustaOrderDTO.getShippingAddress().getPhone());
        orderDTO.setBillingAdress(getOrderAddressDtoFromKuantoKustaOrderAddressDto(kuantoKustaOrderDTO.getBillingAddress()));
        orderDTO.setShippingAddress(getOrderAddressDtoFromKuantoKustaOrderAddressDto(kuantoKustaOrderDTO.getShippingAddress()));
        Map<String, MacroProductDTO> productListDTO = UpdateFeeds.getUpdatedProductList();

        List<OrderLineDTO> lineItems = getOrderLinesFromKuantoKustaOrdersDto(kuantoKustaOrderDTO.getProducts(), productListDTO);
        String shippingPrice = null;
        if (kuantoKustaOrderDTO.getShippingPrice() == null){
            shippingPrice = "0";
        } else {
            shippingPrice = String.valueOf(kuantoKustaOrderDTO.getShippingPrice());
        }
        orderDTO.setShippingDTO (new OrderShippingDTO("KK", shippingPrice ,true));
        orderDTO.setLineItems(lineItems);


        return orderDTO;

    }

    public static List<OrderLineDTO> getOrderLinesFromKuantoKustaOrdersDto (List<KuantoKustaProductDTO> kuantokustaProducts, Map<String, MacroProductDTO> productListDTO){
        List<OrderLineDTO> listOrderLines = new ArrayList<>();

        for (KuantoKustaProductDTO kkProduct : kuantokustaProducts){
            for (Map.Entry<String, MacroProductDTO> product: productListDTO.entrySet()) {
                if (kkProduct.getOfferSku().equals(product.getValue().getId())){
                    OrderLineDTO orderLineDTO = new OrderLineDTO();

                    orderLineDTO.setVariantId(product.getValue().getVariantId());
                    orderLineDTO.setQuantity(kkProduct.getQuantity());
                    listOrderLines.add(orderLineDTO);
                }
            }
        }

        return  listOrderLines;
    }

    public static OrderAddressDTO getOrderAddressDtoFromKuantoKustaOrderAddressDto (KuantoKustaOrderAddressDTO kuantoKustaOrderAddressDTO){
        OrderAddressDTO orderAddressDTO = new OrderAddressDTO();
        String[] name = Utils.getFirstLastName(kuantoKustaOrderAddressDTO.getName());
        orderAddressDTO.setFirstName(name[0]);
        orderAddressDTO.setLastName(name[1]);
        orderAddressDTO.setAddress1(kuantoKustaOrderAddressDTO.getAddress1());
        orderAddressDTO.setAddress2(kuantoKustaOrderAddressDTO.getAddress2());
        orderAddressDTO.setCity(kuantoKustaOrderAddressDTO.getCity());
        orderAddressDTO.setPostalCode(kuantoKustaOrderAddressDTO.getPostalCode());
        orderAddressDTO.setProvince(kuantoKustaOrderAddressDTO.getCity());
        orderAddressDTO.setNipc(kuantoKustaOrderAddressDTO.getNipc());
        orderAddressDTO.setPhone(kuantoKustaOrderAddressDTO.getPhone());
        orderAddressDTO.setCountry(kuantoKustaOrderAddressDTO.getCountry());


        return orderAddressDTO;
    }

    public static void main(String[] args) {
        KuantoKustaMotor.kuantokustaToShopifySync();
    }
}
