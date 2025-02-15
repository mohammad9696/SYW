package Services;

import Constants.ConstantsEnum;
import DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.util.ArrayMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShopifyOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ShopifyOrderService.class);



    public static Map<String, StockDetailsDTO> getStockReservations(){
        Map<String, StockDetailsDTO> stringStockDetailsDTOMap = new ArrayMap<>();
        List<OrderDTO> orderList = getOrdersUnpaidAndPaid();
        List<MoloniDocumentDTO> purchaseOrders = MoloniService.getPurchaseOrders();
        for (MoloniDocumentDTO order : purchaseOrders){
            if (order.getDocumentValueEuros() == order.getDocumentReconciledValueEuros()){
                continue;
            }
            MoloniDocumentDTO orderComplete = MoloniService.getPurchaseOrder(order.getDocumentId());
            if (orderComplete.getDocumentReconciledValueEuros()<0.1){
                for (MoloniProductDTO product : orderComplete.getProductDTOS()){
                    StockDetailsDTO stockDetailsDTO = new StockDetailsDTO(product.getSku());
                    if(stringStockDetailsDTOMap.containsKey(product.getSku())) {
                        stockDetailsDTO = stringStockDetailsDTOMap.get(product.getSku());
                    }
                    int reservedAmount = product.getLineQuantity();
                    int sumToAmount = stockDetailsDTO.getMoloniPurchaseOrders() != null ? stockDetailsDTO.getMoloniPurchaseOrders():0;
                    reservedAmount = reservedAmount + sumToAmount;
                    stockDetailsDTO.setMoloniPurchaseOrders(reservedAmount);
                    stringStockDetailsDTOMap.put(product.getSku(), stockDetailsDTO);

                }
            }
            if (orderComplete.getDocumentValueEuros() > orderComplete.getDocumentReconciledValueEuros()){
                logger.error("ENCOMENDA PARCIAL, FAZER MANUALMENTE {}", order.getDocumentNumber());
                logger.error("ENCOMENDA PARCIAL, FAZER MANUALMENTE {}", order.getDocumentNumber());
                logger.error("ENCOMENDA PARCIAL, FAZER MANUALMENTE {}", order.getDocumentNumber());
            }

        }
        for (OrderDTO order : orderList){
            for (OrderLineDTO line : order.getLineItems()){
                StockDetailsDTO stockDetailsDTO = new StockDetailsDTO(line.getSku());
                if(stringStockDetailsDTOMap.containsKey(line.getSku())) {
                    stockDetailsDTO = stringStockDetailsDTOMap.get(line.getSku());
                }
                int reservedAmount = line.getFulfillableQuantity();
                int sumToAmount = 0;
                if (order.getFinancialStatus().equals("paid") || order.getFinancialStatus().equals("authorized")){
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
        logger.info("Getting orders to fulfill from shopify");
        TypeReference<OrderListDTO> typeReference = new TypeReference<OrderListDTO>() {};
        Map<String, Object> params = new HashMap<>();
        OrderListDTO list = HttpRequestExecutor.getObjectRequestShopify(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_ORDERS.getConstantValue().toString(), params);
        logger.info("Got {} orders to fulfill", list.getOrders().size());
        while (!params.isEmpty()){
            logger.info("shopify order list is paginated. Getting next page.");
            String newReqUrl = params.get("newReqUrl").toString();
            params.remove("newReqUrl");
            OrderListDTO resultNext = HttpRequestExecutor.getObjectRequestShopify(typeReference, newReqUrl, params);
            for (OrderDTO i : list.getOrders()){
                if (i.getId().equals(resultNext.getOrders().get(0).getId())){
                    return list.getOrders();
                }
            }
            for (OrderDTO i : resultNext.getOrders()){
                list.getOrders().add(i);
            }
        }

        List<OrderDTO> ordersToFulfill = new ArrayList<>();
        Collections.reverse(list.getOrders());
        for (OrderDTO order : list.getOrders()){
            List<OrderLineDTO> orderLineDTOS = new ArrayList<>();
            if(order.getFulfillmentStatus() == null || !order.getFulfillmentStatus().equals("partial")){
                ordersToFulfill.add(order);
                for(OrderLineDTO line : order.getLineItems()){
                    if (line.getFulfillableQuantity() > 0){
                        line.setQuantity(line.getFulfillableQuantity());
                        orderLineDTOS.add(line);
                    }
                }
                order.setLineItems(orderLineDTOS);
            }
        }
        return ordersToFulfill;
    }

    protected static List<OrderDTO> getOrdersUnpaidAndPaid(){
        TypeReference<OrderListDTO> typeReference = new TypeReference<OrderListDTO>() {};
        Map<String, Object> params = new HashMap<>();
        OrderListDTO list = HttpRequestExecutor.getObjectRequestShopify(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_ORDERS_ALL_OPEN_UNPAID_AND_PAID.getConstantValue().toString(), params);
        while (!params.isEmpty()){
            logger.info("shopify order list is paginated. Getting next page.");
            String newReqUrl = params.get("newReqUrl").toString();
            params.remove("newReqUrl");
            OrderListDTO resultNext = HttpRequestExecutor.getObjectRequestShopify(typeReference, newReqUrl, params);
            for (OrderDTO i : list.getOrders()){
                if (i.getId().equals(resultNext.getOrders().get(0).getId())){
                    return list.getOrders();
                }
            }
            for (OrderDTO i : resultNext.getOrders()){
                list.getOrders().add(i);
            }
        }
        return list.getOrders();
    }
/*
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
           // FulfillmentDTO fulfillmentDTO = new FulfillmentDTO(trackingNumber, trackingUrl, order);
            //Object result = HttpRequestExecutor.sendRequest(Object.class, new FulfillmentObject(fulfillmentDTO) , fulfillmentDTO.getRequestUrl());
            //System.out.println(result);
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

    }*/
}
