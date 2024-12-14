package Services;

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

    public static String stockStatus(String option1, String string) {

        logger.info("Starting stock keeping service");
        logger.info("How would you like to check?");
        logger.info("1. Check all needs     2. Check Needs for SKU      3. Product name contains");
        Scanner scanner = new Scanner(System.in);

        int option;
        if (option1 != null){
            option = Integer.parseInt(option1);
        } else {
            option= scanner.nextInt();
        }

        switch (option){
            case 1:
                logger.info("Checking all product needs");
                return StockKeepingUnitsService.purchasingNeeds(null, null, getStockReservations());

            case 2:
                logger.info("Checking specific product needs. Please insert sku");
                String sku;
                if (string != null) {
                    sku = string;
                } else {
                    sku= scanner.next();
                }
                return StockKeepingUnitsService.purchasingNeeds(sku, null, getStockReservations());

            case 3:
                logger.info("What string should the products contain?");
                String str;
                if (string != null) {
                    str = string;
                } else {
                    str= scanner.next();
                }
                logger.info("Checking all products containing {} ", str);
                return StockKeepingUnitsService.purchasingNeeds(null, str, getStockReservations());
        }
        return null;
    }
    public static String displayServiceHeader(){
        return Utils.normalizeStringLenght(15, "sku") + " " + Utils.normalizeStringLenght(14, "ean") + " " +
                Utils.normalizeStringLenght(50, "product name") + " " +
                Utils.normalizeStringLenght(5, "stock") + " " + Utils.normalizeStringLenght(9,"stockDays") + " " +
                Utils.normalizeStringLenght(11, "avgSalesDay") + " " +
                Utils.normalizeStringLenght(11, "30daySalesT1") + " " +Utils.normalizeStringLenght(7, "Returns") + " " +Utils.normalizeStringLenght(6, "Buyers") + " " +
                Utils.normalizeStringLenght(12, "90daySalesT2") + " " +Utils.normalizeStringLenght(7, "Returns") + " " +Utils.normalizeStringLenght(6, "Buyers") + " " +
                Utils.normalizeStringLenght(12, "365daySalesT3") + " " +Utils.normalizeStringLenght(7, "Returns") + " " +Utils.normalizeStringLenght(6, "Buyers");
    }

    public static String displayServiceLine(StockDetailsDTO s){
        return Utils.normalizeStringLenght(15, s.getSku()) + " " + Utils.normalizeStringLenght(14, s.getEan()) + " " +
                Utils.normalizeStringLenght(50, s.getProductName()) + " " +
                Utils.normalizeStringLenght(5, s.getMoloniStock().toString()) + " " + Utils.normalizeStringLenght(9, s.getStockDays().toString()) + " " +
                Utils.normalizeStringLenght(11,s.getAvgSalesDays().toString()) + " " +
                Utils.normalizeStringLenght(11, s.getFirstPeriod().getUnitsSold().toString()) + " " +Utils.normalizeStringLenght(7, s.getFirstPeriod().getUnitsReturned().toString()) + " " +Utils.normalizeStringLenght(6, s.getFirstPeriod().getCustomers().toString()) + " " +
                Utils.normalizeStringLenght(12, s.getSecondPeriod().getUnitsSold().toString()) + " " +Utils.normalizeStringLenght(7, s.getSecondPeriod().getUnitsReturned().toString())+ " " +Utils.normalizeStringLenght(6, s.getSecondPeriod().getCustomers().toString()) + " " +
                Utils.normalizeStringLenght(12, s.getThirdPeriod().getUnitsSold().toString()) + " " +Utils.normalizeStringLenght(7, s.getThirdPeriod().getUnitsReturned().toString())+ " " +Utils.normalizeStringLenght(6, s.getThirdPeriod().getCustomers().toString());
    }

    public static Map<String, StockDetailsDTO>  getStockReservations() {
        logger.debug("Getting stock reservations");
        Map<String, StockDetailsDTO> stockReservations = ShopifyOrderService.getStockDetails();
        return stockReservations;

    }

    public static String purchasingNeeds(String sku, String productNameContains, Map<String, StockDetailsDTO> stockReservations) {
        logger.debug("Purchasing needs initiating for sku '{}' and productNameContains '{}' ",sku, productNameContains);
        List<SupplierOrderedLineDate> supplierOrderedLineDates = MoloniService.getSupplierOrderedLines();
        List<StockDetailsDTO> detailsDTOS = StockKeepingUnitsService.getSkuDetailsList(sku, productNameContains, stockReservations, supplierOrderedLineDates, ShopifyProductService.getShopifyProductList());
        StringBuilder sb = new StringBuilder();
        int i=10;
        for (StockDetailsDTO s : detailsDTOS){

            try {
                if (i%10 ==0){
                    System.out.println(displayServiceHeader());
                    sb.append(displayServiceHeader()).append(System.lineSeparator());
                }
                i++;
                System.out.println(displayServiceLine(s));
                sb.append(displayServiceLine(s)).append(System.lineSeparator());
            } catch (NullPointerException e){
                logger.error("Could not print line for {} {}", s.getSku(), s.toString());
            }
            for (SupplierOrderedLineDate ordered : s.getSupplierOrderedLineDates()){
                System.out.println(ordered.toString());
                sb.append(ordered.toString()).append(System.lineSeparator());
            }
        }
        return sb.toString();
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
            if (mp != null && mp.getChildProducts() != null && mp.getChildProducts().length!=0){

                for (MoloniChildProductDTO p : mp.getChildProducts()){
                    price = price + getCostPrice(null, p.getProductDTO().getSku()) * p.getQuantity();
                }
            }
            return price;
        }
        for (MoloniProductStocksDTO mps : stockMovements) {
            mps.setMovementDate(Utils.StringMoloniDateTime(mps.getMovementDateString()));
        }
        for (MoloniProductStocksDTO mps : stockMovements) {
            if (mps.getQuantityMoved() > 0 && mps.getDocumentDTO() != null && mps.getDocumentId() != 0
                    && MoloniService.isSupplierDocumentTypeId(mps.getDocumentDTO().getDocumentTypeId())) {
                if (mps.getQuantityAfterMovement()>= 0){
                    logger.info("Looking for stock purchase before this document");
                    if (mps.getMovementDate().isBefore(dateTime)) {
                        logger.info("Found stock movement for {} that is before {} in document {}{}", sku, date, mps.getDocumentDTO().getDocumentSetName(),mps.getDocumentDTO().getDocumentNumber());
                        MoloniDocumentDTO documentDTO = MoloniService.getMoloniDocumentDTObyId(mps.getDocumentId().toString());
                        if (documentDTO == null || documentDTO.getProductDTOS() == null){
                            continue;
                        }

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
    public static StockDetailsDTO getSkuDetails(String sku, Boolean continueToSelOutOfStock, StockDetailsDTO reservations, Map<String, StockDetailsDTO> mustDoThese, List<SupplierOrderedLineDate> supplierOrderedLineDatesBySKU){

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
                    Integer paidReservations = reservations != null && reservations.getShopifyPaidReservations() != null ? reservations.getShopifyPaidReservations() : 0;
                    Integer unpaidReservations = reservations != null && reservations.getShopifyUnpaidReservations() != null ? reservations.getShopifyUnpaidReservations() : 0;
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

        stockDetails.setSupplierOrderedLineDates(supplierOrderedLineDatesBySKU);
        stockDetails.setContinueToSellOutOfStock(continueToSelOutOfStock);
        for (MoloniProductStocksDTO mps : stockMovements) {
            if (mps.getQuantityMoved() < 0 && mps.getDocumentDTO() != null && mps.getDocumentId() != 0
                    && !MoloniService.isSupplierDocumentTypeId(mps.getDocumentDTO().getDocumentTypeId())) {
                stockDetails.setLastSale(mps.getMovementDate());
                break;
            }
        }



        if (stockMovements.length > 0) {
            stockDetails.setProductName(stockMovements[0].getMoloniProductDTO().getProductName());
            stockDetails.setEan(stockMovements[0].getMoloniProductDTO().getEan());

            int firstPeriodDays ;
            int secondPeriodDays;
            int thirdPeriodDays;
            Long days = Duration.between(stockMovements[stockMovements.length-1].getMovementDate(), LocalDateTime.now()).toDays();
            if (stockMovements.length >= 50 && days< 365){
                LocalDateTime dateAfter90Days = LocalDateTime.now().minusDays(365);
                stockMovements = MoloniService.getStockMovements(sku, null, dateAfter90Days);
                days = Duration.between(stockMovements[stockMovements.length-1].getMovementDate(), LocalDateTime.now()).toDays();
            }
            int daysInt = Integer.parseInt(days.toString());
            stockDetails.setProductActiveForDays(daysInt);
            if (daysInt < 365) {
                firstPeriodDays = daysInt/12;
                secondPeriodDays = daysInt/3;
                thirdPeriodDays = daysInt;
            } else {
                firstPeriodDays = 365;
                secondPeriodDays = 90;
                thirdPeriodDays =30;
            }

            Integer paidReservations = stockDetails.getShopifyPaidReservations() != null ? stockDetails.getShopifyPaidReservations() : 0;
            stockDetails.setFirstPeriod(new SalesTimePeriodDTO(sku, firstPeriodDays, stockMovements));
            stockDetails.setSecondPeriod(new SalesTimePeriodDTO(sku, secondPeriodDays, stockMovements));
            stockDetails.setThirdPeriod(new SalesTimePeriodDTO(sku, thirdPeriodDays, stockMovements));
            stockDetails.setMoloniStock(stockMovements[0].getQuantityAfterMovement() - paidReservations);
            stockDetails.setAvgSalesDays((stockDetails.getFirstPeriod().getSalesPerDay() +stockDetails.getSecondPeriod().getSalesPerDay() +stockDetails.getThirdPeriod().getSalesPerDay())/ 3);
            if (stockDetails.getAvgSalesDays() == 0){
                stockDetails.setAvgSalesDays(0.001);
            }
            stockDetails.setStockDays(stockDetails.getMoloniStock()/stockDetails.getAvgSalesDays());
        } else {
            MoloniProductDTO moloniProductDTO = MoloniService.getProduct(sku);
            if (moloniProductDTO != null ){
                stockDetails.setProductName(moloniProductDTO.getProductName());
                stockDetails.setEan(moloniProductDTO.getEan());
            }
            stockDetails.setMoloniStock(1000-(stockDetails.getShopifyPaidReservations()!=null?stockDetails.getShopifyPaidReservations():0));
            stockDetails.setAvgSalesDays(0.0);
            stockDetails.setStockDays((stockDetails.getShopifyPaidReservations()!=null?1000.0*stockDetails.getShopifyPaidReservations():0));

        }

        return stockDetails;

    }

    public static List<StockDetailsDTO> getSkuDetailsList(String sku, String productNameContains, Map<String, StockDetailsDTO> stockReservations, List<SupplierOrderedLineDate> supplierOrderedLineDates, List<ProductDTO> products ){
        logger.debug("Calculating Purchasing needs for sku '{}' and productNameContains '{}' ",sku, productNameContains);
        List<StockDetailsDTO> purchasingNeeds = new ArrayList<>();
        Map<String, StockDetailsDTO> mustDoThese = new ArrayMap<>();


        for (ProductDTO productDTO : products){
            if((sku != null && productNameContains == null && productDTO.sku().toLowerCase(Locale.ROOT).equals(sku.toLowerCase(Locale.ROOT))) ||
                    (sku == null && productNameContains != null && productDTO.getTitle().toLowerCase(Locale.ROOT).contains(productNameContains.toLowerCase(Locale.ROOT))) ||
                        (sku == null && productNameContains == null) ||
                            (sku != null && productNameContains != null)){
                if(!stockReservations.containsKey(productDTO.sku())){
                    purchasingNeeds.add(getSkuDetails(productDTO.sku(), productDTO.getVariants().get(0).getInventoryPolicy().equalsIgnoreCase("continue"), new StockDetailsDTO(productDTO.sku()), mustDoThese, MoloniService.getSupplierOrderedLineDatesPerSku(productDTO.sku(), supplierOrderedLineDates)));
                } else {
                    purchasingNeeds.add(getSkuDetails(productDTO.sku(), productDTO.getVariants().get(0).getInventoryPolicy().equalsIgnoreCase("continue"), stockReservations.get(productDTO.sku()), mustDoThese, MoloniService.getSupplierOrderedLineDatesPerSku(productDTO.sku(), supplierOrderedLineDates)));
                }
            }
        }
        for (Map.Entry<String, StockDetailsDTO> i : mustDoThese.entrySet()){
            StockDetailsDTO stockDetailsDTO = i.getValue();
            if (stockReservations.containsKey(i.getKey())){
                int pr = stockReservations.get(i.getKey()).getShopifyPaidReservations() != null ? stockReservations.get(i.getKey()).getShopifyPaidReservations() : 0;
                int ur = stockReservations.get(i.getKey()).getShopifyUnpaidReservations() != null ? stockReservations.get(i.getKey()).getShopifyUnpaidReservations() : 0;
                stockDetailsDTO.setShopifyPaidReservations(stockDetailsDTO.getShopifyPaidReservations() + pr);
                stockDetailsDTO.setShopifyUnpaidReservations(stockDetailsDTO.getShopifyUnpaidReservations() + ur);
            }
            for (ProductDTO j :products){
                if (j.sku().equals(i.getKey())){
                    purchasingNeeds.add((getSkuDetails(j.sku(), j.getVariants().get(0).getInventoryPolicy().equalsIgnoreCase("continue"),stockDetailsDTO, mustDoThese, MoloniService.getSupplierOrderedLineDatesPerSku(i.getKey(), supplierOrderedLineDates))));
                    break;
                }
            }
        }

        purchasingNeeds.sort(StockDetailsDTO::compareTo);
        return purchasingNeeds;
    }

}
