package Services;

import Constants.ConstantsEnum;
import Constants.CourierExpeditionEnum;
import DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;

public class CourierExpeditionRequest {

    public static void shipAndFulfill (OrderDTO order){

        CourierExpeditionDTO courierExpeditionDTO = getCourierExpedition(order);
        String accessToken = getOauthToken();
        String trackingCode = createShipmentWithCourier(courierExpeditionDTO, accessToken);
        fulfillOrder(order, trackingCode);
    }

    private static String createShipmentWithCourier(CourierExpeditionDTO courierExpeditionDTO, String accessToken) {
        CourierShipmentCreationResponseDTO response = HttpRequestExecutor.sendRequest(CourierShipmentCreationResponseDTO.class,
                courierExpeditionDTO, ConstantsEnum.COURIER_CREATE_SHIPMENT_URL.getConstantValue().toString(), accessToken);

        System.out.println(response.toString());
        return  response.getTrackingCode();
    }

    private static String getOauthToken() {
        CourierOauthResponseDTO courierOauthResponseDTO = HttpRequestExecutor.sendRequest(CourierOauthResponseDTO.class, new CourierOauthDTO(), ConstantsEnum.COURIER_GET_OAUTH_URL.getConstantValue().toString());
         return courierOauthResponseDTO.getAccessToken();
    }

    private static CourierExpeditionDTO getCourierExpedition  (OrderDTO order){
        ObjectMapper objectMapper = new ObjectMapper();
        OrderAddressDTO senderAddress;
        try {
            senderAddress = objectMapper.readValue(ConstantsEnum.COURIER_REQUEST_SENDER_ADDRESS.getConstantValue().toString(), OrderAddressDTO.class);
        } catch (JsonProcessingException e) {
            throw new NullPointerException("Sender Address is Invalid");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many volumes are in the order below?");
        System.out.println(order.toString()+"\n");
        int volumes = scanner.nextInt();
        Double weight = Double.parseDouble(order.getTotalWeight()) / 1000;
        CourierExpeditionDTO  courierExpeditionDTO = new CourierExpeditionDTO(order.getOrderNumber(), senderAddress, order.getShippingAddress(),
                CourierExpeditionEnum.getExpedition(order.getShippingLine().get(0).getShippingCode()), ""+volumes,  weight.toString() );

        return courierExpeditionDTO;
    }

    private static void fulfillOrder(OrderDTO order, String trackingNumber){
        FulfillmentDTO fulfillmentDTO = new FulfillmentDTO(trackingNumber, ConstantsEnum.TRACKING_URL_PREFIX.getConstantValue().toString(), order);
        Object result = HttpRequestExecutor.sendRequest(Object.class, new FulfillmentObject(fulfillmentDTO) , fulfillmentDTO.getRequestUrl());
        System.out.println(result);
    }


}
