package main;

import Constants.ConstantsEnum;
import Constants.CourierExpeditionEnum;
import DTO.*;
import Services.CourierExpeditionRequest;
import Services.HttpRequestExecutor;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Scanner;

public class UpdateOrders {


    public static void main(String[] args) throws Exception {

        OrderAddressDTO pickupAddres = CourierExpeditionRequest.getPickupAddress();
        List<OrderDTO> orderList = getOrdersToFulfil();
        showOrderOptions(orderList, pickupAddres);

    }

    private static List<OrderDTO> getOrdersToFulfil(){
        TypeReference<OrderListDTO> typeReference = new TypeReference<OrderListDTO>() {};
        OrderListDTO list = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_ORDERS.getConstantValue().toString());

        int i = 1;
        for (OrderDTO order : list.getOrders()){
            System.out.println(i + "   "+ order.toString());
            i++;
        }

        System.out.println("\n");
        return list.getOrders();
    }

    private static void fulfillOne (OrderDTO orderDTO, OrderAddressDTO pickupAddress){
        CourierExpeditionRequest.shipAndFulfill(orderDTO, pickupAddress);
        System.out.println(orderDTO);
    }

    private static void showOrderOptions (List<OrderDTO> orderList, OrderAddressDTO pickupAddress) throws Exception {
        OrderDTO order = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to:");
        System.out.println("1. Fulfill automatically");
        System.out.println("2. Fulfill manually");
        System.out.println("3. Process Order Only");
        System.out.println("9. Exit menu");
        System.out.println("99. Restart (at any time)");
        int option = scanner.nextInt();
        if (option == 1){
            order = getOrder(orderList, scanner);
            fulfillOne(order, pickupAddress);
            orderList.remove(order);
            showOrderOptions(orderList, pickupAddress);
        } else if (option == 2){
            order = getOrder(orderList, scanner);
            System.out.println("Please insert the weight of the order in grams");
            String weightOrder = scanner.next();
            //Main.isExit(Integer.parseInt(weightOrder));
            CourierExpeditionRequest.shipAndFulfill(order, pickupAddress, weightOrder, CourierExpeditionEnum.getExpedition("UNAVAILABLE"));
            orderList.remove(order);
            showOrderOptions(orderList, pickupAddress);
        } else if (option == 3){
            order = getOrder(orderList, scanner);
            System.out.println("Please insert the tracking number");
            String trackingNumber = scanner.next();
            //Main.isExit(Integer.parseInt(trackingNumber));
            System.out.println("Please insert the tracking url");
            String trackingUrl = scanner.next();
            //Main.isExit(Integer.parseInt(trackingUrl));
            FulfillmentDTO fulfillmentDTO = new FulfillmentDTO(trackingNumber, trackingUrl, order);
            Object result = HttpRequestExecutor.sendRequest(Object.class, new FulfillmentObject(fulfillmentDTO) , fulfillmentDTO.getRequestUrl());
            System.out.println(result);
            orderList.remove(order);
            showOrderOptions(orderList, pickupAddress);
        }  else if (option == 9){
            System.out.println("Exiting orders menu\n");
        } else {
            System.out.println("Option not available!\n");
            showOrderOptions(orderList, pickupAddress);
        }
    }

    private static OrderDTO getOrder(List<OrderDTO> orderList, Scanner scanner) throws Exception {
        int i = 1;
        for (OrderDTO order : orderList){
            System.out.println(i + "  " + order);
            i++;
        }
        System.out.println("Please choose the order number");
        int order = scanner.nextInt();
        Main.isExit(order);
        if (orderList.size() >= order){
            return orderList.get(order-1);
        }
        else {
            System.out.println("Order not found\n");
            return getOrder(orderList, scanner);
        }
    }
}
