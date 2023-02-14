package Services;

import Constants.ConstantsEnum;
import Constants.CourierExpeditionEnum;
import Constants.HttpRequestAuthTypeEnum;
import DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LognowExpeditionRequest {

    public static void shipAndFulfill (OrderDTO order, OrderAddressDTO pickupAddress){
        shipAndFulfill (order, pickupAddress, null, null);
    }

    public static void shipAndFulfill (OrderDTO order, OrderAddressDTO pickupAddress, String weightOrder, CourierExpeditionEnum courierExpeditionEnum){

        CourierExpeditionDTO courierExpeditionDTO = getCourierExpedition(order, pickupAddress, weightOrder, courierExpeditionEnum);
        String accessToken = getOauthToken();
        String trackingCode = createShipmentWithCourier(courierExpeditionDTO, accessToken);
        if (trackingCode == null){
            throw new NullPointerException("Tracking Code is null");
        }
        fulfillOrder(order, trackingCode);
    }

    private static String createShipmentWithCourier(CourierExpeditionDTO courierExpeditionDTO, String accessToken) {
        CourierShipmentCreationResponseDTO response = HttpRequestExecutor.sendRequest(CourierShipmentCreationResponseDTO.class,
                courierExpeditionDTO, ConstantsEnum.COURIER_CREATE_SHIPMENT_URL.getConstantValue().toString(), HttpRequestAuthTypeEnum.BEARER_TOKEN, accessToken);

        System.out.println(response.toString());
        return  response.getTrackingCode();
    }

    private static String getOauthToken() {
        CourierOauthResponseDTO courierOauthResponseDTO = HttpRequestExecutor.sendRequest(CourierOauthResponseDTO.class, new CourierOauthDTO(), ConstantsEnum.COURIER_GET_OAUTH_URL.getConstantValue().toString());
         return courierOauthResponseDTO.getAccessToken();
    }

    public static OrderAddressDTO getPickupAddress () {
        try{
            String[] addreses = ConstantsEnum.COURIER_REQUEST_SENDER_ADDRESS.getConstantValue().toString().split("NEWADDRESS");
            OrderAddressDTO pickupAddress = null;
            ObjectMapper objectMapper = new ObjectMapper();
            List<OrderAddressDTO> senderAddresses = new ArrayList<>();
            for (String address : addreses){
                pickupAddress = objectMapper.readValue(address, OrderAddressDTO.class);
                senderAddresses.add(pickupAddress);
            }

            int i = 1;
            System.out.println("Please choose the pickup address:");
            for (OrderAddressDTO address : senderAddresses){
                System.out.println(i + "  " + address.toString());
                i++;
            }

            Scanner scanner = new Scanner(System.in);
            int addressToCollect = scanner.nextInt();
            return senderAddresses.get(addressToCollect - 1);
        } catch (Exception e) {
                throw new NullPointerException("Sender Address is Invalid");
        }

    }
    private static CourierExpeditionDTO getCourierExpedition  (OrderDTO order, OrderAddressDTO pickupAddress, String weightOrder, CourierExpeditionEnum courierExpeditionEnum){

        if (pickupAddress == null){
            pickupAddress = getPickupAddress();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many volumes are in the order below?");
        System.out.println(order.toString()+"\n");
        int volumes = scanner.nextInt();
        Double weight;
        if (weightOrder == null) {
            weight = Double.parseDouble(order.getTotalWeight()) / 1000;
        } else {
            weight = Double.parseDouble(weightOrder)/1000;
        }

        CourierExpeditionEnum expedition;
        if (courierExpeditionEnum == null) {
            if (!order.getShippingLine().isEmpty()){
                expedition = CourierExpeditionEnum.getExpedition(order.getShippingLine().get(0).getShippingCode());
            } else {
                expedition = CourierExpeditionEnum.getExpedition(null);
            }

        } else {
            expedition = courierExpeditionEnum;
        }


        CourierExpeditionDTO  courierExpeditionDTO = new CourierExpeditionDTO(order.getOrderNumber(), pickupAddress, order.getShippingAddress(),
                expedition, ""+volumes,  weight.toString() );

        return courierExpeditionDTO;
    }

    private static void fulfillOrder(OrderDTO order, String trackingNumber){
        String tracking = ConstantsEnum.TRACKING_URL_PREFIX.getConstantValue().toString()+trackingNumber;
        FulfillmentDTO fulfillmentDTO = new FulfillmentDTO(tracking, tracking, order);
        Object result = HttpRequestExecutor.sendRequest(Object.class, new FulfillmentObject(fulfillmentDTO) , fulfillmentDTO.getRequestUrl());
        System.out.println(result);
    }


}
