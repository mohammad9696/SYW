package main;


import Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@RestController
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String start() {
        StringBuilder sb = new StringBuilder();
        sb.append("Do you want to:<br>");
        sb.append("1. Update feeds<br>");
        sb.append("2. Fulfill Orders<br>");
        sb.append("3. Accept Kuantokusta orders<br>");
        sb.append("4. Atualizar ETAs no site<br>");
        sb.append("5. Sincronizar preços Shopify com o Moloni<br>");
        sb.append("6. Sincronizar stocks Moloni com o shopify<br>");
        sb.append("7. Launch product from testShopify<br>");
        sb.append("8. Painel de compras<br>");
        sb.append("9. Executa 5-6-4-1<br>");
        sb.append("10. Margem de Lucro por documento<br>");
        sb.append("11. Get cost price for SKU<br>");
        sb.append("12. Set price for SKU<br>");
        sb.append("13. Compare prices XLSX<br>");
        sb.append("14. All ETA translations with OpenAI<b4>");
        sb.append("99. Restart (at any time)<br>");
        return sb.toString();
    }

    @GetMapping("/{option}/{option2}")
    public static String chooseProcedure (@PathVariable Integer option, @PathVariable Integer option2, @RequestParam (required = false) String var1, @RequestParam (required = false) String var2){
        try {
            if (option == 4){
                ShopifyProductMetafieldsManager.main(new String[] {"4",option2.toString(), var1.toString(), var2.toString()});
                return "ETA Request completed";
            } else if (option == 8){
                return StockKeepingUnitsService.stockStatus(option2.toString(), var1).replaceAll("\n","<br>");

            }  else {
                System.out.println("Option not available!");
            }
        } catch (NumberFormatException e){
            System.out.println("Will proceed with invalid tracking number");
        } catch (Exception e){
            logger.error("Unable to process request");
        }
        return "completed";
    }

    @GetMapping("/{option}")
    public static String chooseProcedure (@PathVariable Integer option, @RequestParam (required = false) String var1, @RequestParam (required = false) String var2){

        try {
            Scanner scanner = new Scanner(System.in);


            if (option == 1){
                UpdateFeeds.updateFeeds();
            } else if (option == 2){
                FulfillmentService.main(null);
            } else if (option == 3){
                KuantoKustaMotor.main(null);
            } else if (option == 4){
                ShopifyProductMetafieldsManager.main(null);
            } else if (option == 5){
                MoloniService.syncAllMoloniProducts();
            } else if (option == 6){
                StockKeepingUnitsService.updateOnlineStocks();
            } else if (option == 7){
                ProductLaunchService.main(null);
            } else if (option == 8){
                StockKeepingUnitsService.stockStatus(null, null);
            } else if (option == 9){
                DailyRun.main(null);
            } else if (option == 10){
                MoloniService.getProfit(scanner);
            } else if (option == 11){
                logger.info("Please insert SKU (case sensitive) to check cost price:");
                String sku = var1;
                Double costPrice = StockKeepingUnitsService.getCostPrice(null, sku);
                logger.info("Cost price for {} is {}", sku, costPrice);
                return costPrice.toString();

            }  else if (option == 12){
                ShopifyProductService.updateProductPrices(scanner);
            } else if (option == 13){
                XlsPriceProcessor.main(null);
            }else if (option == 14){
                HttpGraphQLRequestExecutor.main(null);
            } else {
                System.out.println("Option not available!");
            }
        } catch (NumberFormatException e){
            System.out.println("Will proceed with invalid tracking number");
        } catch (Exception e){
            logger.error("Unable to process request");
        }
        return null;

    }


}
