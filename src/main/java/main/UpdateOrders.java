package main;

import Constants.ConstantsEnum;
import DTO.*;
import Services.PostRequestExecutor;
import Services.SheetsServiceUtil;
import Services.SpreadsheetSnippets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UpdateOrders {

    public static void main(String[] args) {
        List<OrderDTO> orderList = getOrdersToFulfil();
        showOrderOptions(orderList);

    }

    private static List<OrderDTO> getOrdersToFulfil(){
        OrderListDTO list = PostRequestExecutor.getObjectRequest(OrderListDTO.class, ConstantsEnum.GET_REQUEST_SHOPIFY_ORDERS.getConstantValue().toString());

        int i = 1;
        for (OrderDTO order : list.getOrders()){
            System.out.println(i + "   "+ order.toString());
            i++;
        }

        return list.getOrders();
    }

    private static void fulfillOne (OrderDTO orderDTO){
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
            System.out.println("Please choose the order number");
            int order = scanner.nextInt();
            fulfillOne(orderList.get(order-1));
            showOrderOptions(orderList);
        } else if (option == 2){
            for (OrderDTO order : orderList){
                fulfillOne(order);
            }
            showOrderOptions(orderList);
        } else if (option == 9){
            System.out.println("Exiting orders menu");
        } else {
            System.out.println("Option not available!");
            showOrderOptions(orderList);
        }
    }
}
