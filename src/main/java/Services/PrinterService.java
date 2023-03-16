package Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class PrinterService {

    private static final Logger logger = LoggerFactory.getLogger(PrinterService.class);

    public static void print (String printerName, String printUrl) {
        print (printerName, printUrl, 0);
    }
    private static void print (String printerName, String printUrl, int tries) {
        logger.info("Preparing to print from {} the file in {}", printerName, printUrl);
        logger.info("Checking Operating System: {}", System.getProperty("os.name"));

        if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac")){
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            logger.info("Number of print services: " + printServices.length);
            for (PrintService printer : printServices) {
                logger.info("Checking if the Printer: " + printer.getName() + " matches " + printerName);
                if (printer.getName().equals(printerName) && tries<2) {
                    try {
                        logger.info("Printer {} matches. Printing from {} the file in {}", printerName, printerName, printUrl);
                        InputStream is = new URL(printUrl).openStream();
                        DocAttributeSet das = new HashDocAttributeSet();
                        das.add(PrintQuality.HIGH);
                        das.add(new PrinterResolution(300, 300, ResolutionSyntax.DPI));
                        das.add(OrientationRequested.PORTRAIT);
                        //das.add(MediaSizeName.ISO_A4);
                        das.add(MediaSizeName.NA_10X15_ENVELOPE);

/*
                    PDDocument document = PDDocument.load(new URL(printUrl));
                    List<PDPage> pageList = document.getDocumentCatalog().getAllPages();
                    for (PDPage page : pageList){
                        BufferedImage image = page.convertToImage();
                        File file = new File("temp.png");
                        ImageIO.write(image,"png",file);
                        InputStream inputStream = new FileInputStream(file);
                        Doc doc = new SimpleDoc(inputStream, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                        DocPrintJob docPrintJob = printer.createPrintJob();
                        docPrintJob.print(doc, new HashPrintRequestAttributeSet());
                    }

*/
                        Doc pdfDoc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.AUTOSENSE, das);
                        DocPrintJob docPrintJob = printer.createPrintJob();
                        docPrintJob.print(pdfDoc, new HashPrintRequestAttributeSet());

                        break;
                    } catch (FileNotFoundException e) {
                        logger.error("File not found Printing from {} the file in {}. Error: {}", printerName, printUrl);
                        e.printStackTrace();

                    } catch (MalformedURLException e) {
                        logger.error("File path invalid Printing from {} the file in {}", printerName, printUrl);
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error("I/O error Printing from {} the file in {}. Will try again.", printerName, printUrl);
                        tries++;
                        print (printerName, printUrl, tries);
                    } catch (PrintException e) {
                        e.printStackTrace();
                        logger.error("PrintException Printing from {} the file in {}. Will try again.", printerName, printUrl);
                        tries++;
                        print (printerName, printUrl, tries);
                    }
                }

            }
        } else {
            logger.info("There is no printer service for {}",System.getProperty("os.name") );
            logger.info("Please print manually: {}", printUrl);
            logger.info("Please print manually: {}", printUrl);
            logger.warn("Please print manually: {}", printUrl);
        }

    }
}

