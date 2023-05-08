package main;

import Services.MoloniService;
import Services.ShopifyProductMetafieldsManager;
import Services.StockKeepingUnitsService;
import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.PrintRequestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Testes {
    private static final Logger logger = LoggerFactory.getLogger(Testes.class);
    public static void main(String[] args) {

        MoloniService.syncAllMoloniProducts();
        StockKeepingUnitsService.updateOnlineStocks();
        UpdateFeeds.updateFeeds();
        ShopifyProductMetafieldsManager.updateAllProductsEta(null);
        /*
        String urlFatura ="https://www.moloni.pt/downloads/index.php?&action=getDownload&h=fa68ae988332b29e65b6cfa20b607b05&d=554580035&e=syw%40smartify.pt&i=1";
        String urlEtiqueta = "https://outvio-prod.s3.eu-central-1.amazonaws.com/labels/452ea76b-6926-4a45-b739-07a2eca19543.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA37K3UPEEW2EVU2OS%2F20230303%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Date=20230303T155527Z&X-Amz-Expires=86400&X-Amz-Signature=1d2edb94edea66ce1f31ece6c0e29920f2ebee3877e2540aa6677628cd429aea&X-Amz-SignedHeaders=host";
        String urlToUse;
        String printerName ="BrotherLabels";
        String urlImagem ="https://cdn.shopify.com/s/files/1/0514/8403/3175/products/1_d25adf10-2195-477e-946c-9fd5daf6bc32.png?v=1639848814";
        //PrinterService.print("HPLASER",urlFatura );
        //PrinterService.print("BrotherLabels",urlEtiqueta );

        CupsClient cupsClient = null;
        try {
            cupsClient = new CupsClient();
            for (CupsPrinter printer : cupsClient.getPrinters()){
                logger.info("Printer name is {} ",printer.getName());
                if (printer.getName().toLowerCase(Locale.ROOT).contains("brother")){
                    urlToUse = urlEtiqueta;
                } else {
                    urlToUse = urlFatura;
                }
                InputStream inputStream = new URL(urlToUse).openStream();
                PrintJob printJob = new PrintJob.Builder(inputStream).build();
                logger.info("Printing from printer {} the file in url {}", printer.getName(), urlToUse);
                PrintRequestResult printRequestResult = printer.print(printJob);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.getProperty("os.name");
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
        scanner.close();*/
    }
}
