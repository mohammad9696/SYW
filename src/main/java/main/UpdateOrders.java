package main;

import Constants.ConstantsEnum;
import Constants.CourierExpeditionEnum;
import DTO.*;
import Services.CourierExpeditionRequest;
import Services.HttpRequestExecutor;

import java.util.List;
import java.util.Scanner;

public class UpdateOrders {

    public static void main(String[] args) {
        List<OrderDTO> orderList = getOrdersToFulfil();
        showOrderOptions(orderList);

    }

    private static List<OrderDTO> getOrdersToFulfil(){
        OrderListDTO list = HttpRequestExecutor.getObjectRequest(OrderListDTO.class, ConstantsEnum.GET_REQUEST_SHOPIFY_ORDERS.getConstantValue().toString());

        int i = 1;
        for (OrderDTO order : list.getOrders()){
            System.out.println(i + "   "+ order.toString());
            i++;
        }

        System.out.println("\n");
        return list.getOrders();
    }

    private static void fulfillOne (OrderDTO orderDTO){
        CourierExpeditionRequest.shipAndFulfill(orderDTO);
        System.out.println(orderDTO);
    }

    private static void showOrderOptions (List<OrderDTO> orderList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to:");
        System.out.println("1. Fulfill one");
        System.out.println("2. Fulfill all");
        System.out.println("3. Fulfill manually");
        System.out.println("9. Exit menu");
        int option = scanner.nextInt();
        if (option == 1){
            fulfillOne(getOrder(orderList, scanner));
            showOrderOptions(orderList);
        } else if (option == 2){
            for (OrderDTO order : orderList){
                fulfillOne(order);
            }
            showOrderOptions(orderList);
        } else if (option == 3){
            fulfillManually(getOrder(orderList,scanner), scanner);
            showOrderOptions(orderList);
        } else if (option == 9){
            System.out.println("Exiting orders menu\n");
        } else {
            System.out.println("Option not available!\n");
            showOrderOptions(orderList);
        }
    }

    private static void fulfillManually(OrderDTO order, Scanner scanner) {
        FulfillmentDTO fulfillmentDTO;
        System.out.println("Do you want to:");
        System.out.println("1. Process order only");
        System.out.println("2. Process and ship order");
        System.out.println("9. Exit menu");
        int option = scanner.nextInt();
        if (option == 1){
            System.out.println("Please insert the tracking number");
            String trackingNumber = scanner.next();
            System.out.println("Please insert the tracking url");
            String trackingUrl = scanner.next();
            fulfillmentDTO = new FulfillmentDTO(trackingNumber, trackingUrl, order);
            Object result = HttpRequestExecutor.sendRequest(Object.class, new FulfillmentObject(fulfillmentDTO) , fulfillmentDTO.getRequestUrl());
            System.out.println(result);
        } else if (option == 2){
            System.out.println("Please insert the weight of the order in grams");
            String weightOrder = scanner.next();
            CourierExpeditionRequest.shipAndFulfill(order, weightOrder, CourierExpeditionEnum.getExpedition("UNVAILABLE"));
        } else if (option == 9){
            return;
        } else {
            fulfillManually(order, scanner);
        }
    }

    private static OrderDTO getOrder(List<OrderDTO> orderList, Scanner scanner){
        for (OrderDTO order : orderList){
            System.out.println(order);
        }
        System.out.println("Please choose the order number");
        int order = scanner.nextInt();
        if (orderList.size() >= order){
            return orderList.get(order-1);
        }
        else {
            System.out.println("Order not found\n");
            return getOrder(orderList, scanner);
        }
    }
}
