package Services;

import Constants.ConstantsEnum;
import Constants.CourierExpeditionEnum;
import DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.util.ArrayMap;

import java.util.*;

public class ShopifyOrderService {




    public static Map<String, StockDetailsDTO> getStockDetails(){
        Map<String, StockDetailsDTO> stringStockDetailsDTOMap = new ArrayMap<>();
        List<OrderDTO> orderList = getOrdersUnpaidAndPaid();
        for (OrderDTO order : orderList){
            for (OrderLineDTO line : order.getLineItems()){
                StockDetailsDTO stockDetailsDTO = new StockDetailsDTO(line.getSku());
                if(stringStockDetailsDTOMap.containsKey(line.getSku())) {
                    stockDetailsDTO = stringStockDetailsDTOMap.get(line.getSku());
                }
                int reservedAmount = line.getQuantity();
                int sumToAmount = 0;
                if (order.getFinancialStatus().equals("paid")){
                    sumToAmount = stockDetailsDTO.getShopifyPaidReservations() != null? stockDetailsDTO.getShopifyPaidReservations(): 0;
                    reservedAmount = reservedAmount + sumToAmount;
                    stockDetailsDTO.setShopifyPaidReservations(reservedAmount);
                } else {
                    sumToAmount = stockDetailsDTO.getShopifyUnpaidReservations() != null? stockDetailsDTO.getShopifyUnpaidReservations(): 0;
                    reservedAmount = reservedAmount + sumToAmount;
                    stockDetailsDTO.setShopifyUnpaidReservations(reservedAmount);
                }
                stringStockDetailsDTOMap.put(line.getSku(), stockDetailsDTO);
            }
        }
        return stringStockDetailsDTOMap;
    }
    protected static OrderDTO getOrder(List<OrderDTO> orderList, Scanner scanner)  {
        int i = 1;
        for (OrderDTO order : orderList){
            System.out.println(i + "  " + order);
            i++;
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

    protected static List<OrderDTO> getOrdersToFulfil(){
        TypeReference<OrderListDTO> typeReference = new TypeReference<OrderListDTO>() {};
        OrderListDTO list = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_ORDERS.getConstantValue().toString(), new HashMap<>());
        Collections.reverse(list.getOrders());
        return list.getOrders();
    }

    protected static List<OrderDTO> getOrdersUnpaidAndPaid(){
        TypeReference<OrderListDTO> typeReference = new TypeReference<OrderListDTO>() {};
        OrderListDTO list = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_ORDERS_ALL_OPEN_UNPAID_AND_PAID.getConstantValue().toString(), new HashMap<>());
        return list.getOrders();
    }

    private static void lognowFulfillOne(OrderDTO orderDTO, OrderAddressDTO pickupAddress){
        LognowExpeditionRequest.shipAndFulfill(orderDTO, pickupAddress);
        System.out.println(orderDTO);
    }

    private static void lognowShowOrderOptions(List<OrderDTO> orderList, OrderAddressDTO pickupAddress) throws Exception {
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
            lognowFulfillOne(order, pickupAddress);
            orderList.remove(order);
            lognowShowOrderOptions(orderList, pickupAddress);
        } else if (option == 2){
            order = getOrder(orderList, scanner);
            System.out.println("Please insert the weight of the order in grams");
            String weightOrder = scanner.next();
            //Main.isExit(Integer.parseInt(weightOrder));
            LognowExpeditionRequest.shipAndFulfill(order, pickupAddress, weightOrder, CourierExpeditionEnum.getExpedition("UNAVAILABLE"));
            orderList.remove(order);
            lognowShowOrderOptions(orderList, pickupAddress);
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
            lognowShowOrderOptions(orderList, pickupAddress);
        }  else if (option == 9){
            System.out.println("Exiting orders menu\n");
        } else {
            System.out.println("Option not available!\n");
            lognowShowOrderOptions(orderList, pickupAddress);
        }
    }

    public static void lognowFulfillment() throws Exception {

        OrderAddressDTO pickupAddres = LognowExpeditionRequest.getPickupAddress();
        List<OrderDTO> orderList = getOrdersToFulfil();
        lognowShowOrderOptions(orderList, pickupAddres);

    }
}
