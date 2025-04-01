package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FulfillmentService {
    private static final Logger logger = LoggerFactory.getLogger(FulfillmentService.class);

    public static void main(String[] args) {
        //FulfillmentService.showPurchaseOrders();
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
            MoloniService.printShopifyDocumentsInMoloni(orderNumber, null);
        } else if (option == 4){
            System.out.println("DEBUG FECHAR SE NAO SABER DO QUE SE TRATA");
            String orderNumber = scanner.next();
            OrderDTO orderDTO = ShopifyOrderService.getFulfilledOrderByName(orderNumber);
            billingOrder(orderDTO, null);
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
                        line.setMoloniStock(s.getAvailableStock());
                        break;
                    }
                }
                if (line.getMoloniStock() == null) {
                    logger.info("Getting stock details for line {} of order {}", line.getSku(), order.getOrderNumber());
                    StockDetailsDTO stockDetailsDTO = new StockDetailsDTO(line.getSku());
                    stockDetailsDTO.setAvailableStock(MoloniService.getStock(line.getSku()));
                    stocks.add(stockDetailsDTO);
                    line.setMoloniStock(stockDetailsDTO.getAvailableStock());
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

    public static void showPurchaseOrders(){
        while (true){
            List<MoloniDocumentDTO> orders = MoloniService.getPurchaseOrders();
            List<MoloniDocumentDTO> ordersToProcess = new ArrayList<>();
            for (MoloniDocumentDTO i : orders){
                if (i.getDocumentValueEuros()>i.getDocumentReconciledValueEuros()){
                    ordersToProcess.add(i);
                }
            }
            int j = 0;
            for (MoloniDocumentDTO i : ordersToProcess) {
                logger.info("{} {} {}",j, i.getInternalOrderNumber(), i.getDocumentValueEuros());
                j++;
            }
            Scanner scanner = new Scanner(System.in);
            logger.info("Choose order number");
            int k = scanner.nextInt();
            shipPurchaseOrder(MoloniService.getPurchaseOrder(ordersToProcess.get(k).getDocumentId()));

        }

    }
    public static void shipPurchaseOrder(MoloniDocumentDTO purchaseOrder) {
        OutvioOrderDTO order = outvioOrderFromMoloniDocument(purchaseOrder);
        OutvioResponseDTO response = HttpRequestExecutor.sendRequest(OutvioResponseDTO.class, order, ConstantsEnum.OUTVIO_CREATE_ORDER_URL.getConstantValue().toString());

        logger.info("Create Outvio order for {} was sucessfull={}", purchaseOrder.getInternalOrderNumber(), response.getSuccess());


    }
    public static OutvioOrderDTO outvioOrderFromMoloniDocument (MoloniDocumentDTO documentDTO){

        MoloniEntityClientDTO client = MoloniService.getClient(null, null, null, documentDTO.getCustomerId(), null);
        OutvioOrderDTO outvioOrderDTO = new OutvioOrderDTO();

        // Fill OUTVIO_PARAMS
        OutvioOrderDTO.OutvioParamsDTO outvioParams = new OutvioOrderDTO.OutvioParamsDTO();
        outvioParams.setApiKey(ConstantsEnum.OUTVIO_API_KEY.getConstantValue().toString());
        outvioParams.setCmsId("api");
        outvioOrderDTO.setOutvioParams(outvioParams);
        outvioOrderDTO.setId(documentDTO.getInternalOrderNumber());

        // Fill client delivery details
        OutvioOrderDTO.ClientDTO clientDTO = new OutvioOrderDTO.ClientDTO();
        OutvioOrderDTO.ClientDTO.DeliveryDTO deliveryDTO = new OutvioOrderDTO.ClientDTO.DeliveryDTO();
        deliveryDTO.setAddress(documentDTO.getDeliveryDestinationAddress());
        deliveryDTO.setCity(documentDTO.getDeliveryDestinationCity());
        deliveryDTO.setCountryCode("PT");
        deliveryDTO.setEmail(client.getEmail());
        deliveryDTO.setName(documentDTO.getEntityName());
        deliveryDTO.setPhone(client.getPhone());
        deliveryDTO.setPostcode(documentDTO.getDeliveryDestinationZipCode());
        clientDTO.setDelivery(deliveryDTO);
        outvioOrderDTO.setClient(clientDTO);

        // Set currency
        outvioOrderDTO.setCurrency("EUR"); // Replace with the currency used in the document

        // Populate products
        List<OutvioOrderDTO.ProductDTO> products = new ArrayList<>();
        if (documentDTO.getProductDTOS() != null) {
            for (MoloniProductDTO moloniProduct : documentDTO.getProductDTOS()) {
                OutvioOrderDTO.ProductDTO productDTO = new OutvioOrderDTO.ProductDTO();
                productDTO.setName(moloniProduct.getProductName());
                productDTO.setPrice(moloniProduct.getPriceWithoutVat());
                productDTO.setQuantity(moloniProduct.getLineQuantity());
                products.add(productDTO);
            }
        }
        outvioOrderDTO.setProducts(products);

        // Set shipping information
        OutvioOrderDTO.ShippingDTO shippingDTO = new OutvioOrderDTO.ShippingDTO();
        shippingDTO.setPrice(5.0); // Replace with actual shipping price
        shippingDTO.setMethod("Shipping"); // Replace with the shipping method
        outvioOrderDTO.setShipping(shippingDTO);

        // Set total value
        outvioOrderDTO.setTotal(documentDTO.getDocumentValueEuros());
        return  outvioOrderDTO;
    }

    public static MoloniDocumentDTO createReceiptFromInvoiceAndCreditNote(
            MoloniDocumentDTO invoice, MoloniDocumentDTO creditNote, MoloniEntityClientDTO moloniEntityClientDTO) {

        if ( invoice == null || creditNote == null){
            logger.error("Unable to create receipt without valid credit note and invoice!");
            return null;
        }
        MoloniDocumentDTO receipt = new MoloniDocumentDTO();

        // Definir informações básicas do recibo
        receipt.setCompanyId(invoice.getCompanyId());
        receipt.setDate(creditNote.getDate()); // Usar a mesma data da nota de credito
        receipt.setDocumentSetId(invoice.getDocumentSetId());
        receipt.setCustomerId(invoice.getCustomerId());
        receipt.setInternalOrderNumber(invoice.getInternalOrderNumber());

        // Calcular o valor líquido do recibo
        double netValue = invoice.getDocumentValueEuros() - creditNote.getDocumentValueEuros();
        receipt.setDocumentValueEuros(netValue);

        // Associar a fatura e a nota de crédito
        List<MoloniDocumentDTO.AssociatedDocumentDTO> associatedDocuments = new ArrayList<>();

        // Documento associado: Fatura
        MoloniDocumentDTO.AssociatedDocumentDTO associatedInvoice = new MoloniDocumentDTO.AssociatedDocumentDTO();
        associatedInvoice.setAssociatedId(invoice.getDocumentId());
        associatedInvoice.setValue(invoice.getDocumentValueEuros());
        associatedDocuments.add(associatedInvoice);

        // Documento associado: Nota de Crédito
        MoloniDocumentDTO.AssociatedDocumentDTO associatedCreditNote = new MoloniDocumentDTO.AssociatedDocumentDTO();
        associatedCreditNote.setAssociatedId(creditNote.getDocumentId());
        associatedCreditNote.setValue(creditNote.getDocumentValueEuros()); // Valor negativo para abatimento
        associatedDocuments.add(associatedCreditNote);

        receipt.setAssociatedDocuments(associatedDocuments);

        // Detalhes do pagamento
        List<MoloniPaymentMethodDTO> payments = new ArrayList<>();
        MoloniPaymentMethodDTO payment = new MoloniPaymentMethodDTO();
        payment.setPaymentMethodId(ConstantsEnum.MOLONI_PAYMENT_METHOD_ID.getConstantValue().toString()); // Exemplo: ID do método de pagamento (Pagamento)
        payment.setDate(invoice.getDate()); // Data do pagamento
        payment.setValue(netValue); // Valor do pagamento
        payments.add(payment);

        receipt.setPayments(payments);
        receipt.setStatus(1);

        MoloniDocumentDTO.SendEmail sendEmailDTO = new MoloniDocumentDTO.SendEmail();
        List<MoloniDocumentDTO.SendEmail> array = new ArrayList<>();
        sendEmailDTO.setEmail(moloniEntityClientDTO.getEmail());
        sendEmailDTO.setName(moloniEntityClientDTO.getName());
        sendEmailDTO.setMsg("Olá! Em seguimento da emissão da fatura final, segue o recibo de regularização da mesma.");
        array.add(sendEmailDTO);
        receipt.setSendEmail(array);
        return receipt;
    }

    public static MoloniDocumentDTO createInvoiceFromOrderDTO(OrderDTO order, String documentSetName, OutvioResponseDTO outvioResponseDTO, MoloniEntityClientDTO moloniEntityClientDTO) {
        if (documentSetName == null || documentSetName.isEmpty()) {
            documentSetName = ConstantsEnum.MOLONI_DOCUMENTSET_WEB.getConstantValue().toString(); // Série padrão
        }

        MoloniDocumentDTO invoice = new MoloniDocumentDTO();

        // 1. Obter a série de documentos
        String documentSetId = MoloniService.getDocumentSetIdByName(documentSetName);
        invoice.setDocumentSetId(documentSetId);
        invoice.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());

        // 2. Definir a data da Fatura
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        invoice.setDate(formattedDate);
        invoice.setExpirationDate(formattedDate);

        if (outvioResponseDTO != null && order.getShippingAddress() != null){

            invoice.setDeliveryDestinationZipCode(order.getShippingAddress().getPostalCode());
            invoice.setDeliveryDestinationCity(order.getShippingAddress().getCity());
            invoice.setDeliveryDestinationAddress(order.getShippingAddress().getAddress1() + " " + Objects.requireNonNullElse(order.getShippingAddress().getAddress2(), ""));
            invoice.setDeliveryDestinationCountryId(MoloniService.getCountryIdByCountryISOCode(order.getShippingAddress().getCountryCode()));

            invoice.setDeliveryDepartureZipCode(ConstantsEnum.ADDRESS_ZIP_CODE.getConstantValue().toString());
            invoice.setDeliveryDepartureCity(ConstantsEnum.ADDRESS_CITY.getConstantValue().toString());
            invoice.setDeliveryDepartureAddress(ConstantsEnum.ADDRESS_TOTAL.getConstantValue().toString());
            invoice.setDeliveryDepartureCountryId(MoloniService.getCountryIdByCountryISOCode(ConstantsEnum.ADDRESS_COUNTRY_CODE.getConstantValue().toString()));
            invoice.setDeliveryMethodId(Integer.parseInt(MoloniService.getDeliveryMethodIdByName(outvioResponseDTO.getShipments().get(0).getCourier())));
            invoice.setDeliveryDatetime(formattedDate);
        }
        // 3. Associar a referência da encomenda (nosso número interno)
        invoice.setInternalOrderNumber(order.getOrderNumber());

        // 4. Buscar ou criar o cliente no Moloni
        MoloniEntityClientDTO client = MoloniService.getClientObject(order.getBillingAdress().getNipc(), order.getBillingAdress().getFirstName() + " " + order.getBillingAdress().getLastName(),
                order.getBillingAdress().getAddress1() + " " + order.getBillingAdress().getAddress2(), order.getBillingAdress().getPostalCode(), order.getBillingAdress().getCity(), order.getBillingAdress().getCountryCode(),
                        order.getEmail(), order.getBillingAdress().getPhone());

        MoloniEntityClientDTO moloniClient = MoloniService.getOrCreateClient(client);
        invoice.setCustomerId(moloniClient.getCustomerId());

        // 5. Adicionar os produtos da encomenda (mantendo os valores e descrição do Moloni)
        List<MoloniProductDTO> products = new ArrayList<>();
        for (OrderLineDTO lineItem : order.getLineItems()) {
            MoloniProductDTO moloniProduct = MoloniService.getProduct(lineItem.getSku());
            Double taxRate = lineItem.getTaxLineDTOS().get(0).getRate();

            String countryISO = null;
            if (order.getShippingAddress() != null){
                countryISO = order.getShippingAddress().getCountryCode();
            } else {
                countryISO = ConstantsEnum.ADDRESS_COUNTRY_CODE.getConstantValue().toString();
            }
            MoloniTaxesDTO moloniTax =MoloniService.getTaxesByCountryAndValue(countryISO, (int)Math.round(taxRate * 100));
            MoloniProductTaxesDTO tax = new MoloniProductTaxesDTO();
            tax.setTaxId(Long.parseLong(moloniTax.getTaxId().toString()));
            tax.setValueAmount((int)Math.round(taxRate * 100));
            if (moloniProduct != null) {
                MoloniProductDTO product = new MoloniProductDTO();
                product.setProductName(lineItem.getName()); // Mantém o nome do Moloni
                product.setPriceWithoutVat(lineItem.getPrice()/(1+lineItem.getTaxLineDTOS().get(0).getRate())); // Mantém o preço SEM IVA
                product.setLineQuantity(lineItem.getFulfillableQuantity());
                product.setProductId(moloniProduct.getProductId());
                product.setTaxes(List.of(tax)); // Mantém os impostos do Moloni

                products.add(product);
            } else {
                logger.error("Produto não encontrado no Moloni para SKU: {}", lineItem.getSku());
            }
        }
        MoloniProductDTO portes = MoloniService.getProduct("PORTES");

        // Verifica se o método de envio é recolha em loja (baseado no código de envio)
        boolean isStorePickup = order.getShippingLine().get(0).getShippingCode().toLowerCase().contains("smartify.pt");

        // Se não for recolha em loja, adiciona a alínea de portes
        if (!isStorePickup) {
            MoloniProductDTO portesDTO = new MoloniProductDTO();
            portesDTO.setProductName(order.getShippingLine().get(0).getShippingCode());

            double shippingPrice = Double.parseDouble(order.getShippingLine().get(0).getPrice());
            double taxRate = 0.0;

            // Se houver taxas nos portes, usa essa taxa
            if (!order.getShippingLine().get(0).getTaxLines().isEmpty()) {
                taxRate = order.getShippingLine().get(0).getTaxLines().get(0).getRate();
            }

            // Se não houver taxa definida nos portes, usa a maior taxa dos produtos da fatura
            if (taxRate == 0.0) {
                for (MoloniProductDTO product : products) {
                    if (!product.getTaxes().isEmpty() && product.getTaxes().get(0).getTax().getValue() > taxRate) {
                        taxRate = product.getTaxes().get(0).getTax().getValue();
                    }
                }
            }

            // Define o preço SEM IVA e aplica a taxa encontrada
            portesDTO.setPriceWithoutVat(shippingPrice / (1 + taxRate));
            portesDTO.setProductId(portes.getProductId());
            portesDTO.setLineQuantity(1);
            // Define a taxa de imposto (mesmo que seja grátis, tem imposto)
            MoloniTaxesDTO shippingTax = MoloniService.getTaxesByCountryAndValue(order.getBillingAdress().getCountryCode(), (int) Math.round(taxRate * 100));
            MoloniProductTaxesDTO tax = new MoloniProductTaxesDTO();
            tax.setTaxId(Long.parseLong(shippingTax.getTaxId().toString()));
            tax.setValueAmount((int) Math.round(taxRate * 100));
            portesDTO.setTaxes(List.of(tax));
            // Adiciona os portes à lista de produtos da fatura
            products.add(portesDTO);
            invoice.setProductDTOS(products.toArray(new MoloniProductDTO[0]));

            // 6. Definir o tipo de documento como "Fatura" (ID 1)
            invoice.setDocumentTypeId("1");

            // 7. Definir status como rascunho (0)
            invoice.setStatus(1);

            MoloniDocumentDTO.SendEmail sendEmailDTO = new MoloniDocumentDTO.SendEmail();
            List<MoloniDocumentDTO.SendEmail> array = new ArrayList<>();
            sendEmailDTO.setEmail(moloniEntityClientDTO.getEmail());
            sendEmailDTO.setName(moloniEntityClientDTO.getName());
            sendEmailDTO.setMsg("Olá! Em seguimento do seu pedido o qual agradecemos, segue a Fatura final.");
            array.add(sendEmailDTO);
            invoice.setSendEmail(array);
        }

        return invoice;
    }

    private static void processOrder(OrderDTO orderDTO, Scanner scanner, List<ProductDTO> productDTOList){
        logger.warn("Processing order {}", orderDTO.getOrderNumber());

        String sku = Utils.normalizeStringLenght(15,"Sku");
        String ean = Utils.normalizeStringLenght(14, "Barcode");
        String productName = Utils.normalizeStringLenght(65, "Product Name");
        String quantity = Utils.normalizeStringLenght(10, "Quantity");
        String lineToDisplayH = sku + " " + ean + " " + productName + " " + quantity + "  System Stock";

        logger.info("Displaying orderLines for order {}", orderDTO.getOrderNumber());
        System.out.println("Contents of order " + orderDTO.getOrderNumber());
        System.out.println("ORDER NOTES    " + orderDTO.getNote());
        System.out.println("Shipping method: " + orderDTO.getShippingLine().get(0).getShippingCode());
        String postCode = orderDTO.getShippingAddress() != null && orderDTO.getShippingAddress().getPostalCode() != null ? orderDTO.getShippingAddress().getPostalCode() + " " + orderDTO.getShippingAddress().getCity() + ", " + orderDTO.getShippingAddress().getCountry() : "Unavailable";
        System.out.println("Shipping to: " + postCode);
        System.out.println(lineToDisplayH);
        String[] lines = new String[orderDTO.getLineItems().size()];
        int lineN = 0;
        for(OrderLineDTO line : orderDTO.getLineItems()){
            if(line.getMoloniStock() == null){
                line.setMoloniStock(MoloniService.getStock(line.getSku()));
            }
            lines[lineN] = displayOrderLine(line, getProductDetailsFromOrderLine(line, productDTOList)) + "   "+ line.getMoloniStock();
            lineN++;
        }

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
                System.out.println("BYPASS or Type product " + line.getSku() + " barcode:");
                String barcode = scanner.next();
                String toMatch = productDTO.getVariants().get(0).getBarcode();
                if (toMatch.equals(barcode) || barcode.equals("BYPASS")){
                    proceed = true;
                } else {
                     MoloniProductDTO productDTO1 = MoloniService.getProduct(productDTO.sku());
                     loopA:
                     for (MoloniProductProperty i : productDTO1.getProperties()){
                         if (i.getTitle().equals("BarcodesAlternativosPorVirgula")){
                             toMatch =  toMatch+","+i.getValue();
                             String[] barcodes = toMatch.split(",");
                             for (String j : barcodes){
                                 if (j.equals(barcode)){
                                     proceed = true;
                                     break loopA;
                                 }
                             }
                             logger.warn("Barcode of product {}, shoud be {} and was matched as {}", line.getSku(), toMatch, barcode);
                             System.out.println("Barcode doesn't match. Proceed or try again? 1: Try Again  " + productDTO.getVariants().get(0).getBarcode() + ": prooceed");
                         }
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
            OutvioResponseDTO response = HttpRequestExecutor.sendRequest(OutvioResponseDTO.class, outvioShipDTO, ConstantsEnum.OUTVIO_SHIP_URL.getConstantValue().toString());

            if (response != null && response.getSuccess()){
                logger.info("Order {} was shipped successfully", orderDTO.getOrderNumber());
                logger.info("Printing label for order {}", orderDTO.getOrderNumber());
                PrinterService.print(ConstantsEnum.SYSTEM_PRINTER_LABEL.getConstantValue().toString(), response.getPdfLabelUrls()[0]);
            } else {
                logger.error("Could not print label for order {}", orderDTO.getOrderNumber());
                return;
            }
            FulfillmentService.billingOrder(orderDTO, response);
            MoloniService.printShopifyDocumentsInMoloni(orderDTO.getOrderNumber(), null);
            return;
        } else if (pickup) {
            fulfillOrderDirectly(orderDTO, "store-pickup", "store-pickup");

            FulfillmentService.billingOrder(orderDTO, null);
            MoloniService.printShopifyDocumentsInMoloni(orderDTO.getOrderNumber(), null);
            logger.warn("This is a store pickup print two copies of invoice");
            logger.warn("This is a store pickup print two copies of invoice");
            logger.warn("This is a store pickup print two copies of invoice");
            MoloniService.printShopifyDocumentsInMoloni(orderDTO.getOrderNumber(), "1");
            logger.warn("This is a store pickup print two copies of invoice");
            logger.warn("This is a store pickup print two copies of invoice");
        } else if (manualShipping){
            fulfillOrderDirectly(orderDTO, "expedido","expedido");
            logger.warn("This is a CTT order, prepare and take to CTT");
            logger.warn("This is a CTT order, prepare and take to CTT");
            logger.warn("This is a CTT order, prepare and take to CTT");
            FulfillmentService.billingOrder(orderDTO, null);
            MoloniService.printShopifyDocumentsInMoloni(orderDTO.getOrderNumber(), null);
            logger.warn("This is a CTT order, prepare and take to CTT");
            logger.warn("This is a CTT order, prepare and take to CTT");
        }
    }

    private static void billingOrder (OrderDTO orderDTO, OutvioResponseDTO outvioResponseDTO){
        MoloniEntityClientDTO client = MoloniService.getClient(orderDTO.getBillingAdress().getPhone(), null, orderDTO.getBillingAdress().getNipc(), null, orderDTO.getEmail());

        // 1 consultar FR adiantamento
        List<MoloniDocumentDTO> invoicesInserted = MoloniService.getAllInvoiceReceiptsBySetIdAndCustomer(ConstantsEnum.MOLONI_DOCUMENTSET_ADIANTAMENTO.getConstantValue().toString(),client.getCustomerId());
        String documentSelected = null;
        for (MoloniDocumentDTO i : invoicesInserted){
            if (i.getDocumentValueEuros() == Double.parseDouble(orderDTO.getTotalPrice())){
                documentSelected = i.getDocumentId();
            }
        }
        MoloniDocumentDTO invoiceReceipt = MoloniService.getOneInvoiceReceipt(documentSelected);

        MoloniEntityClientDTO moloniEntityClientDTO = MoloniService.getClient(null, null, null, invoiceReceipt.getCustomerId(), null);
        // 2 criar nota de credito de Adiantamento
        MoloniDocumentDTO dto = MoloniService.createCreditNoteFromInvoice(invoiceReceipt, moloniEntityClientDTO);
        dto = MoloniService.insertCreditNote(dto);

        List<MoloniDocumentDTO> creditNotes = MoloniService.getAllCreditNotesBySetIdAndCustomer(ConstantsEnum.MOLONI_DOCUMENTSET_ADIANTAMENTO.getConstantValue().toString(), client.getCustomerId());
        for (MoloniDocumentDTO i : creditNotes){
            if (i.getDocumentValueEuros() == Double.parseDouble(orderDTO.getTotalPrice())){
                documentSelected = i.getDocumentId();
            }
        }
        MoloniDocumentDTO creditNote = MoloniService.getOneCreditNote(documentSelected);


        // 3 criar fatura de encomenda shopify

        dto = createInvoiceFromOrderDTO(orderDTO, null, outvioResponseDTO, moloniEntityClientDTO);
        dto = MoloniService.insertInvoice(dto);

        List<MoloniDocumentDTO> invoices = MoloniService.getAllInvoicesBySetIdAndCustomer(null, client.getCustomerId());
        for (MoloniDocumentDTO i : invoices){
            if (i.getDocumentValueEuros() == Double.parseDouble(orderDTO.getTotalPrice())){
                documentSelected = i.getDocumentId();
            }
        }
        MoloniDocumentDTO invoiceDto = MoloniService.getOneInvoice(documentSelected);

        //4 criar recibo de FT
        dto = createReceiptFromInvoiceAndCreditNote(invoiceDto, creditNote, moloniEntityClientDTO);
        MoloniDocumentDTO receipt = MoloniService.insertReceipt(dto);

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

        String reqUrl = ConstantsEnum.FULFILLMENT_REQUEST_URL_PREFIX.getConstantValue().toString();
        Object result = HttpRequestExecutor.sendRequestShopify(Object.class, orders , reqUrl);
        System.out.println(result);
    }
}
