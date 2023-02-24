package Services;

import DTO.ProductDTO;
import DTO.StockDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class StockKeepingUnitsService {

    private static final Logger logger = LoggerFactory.getLogger(StockKeepingUnitsService.class);

    public static void updateOnlineStocks(){
        logger.info("Starting to update all online stocks");
        List<ProductDTO> productList = ShopifyProductService.getShopifyProductList();
        Map<String, StockDetailsDTO> stockDetails = ShopifyOrderService.getStockDetails();
        for (ProductDTO product : productList){
            StockDetailsDTO stockDetailsDTO = new StockDetailsDTO(product.sku());
            if(stockDetails.containsKey(product.sku())){
                stockDetailsDTO = stockDetails.get(product.sku());
                int moloniStock = MoloniService.getStock(product.sku());
                int shopifyStock = product.getVariants().get(0).getInventoryQuantity();
                stockDetailsDTO.setMoloniStock(moloniStock);
                stockDetailsDTO.setShopifyStock(shopifyStock);
                stockDetails.put(product.sku(), stockDetailsDTO);
            }
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
}
