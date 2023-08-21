package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FulfillmentService {
    private static final Logger logger = LoggerFactory.getLogger(FulfillmentService.class);
    public static void main(String[] args) {
        logger.info("Initiating order fulfillment service.");
        Scanner scanner =new Scanner(System.in);
        System.out.println("1: Process All Orders           2: Process one by one");
        System.out.println("3: Print invoice by order number");
        int option = scanner.nextInt();
        if (option == 1){
            logger.info("Processing orders automatically");
            autoProcessingOrders(scanner);
        } else if (option == 2){
            logger.info("Processing orders one by one");
            processOrdersIndividually(scanner);
        } else if (option == 3){
            System.out.println("Please insert shopify order number");
            String orderNumber = scanner.next();
            logger.info("Printing invoices for orders {}", orderNumber);
            MoloniService.printShopifyDocumentsInMoloni(orderNumber);
        } else {
            return;
        }

    }

    private static void autoProcessingOrders (Scanner scanner){
        logger.info("Processing all orders");
        List<ProductDTO> productDTOList = ShopifyProductService.getShopifyProductList();
        List<StockDetailsDTO> stocks = new ArrayList<>();
        List<OrderDTO> orderDTOS = ShopifyOrderService.getOrdersToFulfil();
        List<OrderDTO> ordersWithSystemStockPriority = new ArrayList<>();
        List<OrderDTO> ordersWithSystemStock = new ArrayList<>();
        List<OrderDTO> ordersWithoutSystemStock = new ArrayList<>();

        for (OrderDTO order : orderDTOS){
            logger.info("Getting lines for order {}", order.getOrderNumber());
            boolean allInSystemStock = true;
            for (OrderLineDTO line : order.getLineItems()){
                logger.info("Getting line {} details for order {} ",line.getSku(), order.getOrderNumber());
                for (StockDetailsDTO s : stocks){
                    if (line.getSku().equals(s.getSku())){
                        line.setMoloniStock(s.getMoloniStock());
                        break;
                    }
                }
                if (line.getMoloniStock() == null) {
                    logger.info("Getting stock details for line {} of order {}", line.getSku(), order.getOrderNumber());
                    StockDetailsDTO stockDetailsDTO = new StockDetailsDTO(line.getSku());
                    stockDetailsDTO.setMoloniStock(MoloniService.getStock(line.getSku()));
                    stocks.add(stockDetailsDTO);
                    line.setMoloniStock(stockDetailsDTO.getMoloniStock());
                }

                if(line.getQuantity() > line.getMoloniStock()){
                    allInSystemStock = false;
                    break;
                }
            }
            if (allInSystemStock) {
                if (!order.getShippingLine().isEmpty() && order.getShippingLine().get(0).getShippingCode().toLowerCase(Locale.ROOT).contains("express")){
                    ordersWithSystemStockPriority.add(order);
                } else {
                    ordersWithSystemStock.add(order);
                }

            } else {
                ordersWithoutSystemStock.add(order);
            }

        }

        logger.info("{} orders in priority line", ordersWithSystemStockPriority.size());
        logger.info("{} orders in system stock", ordersWithSystemStock.size());
        logger.info("{} orders in without system stock", ordersWithoutSystemStock.size());
        for (OrderDTO order : ordersWithSystemStockPriority){
            processOrder(order, scanner, productDTOList);
        }
        for (OrderDTO order : ordersWithSystemStock){
            processOrder(order, scanner, productDTOList);
        }
        for (OrderDTO order : ordersWithoutSystemStock){
            processOrder(order, scanner, productDTOList);
        }
    }

    private static void processOrdersIndividually(Scanner scanner){

        List<ProductDTO> productDTOList = ShopifyProductService.getShopifyProductList();
        while (true){
            OrderDTO orderDTO = ShopifyOrderService.getOrder(ShopifyOrderService.getOrdersToFulfil(), scanner);
            processOrder(orderDTO, scanner, productDTOList);
        }
    }

    private static void processOrder(OrderDTO orderDTO, Scanner scanner, List<ProductDTO> productDTOList){
        logger.warn("Processing order {}", orderDTO.getOrderNumber());

        String sku = Utils.normalizeStringLenght(15,"Sku");
        String ean = Utils.normalizeStringLenght(14, "Barcode");
        String productName = Utils.normalizeStringLenght(65, "Product Name");
        String quantity = Utils.normalizeStringLenght(10, "Quantity");
        String lineToDisplayH = sku + " " + ean + " " + productName + " " + quantity + "  System Stock";

        logger.info("Displaying orderLines for order {}", orderDTO.getOrderNumber());

        String[] lines = new String[orderDTO.getLineItems().size()];
        int lineN = 0;
        for(OrderLineDTO line : orderDTO.getLineItems()){
            if(line.getMoloniStock() == null){
                line.setMoloniStock(MoloniService.getStock(line.getSku()));
            }
            lines[lineN] = displayOrderLine(line, getProductDetailsFromOrderLine(line, productDTOList)) + "   "+ line.getMoloniStock();
            lineN++;
        }
        System.out.println("Contents of order " + orderDTO.getOrderNumber() + "     ORDER NOTES    " + orderDTO.getNote());
        System.out.println(lineToDisplayH);
        for (String line : lines){
            System.out.println(line);
        }

        System.out.println("Please check physical stocks. Is all in stock?  1: Yes     2: No");
        int option = scanner.nextInt();
        if (option != 1){
            logger.warn("No enough stock was found for order {} ", orderDTO.getOrderNumber());
            return;
        } else {
            logger.warn("Stock confirmado para a encomenda {}", orderDTO.getOrderNumber());
        }

        boolean proceed;
        for(OrderLineDTO line : orderDTO.getLineItems()){
            ProductDTO productDTO = getProductDetailsFromOrderLine(line, productDTOList);
            if (!productDTO.getImages().isEmpty()){
                ShowImage.LaunchImage(productDTO.getImages().get(0).getSrc());
            }

            proceed = false;
            while (!proceed){
                System.out.println("Type product " + line.getSku() + " barcode:");
                String barcode = scanner.next();
                if (productDTO.getVariants().get(0).getBarcode().equals(barcode)){
                    proceed = true;
                } else {
                    logger.warn("Barcode of product {}, shoud be {} and was matched as {}", line.getSku(), productDTO.getVariants().get(0).getBarcode(), barcode);
                    System.out.println("Barcode doesn't match. Proceed or try again? 1: Try Again  " + productDTO.getVariants().get(0).getBarcode() + ": prooceed");
                    String howToProceed = scanner.next();
                    if(howToProceed.equals(productDTO.getVariants().get(0).getBarcode())){
                        logger.warn("Product {} for order {} was manually processed because barcode didn't match", line.getSku(), orderDTO.getOrderNumber());
                        proceed = true;
                    }

                }
            }

            int qty;
            proceed = false;
            while (!proceed){
                System.out.println("Please insert quantity of items");
                qty = scanner.nextInt();
                if ( qty == line.getQuantity()){
                    logger.info("Product {} quantity was checked and was correct. {} units.}", line.getSku() + " " + line.getName(), line.getQuantity());
                    proceed = true;
                } else {
                    logger.warn("Product {} quantity was checked and was not correct. Should be {} units and was counterd {}", line.getSku() + " " + line.getName(), line.getQuantity(), qty);
                    System.out.println("Please fix the quantities to " + line.getQuantity() + "units");

                }
            }
        }
        proceed = false;
        int volumes = 0;
        while (!proceed){
            System.out.println("How many volumes were needed?");
            volumes = scanner.nextInt();
            System.out.println("Are you sure " + volumes + " volume(s) were needed? 1: Yes     2: No");
            if (scanner.nextInt() == 1){
                proceed = true;
            }
        }
        boolean ship = true;
        boolean pickup = false;
        boolean manualShipping = false;
        if (!orderDTO.getShippingLine().isEmpty()){
            if (orderDTO.getShippingLine().get(0).getShippingCode().equals(ConstantsEnum.SHOPIFY_ORDER_SHIPPING_CODE_PICKUP.getConstantValue().toString())){
                ship = false;
                pickup = true;
            }
        }
        if (!pickup && orderDTO.getShippingAddress()!= null && orderDTO.getShippingAddress().getPostalCode().startsWith("9") && orderDTO.getShippingAddress().getCountryCode().equals("PT")){
            manualShipping = true;
            ship = false;
        }

        if (ship){
            OutvioShipDTO outvioShipDTO= new OutvioShipDTO(orderDTO.getOrderNumber(), volumes);
            OutvioShipResponseDTO response = HttpRequestExecutor.sendRequest(OutvioShipResponseDTO.class, outvioShipDTO, ConstantsEnum.OUTVIO_SHIP_URL.getConstantValue().toString());

            if (response != null && response.getSuccess()){
                logger.info("Order {} was shipped successfully", orderDTO.getOrderNumber());
                logger.info("Printing label for order {}", orderDTO.getOrderNumber());
                PrinterService.print(ConstantsEnum.SYSTEM_PRINTER_LABEL.getConstantValue().toString(), response.getPdfLabelUrls()[0]);
            } else {
                logger.error("Could not print label for order {}", orderDTO.getOrderNumber());
                return;
            }
            return;
        } else if (pickup) {
            fulfillOrderDirectly(orderDTO, "store-pickup", "store-pickup");
            logger.warn("This is a store pickup print two copies of invoice");
            logger.warn("This is a store pickup print two copies of invoice");
            logger.warn("This is a store pickup print two copies of invoice");
            logger.warn("This is a store pickup print two copies of invoice");
            logger.warn("This is a store pickup print two copies of invoice");
        } else if (manualShipping){
            fulfillOrderDirectly(orderDTO, "expedido","expedido");
            logger.warn("This is a CTT order, prepare and take to CTT");
            logger.warn("This is a CTT order, prepare and take to CTT");
            logger.warn("This is a CTT order, prepare and take to CTT");
            logger.warn("This is a CTT order, prepare and take to CTT");
            logger.warn("This is a CTT order, prepare and take to CTT");
        }
        MoloniService.printShopifyDocumentsInMoloni(orderDTO.getOrderNumber());
        if (pickup) MoloniService.printShopifyDocumentsInMoloni(orderDTO.getOrderNumber());

    }
    private static String displayOrderLine(OrderLineDTO line, ProductDTO product){

        String sku = Utils.normalizeStringLenght(15,line.getSku());
        String ean = Utils.normalizeStringLenght(14, product.getVariants().get(0).getBarcode());
        String productName = Utils.normalizeStringLenght(65, line.getName());
        String quantity = Utils.normalizeStringLenght(10, line.getQuantity()+"");
        return sku + " " + ean + " " + productName + " " + quantity;
    }

    private static ProductDTO getProductDetailsFromOrderLine (OrderLineDTO line, List<ProductDTO> productDTOList){
        for (ProductDTO productDTO : productDTOList){
            //logger.debug("getProductDetailsFromOrderLine iterating product {}", productDTO.getTitle());
            if (line.getSku().equals(productDTO.sku())){
                return productDTO;
            }
        }
        logger.error("Could not find product in store with sku {}", line.getSku());
        return null;
    }

    private static void fulfillOrderDirectly(OrderDTO order, String trackingNumber, String trackingUrl){

        FulfillmentOrdersDTO ordersDTO = new FulfillmentOrdersDTO();
        Long fulfillmentOrderId = ordersDTO.getFulfillmentOrderId(order);
        FulfillmentOrdersDTO orders = new FulfillmentOrdersDTO();
        FulfillmentOrderDTO or = new FulfillmentOrderDTO(fulfillmentOrderId, trackingNumber, trackingUrl);
        orders.setFulfillmentOrderDTO(or);

        String reqUrl = ConstantsEnum.FULFILLMENT_REQUEST_URL_PREFIX.getConstantValue().toString()+fulfillmentOrderId+ConstantsEnum.FULFILLMENT_REQUEST_URL_SUFIX.getConstantValue().toString();
        Object result = HttpRequestExecutor.sendRequest(Object.class, orders , reqUrl);
        System.out.println(result);
    }
}
