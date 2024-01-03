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
import java.util.*;

public class MoloniService {
    private static final Logger logger = LoggerFactory.getLogger(MoloniService.class);

    private MoloniDocumentTypeDTO[] types;

    public MoloniService() {
        try {
            logger.info("Initiating moloni service and getting document types");
            this.types = HttpRequestExecutor.sendRequest(MoloniDocumentTypeDTO[].class, new MoloniDocumentTypeDTO(), ConstantsEnum.MOLONI_DOCUMENT_GET_TYPES.getConstantValue().toString()+getToken());
            logger.debug("Initiated moloni service and got document types successfully");
        } catch (Exception e){
            logger.error("Error Initiating moloni service and getting document types. Trying again...");
            this.types = HttpRequestExecutor.sendRequest(MoloniDocumentTypeDTO[].class, new MoloniDocumentTypeDTO(), ConstantsEnum.MOLONI_DOCUMENT_GET_TYPES.getConstantValue().toString()+getToken());
        }
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
        MoloniService moloniService = new MoloniService();
        for (int i =0; i<moloniService.types.length; i++){
            logger.info(i + "    " + moloniService.types[i].getTitle());
        }
        MoloniDocumentTypeDTO type = moloniService.types[scanner.nextInt()];
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
            double profitUnit = finalDocument.getProductDTOS()[i].getPriceWithoutVat()-discount-costPrice;
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

    public boolean isSupplierDocumentTypeId(String documentTypeId){
        for (MoloniDocumentTypeDTO type : this.types){
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
        try{
            logger.info("Getting token for moloni request");
            TypeReference<MoloniTokenDTO> typeReference = new TypeReference<MoloniTokenDTO>(){};
            MoloniTokenDTO token = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.MOLONI_GET_TOKEN.getConstantValue().toString(), new ArrayMap<>());
            logger.debug("Got moloni token {}",token.getAccessToken());
            return token.getAccessToken();
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
        document.setShopifyOrderNumber(shopifyOrderNumber);
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

}
