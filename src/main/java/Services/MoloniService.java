package Services;

import Constants.ConstantsEnum;
import DTO.MoloniProductDTO;
import DTO.MoloniProductTaxesDTO;
import DTO.MoloniTokenDTO;
import DTO.ProductDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.util.ArrayMap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MoloniService {

    private static String getToken(){
        TypeReference<MoloniTokenDTO> typeReference = new TypeReference<MoloniTokenDTO>(){};
        MoloniTokenDTO token = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.MOLONI_GET_TOKEN.getConstantValue().toString(), new ArrayMap<>());
        return token.getAccessToken();
    }

    public static boolean existsProductMoloni(String sku){
        MoloniProductDTO product = getProduct(sku);
        if (product == null){
            return false;
        } else {
            return true;
        }
    }
    private static MoloniProductDTO getProduct(String sku){
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

    public static boolean createMoloniProduct(ProductDTO productDTO){
        if(existsProductMoloni(productDTO.sku())){
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
                System.out.println("Not syncing because already up to date: " + productDTO.sku()+ " " + productDTO.getTitle());
            }

            if (isNeededToSync){
                moloniProductDTO = new MoloniProductDTO(moloniProductDTO.getCompanyId(), moloniProductDTO.getProductId(),
                        productDTO.getVariants().get(0).getBarcode(), productDTO.getTitle(), productDTO.sku(),
                        shopifyProductPrice);
                updateProduct(moloniProductDTO);
                System.out.println("Synced successfully: " + productDTO.sku()+ " " + productDTO.getTitle());
            }
        } else {
            System.out.println("Not syncing because already up to date: " + productDTO.sku()+ " " + productDTO.getTitle());
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
            System.out.println("Syncing: " + productDTO.sku()+ " " + productDTO.getTitle());
            syncMoloniProduct(productDTO);
        }
    }

}
