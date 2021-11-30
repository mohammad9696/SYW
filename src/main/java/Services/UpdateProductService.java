package Services;

import Constants.ConstantsEnum;
import Constants.ProductSellTypeEnum;
import DTO.*;
import main.UpdateFeeds;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateProductService {

    private static List<ProductDTO> getProductToUpdateList (ProductSellTypeEnum showProducts){
        List<ProductDTO> products = UpdateFeeds.getShopifyProductList();
        List<ProductDTO> productsToDisplay = new ArrayList<>();
        for(ProductDTO product : products){
            if (showProducts == ProductSellTypeEnum.PRE_SALE_PRODUCT){
                if (product.getTitle().toLowerCase().contains("PRÉ-VENDA".toLowerCase())){
                    productsToDisplay.add(product);
                }
            } else if (showProducts == ProductSellTypeEnum.AVALILABLE_NOW_PRODUCT){
                if (!product.getTitle().toLowerCase().contains("PRÉ-VENDA".toLowerCase())){
                    productsToDisplay.add(product);
                }
            } else if (showProducts == ProductSellTypeEnum.DISCOUNTED_PRODUCT){
                ProductVariantDTO variantDTO = product.getVariants().get(0);
                if (variantDTO.getCompareAtPrice() != null && variantDTO.getPrice() < variantDTO.getCompareAtPrice() &&
                        !product.getTitle().toLowerCase().contains("PRÉ-VENDA".toLowerCase())){
                    productsToDisplay.add(product);
                }
            }
        }

        return productsToDisplay;
    }

    public static ProductDTO getProductToUpdate(ProductSellTypeEnum showProducts){
        List<ProductDTO> productsToDisplay = getProductToUpdateList(showProducts);
        int i = 1;
        for(ProductDTO product : productsToDisplay){
            System.out.println(i + "  " + product.toString());
            i++;
        }
        System.out.println("Escolha o produto:");
        Scanner scanner = new Scanner(System.in);
        int productNo = scanner.nextInt();
        productNo--;
        while ((productsToDisplay.size() < productNo || productNo+1 < 0)){
            System.out.println("Produto inválido!");
            System.out.println("Escolha o produto:");
            productNo = scanner.nextInt();
        }
        return productsToDisplay.get(productNo);
    }

    public static boolean removeAllProductDiscounts(){
        return false;
    }
    public static boolean preSaleProduct(){

        ProductDTO productToUpdate = getProductToUpdate(ProductSellTypeEnum.AVALILABLE_NOW_PRODUCT);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert pre-sale price");
        double price = scanner.nextDouble();

        System.out.println("Insert pre-sale stock");
        int stock = scanner.nextInt();

        System.out.println("Insert pre-sale availability day (1-31)");
        int day = scanner.nextInt();
        System.out.println("Insert pre-sale availability month (1-12)");
        int month = scanner.nextInt();
        System.out.println("Insert pre-sale availability year (2021-2023)");
        int year = scanner.nextInt();
        String dateAvailable = day + "/" + month + "/" + year;


        ProductDTO product = new ProductDTO();
        product.setId(productToUpdate.getId());
        product.setBodyHtml("<p>" + ConstantsEnum.PRODUCT_PRE_SALE_MESSAGE.getConstantValue().toString() + " " + dateAvailable + ".\r\n</p>" +productToUpdate.getBodyHtml());
        product.setTitle(ConstantsEnum.PRODUCT_PRE_SALE_TITLE.getConstantValue().toString() + productToUpdate.getTitle());

        HttpRequestExecutor.updateRequest(Object.class, new ProductObjectDTO(product), getUpdateProductRequestUrl(productToUpdate));

        ProductVariantDTO variant = new ProductVariantDTO();
        variant.setId(productToUpdate.getVariants().get(0).getId());
        variant.setPrice(price);
        variant.setCompareAtPrice(productToUpdate.getVariants().get(0).getPrice());

        HttpRequestExecutor.updateRequest(Object.class, new ProductVariantObjectDTO(variant), getUpdateProductVariantRequestUrl(productToUpdate));

        ProductInventoryDTO inventoryDTO = new ProductInventoryDTO();
        inventoryDTO.setLocationId(Long.parseLong(ConstantsEnum.SHOPIFY_MAIN_LOCATION_ID.getConstantValue().toString()));
        inventoryDTO.setAvailable(stock);
        inventoryDTO.setInventoryItemId(productToUpdate.getVariants().get(0).getInventoryItemId());

        HttpRequestExecutor.sendRequest(Object.class, inventoryDTO, getUpdateProductInventoryRequestUrl(productToUpdate));

        return true;
    }

    public static boolean removePreSaleProduct (){
        ProductDTO productToUpdate = getProductToUpdate(ProductSellTypeEnum.PRE_SALE_PRODUCT);
        ProductDTO product = new ProductDTO();
        product.setId(productToUpdate.getId());

        String body = productToUpdate.getBodyHtml().split("</p>",2)[1];
        product.setBodyHtml(body);

        String title = productToUpdate.getTitle().replace(ConstantsEnum.PRODUCT_PRE_SALE_TITLE.getConstantValue().toString(), "");
        product.setTitle(title);

        HttpRequestExecutor.updateRequest(Object.class, new ProductObjectDTO(product), getUpdateProductRequestUrl(productToUpdate));

        ProductVariantDTO variant = new ProductVariantDTO();
        variant.setId(productToUpdate.getVariants().get(0).getId());
        variant.setPrice(productToUpdate.getVariants().get(0).getCompareAtPrice());

        HttpRequestExecutor.updateRequest(Object.class, new ProductVariantObjectDTO(variant), getUpdateProductVariantRequestUrl(productToUpdate));

        return true;
    }

    public static boolean removeAllDiscounts(){
        List<ProductDTO> productsToDisplay = getProductToUpdateList(ProductSellTypeEnum.DISCOUNTED_PRODUCT);
        int i = 1;
        for(ProductDTO product : productsToDisplay){
            System.out.println(i + "  " + product.toString());
            i++;
        }
        System.out.println("Are you sure you want to remove the discount applied in all the products above? (1- Yes    2- No)");
        Scanner scanner = new Scanner(System.in);
        int yes = scanner.nextInt();
        if (yes==1){
            for(ProductDTO product : productsToDisplay){
                removeProductDiscount(product);
            }
            System.out.println("All discounts removed");
        }
        return true;
    }

    public static boolean removeProductDiscount(ProductDTO productToUpdate){
        ProductVariantDTO variant = new ProductVariantDTO();
        variant.setId(productToUpdate.getVariants().get(0).getId());
        variant.setPrice(productToUpdate.getVariants().get(0).getCompareAtPrice());

        HttpRequestExecutor.updateRequest(Object.class, new ProductVariantObjectDTO(variant), getUpdateProductVariantRequestUrl(productToUpdate));

        return true;
    }


    private static String getUpdateProductRequestUrl(ProductDTO productDTO){
        String url = ConstantsEnum.SHOPIFY_UPDATE_PRODUCT_PREFIX.getConstantValue().toString();
        url = url + productDTO.getId();
        url = url +(ConstantsEnum.SHOPIFY_UPDATE_PRODUCT_SUFIX.getConstantValue().toString());
        return url;
    }

    private static String getUpdateProductVariantRequestUrl(ProductDTO productDTO){
        String url = ConstantsEnum.SHOPIFY_UPDATE_PRODUCT_PREFIX.getConstantValue().toString();
        url = url + productDTO.getId();
        url = url + (ConstantsEnum.SHOPIFY_UPDATE_PRODUCT_VARIANT_PRE_SUFIX.getConstantValue().toString());
        url = url + productDTO.getVariants().get(0).getId();
        url = url + (ConstantsEnum.SHOPIFY_UPDATE_PRODUCT_VARIANT_POS_PREFIX.getConstantValue().toString());
        return url;
    }

    private static String getUpdateProductInventoryRequestUrl(ProductDTO productDTO){
        return  ConstantsEnum.SHOPIFY_UPDATE_INVENTORY_PRODUCT_URL.getConstantValue().toString();
    }

    public static void main(String[] args) {
        removeProductDiscount(getProductToUpdate(ProductSellTypeEnum.DISCOUNTED_PRODUCT));
        //preSaleProduct();
        //removePreSaleProduct();
    }
}
