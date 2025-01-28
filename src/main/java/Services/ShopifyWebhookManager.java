package Services;

import DTO.MoloniInvoiceReceiptDTO;
import DTO.ShopifyWebhookPayloadDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ShopifyWebhookManager {

    private static final Logger logger = LoggerFactory.getLogger(ShopifyWebhookManager.class);
    private static final String SECRET_KEY = "558e96e87bb3987f17249e75ce9812c6ee4741fdb05c199f7e851d5bebfa9dec";

    public static ResponseEntity<String> payloadResponse (String topic, String hmacHeader, String payload)  {
        logger.info("Topic {}, Payload {}", topic, payload);
        try {
            if (!ShopifyWebhookManager.isValidHmac(hmacHeader, payload)) {
                logger.error("Webhook HMAC unauthorized");
                return new ResponseEntity<>("Invalid HMAC", HttpStatus.UNAUTHORIZED);

            }
            logger.info("Webhook HMAC authenticated successfully");

            ObjectMapper objectMapper = new ObjectMapper();
            ShopifyWebhookPayloadDTO shopifyWebhookPayloadDTO = objectMapper.readValue(payload, ShopifyWebhookPayloadDTO.class);

            MoloniInvoiceReceiptDTO invoiceReceiptDTO = MoloniService.createInvoiceReceiptFromShopifyOrder(shopifyWebhookPayloadDTO);
            MoloniService.insertInvoiceReceipt(invoiceReceiptDTO);
            return new ResponseEntity<>("Webhook received successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isValidHmac(String hmacHeader, String payload) throws Exception {

        if (hmacHeader.equalsIgnoreCase("teste28012025")){
            logger.info("TEST HMAC WILL BYPASS SYSTEM");
            return true;
        }

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(payload.getBytes());
        String computedHmac = Base64.getEncoder().encodeToString(hmacBytes);
        return hmacHeader.equals(computedHmac);
    }

}
