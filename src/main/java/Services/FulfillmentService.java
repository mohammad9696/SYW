package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class FulfillmentService {
    private static final Logger logger = LoggerFactory.getLogger(FulfillmentService.class);
    public static void main(String[] args) {
        logger.info("Initiating order fulfillment service.");

        processOrders();
    }

    private static void processOrders(){
        Scanner scanner =new Scanner(System.in);
        List<ProductDTO> productDTOList = ShopifyProductService.getShopifyProductList();
        OrderDTO orderDTO = ShopifyOrderService.getOrder(ShopifyOrderService.getOrdersToFulfil(), scanner);
        logger.warn("Processing order {}", orderDTO.getOrderNumber());

        String sku = Utils.normalizeStringLenght(15,"Sku");
        String ean = Utils.normalizeStringLenght(14, "Barcode");
        String productName = Utils.normalizeStringLenght(65, "Product Name");
        String quantity = Utils.normalizeStringLenght(10, "Quantity");
        String lineToDisplayH = sku + " " + ean + " " + productName + " " + quantity;

        logger.info("Displaying orderLines for order {})", orderDTO.getOrderNumber());
        System.out.println(lineToDisplayH);
        for(OrderLineDTO line : orderDTO.getLineItems()){
            System.out.println(displayOrderLine(line, getProductDetailsFromOrderLine(line, productDTOList)));
        }

        System.out.println("Is all in stock?  1: Yes     2: No");
        int option = scanner.nextInt();
        if (option != 1){
            logger.warn("No enough stock was found for order {} ", orderDTO.getOrderNumber());
            processOrders();
            return;
        } else {
            logger.warn(("Stock confirmado para a encomenda"));

        }

        for(OrderLineDTO line : orderDTO.getLineItems()){
            ProductDTO productDTO = getProductDetailsFromOrderLine(line, productDTOList);
            if (!productDTO.getImages().isEmpty()){
                ShowImage.LaunchImage(productDTO.getImages().get(0).getSrc());
            }

            boolean proceed = false;
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

            MoloniService.printShopifyDocumentsInMoloni(orderDTO.getOrderNumber());



        }




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
            if (productDTO.getVariants().get(0).getSku().equals(line.getSku())){
                return productDTO;
            }
        }
        logger.error("Could not find product in store with sku {}", line.getSku());
        return null;
    }
}
