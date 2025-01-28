package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.util.ArrayMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MoloniService {
    private static final Logger logger = LoggerFactory.getLogger(MoloniService.class);

    public static final MoloniDocumentTypeDTO[] types = HttpRequestExecutor.sendRequest(MoloniDocumentTypeDTO[].class, new MoloniDocumentTypeDTO(), ConstantsEnum.MOLONI_DOCUMENT_GET_TYPES.getConstantValue().toString()+getToken());
    public static String token = null;
    public static LocalDateTime tokenDateTime = null;
    public static List<MoloniTaxesDTO> taxes = null;
    public static List<MoloniPaymentMethodDTO> paymentMethods;

    public MoloniService() {
    /*    try {
            logger.info("Initiating moloni service and getting document types");
            this.types = HttpRequestExecutor.sendRequest(MoloniDocumentTypeDTO[].class, new MoloniDocumentTypeDTO(), ConstantsEnum.MOLONI_DOCUMENT_GET_TYPES.getConstantValue().toString()+getToken());
            logger.debug("Initiated moloni service and got document types successfully");
        } catch (Exception e){
            logger.error("Error Initiating moloni service and getting document types. Trying again...");
            this.types = HttpRequestExecutor.sendRequest(MoloniDocumentTypeDTO[].class, new MoloniDocumentTypeDTO(), ConstantsEnum.MOLONI_DOCUMENT_GET_TYPES.getConstantValue().toString()+getToken());
        }*/
    }

    public static String getPaymentMethodIdByName (String name){
        List<MoloniPaymentMethodDTO> methods = getAllPaymentMethods();
        for (MoloniPaymentMethodDTO i : methods){
            if (name.equalsIgnoreCase(i.getName())){
                return i.getPaymentMethodId();
            }
        }
        logger.error("Couldn't find payment method with name {}", name);
        return null;
    }
    public static List<MoloniPaymentMethodDTO> getAllPaymentMethods (){
        if (paymentMethods != null) {
            return paymentMethods;
        } else {
            MoloniDocumentDTO documentDTO = new MoloniDocumentDTO();
            documentDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
            paymentMethods = Arrays.asList(HttpRequestExecutor.sendRequest(MoloniPaymentMethodDTO[].class, documentDTO, ConstantsEnum.MOLONI_PAYMENT_METHODS_GET_ALL.getConstantValue().toString()+getToken()));

            return getAllPaymentMethods();
        }

    }

    public static MoloniTaxesDTO  getTaxesByCountryAndValue (String countryISO, Integer value){
        logger.info("Getting tax by country and id value");
        List<MoloniTaxesDTO> taxesDTOS = getAllTaxes();
        for (MoloniTaxesDTO i : taxesDTOS){
            if (i.getFiscalZone().equalsIgnoreCase(countryISO) && i.getValue()==value){
                return i;
            }
        }
        logger.error("Could not find tax with fiscal zone {} and value {}", countryISO, value);
        return null;
    }

    public static List<MoloniTaxesDTO> getAllTaxes (){
        if (taxes != null) {
            return taxes;
        } else {
            MoloniDocumentDTO documentDTO = new MoloniDocumentDTO();
            documentDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
            taxes = Arrays.asList(HttpRequestExecutor.sendRequest(MoloniTaxesDTO[].class, documentDTO, ConstantsEnum.MOLONI_TAXES_GET_ALL.getConstantValue().toString()+getToken()));

            return getAllTaxes();
        }

    }
    public static Integer getCountryIdByName (String name){
        List<MoloniCountryDTO> countries = Arrays.asList(HttpRequestExecutor.sendRequest(MoloniCountryDTO[].class, null, ConstantsEnum.MOLONI_COUNTRIES_GET_ALL.getConstantValue().toString()+getToken()));

        for (MoloniCountryDTO i : countries){
            if (i.getName().equalsIgnoreCase(name)){
                return i.getCountryId();
            }
        }
        return null;

    }
    public static MoloniDocumentDTO getPurchaseOrder(String purchaseOrderId){
        MoloniDocumentDTO documentDTO = new MoloniDocumentDTO();
        documentDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        documentDTO.setDocumentId(purchaseOrderId);
        return HttpRequestExecutor.sendRequest(MoloniDocumentDTO.class, documentDTO, ConstantsEnum.MOLONI_PURCHASE_ORDER_GET_ONE_URL.getConstantValue().toString()+getToken());
    }
    public static List<MoloniDocumentDTO> getPurchaseOrders(){
        MoloniDocumentDTO documentDTO = new MoloniDocumentDTO();
        documentDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        return Arrays.asList(HttpRequestExecutor.sendRequest(MoloniDocumentDTO[].class, documentDTO, ConstantsEnum.MOLONI_PURCHASE_ORDER_GET_ALL_URL.getConstantValue().toString()+getToken()));
    }
    public static MoloniDocumentDTO createPurchaseOrder (MoloniDocumentDTO dto){
        return HttpRequestExecutor.sendRequest(MoloniDocumentDTO.class, dto, ConstantsEnum.MOLONI_INSERT_PURCHASE_ORDER_URL.getConstantValue().toString()+getToken());

    }

    public static String getDocumentSetIdByName (String containsName){

        MoloniDocumentSetDTO dto = new MoloniDocumentSetDTO();
        dto.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());

        MoloniDocumentSetDTO[] dtos = null;
        dtos = HttpRequestExecutor.sendRequest(MoloniDocumentSetDTO[].class, dto, ConstantsEnum.MOLONI_DOCUMENT_GET_DOCUMENT_SET_URL.getConstantValue().toString()+getToken());
        List<MoloniDocumentSetDTO> list = Arrays.asList(dtos);

        for (MoloniDocumentSetDTO i : list){
            if (i.getName().toLowerCase().contains(containsName.toLowerCase())){
                return i.getDocumentSetId();
            }
        }
        return null;
    }

    public static String getNextClientNumber (){
        MoloniEntityClientDTO entityClientDTO = new MoloniEntityClientDTO();
        entityClientDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        entityClientDTO = HttpRequestExecutor.sendRequest(MoloniEntityClientDTO.class, entityClientDTO, ConstantsEnum.MOLONI_CLIENT_GET_NEXT_NUMBER_URL.getConstantValue().toString()+getToken());

        return entityClientDTO.getClientNumber();
    }

    public static MoloniEntityClientDTO getOrCreateClient (MoloniEntityClientDTO clientToGetOrCreate){
        MoloniEntityClientDTO result = getClient(clientToGetOrCreate.getPhone() , clientToGetOrCreate.getClientNumber(), clientToGetOrCreate.getVat(), clientToGetOrCreate.getCustomerId(), clientToGetOrCreate.getEmail());
        if ( result != null){
            return result;
        }

        result = HttpRequestExecutor.sendRequest(MoloniEntityClientDTO.class, clientToGetOrCreate, ConstantsEnum.MOLONI_CLIENT_INSERT_URL.getConstantValue().toString()+getToken());


        return result;
    }
    public static MoloniEntityClientDTO getClient (String phone, String customerNumber, String vatId, String customerId, String email){
        List<MoloniEntityClientDTO> clientDTOS = new ArrayList<>();

        if (customerId != null && !customerId.equals("")){
            MoloniEntityClientDTO result = getClientById(customerId);
            if (result != null){
                return result;
            }
        }
        if (email != null && !email.equals("")){
            clientDTOS = getClientByEmail(email);
            for (MoloniEntityClientDTO i : clientDTOS){
                if (i.getEmail().equals(email)){
                    return i;
                }
            }
        }
        if (customerNumber != null && !customerNumber.equals("")){
            clientDTOS =getClientBySearch(customerNumber);
            for (MoloniEntityClientDTO i : clientDTOS){
                if (i.getClientNumber().equals(customerNumber)){
                    return i;
                }
            }
        }
        if (vatId != null && !vatId.equals("")){
            clientDTOS =getClientBySearch(vatId);
            for (MoloniEntityClientDTO i : clientDTOS){
                if (i.getVat().equals(vatId)){
                    return i;
                }
            }
        }
        if (phone != null && !phone.equals("")){
            clientDTOS =getClientBySearch(phone);
            for (MoloniEntityClientDTO i : clientDTOS){
                if (i.getPhone().equals(phone)){
                    return i;
                }
            }
        }
        return null;
    }
    private static MoloniEntityClientDTO getClientById (String customerId){
        MoloniEntityClientDTO moloniEntityClientDTO = new MoloniEntityClientDTO();
        moloniEntityClientDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        moloniEntityClientDTO.setCustomerId(customerId);
        return HttpRequestExecutor.sendRequest(MoloniEntityClientDTO.class, moloniEntityClientDTO, ConstantsEnum.MOLONI_CLIENT_GET_ONE_URL.getConstantValue().toString()+getToken());
    }
    private static List<MoloniEntityClientDTO> getClientBySearch(String search) {

        MoloniEntityClientDTO moloniEntityClientDTO = new MoloniEntityClientDTO();
        moloniEntityClientDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        moloniEntityClientDTO.setSearch(search);
        MoloniEntityClientDTO[] moloniEntityClientListObjectDTO = HttpRequestExecutor.sendRequest(MoloniEntityClientDTO[].class, moloniEntityClientDTO, ConstantsEnum.MOLONI_CLIENT_GET_BY_SEARCH_URL.getConstantValue().toString()+getToken());

        return Arrays.asList(moloniEntityClientListObjectDTO);
    }

    private static List<MoloniEntityClientDTO> getClientByEmail(String email) {
        MoloniEntityClientDTO moloniEntityClientDTO = new MoloniEntityClientDTO();
        moloniEntityClientDTO.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        moloniEntityClientDTO.setEmail(email);
        MoloniEntityClientDTO[] moloniEntityClientListObjectDTO = HttpRequestExecutor.sendRequest(MoloniEntityClientDTO[].class, moloniEntityClientDTO, ConstantsEnum.MOLONI_CLIENT_GET_BY_EMAIL_URL.getConstantValue().toString()+getToken());
        return Arrays.asList(moloniEntityClientListObjectDTO);
    }

    public static MoloniProductStocksDTO[] getStockMovements(String sku, LocalDateTime beforeDate, LocalDateTime untilDate){
        // beforeDate format yyyy-MM-dd
        logger.debug("Getting stock movements for {} and beforeDate '{}' with results until '{}'", sku, beforeDate, untilDate);

        MoloniProductDTO productDTO = getProduct(sku);
        MoloniProductStocksDTO[] stockMovements;
        try {
            MoloniProductStocksDTO productStocks = new MoloniProductStocksDTO(productDTO.getProductId());
            stockMovements = HttpRequestExecutor.sendRequest(MoloniProductStocksDTO[].class, productStocks, ConstantsEnum.MOLONI_STOCKS_GET_ALL.getConstantValue().toString()+getToken());
            MoloniProductStocksDTO lastMovement = stockMovements[stockMovements.length-1];
            lastMovement.setMovementDate(Utils.StringMoloniDateTime(lastMovement.getMovementDateString()));

            if (beforeDate != null && stockMovements.length>=50 && lastMovement.getMovementDate().isAfter(beforeDate)){
                String day = beforeDate.getDayOfMonth() > 9? beforeDate.getDayOfMonth() + "" : "0"+beforeDate.getDayOfMonth();
                String month = beforeDate.getMonthValue() > 9 ? beforeDate.getMonth() + "": "0"+beforeDate.getMonth();
                String bDate = beforeDate.getYear() + "-" + month + "-" + day;
                productStocks.setMovementDateBefore(bDate);

                logger.info("Getting stock movements for {} before {}", sku, beforeDate);
                stockMovements = HttpRequestExecutor.sendRequest(MoloniProductStocksDTO[].class, productStocks, ConstantsEnum.MOLONI_STOCKS_GET_ALL.getConstantValue().toString()+getToken());
            }
            lastMovement = stockMovements[stockMovements.length-1];
            lastMovement.setMovementDate(Utils.StringMoloniDateTime(lastMovement.getMovementDateString()));
            int skipResults = stockMovements.length;
            while (untilDate != null && stockMovements.length>= 50 && lastMovement.getMovementDate().isAfter(untilDate)){
                logger.info("Results might be paginated for stockMovements of {}, skipping the first {} results", sku, skipResults);
                MoloniProductStocksDTO[] mvm;
                skipResults = skipResults + 50;
                productStocks.setSkipFirstResults(skipResults);
                mvm = HttpRequestExecutor.sendRequest(MoloniProductStocksDTO[].class, productStocks, ConstantsEnum.MOLONI_STOCKS_GET_ALL.getConstantValue().toString()+getToken());
                MoloniProductStocksDTO[] newArrayMovements = new MoloniProductStocksDTO[stockMovements.length+mvm.length];
                int i = 0;
                for (MoloniProductStocksDTO j : stockMovements){
                    newArrayMovements[i] = j;
                    i++;
                }
                for (MoloniProductStocksDTO j : mvm){
                    newArrayMovements[i] = j;
                    i++;
                }
                stockMovements = newArrayMovements;
                lastMovement = stockMovements[stockMovements.length-1];
                lastMovement.setMovementDate(Utils.StringMoloniDateTime(lastMovement.getMovementDateString()));
                if (mvm.length < 50) break;
            }
        } catch (Exception e){
            logger.error("Could not get stock movements for {}", sku);
            stockMovements = new MoloniProductStocksDTO[0];
        }
        logger.info("Got {} stock movements for {}", stockMovements.length,sku);
        return stockMovements;
    }

    public static boolean isSupplierDocumentTypeIdStatic(String documentTypeId){
        MoloniDocumentTypeDTO[] types = HttpRequestExecutor.sendRequest(MoloniDocumentTypeDTO[].class, new MoloniDocumentTypeDTO(), ConstantsEnum.MOLONI_DOCUMENT_GET_TYPES.getConstantValue().toString()+getToken());
        for (MoloniDocumentTypeDTO type : types){
            if(type.getDocumentTypeId().equals(documentTypeId)){
                if(type.getTitle().toLowerCase(Locale.ROOT).contains("fornecedor")){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static MoloniDocumentDTO getMoloniDocumentDTObyId(String documentId){

        logger.info("Getting moloni document by Id {}", documentId);
        MoloniDocumentDTO docFetchedById = new MoloniDocumentDTO();
        docFetchedById.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        docFetchedById.setDocumentId(documentId);
        return HttpRequestExecutor.sendRequest(MoloniDocumentDTO.class, docFetchedById, ConstantsEnum.MOLONI_DOCUMENT_GET_ONE.getConstantValue().toString()+getToken());

    }

    public static MoloniDocumentDTO[] getMoloniDocumentsByType (MoloniDocumentTypeDTO type){
        MoloniDocumentDTO doc = new MoloniDocumentDTO();
        doc.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        doc.setDocumentTypeId(type.getDocumentTypeId());

        return HttpRequestExecutor.sendRequest(MoloniDocumentDTO[].class, doc, ConstantsEnum.MOLONI_DOCUMENT_GET_ALL.getConstantValue().toString()+getToken());

    }
    public static void getProfit(Scanner scanner) {
        logger.info("Starting getProfit method");
        logger.info("Please choose the document type:");
        for (int i =0; i<MoloniService.types.length; i++){
            logger.info(i + "    " + MoloniService.types[i].getTitle());
        }
        MoloniDocumentTypeDTO type = MoloniService.types[scanner.nextInt()];
        MoloniDocumentDTO[] moloniDocumentDTOS = getMoloniDocumentsByType(type);

        for (int i = 0; i< moloniDocumentDTOS.length;i++){
            logger.info("{} {}  {}  {}   {}   {} ", i,moloniDocumentDTOS[i].getDate(), moloniDocumentDTOS[i].getDocumentSetName(), moloniDocumentDTOS[i].getDocumentNumber(),
                    moloniDocumentDTOS[i].getDocumentValueEuros() ,moloniDocumentDTOS[i].getEntityName());
        }

        MoloniDocumentDTO finalDocument = getMoloniDocumentDTObyId(moloniDocumentDTOS[scanner.nextInt()].getDocumentId());
        logger.info("Document contains:");
        double documentProfit = 0.0;
        List<MoloniDocumentLineDTO> lines = new ArrayList<>();
        for (int i = 0; i< finalDocument.getProductDTOS().length; i++){
            logger.info("SKU:{} Preço:{} Qty:{} Nome:{}", finalDocument.getProductDTOS()[i].getSku(), finalDocument.getProductDTOS()[i].getPriceWithoutVat(), finalDocument.getProductDTOS()[i].getLineQuantity()
                , finalDocument.getProductDTOS()[i].getProductName());
            double costPrice = StockKeepingUnitsService.getCostPrice(finalDocument.getDate(), finalDocument.getProductDTOS()[i].getSku());
            logger.info("Cost price: {}", costPrice);
            double discount = finalDocument.getProductDTOS()[i].getDiscount()!= null ? finalDocument.getProductDTOS()[i].getDiscount() : 0;
            double profitUnit = finalDocument.getProductDTOS()[i].getPriceWithoutVat()*((100-discount)/100)-costPrice;
            double profit = profitUnit * finalDocument.getProductDTOS()[i].getLineQuantity();
            documentProfit = documentProfit + profit;

            MoloniDocumentLineDTO line = new MoloniDocumentLineDTO();
            line.setSku(finalDocument.getProductDTOS()[i].getSku());
            line.setProductName(finalDocument.getProductDTOS()[i].getProductName());
            line.setSellPrice(finalDocument.getProductDTOS()[i].getPriceWithoutVat());
            line.setQuantity(finalDocument.getProductDTOS()[i].getLineQuantity());
            line.setCostPrice(costPrice);
            line.setMarginPerUnit(profitUnit);
            line.setProfit(profit);
            lines.add(line);
            logger.info("Sale price of {}  is {} with cost price {}, and margin per unit is {}, total line profit is {}", finalDocument.getProductDTOS()[i].getSku(),
                    finalDocument.getProductDTOS()[i].getPriceWithoutVat(), costPrice, finalDocument.getProductDTOS()[i].getPriceWithoutVat()-costPrice, profit);
        }
        for (MoloniDocumentLineDTO i : lines){
            logger.info("SKU:{} SellPrice:{} CostPrice:{} SellQuantity:{} Margin:{} Profit:{} {}", Utils.normalizeStringLenght(15,i.getSku()), Utils.normalizeStringLenght(5,i.getSellPrice()+""), Utils.normalizeStringLenght(5,""+i.getCostPrice()),
                    Utils.normalizeStringLenght(3,""+i.getQuantity()), Utils.normalizeStringLenght(5,""+i.getMarginPerUnit()), Utils.normalizeStringLenght(7,""+i.getProfit()), Utils.normalizeStringLenght(50,i.getProductName()));
        }
        logger.info("This document had profit of {}", documentProfit);
        scanner.next();
        getProfit(scanner);
    }

    public static boolean isSupplierDocumentTypeId(String documentTypeId){
        for (MoloniDocumentTypeDTO type : types){
            if(type.getDocumentTypeId().equals(documentTypeId)){
                if(type.getTitle().toLowerCase(Locale.ROOT).contains("fornecedor")){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private static String getToken(){
        if (token != null && !LocalDateTime.now().isAfter(tokenDateTime.plusMinutes(59))){
            return token;
        }
        try{
            logger.info("Getting token for moloni request");
            TypeReference<MoloniTokenDTO> typeReference = new TypeReference<MoloniTokenDTO>(){};
            MoloniTokenDTO moloniToken = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.MOLONI_GET_TOKEN.getConstantValue().toString(), null, null, new ArrayMap<>());
            logger.debug("Got moloni token {}",moloniToken.getAccessToken());
            token = moloniToken.getAccessToken();
            tokenDateTime = LocalDateTime.now();
            return moloniToken.getAccessToken();
        } catch (Exception e){
            logger.error("Getting token for moloni failed. Trying again");
            return getToken();
        }
    }
    
    public static int getStock(String sku){
        logger.info("Getting stock for {}", sku);
        try {
            MoloniProductDTO productDTO = getProduct(sku);
            if (productDTO.getSku() != null){
                if (!isBundle(productDTO)){
                    return productDTO.getInventory();
                } else {
                    Integer stock = null;
                    for (MoloniChildProductDTO i : productDTO.getChildProducts()){
                        int childProductStock = i.getProductDTO().getInventory()/ i.getQuantity();
                        if (stock == null ){
                            stock = childProductStock;
                        } else {
                            if (childProductStock < stock){
                                stock = childProductStock;
                            }
                        }
                    }
                    return stock;
                }
            }
            return 0;
        } catch (Exception e){
            logger.error("There was an error getting product {}", sku);
            return 0;
        }
    }

    public static boolean isBundle (MoloniProductDTO productDTO){
        if (productDTO.getChildProducts()!= null && productDTO.getChildProducts().length>0){
            return true;
        }
        return false;
    }




    public static boolean existsProductMoloni(String sku){
        logger.info("Checking if exists sku in moloni {}", sku);
        MoloniProductDTO product = getProduct(sku);
        if (product == null){
            return false;
        } else {
            return true;
        }
    }

    public static List<MoloniProductDTO> getAllProducts(){
        logger.info("Getting All MoloniProductsDTO");
        List<MoloniProductDTO> result = new ArrayList<>();
        try {
            MoloniProductDTO product = new MoloniProductDTO();
            product.setCompanyId(Long.parseLong(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString()));
            product.setSkipFirstResults(0);
            MoloniProductDTO[] products = new MoloniProductDTO[0];
            boolean first = true;
            while (products.length != 0 || first){
                if (!first) {
                    product.setSkipFirstResults(product.getSkipFirstResults()+50);
                }
                first =false;
                products = HttpRequestExecutor.sendRequest(MoloniProductDTO[].class, product, ConstantsEnum.MOLONI_PRODUCT_GET_ALL.getConstantValue().toString()+getToken());

                for (MoloniProductDTO p : products){
                    if (p.getHasStock()==1){
                        result.add(p);
                    }
                }
            }

        } catch (Exception e) {
            return null;
        }

        return result;
    }

    public static MoloniProductDTO getProduct(String sku){
        logger.info("Getting MoloniProductDTO sku from moloni {}", sku);
        try {
            MoloniProductDTO product = new MoloniProductDTO(Long.parseLong(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString()), sku);
            MoloniProductDTO[] products = HttpRequestExecutor.sendRequest(MoloniProductDTO[].class, product, ConstantsEnum.MOLONI_PRODUCT_GET_ONE.getConstantValue().toString()+getToken());

            if(products != null && products.length != 0){
                for (MoloniProductDTO i : products){
                    if(i.getSku().equals(sku)){
                        return i;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;

    }

    public static void getOneDocument(String documentId){
        //MoloniDocumentDTO response = HttpRequestExecutor.sendRequest(MoloniDocumentDTO.class, document, ConstantsEnum.MOLONI_DOCUMENT_GET_ONE.getConstantValue()+getToken());

    }

    public static void printShopifyDocumentsInMoloni(String shopifyOrderNumber ){
        String[] documentIds = getMoloniDocumentIdsFromShopifyOrder(shopifyOrderNumber);
        if (documentIds == null ){
            logger.error("Unable to get document don't close this order {} and show supervisor",shopifyOrderNumber);
            logger.error("Unable to get document don't close this order {} and show supervisor",shopifyOrderNumber);
        } else {
            for(int i = 0; i<documentIds.length; i++){
                String printUrl = getPdfLink(documentIds[i]);
                PrinterService.print(ConstantsEnum.SYSTEM_PRINTER_PAPER.getConstantValue().toString(), printUrl);
            }
        }


    }
    public static String getPdfLink(String documentId){
        String result = "";
        Map<String, String> queryParams;
        logger.info("Getting pdf link for documentId {}", documentId);
        MoloniDocumentDTO document = new MoloniDocumentDTO();
        document.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        document.setDocumentId(documentId);
        MoloniDocumentDTO response = HttpRequestExecutor.sendRequest(MoloniDocumentDTO.class, document, ConstantsEnum.MOLONI_DOCUMENT_PDF_LINK.getConstantValue()+getToken());
        if (response != null && response.getPdfLinkRequestValid() == null){
            logger.info("Building pdf link for documentId {}", documentId);
            queryParams = Utils.getQueryParamsFromUrl(response.getPdfDownloadUrl());
            result = ConstantsEnum.MOLONI_DOCUMENT_PDF_LINK_DOWNLOAD.getConstantValue().toString();
            result = Utils.addParameter(result, "action", "getDownload");
            result = Utils.addParameter(result, "h",queryParams.get("h"));
            result = Utils.addParameter(result, "d",queryParams.get("d"));
            result = Utils.addParameter(result, "e","syw@smartify.pt");
            result = Utils.addParameter(result, "i","1");
            logger.info("Built pdf link for documentId {} as {}", documentId, result);
        } else {
            logger.error("Could not get pdf link for documentId {}", documentId);
        }

        return result;
    }
    private static String[] getMoloniDocumentIdsFromShopifyOrder(String shopifyOrderNumber){
        return getMoloniDocumentIdsFromShopifyOrder(shopifyOrderNumber, 0);
    }
    private static String[] getMoloniDocumentIdsFromShopifyOrder(String shopifyOrderNumber, int tries){
        logger.info("Trying to get document for shopify order {}", shopifyOrderNumber);
        MoloniDocumentDTO document = new MoloniDocumentDTO();
        document.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        document.setInternalOrderNumber(shopifyOrderNumber);
        String[] documentIds;
        try {
            logger.info("Waiting 15 seconds for document to be created and then retrieved for order {}", shopifyOrderNumber);
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            logger.error("Waiting 15 seconds for document unsuccessful");
        }
        MoloniDocumentDTO[] response = HttpRequestExecutor.sendRequest(MoloniDocumentDTO[].class, document, ConstantsEnum.MOLONI_DOCUMENT_GET_ALL.getConstantValue()+getToken());
        if(response.length==1){
            logger.info("Document was found with order number {}",shopifyOrderNumber);
            documentIds = new String[1];
            documentIds[0] = response[0].getDocumentId();
        } else if(response.length > 1){
            logger.warn("More than one documents were found for order {}",shopifyOrderNumber);
            documentIds = new String[response.length];
            for (int i = 0; i<response.length; i++){
                documentIds[i] = response[i].getDocumentId();
            }
        } else {
            documentIds = null;
            logger.info("No document was found with order number {} trying again in 15 seconds",shopifyOrderNumber);

            tries++;
            if(tries<=6){
                documentIds = getMoloniDocumentIdsFromShopifyOrder(shopifyOrderNumber, tries);
            } else {
                logger.error("Unable to get document don't close this order {} and show supervisor",shopifyOrderNumber);
                logger.error("Unable to get document don't close this order {} and show supervisor",shopifyOrderNumber);
                logger.error("Unable to get document don't close this order {} and show supervisor",shopifyOrderNumber);
            }
        }
        return documentIds;
    }

    public static boolean createMoloniProduct(ProductDTO productDTO){
        logger.info("Trying to create product {}", productDTO.sku());
        if(existsProductMoloni(productDTO.sku())){
            logger.info("Moloni sku {} already exists", productDTO.sku());
            return false;
        } else {
            MoloniProductDTO moloniProductDTO = new MoloniProductDTO();
            moloniProductDTO.setCompanyId(Long.parseLong(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString()));
            moloniProductDTO.setCategoryId(Long.parseLong(ConstantsEnum.MOLONI_CATEGORY_ID_SHOPIFY.getConstantValue().toString()));
            moloniProductDTO.setType(1L);
            moloniProductDTO.setProductName(productDTO.getTitle());
            moloniProductDTO.setSku(productDTO.sku());
            moloniProductDTO.setEan(productDTO.barcode());
            moloniProductDTO.setPriceWithoutVat(productDTO.priceWithoutVat());
            moloniProductDTO.setMeasurementUnitId(Long.parseLong(ConstantsEnum.MOLONI_MEASUMENTS_UNIT_ID.getConstantValue().toString()));
            moloniProductDTO.setHasStock(1L);
            moloniProductDTO.setProductCategory("M");
            moloniProductDTO.setInventory(0);
            List<MoloniProductTaxesDTO> taxes = new ArrayList<>();
            taxes.add(new MoloniProductTaxesDTO(Long.parseLong(ConstantsEnum.MOLONI_TAX_ID_VATNORMAL.getConstantValue().toString()),0L,0L));
            moloniProductDTO.setTaxes(taxes);

            createProduct(moloniProductDTO);

            logger.info("Moloni sku {} created successfully", productDTO.sku());
            return true;
        }

    }

    private static void updateProduct(MoloniProductDTO productToUpdate){
        HttpRequestExecutor.sendRequest(Object.class, productToUpdate, ConstantsEnum.MOLONI_PRODUCT_UPDATE.getConstantValue().toString()+getToken());
    }

    private static void createProduct(MoloniProductDTO productToCreate){
        HttpRequestExecutor.sendRequest(Object.class, productToCreate, ConstantsEnum.MOLONI_PRODUCT_CREATE.getConstantValue().toString()+getToken());

    }

    public static void syncMoloniProduct(ProductDTO productDTO){
        logger.info("Syncing product {} data to Moloni", productDTO.sku());
        MoloniProductDTO moloniProductDTO = getProduct(productDTO.sku());
        boolean isNeededToSync = true;

        if (moloniProductDTO != null){
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            double shopifyProductPrice = Double.parseDouble(decimalFormat.format(productDTO.getVariants().get(0).getPrice()/Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString())));
            double moloniProductPrice = Double.parseDouble(decimalFormat.format(moloniProductDTO.getPriceWithoutVat()));
            if (productDTO.getTitle().equals(moloniProductDTO.getProductName()) && shopifyProductPrice - moloniProductPrice == 0.0){
                if(productDTO.getVariants().get(0).getBarcode() != null && productDTO.getVariants().get(0).getBarcode().equals(moloniProductDTO.getEan())){
                    isNeededToSync = false;
                    logger.info("Not syncing because already up to date: " + productDTO.sku()+ " " + productDTO.getTitle());
                }
            }

            if (isNeededToSync){
                moloniProductDTO = new MoloniProductDTO(moloniProductDTO.getCompanyId(), moloniProductDTO.getProductId(),
                        productDTO.getVariants().get(0).getBarcode(), productDTO.getTitle(), productDTO.sku(),
                        shopifyProductPrice);
                updateProduct(moloniProductDTO);
                logger.info("Synced successfully: " + productDTO.sku()+ " " + productDTO.getTitle());
            }
        } else {
            logger.info("Not syncing because already up to date: " + productDTO.sku()+ " " + productDTO.getTitle());
        }
    }

    public static List<SupplierOrderedLineDate> getSupplierOrderedLineDatesPerSku(String sku, List<SupplierOrderedLineDate> skus ){
        logger.info("getSupplierOrderedLineDatesPerSku for {}", sku);
        List<SupplierOrderedLineDate> newList = new ArrayList<>();
        for (SupplierOrderedLineDate i : skus){
            if (i.getSku().equals(sku)){
                newList.add(i);
            }
        }
        return newList;
    }

    public static List<SupplierOrderedLineDate> getSupplierOrderedLines (){
        logger.info("Getting supplier ordered lines");
        //id 7 = nota de encomenda de fornecedor
        MoloniDocumentTypeDTO type7 = new MoloniDocumentTypeDTO("7");
        MoloniDocumentDTO[] documents =getMoloniDocumentsByType(type7);
        List<MoloniDocumentDTO> finalDocuments = new ArrayList<>();
        List<SupplierOrderedLineDate> supplierOrderedLineDates = new ArrayList<>();
        for (MoloniDocumentDTO i : documents){
            if (i.getDocumentReconciledValueEuros() == 0){
                MoloniDocumentDTO doc = getMoloniDocumentDTObyId(i.getDocumentId());
                if (doc == null) continue;
                finalDocuments.add(doc);
                for (MoloniProductDTO j : doc.getProductDTOS()){
                    SupplierOrderedLineDate n = new SupplierOrderedLineDate();
                    n.setDateOrdered(doc.getLastModified());
                    n.setSupplier(doc.getEntityName());
                    n.setDateExpected(doc.getDate());
                    n.setDescription(j.getProductName());
                    n.setPurchasePrice(j.getPriceWithoutVat().toString());
                    n.setQuantity(j.getLineQuantity().toString());
                    n.setSku(j.getSku());
                    supplierOrderedLineDates.add(n);
                }
            }

        }
        return supplierOrderedLineDates;

    }

    public static void syncAllMoloniProducts() {
        List<ProductDTO> products = ShopifyProductService.getShopifyProductList();
        List<ProductDTO> productsToSync = new ArrayList<>();
        boolean isToAdd = true;
        for (ProductDTO productDTO : products) {
            for (ProductDTO productDTO2 : products) {
                if (!productDTO.getId().equals(productDTO2.getId()) &&
                        productDTO.sku() != null &&
                        productDTO2.sku() != null &&
                        productDTO.sku().equals(productDTO2.sku())) {
                    isToAdd=false;
                    break;
                }
            }
            if (isToAdd){
                productsToSync.add(productDTO);
            }
            isToAdd = true;
        }

        for (ProductDTO productDTO : productsToSync) {
            logger.info("Preparing to sync: " + productDTO.sku()+ " " + productDTO.getTitle());
            syncMoloniProduct(productDTO);
        }
    }

    public static void insertInvoiceReceipt(MoloniInvoiceReceiptDTO dto){
        HttpRequestExecutor.sendRequest(MoloniInvoiceReceiptDTO.class, dto, ConstantsEnum.MOLONI_INVOICE_RECEIPT_INSERT_URL.getConstantValue().toString()+getToken());

    }
    public static MoloniInvoiceReceiptDTO createInvoiceReceiptFromShopifyOrder(ShopifyWebhookPayloadDTO shopifyPayload) {
        MoloniInvoiceReceiptDTO invoice = new MoloniInvoiceReceiptDTO();

        // 1. Obter a série de documentos dinamicamente
        String documentSetId = MoloniService.getDocumentSetIdByName("ADIANTAMENTO");
        invoice.setDocumentSetId(documentSetId);
        invoice.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        // 2. Data atual como data da fatura
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ISO_DATE); // Formato ISO 8601 (ex.: "2025-01-21")
        invoice.setDate(formattedDate);

        // 3. Configurar os dados do cliente
        MoloniEntityClientDTO client = new MoloniEntityClientDTO();
        client.setName(shopifyPayload.getCustomer().getFirstName() + " " + shopifyPayload.getCustomer().getLastName());
        client.setVat(shopifyPayload.getBillingAddress().getVatId() != null ? shopifyPayload.getBillingAddress().getVatId() : null);
        client.setAddress(shopifyPayload.getBillingAddress().getAddress1() + " " + shopifyPayload.getBillingAddress().getAddress2());
        client.setCity(shopifyPayload.getBillingAddress().getCity());
        client.setZipCode(shopifyPayload.getBillingAddress().getPostcode());
        client.setCountryId(MoloniService.getCountryIdByName(shopifyPayload.getBillingAddress().getCountry()));

        MoloniEntityClientDTO moloniClient = MoloniService.getOrCreateClient(client);
        invoice.setEntityName(moloniClient.getName());
        invoice.setEntityVat(moloniClient.getVat());
        invoice.setEntityAddress(moloniClient.getAddress());
        invoice.setEntityCity(moloniClient.getCity());
        invoice.setEntityZipCode(moloniClient.getZipCode());
        invoice.setEntityCountry(moloniClient.getCountry().getName());
        invoice.setEntityCountryId(moloniClient.getCountryId());
        invoice.setCustomerId(moloniClient.getCustomerId());

        // 4. Agrupar os produtos por tipo de IVA
        Map<Double, Double> ivaGroupedTotals = new HashMap<>();

        for (ShopifyWebhookPayloadDTO.LineItem lineItem : shopifyPayload.getLineItems()) {
            for (ShopifyWebhookPayloadDTO.TaxLine taxLine : lineItem.getTaxLines()) {
                double taxRate = taxLine.getRate(); // Por exemplo, 0.23 para 23% de IVA
                double lineTotalExclTax = Double.parseDouble(lineItem.getPrice()) - Double.parseDouble(lineItem.getTaxLines().get(0).getPrice());

                ivaGroupedTotals.put(taxRate, ivaGroupedTotals.getOrDefault(taxRate, 0.0) + lineTotalExclTax);
            }
        }

        // 5. Criar produtos por tipo de IVA
        List<MoloniProductDTO> products = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : ivaGroupedTotals.entrySet()) {
            double taxRate = entry.getKey();
            double totalValue = entry.getValue();

            MoloniProductDTO mp = MoloniService.getProduct("ADIANTAMENTO");
            MoloniProductDTO product = new MoloniProductDTO();
            product.setProductName("ADIANTAMENTO - IVA " + (taxRate * 100) + "%");
            product.setPriceWithoutVat(totalValue);
            product.setLineQuantity(1);
            product.setProductId(mp.getProductId());

            // Adicionar os impostos ao produto
            String countryISO = null;
            if (shopifyPayload.getShippingAddress() != null){
                countryISO = shopifyPayload.getShippingAddress().getCountryISOCode();
            } else {
                countryISO = shopifyPayload.getCustomer().getDefaultAddress().getCountryISOCode();
            }

            MoloniTaxesDTO moloniTax =getTaxesByCountryAndValue(countryISO, (int)Math.round(taxRate * 100));
            MoloniProductTaxesDTO tax = new MoloniProductTaxesDTO();
            tax.setTaxId(Long.parseLong(moloniTax.getTaxId().toString()));
            tax.setValueAmount((int)Math.round(taxRate * 100));
            product.setTaxes(List.of(tax));

            products.add(product);
        }

        invoice.setProducts(products);

        // 6. Adicionar pagamento
        MoloniInvoiceReceiptDTO.Payment payment = new MoloniInvoiceReceiptDTO.Payment();
        payment.setPaymentMethodId(getPaymentMethodIdByName(shopifyPayload.getPaymentGatewayNames().get(0))); // Método de pagamento
        payment.setValue(Double.parseDouble(shopifyPayload.getCurrentTotalPrice()));
        payment.setDate(formattedDate);

        invoice.setPayments(List.of(payment));

        // 7. Configurar tipo de documento como fatura-recibo
        invoice.setDocumentTypeId(27); // Código SAFT para Fatura-Recibo

        // 8. Configurar status como rascunho
        invoice.setStatus(0); // 0 para rascunho, conforme documentação

        return invoice;
    }

}
