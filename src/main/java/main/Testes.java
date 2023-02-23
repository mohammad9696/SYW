package main;

import Services.MoloniService;
import Utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Testes {
    public static void main(String[] args) {
        String urlFatura ="https://www.moloni.pt/downloads/index.php?action=getDownload&h=fdf1b7d9f7f9ce0434405e65d388ea55&d=552436497&e=tech@smartify.pt&i=1";
        String urlEtiqueta ="https://outvio-prod.s3.eu-central-1.amazonaws.com/labels/604baa69-572c-4fa2-aade-242e4e529cfd.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA37K3UPEEW2EVU2OS%2F20230221%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Date=20230221T170725Z&X-Amz-Expires=86400&X-Amz-Signature=befc468367d0ab40e901d9eda2f1e478ca1b91e6a758f961c98a223ab65a6a25&X-Amz-SignedHeaders=host";
        //PrinterService.print("HPLASER",urlFatura );
        //PrinterService.print("BrotherLabels",urlEtiqueta );

        //MoloniService.getMoloniDocumentIdsFromShopifyOrder("#2334802");

        //System.out.println(MoloniService.getPdfLink("552436497"));
    }
}
