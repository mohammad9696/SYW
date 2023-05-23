package main;

import DTO.ProductDTO;
import Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        System.out.println("Welcome to Smartify Your Work");
        logger.info("Application was initiated.");
        chooseProcedure();


    }

    public static void chooseProcedure (){

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to:");
            System.out.println("1. Update feeds");
            System.out.println("2. Fulfill Orders");
            System.out.println("3. Accept Kuantokusta orders");
            System.out.println("4. Atualizar ETAs no site");
            System.out.println("5. Sincronizar preços Shopify com o Moloni");
            System.out.println("6. Sincronizar stocks Moloni com o shopify");
            System.out.println("7. Launch product from testShopify");
            System.out.println("8. Painel de compras");
            System.out.println("9. Executa 5-6-1-4");
            System.out.println("10. Margem de Lucro por documento");
            System.out.println("11. Get cost price for SKU");
            System.out.println("12. Set price for SKU");
            System.out.println("99. Restart (at any time)");
            int option = scanner.nextInt();
            if (option == 1){
                UpdateFeeds.updateFeeds();
                chooseProcedure();
            } else if (option == 2){
                FulfillmentService.main(null);
                chooseProcedure();
            } else if (option == 3){
                KuantoKustaMotor.main(null);
                chooseProcedure();
            } else if (option == 4){
                ShopifyProductMetafieldsManager.main(null);
                chooseProcedure();
            } else if (option == 5){
                MoloniService.syncAllMoloniProducts();
                chooseProcedure();
            } else if (option == 6){
                StockKeepingUnitsService.updateOnlineStocks();
            } else if (option == 7){
                ProductLaunchService.main(null);
                chooseProcedure();
            } else if (option == 8){
                StockKeepingUnitsService.main(null);
                chooseProcedure();
            } else if (option == 9){
                Testes.main(null);
            } else if (option == 10){
                MoloniService.getProfit(scanner);
            } else if (option == 11){
                while (true) {
                    logger.info("Please insert SKU (case sensitive) to check cost price:");
                    String sku = scanner.next();
                    logger.info("Cost price for {} is {}", sku, StockKeepingUnitsService.getCostPrice(null, sku));
                    scanner.next();
                }
            } else if (option == 12){
                ShopifyProductService.updateProductPrices(scanner);
            } else {
                System.out.println("Option not available!");
                chooseProcedure();
            }
        } catch (NumberFormatException e){
            System.out.println("Will proceed with invalid tracking number");
        } catch (Exception e){
            chooseProcedure();
        }

    }
}
