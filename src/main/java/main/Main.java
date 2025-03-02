package main;

import Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        System.out.println("Welcome to Smartify Your Work");

        SpringApplication.run(Main.class, args);
        logger.info("Application was initiated.");
       // chooseProcedure(args);

    }
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            String port = System.getenv("PORT");
            if (port != null) {
                factory.setPort(Integer.parseInt(port));
            }
        };
    }
    public static void chooseProcedure (String[] args){

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
            System.out.println("9. Executa 5-6-4-1");
            System.out.println("10. Margem de Lucro por documento");
            System.out.println("11. Get cost price for SKU");
            System.out.println("12. Set price for SKU");
            System.out.println("13. Compare prices XLSX");
            System.out.println("14. Translate All etas");
            System.out.println("15. Apply bulk discount per brand");
            System.out.println("16. Apply scheduled prices");
            System.out.println("99. Restart (at any time)");

            Integer option = null;
            try {
                option = Integer.parseInt(args[0]);
            } catch (Exception e){
                option = scanner.nextInt();
            }

            if (option == 1){
                UpdateFeeds.updateFeeds();
                chooseProcedure(null);
            } else if (option == 2){
                FulfillmentService.main(null);
                chooseProcedure(null);
            } else if (option == 3){
                KuantoKustaMotor.main(null);
                chooseProcedure(null);
            } else if (option == 4){
                ShopifyProductMetafieldsManager.main(null);
                chooseProcedure(null);
            } else if (option == 5){
                MoloniService.syncAllMoloniProducts();
                chooseProcedure(null);
            } else if (option == 6){
                StockKeepingUnitsService.updateOnlineStocks();
            } else if (option == 7){
                ProductLaunchService.main(null);
                chooseProcedure(null);
            } else if (option == 8){
                StockKeepingUnitsService.stockStatus(null,null);
                chooseProcedure(null);
            } else if (option == 9){
                DailyRun.main(null);
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
                chooseProcedure(null);
            } else if (option == 13){
                XlsPriceProcessor.main(null);
                chooseProcedure(null);
            } else if (option == 14){
                HttpGraphQLRequestExecutor.main(null);
                chooseProcedure(null);
            } else if (option == 15){
                UpdateFeeds.applyBulkDiscounts(scanner);
                chooseProcedure(null);
            } else if (option == 16){
                UpdateFeeds.updateScheduledPrices();
                chooseProcedure(null);
            } else {
                System.out.println("Option not available!");
                chooseProcedure(null);
            }
        }  catch (Exception e){
            chooseProcedure(null);
        }

    }
}
