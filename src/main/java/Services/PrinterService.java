package Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.standard.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PrinterService {

    private static final Logger logger = LoggerFactory.getLogger(PrinterService.class);

    public static void print (String printerName, String printUrl) {
        print (printerName, printUrl, 0);
    }
    private static void print (String printerName, String printUrl, int tries) {
        logger.info("Preparing to print from {} the file in {}", printerName, printUrl);
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        logger.info("Number of print services: " + printServices.length);
        for (PrintService printer : printServices) {
            logger.info("Checking if the Printer: " + printer.getName() + "matches " + printerName);
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

                    Doc pdfDoc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.AUTOSENSE, das);
                    DocPrintJob docPrintJob = printer.createPrintJob();

                    HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
                    pras.add(PrintQuality.HIGH);
                    pras.add(new PrinterResolution(300, 300, ResolutionSyntax.DPI));
                    pras.add(OrientationRequested.PORTRAIT);

                    docPrintJob.print(pdfDoc, pras);

                    break;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    logger.error("File not found Printing from {} the file in {}. Error: {}", printerName, printUrl);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    logger.error("File path invalid Printing from {} the file in {}", printerName, printUrl);
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
    }
}

