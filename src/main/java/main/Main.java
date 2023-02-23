package main;

import Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            System.out.println("1. Update products/feeds");
            System.out.println("2. Check/update orders");
            System.out.println("3. Accept Kuantokusta orders");
            System.out.println("4. Atualizar ETAs no site");
            System.out.println("5. Sincronizar dados Shopify com o Moloni");
            System.out.println("6. Launch product from testShopify");
            System.out.println("7. Fulfill Orders");
            System.out.println("9. Exit program");
            System.out.println("99. Restart (at any time)");
            int option = scanner.nextInt();
            if (option == 1){
                UpdateFeeds.main(null);
                chooseProcedure();
            } else if (option == 2){
                System.out.println("Lognow indisponível. Opção desabilitada");
                return;
                //UpdateOrders.main(null);
                //chooseProcedure();
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
                ProductLaunchService.main(null);
                chooseProcedure();
            } else if (option == 7){
                FulfillmentService.main(null);
                chooseProcedure();
            } else if (option == 9){
                System.out.println("Good bye!");
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
