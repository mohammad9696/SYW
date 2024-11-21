package Services;

import Constants.ConstantsEnum;
import Constants.MetafieldTypeEnum;
import Constants.ProductMetafieldEnum;
import Constants.ProductMetafieldPlaceholdersEnum;
import DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ShopifyProductMetafieldsManager {
    private static final Logger logger = LoggerFactory.getLogger(ShopifyProductMetafieldsManager.class);
    private List<ProductMetafieldDTO> metafieldsToUpdate = new ArrayList<>();


    public void updateMetafields(){
        HttpGraphQLRequestExecutor.setProductMetafields(metafieldsToUpdate);
        metafieldsToUpdate = new ArrayList<>();
    }
    public ProductMetafieldDTO getMetafield(boolean liveMetafield, ProductDTO productDTO, ProductMetafieldEnum productMetafieldEnum){
        logger.debug("Getting metafield {} for product {}", productMetafieldEnum.getKey(), productDTO.sku());
        if (!productDTO.getMetafields().isEmpty()){
            return productDTO.getMetafield(productMetafieldEnum);
        }
        return null;
        //método para baixo não será usado em principio
        /*
        String requestUrl = null;
        if (liveMetafield) {
            requestUrl = ConstantsEnum.GET_PRODUCT_METAFIELDS_PREFIX.getConstantValue()+productDTO.getId()+ConstantsEnum.GET_PRODUCT_METAFIELDS_SUFIX.getConstantValue()+productMetafieldEnum.getUrlWithKeyAndNameSpace();
        } else {
            requestUrl = ConstantsEnum.GET_TEST_PRODUCT_METAFIELDS_PREFIX.getConstantValue()+productDTO.getId()+ConstantsEnum.GET_TEST_PRODUCT_METAFIELDS_SUFIX.getConstantValue()+productMetafieldEnum.getUrlWithKeyAndNameSpace();
        }
        TypeReference<ProductMetafieldListDTO> metafieldListDTOTypeReference = new TypeReference<ProductMetafieldListDTO>() {};
        ProductMetafieldListDTO result = HttpRequestExecutor.getObjectRequestShopify(metafieldListDTOTypeReference, requestUrl, new HashMap<>());

        if(result!= null && result.getMetafields() != null && !result.getMetafields().isEmpty())
            return result.getMetafields().get(0);

        return null;*/
    }

    private ProductMetafieldDTO getOrSimulateMetafield(boolean liveMetafield, ProductDTO productDTO, ProductMetafieldEnum productMetafieldEnum){
        return getOrSimulateMetafield(liveMetafield, productDTO,productMetafieldEnum, 0);
    }

    private ProductMetafieldDTO getOrSimulateMetafield(boolean liveMetafield, ProductDTO productDTO, ProductMetafieldEnum productMetafieldEnum, int tries){
        ProductMetafieldDTO result = getMetafield(liveMetafield, productDTO,productMetafieldEnum);
        if (result == null){
            // deixar de criar metafields sem necessidade
            result = new ProductMetafieldDTO(productMetafieldEnum.getKey(),productMetafieldEnum.getNamespace(), productMetafieldEnum.getDefaultMessage());
        }
        /*
        if (result == null){
            logger.error("Could not getOrCreateMetafied {} for product {}", productMetafieldEnum, productDTO.sku());
            if (tries >= 3){
                logger.error("Exceeded 3 tries and Could not getOrCreateMetafied {} for product {}", productMetafieldEnum, productDTO.sku());
            } else {
                tries++;
                logger.error("Try no. {} getOrCreateMetafied {} for product {}",tries, productMetafieldEnum, productDTO.sku());
                return getOrCreateMetafield(liveMetafield, productDTO,productMetafieldEnum, tries);
            }
        } */
        return result;
    }

    protected <T> ProductMetafieldDTO createOrUpdateMetafield(boolean liveMetafield, ProductDTO productDTO, ProductMetafieldEnum productMetafieldEnum, T value){
        return createOrUpdateMetafield(liveMetafield, productDTO, productMetafieldEnum, value, null);
    }

    protected <T> ProductMetafieldDTO createOrUpdateMetafield(boolean liveMetafield, ProductDTO productDTO, ProductMetafieldEnum productMetafieldEnum, T value, MetafieldTypeEnum metafieldType){
        logger.debug("Creating or Updating metafield {} for product {}", productMetafieldEnum, productDTO.sku());
        String requestUrl = null;
        if (liveMetafield){
            requestUrl = ConstantsEnum.GET_PRODUCT_METAFIELDS_PREFIX.getConstantValue()+productDTO.getId()+ConstantsEnum.GET_PRODUCT_METAFIELDS_SUFIX.getConstantValue();
        } else {
            requestUrl = ConstantsEnum.GET_TEST_PRODUCT_METAFIELDS_PREFIX.getConstantValue()+productDTO.getId()+ConstantsEnum.GET_TEST_PRODUCT_METAFIELDS_SUFIX.getConstantValue();
        }

        ProductMetafieldDTO metafieldDTO = new ProductMetafieldDTO(productMetafieldEnum.getKey(),productMetafieldEnum.getNamespace(), productMetafieldEnum.getDefaultMessage());
        metafieldDTO.setValue(value.toString());
        metafieldDTO.setOwner_id(productDTO.getId());
        if (metafieldType != null){
            metafieldDTO.setType(metafieldType.getKey());
        }

        metafieldsToUpdate.add(metafieldDTO);
        return metafieldDTO;
        /*
        ProductMetafieldObjectDTO result = HttpRequestExecutor.sendRequestShopify(ProductMetafieldObjectDTO.class, new ProductMetafieldObjectDTO(metafieldDTO), requestUrl);

        if (result == null){
            logger.error("Error in createOrUpdateMetafield {} for product {}. Trying again...",productMetafieldEnum, productDTO.sku());
            return createOrUpdateMetafield(liveMetafield, productDTO, productMetafieldEnum, value, metafieldType);
        }

        logger.debug("createOrUpdateMetafield sucessfully: {}",result.toString());
        return result.getProductMetafieldDTO();

         */
    }

    private boolean isWorkDay (LocalDateTime date){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (isHoliday(date) || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            return false;
        return true;
    }

    private LocalDateTime nextWorkDay (LocalDateTime date){
        while (!isWorkDay(date)) {
            date = date.plusDays(1);
        }
        return date;
    }
    private LocalDateTime countWorkDays (LocalDateTime availableDate, int days){
        int hour = availableDate.getHour();

        LocalDateTime workDayDate = nextWorkDay(availableDate);

        if(hour>=Integer.parseInt(ConstantsEnum.ETA_CUTOUT_TIME.getConstantValue().toString())
                && isWorkDay(availableDate)){

            workDayDate = workDayDate.plusDays(1);
            workDayDate = nextWorkDay(workDayDate);
        }

        while (days>0){
            workDayDate = workDayDate.plusDays(1);
            workDayDate = nextWorkDay(workDayDate);
            days--;
        }

        return workDayDate.withHour(10);


    }

    private boolean isHoliday(LocalDateTime availableDate) {
        String holidayString = ConstantsEnum.HOLIDAY.getConstantValue().toString();
        String[] holidaysDilimitedString = holidayString.split(",");
        ArrayList<LocalDate> holidays = new ArrayList<>();
        for (String i : holidaysDilimitedString){
            try{
                holidays.add(LocalDate.parse(i));
            } catch(Exception e){
                continue;
            }
        }

        for (LocalDate i : holidays){
            if(i.isEqual(availableDate.toLocalDate())){
                return true;
            }
        }
        return false;

    }

    public void calculateETA (ProductDTO productDTO){
/*
        new Thread(){
            @Override
            public void run() {

                try {

                } catch (Exception e){
                    System.out.println("ETA UPDATE ERRROR : " + productDTO.getTitle());
                    calculateETA(productDTO);
                }


            }

        }.start();
*/
        String resultEtaMessage ="";
        String resultEtaCartMessage ="";
        String resultEtaCartNoStockMessage ="";
        ProductMetafieldDTO etaMessage = null;
        Map<String, String> placeholders = new HashMap<>();
        int stockAvailable = productDTO.getVariants().get(0).getInventoryQuantity();
        boolean availableToSell = stockAvailable > 0 || (productDTO.getVariants().get(0).getInventoryPolicy().equalsIgnoreCase("CONTINUE"));

        if (availableToSell) {
            LocalDateTime now = java.time.LocalDateTime.now();
            int minDaysToDeliver = 0;
            int maxDaysToDeliver = 0;
            boolean onProcessingHasStock = true;
            ProductMetafieldDTO etaCartMessage = getOrSimulateMetafield(true, productDTO, ProductMetafieldEnum.ETA_CART);

            minDaysToDeliver = 1;
            maxDaysToDeliver = minDaysToDeliver + 2;
            etaMessage = getOrSimulateMetafield(true, productDTO, ProductMetafieldEnum.ETA);
            LocalDateTime availableDate = countWorkDays(now, minDaysToDeliver);
            placeholders.put(ProductMetafieldPlaceholdersEnum.DATE_MIN.getKey(), Utils.Utils.dateFormat(availableDate));
            placeholders.put(ProductMetafieldPlaceholdersEnum.DATE_MAX.getKey(), Utils.Utils.dateFormat(countWorkDays(now, maxDaysToDeliver)));
            resultEtaCartMessage = replacePlaceholders(etaCartMessage.getValue().toString(), placeholders);
            if (stockAvailable > 0) {
                resultEtaMessage = replacePlaceholders(etaMessage.getValue(), placeholders);
            }


            etaMessage = getOrSimulateMetafield(true, productDTO, ProductMetafieldEnum.ETA2);
            LocalDateTime deliveryDate = getEtaDate(productDTO);
            if (deliveryDate != null && now.isBefore(deliveryDate)) {
                Long minDays = ChronoUnit.DAYS.between(now, deliveryDate);
                minDaysToDeliver = minDays.intValue();
                maxDaysToDeliver = minDaysToDeliver +2;
                placeholders.put(ProductMetafieldPlaceholdersEnum.DATE_MIN.getKey(), Utils.Utils.dateFormat(countWorkDays(deliveryDate,0)));
                placeholders.put(ProductMetafieldPlaceholdersEnum.DATE_MAX.getKey(), Utils.Utils.dateFormat(countWorkDays(deliveryDate,1)));
            } else {
                int minDays = Integer.parseInt(getOrSimulateMetafield(true, productDTO, ProductMetafieldEnum.ETA_MIN).getValue().toString());
                int maxDays = Integer.parseInt(getOrSimulateMetafield(true, productDTO, ProductMetafieldEnum.ETA_MAX).getValue().toString());
                minDaysToDeliver = minDays;
                maxDaysToDeliver = maxDays;
                placeholders.put(ProductMetafieldPlaceholdersEnum.DATE_MIN.getKey(), Utils.Utils.dateFormat(countWorkDays(now, minDays)));
                placeholders.put(ProductMetafieldPlaceholdersEnum.DATE_MAX.getKey(), Utils.Utils.dateFormat(countWorkDays(now, maxDays)));
            }

            resultEtaCartNoStockMessage = replacePlaceholders(etaCartMessage.getValue().toString(), placeholders);

            if (stockAvailable <= 0){
                resultEtaMessage = replacePlaceholders(etaMessage.getValue(), placeholders);
            }

            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_RESULT, resultEtaMessage);
            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_CART_RESULT, resultEtaCartMessage);
            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_CART_RESULT_NO_STOCK, resultEtaCartNoStockMessage);
            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_MIN_DAYS, minDaysToDeliver);
            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_MAX_DAYS, maxDaysToDeliver+"");
            updateMetafields();
            logger.info("ETA was updated : " + productDTO.getTitle());
        } else {
            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_RESULT, ConstantsEnum.ETA_DEFAULT_UNAVAILABLE.getConstantValue());
            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_CART_RESULT, ConstantsEnum.ETA_DEFAULT_UNAVAILABLE.getConstantValue());
            createOrUpdateMetafield(true, productDTO, ProductMetafieldEnum.ETA_CART_RESULT_NO_STOCK, ConstantsEnum.ETA_DEFAULT_UNAVAILABLE.getConstantValue());
            updateMetafields();
            logger.info("ETA was removed : " + productDTO.getTitle());
        }


    }

    private String replacePlaceholders (String stringToReplaceWithPlaceholders, Map<String, String> placeholders){
        String result = new String(stringToReplaceWithPlaceholders);
        for (Map.Entry<String, String> i : placeholders.entrySet()){
            if(stringToReplaceWithPlaceholders.contains("{"+i.getKey()+"}")){
                result=stringToReplaceWithPlaceholders.replace("{"+i.getKey()+"}", i.getValue());
                stringToReplaceWithPlaceholders = new String(result);
            }
        }
        return result;
    }

    private LocalDateTime getEtaDate(ProductDTO productDTO){
        ProductMetafieldDTO metafieldDTO = getMetafield(true, productDTO, ProductMetafieldEnum.ETA_DATE);
        if(metafieldDTO == null){
            return null;
        }
        LocalDate date = LocalDate.parse(metafieldDTO.getValue());
        return date.atStartOfDay();
    }

    public static void updateProductEta(String[] args){
        List<ProductDTO> products= ShopifyProductService.getShopifyProductList();
        int i = 0;
        int start = 0;
        int end = 0;
        for (ProductDTO productDTO : products){
            System.out.println(i + " " + productDTO.getVariants().get(0).getSku() + "  " + productDTO.getTitle() + "   Stock: " + productDTO.getVariants().get(0).getInventoryQuantity() + "  continueToSell:" + productDTO.getVariants().get(0).getInventoryPolicy());
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual o intervalo que pretende atualizar? ");
        if (args[2] !=null){
            start = Integer.parseInt(args[2]);
        } else {
            System.out.println("Desde o produto nº:");
            start = scanner.nextInt();
        }
        if (args[3] !=null){
            end = Integer.parseInt(args[3]);
        } else {
            System.out.println("Até ao produto nº:");
            end = scanner.nextInt();
        }
        if (end < start){
            end = start;
        }
        if (start > products.size()){
            start = 0;
            end = products.size()-1;
        }
        if (end> products.size()){
            end = products.size()-1;
        }

        ShopifyProductMetafieldsManager shopifyProductMetafieldsManager = new ShopifyProductMetafieldsManager();
        while(start<=end){
            ProductDTO product = products.get(start);
            logger.info(start + "  " + product.sku() +"  " +product.getTitle() + " was consulted and has id " + product.getId());
            shopifyProductMetafieldsManager.calculateETA(product);
            start++;
        }

    }

    public static void updateAllProductsEta(String[] args){
        logger.info("Updating all product ETAs");
        ShopifyProductMetafieldsManager shopifyProductMetafieldsManager = new ShopifyProductMetafieldsManager();

        List<ProductDTO> products= ShopifyProductService.getShopifyProductList();
        int i = 0;
        LocalDateTime start = LocalDateTime.now();
        for(ProductDTO product : products){

            i++;
            logger.info(i + "  " + product.getTitle() + " was consulted and has id " + product.getId());
            try {
                shopifyProductMetafieldsManager.calculateETA(product);
                LocalDateTime end = LocalDateTime.now();
                logger.info("Time taken:   " +Duration.between(start,end));
            } catch (Exception e){
                LocalDateTime end = LocalDateTime.now();
                logger.error("There was an error! Time taken:   " +Duration.between(start,end));
                continue;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("1 - Atualizar todas as ETAs (cerca de 30 minutos)");
        System.out.println("2 - Atualizar de alguns produtos");
        Scanner scanner = new Scanner(System.in);
        Integer option;
        if (args[1]==null){
            option = scanner.nextInt();
        } else {
            option = Integer.parseInt(args[1]);
        }

        if(option == 1){
            ShopifyProductMetafieldsManager.updateAllProductsEta(null);
        } else {
            ShopifyProductMetafieldsManager.updateProductEta(args);
        }

    }

}
