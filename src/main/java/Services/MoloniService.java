package Services;

import Constants.ConstantsEnum;
import DTO.MoloniProductDTO;
import DTO.MoloniTokenDTO;
import DTO.ProductDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.util.ArrayMap;
import main.UpdateFeeds;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MoloniService {

    private static String getToken(){
        TypeReference<MoloniTokenDTO> typeReference = new TypeReference<MoloniTokenDTO>(){};
        MoloniTokenDTO token = HttpRequestExecutor.getObjectRequest(typeReference, ConstantsEnum.MOLONI_GET_TOKEN.getConstantValue().toString(), new ArrayMap<>());
        return token.getAccessToken();
    }

    private static MoloniProductDTO getProduct(String sku){
        try {
            MoloniProductDTO product = new MoloniProductDTO(Long.parseLong(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString()), sku);
            MoloniProductDTO[] products = HttpRequestExecutor.sendRequest(MoloniProductDTO[].class, product, ConstantsEnum.MOLONI_PRODUCT_GET_ONE.getConstantValue().toString()+getToken());

            if(products.length != 0){
                return products[0];
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    private static void updateProduct(MoloniProductDTO productToUpdate){
        HttpRequestExecutor.sendRequest(Object.class, productToUpdate, ConstantsEnum.MOLONI_PRODUCT_UPDATE.getConstantValue().toString()+getToken());
    }

    private static void syncMoloniProduct(ProductDTO productDTO){
        MoloniProductDTO moloniProductDTO = getProduct(productDTO.getSku());
        boolean isNeededToSync = true;

        if (moloniProductDTO != null){
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            double shopifyProductPrice = Double.parseDouble(decimalFormat.format(productDTO.getVariants().get(0).getPrice()/Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString())));
            double moloniProductPrice = Double.parseDouble(decimalFormat.format(moloniProductDTO.getPriceWithoutVat()));
            if (productDTO.getTitle().equals(moloniProductDTO.getProductName()) &&
                    shopifyProductPrice == moloniProductPrice &&
                    productDTO.getVariants().get(0).getBarcode().equals(moloniProductDTO.getEan())){
                isNeededToSync = false;
                System.out.println("Not syncing because already up to date: " + productDTO.getSku()+ " " + productDTO.getTitle());
            }

            if (isNeededToSync){
                moloniProductDTO = new MoloniProductDTO(moloniProductDTO.getCompanyId(), moloniProductDTO.getProductId(),
                        productDTO.getVariants().get(0).getBarcode(), productDTO.getTitle(), productDTO.getSku(),
                        shopifyProductPrice);
                updateProduct(moloniProductDTO);
                System.out.println("Synced successfully: " + productDTO.getSku()+ " " + productDTO.getTitle());
            }
        } else {
            System.out.println("Not syncing because already up to date: " + productDTO.getSku()+ " " + productDTO.getTitle());
        }
    }

    public static void syncAllMoloniProducts() {
        List<ProductDTO> products = UpdateFeeds.getShopifyProductList();
        List<ProductDTO> productsToSync = new ArrayList<>();
        boolean isToAdd = true;
        for (ProductDTO productDTO : products) {
            for (ProductDTO productDTO2 : products) {
                if (!productDTO.getId().equals(productDTO2.getId()) &&
                        productDTO.getSku() != null &&
                        productDTO2.getSku() != null &&
                        productDTO.getSku().equals(productDTO2.getSku())) {
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
            System.out.println("Syncing: " + productDTO.getSku()+ " " + productDTO.getTitle());
            syncMoloniProduct(productDTO);
        }
    }

}
