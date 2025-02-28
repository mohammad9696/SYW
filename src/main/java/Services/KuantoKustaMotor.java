package Services;

import Constants.ConstantsEnum;
import Constants.HttpRequestAuthTypeEnum;
import Constants.KuantoKustaOrdersEnum;
import DTO.*;
import Utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import main.UpdateFeeds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Services.MoloniService.createPurchaseOrder;

public class KuantoKustaMotor {

    private static final Logger logger = LoggerFactory.getLogger(KuantoKustaMotor.class);

    public static void kuantokustaToShopifySync(){
        logger.info("Preparing to lists order to sync from kuantokusta");
        List<KuantoKustaOrderDTO> ordersToApprove = retrieveOrders();
        for (KuantoKustaOrderDTO kuantoKustaOrderDTO : ordersToApprove){

            Scanner scanner = new Scanner(System.in);
            System.out.println("Order to accept: " + kuantoKustaOrderDTO.getKuantoKustaOrderId() );
            for (KuantoKustaProductDTO product : kuantoKustaOrderDTO.getProducts()){
                System.out.println(product.getOfferSku() + "  " + product.getName() + "  quantidade: " + product.getQuantity() + " preço: " + product.getPrice());
            }
            System.out.println("Portes: " + kuantoKustaOrderDTO.getShippingPrice());
            System.out.println("1: Accept  2: Don't accept (accept later)");
            int option = scanner.nextInt();

            if (option!=1){
                logger.info("Preparing to sync order {} from kuantokusta", kuantoKustaOrderDTO.getKuantoKustaOrderId());
                continue;
            }

            OrderDTO order = null;
            try {

                Boolean valid  = createMoloniOrderDtoFromKuantoKustaOrderDto(kuantoKustaOrderDTO).checkPdfLinkRequestValid();
                if (valid){
                    approveOrderKuantoKusta(kuantoKustaOrderDTO.getKuantoKustaOrderId());
                }
               /*
                order = createShopifyOrderDtoFromKuantoKustaOrderDto(kuantoKustaOrderDTO);
                order = CreateOrderService.createDraftOrderShopify(order);
                if (order == null){
                    throw new Exception("Order not created");
                }
                updateOrderShopify(order.getId(), ConstantsEnum.KUANTOKUSTA_MESSAGE_SHOPIFY.getConstantValue() + kuantoKustaOrderDTO.getKuantoKustaOrderId());
                if (order.getLineItems().get(0).getPrice() != kuantoKustaOrderDTO.getProducts().get(0).getPrice()){
                    System.out.println("!!!! Order created with price " +order.getLineItems().get(0).getPrice()  + " it should be" + kuantoKustaOrderDTO.getProducts().get(0).getPrice());
                    System.out.println("!!!! Order created with price " +order.getLineItems().get(0).getPrice()  + " it should be" + kuantoKustaOrderDTO.getProducts().get(0).getPrice());
                    System.out.println("!!!! Order created with price " +order.getLineItems().get(0).getPrice()  + " it should be" + kuantoKustaOrderDTO.getProducts().get(0).getPrice());
                }
                */
                //updateOrderShopify("4628576895233", ConstantsEnum.KUANTOKUSTA_MESSAGE_SHOPIFY.getConstantValue() + "9529-511334-4247");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static List<KuantoKustaOrderDTO> retrieveOrders (){

        TypeReference<List<KuantoKustaOrderDTO>> typeReference = new TypeReference<List<KuantoKustaOrderDTO>>() {};
        List<KuantoKustaOrderDTO> orders = HttpRequestExecutor.getObjectRequest(typeReference , KuantoKustaOrdersEnum.GET_ORDERS_WAITING_APPROVAL.getOrderUrl(), HttpRequestAuthTypeEnum.XXX_API_KEY, ConstantsEnum.KUANTOKUSTA_API_KEY.getConstantValue().toString(), new HashMap<>());

        return orders;
    }

    public static void approveOrderKuantoKusta(String kuantokustaOrderId){
        System.out.println("kuantokusta order id " + kuantokustaOrderId );
        HttpRequestExecutor.patchObjectRequest(KuantoKustaOrdersEnum.getUrlApproveOrder(kuantokustaOrderId), HttpRequestAuthTypeEnum.XXX_API_KEY, ConstantsEnum.KUANTOKUSTA_API_KEY.getConstantValue().toString());
    }

    public static void updateOrderShopify(String orderId, String note){
        String requestUrl = ConstantsEnum.UPDATE_ORDER_REQUEST_SHOPIFY_PREFIX.getConstantValue() + orderId +
                ConstantsEnum.UPDATE_ORDER_REQUEST_SHOPIFY_SUFIX.getConstantValue();
        UpdateOrderObjectDTO updateOrderObjectDTO = new UpdateOrderObjectDTO(orderId, note);
        OrderDTO orderDTO = HttpRequestExecutor.updateRequest(OrderDTO.class, updateOrderObjectDTO, requestUrl);
    }

    public static MoloniDocumentDTO createMoloniOrderDtoFromKuantoKustaOrderDto (KuantoKustaOrderDTO kuantoKustaOrderDTO){
        MoloniDocumentDTO dto = new MoloniDocumentDTO();
        //ZonedDateTime zonedDateTime = ZonedDateTime.parse(kuantoKustaOrderDTO.getCreatedAt());
        //LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        LocalDateTime localDateTime = LocalDateTime.now();
        dto.setCompanyId(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        dto.setInternalOrderNumber("KK-"+kuantoKustaOrderDTO.getKuantoKustaOrderId());
        dto.setDate(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setExpirationDate(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto.setDocumentSetId(MoloniService.getDocumentSetIdByName("KK"));
        dto.setDeliveryDestinationAddress(kuantoKustaOrderDTO.getShippingAddress().getAddress1()+ " " +kuantoKustaOrderDTO.getShippingAddress().getAddress2());
        dto.setDeliveryDestinationCity(kuantoKustaOrderDTO.getShippingAddress().getCity());
        dto.setDeliveryDestinationZipCode(kuantoKustaOrderDTO.getShippingAddress().getPostalCode());
        dto.setDeliveryDestinationCountryId(MoloniService.getCountryIdByName(kuantoKustaOrderDTO.getShippingAddress().getCountry()));
        dto.setStatus(1);
        MoloniService moloniService = new MoloniService();
        List<MoloniProductDTO> moloniProductDTOS = new ArrayList<>();
        int docLineOrder = 1;

        for (KuantoKustaProductDTO i: kuantoKustaOrderDTO.getProducts()){
            MoloniProductDTO productDTO = moloniService.getProduct(i.getOfferSku());
            MoloniProductDTO lineProduct = kkProductToMoloni(i, productDTO, docLineOrder);
            moloniProductDTOS.add(lineProduct);
            docLineOrder++;
        }

        Double shippingPrice = kuantoKustaOrderDTO.getShippingPrice() == null? 0.0 : kuantoKustaOrderDTO.getShippingPrice();
        moloniProductDTOS.add(getShippingProduct("KuantoKusta Portes", shippingPrice, docLineOrder));
        dto.setProductDTOS(moloniProductDTOS.toArray(new MoloniProductDTO[0]));

        MoloniEntityClientDTO kkData = MoloniService.getClientObject(kuantoKustaOrderDTO.getBillingAddress().getNipc(),kuantoKustaOrderDTO.getBillingAddress().getName(),
                kuantoKustaOrderDTO.getBillingAddress().getAddress1() +" "+ kuantoKustaOrderDTO.getBillingAddress().getAddress2(),
                kuantoKustaOrderDTO.getBillingAddress().getPostalCode(), kuantoKustaOrderDTO.getBillingAddress().getCity(), "PT",
                kuantoKustaOrderDTO.getEmail(),kuantoKustaOrderDTO.getBillingAddress().getPhone());


        MoloniEntityClientDTO client = MoloniService.getOrCreateClient(kkData);
        dto.setCustomerId(client.getCustomerId());

        FulfillmentService.shipPurchaseOrder(dto);
        return createPurchaseOrder(dto);

    }

    private static MoloniProductDTO getShippingProduct (String lineName, Double linePrice, int lineOrder){
        MoloniProductDTO lineProduct = new MoloniProductDTO();
        lineProduct.setProductId(Long.parseLong(ConstantsEnum.MOLONI_SHIPPING_PRODUCT_ID.getConstantValue().toString()));
        lineProduct.setProductName(lineName);
        lineProduct.setLineQuantity(1);
        lineProduct.setDiscount(0.0);
        lineProduct.setPriceWithoutVat(linePrice/Double.parseDouble(ConstantsEnum.VAT_PT.getConstantValue().toString()));
        lineProduct.setLineOrder(lineOrder);

        List<MoloniProductTaxesDTO> taxes = new ArrayList<>();
        MoloniProductTaxesDTO tax = new MoloniProductTaxesDTO();
        tax.setTaxId(Long.parseLong(ConstantsEnum.MOLONI_TAX_ID_VATNORMAL.getConstantValue().toString()));
        taxes.add(tax);
        lineProduct.setTaxes(taxes);
        return lineProduct;
    }

    private static MoloniProductDTO kkProductToMoloni (KuantoKustaProductDTO i, MoloniProductDTO productDTO, int order ){
        MoloniProductDTO lineProduct = new MoloniProductDTO();
        lineProduct.setProductId(productDTO.getProductId());
        lineProduct.setProductName(i.getName());
        lineProduct.setLineQuantity(i.getQuantity());
        lineProduct.setDiscount(0.0);
        lineProduct.setPriceWithoutVat(i.getPrice()/(1+productDTO.getTaxes().get(0).taxPercentageValue()));
        lineProduct.setLineOrder(order);
        lineProduct.setTaxes(productDTO.getTaxes());
        return lineProduct;
    }
    public static OrderDTO createShopifyOrderDtoFromKuantoKustaOrderDto(KuantoKustaOrderDTO kuantoKustaOrderDTO) throws GeneralSecurityException, IOException {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPhone(kuantoKustaOrderDTO.getShippingAddress().getPhone());
        orderDTO.setBillingAdress(getOrderAddressDtoFromKuantoKustaOrderAddressDto(kuantoKustaOrderDTO.getBillingAddress()));
        orderDTO.setShippingAddress(getOrderAddressDtoFromKuantoKustaOrderAddressDto(kuantoKustaOrderDTO.getShippingAddress()));
        Map<String, MacroProductDTO> productListDTO = UpdateFeeds.getUpdatedProductList();

        List<OrderLineDTO> lineItems = getOrderLinesFromKuantoKustaOrdersDto(kuantoKustaOrderDTO.getProducts(), productListDTO);
        String shippingPrice = null;
        if (kuantoKustaOrderDTO.getShippingPrice() == null){
            shippingPrice = "0";
        } else {
            shippingPrice = String.valueOf(kuantoKustaOrderDTO.getShippingPrice());
        }
        orderDTO.setShippingDTO (new OrderShippingDTO("Portes kuantokusta", shippingPrice ,true));
        orderDTO.setLineItems(lineItems);

        return orderDTO;

    }

    public static List<OrderLineDTO> getOrderLinesFromKuantoKustaOrdersDto (List<KuantoKustaProductDTO> kuantokustaProducts, Map<String, MacroProductDTO> productListDTO){
        List<OrderLineDTO> listOrderLines = new ArrayList<>();

        for (KuantoKustaProductDTO kkProduct : kuantokustaProducts){
            for (Map.Entry<String, MacroProductDTO> product: productListDTO.entrySet()) {
                if (kkProduct.getOfferSku().equals(product.getValue().getVariantId())){
                    OrderLineDTO orderLineDTO = new OrderLineDTO();
                    orderLineDTO.setVariantId(product.getValue().getVariantId());
                    orderLineDTO.setQuantity(kkProduct.getQuantity());
                    listOrderLines.add(orderLineDTO);
                }
            }
        }

        return  listOrderLines;
    }

    public static OrderAddressDTO getOrderAddressDtoFromKuantoKustaOrderAddressDto (KuantoKustaOrderAddressDTO kuantoKustaOrderAddressDTO){
        OrderAddressDTO orderAddressDTO = new OrderAddressDTO();
        String[] name = Utils.getFirstLastName(kuantoKustaOrderAddressDTO.getName());
        orderAddressDTO.setFirstName(name[0]);
        orderAddressDTO.setLastName(name[1]);
        orderAddressDTO.setAddress1(kuantoKustaOrderAddressDTO.getAddress1());
        orderAddressDTO.setAddress2(kuantoKustaOrderAddressDTO.getAddress2());
        orderAddressDTO.setCity(kuantoKustaOrderAddressDTO.getCity());
        orderAddressDTO.setPostalCode(kuantoKustaOrderAddressDTO.getPostalCode());
        orderAddressDTO.setProvince(kuantoKustaOrderAddressDTO.getCity());
        orderAddressDTO.setNipc(kuantoKustaOrderAddressDTO.getNipc());
        orderAddressDTO.setPhone(kuantoKustaOrderAddressDTO.getPhone());
        orderAddressDTO.setCountry(kuantoKustaOrderAddressDTO.getCountry());


        return orderAddressDTO;
    }

    public static void main(String[] args) {
        KuantoKustaMotor.kuantokustaToShopifySync();
    }
}
