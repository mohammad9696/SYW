package Services;

import Constants.ConstantsEnum;
import Constants.MetafieldTypeEnum;
import Constants.ProductMetafieldEnum;
import Constants.ProductSellTypeEnum;
import DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShopifyProductService {

    private static final Logger logger = LoggerFactory.getLogger(ShopifyProductService.class);

    private static List<ProductDTO> getProductToUpdateList (ProductSellTypeEnum showProducts){
        List<ProductDTO> products = getShopifyProductList();
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
        product.setTitle(ConstantsEnum.PRODUCT_PRE_SALE_TITLE.getConstantValue().toString() + " " + productToUpdate.getTitle());

        HttpRequestExecutor.updateRequest(Object.class, new ProductObjectDTO(product), getUpdateProductRequestUrl(true, productToUpdate));

        ProductVariantDTO variant = new ProductVariantDTO();
        variant.setId(productToUpdate.getVariants().get(0).getId());
        variant.setPrice(price);
        variant.setCompareAtPrice(productToUpdate.getVariants().get(0).getPrice());

        HttpRequestExecutor.updateRequest(Object.class, new ProductVariantObjectDTO(variant), getUpdateProductVariantRequestUrl(productToUpdate));

        setInvetory(stock, productToUpdate);

        return true;
    }

    public static void setInvetory (int stock, ProductDTO productDTO){
        /* old with ID before graphql
        ProductInventoryDTO inventoryDTO = new ProductInventoryDTO();
        inventoryDTO.setLocationId((ConstantsEnum.SHOPIFY_MAIN_LOCATION_ID.getConstantValue().toString()));
        inventoryDTO.setAvailable(stock);
        inventoryDTO.setInventoryItemId(productDTO.getVariants().get(0).getInventoryItemId());
        HttpRequestExecutor.sendRequestShopify(Object.class, inventoryDTO, getUpdateProductInventoryRequestUrl(productDTO));
        */

        HttpGraphQLRequestExecutor.setAvailableInventory(stock, productDTO.getVariants().get(0).getInventoryItemId());

    }

    public static boolean removePreSaleProduct (){
        ProductDTO productToUpdate = getProductToUpdate(ProductSellTypeEnum.PRE_SALE_PRODUCT);
        ProductDTO product = new ProductDTO();
        product.setId(productToUpdate.getId());

        String body = productToUpdate.getBodyHtml().split("</p>",2)[1];
        product.setBodyHtml(body);

        String title = productToUpdate.getTitle().replace(ConstantsEnum.PRODUCT_PRE_SALE_TITLE.getConstantValue().toString() + " ", "");
        product.setTitle(title);

        HttpRequestExecutor.updateRequest(Object.class, new ProductObjectDTO(product), getUpdateProductRequestUrl(true, productToUpdate));

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
        updateProductPrice(productToUpdate, productToUpdate.getVariants().get(0).getCompareAtPrice(), null, true );
        return true;
    }


    protected static String getUpdateProductRequestUrl(boolean live, ProductDTO productDTO){
        String url;
        if (live){
            url = ConstantsEnum.SHOPIFY_UPDATE_PRODUCT_PREFIX.getConstantValue().toString();
            url = url + productDTO.getId();
            url = url +(ConstantsEnum.SHOPIFY_UPDATE_PRODUCT_SUFIX.getConstantValue().toString());
        } else {
            url = ConstantsEnum.TESTSHOPIFY_UPDATE_PRODUCT_PREFIX.getConstantValue().toString();
            url = url + productDTO.getId();
            url = url +(ConstantsEnum.TESTSHOPIFY_UPDATE_PRODUCT_SUFIX.getConstantValue().toString());
        }

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

    public static List<ProductDTO> getShopifyProductList(){

        logger.info("Getting all product details list from shopify");/*
        TypeReference<ProductListDTO> typeReference = new TypeReference<ProductListDTO>() {};
        Map<String, Object> params = new HashMap<>();
        List<ProductDTO> result = HttpRequestExecutor.getObjectRequestShopify(typeReference, ConstantsEnum.GET_REQUEST_SHOPIFY_PRODUCTS.getConstantValue().toString(), params).getProducts();
        while (!params.isEmpty()){
            logger.info("Getting shopify product list is paginated. Getting next page.");
            String newReqUrl = params.get("newReqUrl").toString();
            params.remove("newReqUrl");
            List<ProductDTO> resultNext = HttpRequestExecutor.getObjectRequestShopify(typeReference, newReqUrl, params).getProducts();
            for (ProductDTO i : result){
                if (i.sku().equals(resultNext.get(0).sku())){
                    return result;
                }
            }
            for (ProductDTO i : resultNext){
                result.add(i);
            }
        }
*/
        List<ProductDTO> result = HttpGraphQLRequestExecutor.getAllProducts();

         logger.info("Got product details for {} products", result.size());
        return result;
    }

    public static List<ProductDTO> getTestShopifyProductList(){

        TypeReference<ProductListDTO> typeReference = new TypeReference<ProductListDTO>() {};
        Map<String, Object> params = new HashMap<>();
        List<ProductDTO> result = HttpRequestExecutor.getObjectRequestShopify(typeReference, ConstantsEnum.GET_REQUEST_TESTSHOPIFY_PRODUCTS.getConstantValue().toString(), params).getProducts();
        while (!params.isEmpty()){
            String newReqUrl = params.get("newReqUrl").toString();
            params.remove("newReqUrl");
            List<ProductDTO> resultNext = HttpRequestExecutor.getObjectRequestShopify(typeReference, newReqUrl, params).getProducts();
            for (ProductDTO i : resultNext){
                result.add(i);
            }
        }
        return result;
    }

    protected ProductDTO createShopifyProduct (ProductDTO toLaunch, String metaTitle, String metaDescription){

        logger.info("Creating shopify product");
        ProductVariantDTO productVariantDTO = new ProductVariantDTO();
        productVariantDTO.setTitle(toLaunch.getVariants().get(0).getTitle());
        productVariantDTO.setPrice(toLaunch.getVariants().get(0).getPrice());
        productVariantDTO.setSku(toLaunch.getVariants().get(0).getSku());
        productVariantDTO.setPosition(toLaunch.getVariants().get(0).getPosition());
        productVariantDTO.setInventoryPolicy(toLaunch.getVariants().get(0).getInventoryPolicy());
        productVariantDTO.setCompareAtPrice(toLaunch.getVariants().get(0).getCompareAtPrice());
        productVariantDTO.setFulfillmentService(toLaunch.getVariants().get(0).getFulfillmentService());
        productVariantDTO.setInventoryManagement(toLaunch.getVariants().get(0).getInventoryManagement());
        productVariantDTO.setOption1(toLaunch.getVariants().get(0).getOption1());
        productVariantDTO.setTaxable(toLaunch.getVariants().get(0).getTaxable());
        productVariantDTO.setWeight(toLaunch.getVariants().get(0).getWeight());
        productVariantDTO.setGrams(toLaunch.getVariants().get(0).getGrams());
        productVariantDTO.setWeightUnit(toLaunch.getVariants().get(0).getWeightUnit());
        productVariantDTO.setRequiresShipping(toLaunch.getVariants().get(0).getRequiresShipping());
        productVariantDTO.setBarcode(toLaunch.getVariants().get(0).getBarcode());

        List<ProductVariantDTO> productVariantList = new ArrayList<>();
        productVariantList.add(productVariantDTO);

        List<ProductImageDTO> images = new ArrayList<>();
        for (ProductImageDTO i : toLaunch.getImages()){
            ProductImageDTO newImage = new ProductImageDTO();
            newImage.setAltText(i.getAltText());
            newImage.setPosition(i.getPosition());
            newImage.setWidth(i.getWidth());
            newImage.setHeight(i.getHeight());
            newImage.setSrc(i.getSrc());
            images.add(newImage);
        }

        ProductDTO productToLaunch = new ProductDTO();
        productToLaunch.setTitle(toLaunch.getTitle());
        productToLaunch.setBodyHtml(toLaunch.getBodyHtml());
        productToLaunch.setBrand(toLaunch.getBrand());
        productToLaunch.setProductType(toLaunch.getProductType());
        productToLaunch.setHandle(toLaunch.getHandle());
        productToLaunch.setStatus("draft");
        productToLaunch.setPublishedScope("global");
        productToLaunch.setTags(toLaunch.getTags());
        productToLaunch.setVariants(productVariantList);
        productToLaunch.setImages(images);

        logger.debug("Post request create shopify product");
        HttpRequestExecutor.sendRequestShopify(Object.class, new ProductObjectDTO(productToLaunch), ConstantsEnum.SHOPIFY_CREATE_PRODUCT.getConstantValue().toString());
        ProductDTO result = null;
        List<ProductDTO> productList = getShopifyProductList();
        for (ProductDTO i : productList){
            if (i.sku().equals(productToLaunch.sku())){
                result = i;
                break;
            }
        }

        if (result != null){
            ShopifyProductMetafieldsManager metafieldsService = new ShopifyProductMetafieldsManager();
            if (metaTitle != null){
                metafieldsService.createOrUpdateMetafield(true, result, ProductMetafieldEnum.META_TITLE, metaTitle, MetafieldTypeEnum.SINGLE_LINE_TEXT_FIELD);
            }
            if (metaDescription != null){
                metafieldsService.createOrUpdateMetafield(true, result, ProductMetafieldEnum.META_DESCRIPTION, metaDescription, MetafieldTypeEnum.MULTI_LINE_TEXT_FIELD);
            }
            int minDays = Integer.parseInt(ConstantsEnum.ETA_DEFAULT_ETA_MIN_DAYS.getConstantValue().toString());
            int maxDays = Integer.parseInt(ConstantsEnum.ETA_DEFAULT_ETA_MAX_DAYS.getConstantValue().toString());
            Scanner scanner = new Scanner(System.in);
            System.out.println("At least how many days to deliver when out of stock?");
            if (scanner.hasNextInt()){
                minDays = scanner.nextInt();
            }

            System.out.println("At max how many days to deliver when out of stock?");
            if (scanner.hasNextInt()){
                maxDays = scanner.nextInt();
            }
            metafieldsService.createOrUpdateMetafield(true, result, ProductMetafieldEnum.ETA, ProductMetafieldEnum.ETA.getDefaultMessage());
            metafieldsService.createOrUpdateMetafield(true, result, ProductMetafieldEnum.ETA2, ProductMetafieldEnum.ETA2.getDefaultMessage());
            metafieldsService.createOrUpdateMetafield(true, result, ProductMetafieldEnum.ETA_CART, ProductMetafieldEnum.ETA_CART.getDefaultMessage());
            metafieldsService.createOrUpdateMetafield(true, result, ProductMetafieldEnum.ETA_MIN, minDays);
            metafieldsService.createOrUpdateMetafield(true, result, ProductMetafieldEnum.ETA_MAX, maxDays);
            metafieldsService.updateMetafields();
        }

        return result;
    }


    public static void updateProductPrices (Scanner scanner){
        List<ProductDTO> productsFromShopify = ShopifyProductService.getShopifyProductList();
        while (true) {
            logger.info("Please insert sku to update price");
            String sku = scanner.next();
            logger.info("Updating price for sku {}", sku);
            Integer indexFound = null;
            int index = 0;
            for (ProductDTO p : productsFromShopify){
                if (p.sku().equals(sku)){
                    if (indexFound == null){
                        indexFound = index;
                    } else {
                        logger.error("SKU {} is not unique, will not update", sku);
                        indexFound = null; break;
                    }

                }
                index++;
            }
            if (indexFound != null){
                ProductDTO p = productsFromShopify.get(indexFound);
                System.out.println("Current price: " + p.getVariants().get(0).getPrice());
                System.out.println("Current compare price: " + p.getVariants().get(0).getCompareAtPrice());

                System.out.println("Please insert new price for " + sku);
                Double priceDouble = scanner.nextDouble();
                System.out.println("Please insert compare at price for " + sku);
                Double comparePriceDouble = scanner.nextDouble();
                updateProductPrice(p, priceDouble, comparePriceDouble, false);
            } else {
                logger.error("Could not find sku {}", sku);
            }
        }

    }

    public static void updateProductPrice (ProductDTO productDTO, Double price, Double comparePrice, boolean ignoreCosts){

        ProductVariantDTO variant = new ProductVariantDTO();
        variant.setId(productDTO.getVariants().get(0).getId());
        variant.setPrice(price);
        if (comparePrice == 0) comparePrice = null;
        variant.setCompareAtPrice(comparePrice);
        Scanner scanner = new Scanner(System.in);
        boolean process =true;
        double costPrice =StockKeepingUnitsService.getCostPrice(null, productDTO.sku());
        if (costPrice> price && !ignoreCosts){
            process = false;
            logger.error("Can't update price for {} because cost is {} and trying to set price {}. Insert override password to override", productDTO.sku(), costPrice, price);
            if (scanner.next().equals("OVERRIDE")){
                process = true;
            }
        }
        if (process) {
            logger.info("New price margin     {}  %  ", (1-costPrice/price)*100 );
            logger.info("Updating price for {}  cost is {} and trying to set price {}",  productDTO.sku(), costPrice, price);
            //HttpRequestExecutor.updateRequest(Object.class, new ProductVariantObjectDTO(variant), getUpdateProductVariantRequestUrl(productDTO));
            HttpGraphQLRequestExecutor.updateProductVariantPrice(productDTO.getId(),variant.getId(), variant.getPrice(), variant.getCompareAtPrice());
        } else {
            logger.warn("Password was wrong. try again later");
        }

    }
}
