package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import com.google.api.client.util.ArrayMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class StockKeepingUnitsService {

    private static final Logger logger = LoggerFactory.getLogger(StockKeepingUnitsService.class);

    public static void main(String[] args) {

        logger.info("Starting stock keeping service");
        logger.info("How would you like to check?");
        logger.info("1. Check all needs     2. Check Needs for SKU      3. Product name contains");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch (option){
            case 1:
                logger.info("Checking all product needs");
                StockKeepingUnitsService.purchasingNeeds(null, null);
                main(null);
            case 2:
                logger.info("Checking specific product needs. Please insert sku");
                String sku  = scanner.next();
                StockKeepingUnitsService.purchasingNeeds(sku, null);
                main(null);
            case 3:
                logger.info("What string should the products contain?");
                String str  = scanner.next();
                logger.info("Checking all products containing {} ", str);
                StockKeepingUnitsService.purchasingNeeds(null, str);
                main(null);
        }
    }
    public static String displayServiceHeader(){
        return Utils.normalizeStringLenght(15, "sku") + " " + Utils.normalizeStringLenght(50, "product name") + " " +
                Utils.normalizeStringLenght(5, "stock") + " " + Utils.normalizeStringLenght(9,"stockDays") + " " +
                Utils.normalizeStringLenght(11, "avgSalesDay") + " " +
                Utils.normalizeStringLenght(11, "7daySalesT1") + " " +Utils.normalizeStringLenght(7, "Returns") + " " +Utils.normalizeStringLenght(6, "Buyers") + " " +
                Utils.normalizeStringLenght(12, "30daySalesT2") + " " +Utils.normalizeStringLenght(7, "Returns") + " " +Utils.normalizeStringLenght(6, "Buyers") + " " +
                Utils.normalizeStringLenght(12, "90daySalesT2") + " " +Utils.normalizeStringLenght(7, "Returns") + " " +Utils.normalizeStringLenght(6, "Buyers");
    }

    public static String displayServiceLine(StockDetailsDTO s){
        return Utils.normalizeStringLenght(15, s.getSku()) + " " + Utils.normalizeStringLenght(50, s.getProductName()) + " " +
                Utils.normalizeStringLenght(5, s.getMoloniStock().toString()) + " " + Utils.normalizeStringLenght(9, s.getStockDays().toString()) + " " +
                Utils.normalizeStringLenght(11,s.getAvgSalesDays().toString()) + " " +
                Utils.normalizeStringLenght(11, s.getSevenDaysOrFirstPeriod().getUnitsSold().toString()) + " " +Utils.normalizeStringLenght(7, s.getSevenDaysOrFirstPeriod().getUnitsReturned().toString()) + " " +Utils.normalizeStringLenght(6, s.getSevenDaysOrFirstPeriod().getCustomers().toString()) + " " +
                Utils.normalizeStringLenght(12, s.getThirtyDaysOrSecondPeriod().getUnitsSold().toString()) + " " +Utils.normalizeStringLenght(7, s.getThirtyDaysOrSecondPeriod().getUnitsReturned().toString())+ " " +Utils.normalizeStringLenght(6, s.getThirtyDaysOrSecondPeriod().getCustomers().toString()) + " " +
                Utils.normalizeStringLenght(12, s.getNinetyDaysOrThirdPeriod().getUnitsSold().toString()) + " " +Utils.normalizeStringLenght(7, s.getNinetyDaysOrThirdPeriod().getUnitsReturned().toString())+ " " +Utils.normalizeStringLenght(6, s.getNinetyDaysOrThirdPeriod().getCustomers().toString());
    }

    public static void purchasingNeeds(String sku, String productNameContains) {
        logger.debug("Purchasing needs initiating for sku '{}' and productNameContains '{}' ",sku, productNameContains);
        Map<String, StockDetailsDTO> stockReservations = ShopifyOrderService.getStockDetails();
        List<StockDetailsDTO> detailsDTOS = StockKeepingUnitsService.calculatePurchasingNeeds(sku, productNameContains, stockReservations);

        int i=10;
        for (StockDetailsDTO s : detailsDTOS){

            try {
                if (i%10 ==0){
                    logger.info(displayServiceHeader());
                }
                i++;
                logger.info(displayServiceLine(s));
            } catch (NullPointerException e){
                logger.error("Could not print line for {} {}", s.getSku(), s.toString());
            }
        }
    }

    public static void updateOnlineStocks(){
        logger.info("Starting to update all online stocks");
        List<ProductDTO> productList = ShopifyProductService.getShopifyProductList();
        Map<String, StockDetailsDTO> stockDetails = ShopifyOrderService.getStockDetails();
        for (ProductDTO product : productList){
            StockDetailsDTO stockDetailsDTO = new StockDetailsDTO(product.sku());
            if(stockDetails.containsKey(product.sku())) {
                stockDetailsDTO = stockDetails.get(product.sku());
            }
            int moloniStock = MoloniService.getStock(product.sku());
            int shopifyStock = product.getVariants().get(0).getInventoryQuantity();
            stockDetailsDTO.setMoloniStock(moloniStock);
            stockDetailsDTO.setShopifyStock(shopifyStock);
            stockDetails.put(product.sku(), stockDetailsDTO);

        }

        for (String key : stockDetails.keySet()){
            StockDetailsDTO stockDetailsDTO = stockDetails.get(key);
            ProductDTO product = null;
            for (ProductDTO p : productList ){
                if (p.sku().equals(key)){
                    product = p;
                    break;
                }
            }
            if (product == null) continue;

            int paidReservations = stockDetailsDTO.getShopifyPaidReservations() != null ? stockDetailsDTO.getShopifyPaidReservations() : 0;
            if (stockDetailsDTO.getMoloniStock()- paidReservations  == stockDetailsDTO.getShopifyStock()){
                logger.info("Stock for {} in moloni is {} and in shopify is {} and paid reservations {} not needed to sync",product.sku(), stockDetailsDTO.getMoloniStock(), stockDetailsDTO.getShopifyStock(), paidReservations);
            } else {
                logger.warn("Stock for {} in moloni was {} and in shopify was {}, total paid reservatios were {}, syncing",product.sku(), stockDetailsDTO.getMoloniStock(), stockDetailsDTO.getShopifyStock(), paidReservations);
                int stockToSetInShopify = stockDetailsDTO.getMoloniStock()- paidReservations;
                ShopifyProductService.setInvetory(stockToSetInShopify, product);
            }
        }
    }

    public static Double getCostPrice (String date, String sku) {
        LocalDateTime dateTime;
        if (date!= null){
            dateTime = Utils.StringMoloniDateTime(date);
            dateTime = dateTime.minusDays(7);
        } else {
            dateTime = LocalDateTime.now();
        }

        MoloniProductStocksDTO[] stockMovements = MoloniService.getStockMovements(sku, dateTime, null);

        if (stockMovements.length == 0){
            Double price = 0.0;
            MoloniProductDTO mp = MoloniService.getProduct(sku);
            if (mp.getChildProducts().length!=0){

                for (MoloniChildProductDTO p : mp.getChildProducts()){
                    price = price + getCostPrice(null, p.getProductDTO().getSku()) * p.getQuantity();
                }
            }
            return price;
        }
        MoloniService moloniService = new MoloniService();
        for (MoloniProductStocksDTO mps : stockMovements) {
            mps.setMovementDate(Utils.StringMoloniDateTime(mps.getMovementDateString()));
        }
        for (MoloniProductStocksDTO mps : stockMovements) {
            if (mps.getQuantityMoved() > 0 && mps.getDocumentDTO() != null && mps.getDocumentId() != 0
                    && moloniService.isSupplierDocumentTypeId(mps.getDocumentDTO().getDocumentTypeId())) {
                if (mps.getQuantityAfterMovement()>= 0){
                    logger.info("Looking for stock purchase before this document");
                    if (mps.getMovementDate().isBefore(dateTime)) {
                        logger.info("Found stock movement for {} that is before {} in document {}{}", sku, date, mps.getDocumentDTO().getDocumentSetName(),mps.getDocumentDTO().getDocumentNumber());
                        MoloniDocumentDTO documentDTO = MoloniService.getMoloniDocumentDTObyId(mps.getDocumentId().toString());
                        for (MoloniProductDTO dto : documentDTO.getProductDTOS()){
                            if (dto.getSku().equals(sku)){
                                if (dto.getDiscount() == null) dto.setDiscount(0.0);
                                return dto.getPriceWithoutVat()*((1-dto.getDiscount()/100));
                            }
                        }
                    }
                } else {
                    logger.info("Looking for stock purchase after this document");
                    if (mps.getMovementDate().isBefore(dateTime)) {
                        logger.info("Found stock movement for {} that is before {} in document {}{}", sku, date, mps.getDocumentDTO().getDocumentSetName(),mps.getDocumentDTO().getDocumentNumber());
                        MoloniDocumentDTO documentDTO = MoloniService.getMoloniDocumentDTObyId(mps.getDocumentId().toString());
                        for (MoloniProductDTO dto : documentDTO.getProductDTOS()){
                            if (dto.getSku().equals(sku)){
                                if (dto.getDiscount() == null) dto.setDiscount(0.0);
                                return dto.getPriceWithoutVat()*((1-dto.getDiscount()/100));
                            }
                        }
                    }
                }

            }
        }

        return 0.0;
    }
    public static StockDetailsDTO getPurchasingNeeds(String sku, StockDetailsDTO reservations, MoloniService moloniService, Map<String, StockDetailsDTO> mustDoThese){

        //
        logger.debug("Getting purchasing needs for sku '{}'  ",sku);
        MoloniProductStocksDTO[] stockMovements = MoloniService.getStockMovements(sku, null, null);
        logger.debug("Got {} stock movements for sku {}", stockMovements.length, sku);


        if (stockMovements.length == 0) {
            MoloniProductDTO productDTO = MoloniService.getProduct(sku);
            if (productDTO != null && productDTO.getChildProducts().length != 0) {
                for (MoloniChildProductDTO childProductDTO : productDTO.getChildProducts()) {
                    StockDetailsDTO updating = new StockDetailsDTO(childProductDTO.getProductDTO().getSku());
                    updating.setShopifyUnpaidReservations(0);
                    updating.setShopifyPaidReservations(0);
                    if (mustDoThese.containsKey(childProductDTO.getProductDTO().getSku())) {
                        updating = mustDoThese.get(childProductDTO.getProductDTO().getSku());
                    }
                    Integer paidReservations = reservations.getShopifyPaidReservations() != null ? reservations.getShopifyPaidReservations() : 0;
                    Integer unpaidReservations = reservations.getShopifyUnpaidReservations() != null ? reservations.getShopifyUnpaidReservations() : 0;
                    updating.setShopifyPaidReservations(updating.getShopifyPaidReservations() + paidReservations * childProductDTO.getQuantity());
                    updating.setShopifyUnpaidReservations(updating.getShopifyUnpaidReservations() + unpaidReservations * childProductDTO.getQuantity());
                    mustDoThese.put(childProductDTO.getProductDTO().getSku(), updating);
                }
            }
        }

        for (MoloniProductStocksDTO mps :stockMovements){
            mps.setMovementDate(Utils.StringMoloniDateTime(mps.getMovementDateString()));
        }

        StockDetailsDTO stockDetails;
        if (reservations == null){
            stockDetails = new StockDetailsDTO(sku);
        } else {
            stockDetails = reservations;
        }


        if (stockMovements.length > 0) {
            stockDetails.setProductName(stockMovements[0].getMoloniProductDTO().getProductName());

            int firstPeriodDays ;
            int secondPeriodDays;
            int thirdPeriodDays;
            Long days = Duration.between(stockMovements[stockMovements.length-1].getMovementDate(), LocalDateTime.now()).toDays();
            if (stockMovements.length >= 50 && days< 90){
                LocalDateTime dateAfter90Days = LocalDateTime.now().minusDays(90);
                stockMovements = MoloniService.getStockMovements(sku, null, dateAfter90Days);
                days = Duration.between(stockMovements[stockMovements.length-1].getMovementDate(), LocalDateTime.now()).toDays();
            }
            int daysInt = Integer.parseInt(days.toString());
            stockDetails.setProductActiveForDays(daysInt);
            if (daysInt < 90) {
                firstPeriodDays = daysInt/12;
                secondPeriodDays = daysInt/3;
                thirdPeriodDays = daysInt;
            } else {
                firstPeriodDays = 7;
                secondPeriodDays = 30;
                thirdPeriodDays =90;
            }

            Integer paidReservations = stockDetails.getShopifyPaidReservations() != null ? stockDetails.getShopifyPaidReservations() : 0;
            stockDetails.setSevenDaysOrFirstPeriod(new SalesTimePeriodDTO(sku, firstPeriodDays, stockMovements, moloniService));
            stockDetails.setThirtyDaysOrSecondPeriod(new SalesTimePeriodDTO(sku, secondPeriodDays, stockMovements, moloniService));
            stockDetails.setNinetyDaysOrThirdPeriod(new SalesTimePeriodDTO(sku, thirdPeriodDays, stockMovements, moloniService));
            stockDetails.setMoloniStock(stockMovements[0].getQuantityAfterMovement() - paidReservations);
            stockDetails.setAvgSalesDays((stockDetails.getSevenDaysOrFirstPeriod().getSalesPerDay() +stockDetails.getThirtyDaysOrSecondPeriod().getSalesPerDay() +stockDetails.getNinetyDaysOrThirdPeriod().getSalesPerDay())/ 3);
            if (stockDetails.getAvgSalesDays() == 0){
                stockDetails.setAvgSalesDays(0.001);
            }
            stockDetails.setStockDays(stockDetails.getMoloniStock()/stockDetails.getAvgSalesDays());
        } else {
            stockDetails.setProductName("");
            stockDetails.setMoloniStock(0);
            stockDetails.setAvgSalesDays(0.0);
            stockDetails.setStockDays(1000.0);

        }

        return stockDetails;

    }

    public static List<StockDetailsDTO> calculatePurchasingNeeds(String sku, String productNameContains, Map<String, StockDetailsDTO> stockReservations){
        logger.debug("Calculating Purchasing needs for sku '{}' and productNameContains '{}' ",sku, productNameContains);
        List<ProductDTO> products = ShopifyProductService.getShopifyProductList();
        List<StockDetailsDTO> purchasingNeeds = new ArrayList<>();
        Map<String, StockDetailsDTO> mustDoThese = new ArrayMap<>();

        MoloniService moloniService = new MoloniService();

        for (ProductDTO productDTO : products){
            if((sku != null && productNameContains == null && productDTO.sku().toLowerCase(Locale.ROOT).equals(sku.toLowerCase(Locale.ROOT))) ||
                    (sku == null && productNameContains != null && productDTO.getTitle().toLowerCase(Locale.ROOT).contains(productNameContains.toLowerCase(Locale.ROOT))) ||
                        (sku == null && productNameContains == null) ||
                            (sku != null && productNameContains != null)){
                if(!stockReservations.containsKey(productDTO.sku())){
                    purchasingNeeds.add(getPurchasingNeeds(productDTO.sku(), new StockDetailsDTO(productDTO.sku()), moloniService, mustDoThese));
                } else {
                    purchasingNeeds.add(getPurchasingNeeds(productDTO.sku(), stockReservations.get(productDTO.sku()), moloniService, mustDoThese));
                }
            }
        }
        for (Map.Entry<String, StockDetailsDTO> i : mustDoThese.entrySet()){
            StockDetailsDTO stockDetailsDTO = i.getValue();
            if (stockReservations.containsKey(i.getKey())){
                stockDetailsDTO.setShopifyPaidReservations(stockDetailsDTO.getShopifyPaidReservations() + stockReservations.get(i.getKey()).getShopifyPaidReservations());
                stockDetailsDTO.setShopifyUnpaidReservations(stockDetailsDTO.getShopifyUnpaidReservations() + stockReservations.get(i.getKey()).getShopifyUnpaidReservations());
            }
            purchasingNeeds.add((getPurchasingNeeds(i.getKey(),stockDetailsDTO, moloniService, mustDoThese)));
        }

        purchasingNeeds.sort(StockDetailsDTO::compareTo);
        return purchasingNeeds;
    }

}
