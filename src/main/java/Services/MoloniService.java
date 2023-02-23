package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.util.ArrayMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoloniService {

    private static final Logger logger = LoggerFactory.getLogger(MoloniService.class);

    private static String getToken(){
        logger.info("Getting token for moloni request");
        TypeReference<MoloniTokenDTO> typeReference = new TypeReference<MoloniTokenDTO>(){};
        MoloniTokenDTO token = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.MOLONI_GET_TOKEN.getConstantValue().toString(), new ArrayMap<>());
        return token.getAccessToken();
    }

    public static boolean existsProductMoloni(String sku){
        logger.info("Checkint if exists sku in moloni {}", sku);
        MoloniProductDTO product = getProduct(sku);
        if (product == null){
            return false;
        } else {
            return true;
        }
    }
    private static MoloniProductDTO getProduct(String sku){
        logger.info("Getting sku from moloni {}", sku);
        try {
            MoloniProductDTO product = new MoloniProductDTO(Long.parseLong(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString()), sku);
            MoloniProductDTO[] products = HttpRequestExecutor.sendRequest(MoloniProductDTO[].class, product, ConstantsEnum.MOLONI_PRODUCT_GET_ONE.getConstantValue().toString()+getToken());

            if(products != null && products.length != 0 && products[0].getSku().equals(sku)){
                return products[0];
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public static void printShopifyDocumentsInMoloni(String shopifyOrderNumber ){
        String[] documentIds = getMoloniDocumentIdsFromShopifyOrder(shopifyOrderNumber);
        for(int i = 0; i<documentIds.length; i++){
            String printUrl = getPdfLink(documentIds[i]);
            PrinterService.print(ConstantsEnum.SYSTEM_PRINTER_PAPER.getConstantValue().toString(), printUrl);
        }


    }
    private static String getPdfLink(String documentId){
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
            logger.error("Waiting 15 seconds for document unsuccesfull");
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
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    logger.error("Could not wait 15 seconds to retry getting document for order {}", shopifyOrderNumber);
                }
                getMoloniDocumentIdsFromShopifyOrder(shopifyOrderNumber, tries);
            } else {
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

    private static void syncMoloniProduct(ProductDTO productDTO){
        logger.info("Syncing product {} data to Moloni", productDTO.sku());
        MoloniProductDTO moloniProductDTO = getProduct(productDTO.sku());
        boolean isNeededToSync = true;

        if (moloniProductDTO != null){
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            double shopifyProductPrice = Double.parseDouble(decimalFormat.format(productDTO.getVariants().get(0).getPrice()/Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString())));
            double moloniProductPrice = Double.parseDouble(decimalFormat.format(moloniProductDTO.getPriceWithoutVat()));
            if (productDTO.getTitle().equals(moloniProductDTO.getProductName()) &&
                    shopifyProductPrice == moloniProductPrice &&
                    productDTO.getVariants().get(0).getBarcode().equals(moloniProductDTO.getEan())){
                isNeededToSync = false;
                logger.info("Not syncing because already up to date: " + productDTO.sku()+ " " + productDTO.getTitle());
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
