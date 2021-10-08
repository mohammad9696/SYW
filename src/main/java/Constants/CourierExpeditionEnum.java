package Constants;

import DTO.CourierExpeditionDTO;

import java.util.Scanner;

public enum CourierExpeditionEnum {
    NORMAL(ConstantsEnum.COURIER_SERVICE_CODE_NORMAL.getConstantValue().toString(), ConstantsEnum.SHOPIFY_COURIER_SERVICE_CODE_NORMAL.getConstantValue().toString()),
    EXPRESS(ConstantsEnum.COURIER_SERVICE_CODE_EXPRESS.getConstantValue().toString(), ConstantsEnum.SHOPIFY_COURIER_SERVICE_CODE_EXPRESS.getConstantValue().toString()),
    URGENT(ConstantsEnum.COURIER_SERVICE_CODE_URGENT.getConstantValue().toString(), ConstantsEnum.SHOPIFY_COURIER_SERVICE_CODE_URGENT.getConstantValue().toString()),
    ISLANDS(ConstantsEnum.COURIER_SERVICE_CODE_ISLANDS.getConstantValue().toString(), ConstantsEnum.SHOPIFY_COURIER_SERVICE_CODE_ISLANDS.getConstantValue().toString());

    private String courierServiceCode;
    private String shopifyServiceCode;

    CourierExpeditionEnum(String serviceCode, String shopifyServiceCode) {
        this.courierServiceCode = serviceCode;
        this.shopifyServiceCode = shopifyServiceCode;
    }

    public String getCourierServiceCode() {
        return courierServiceCode;
    }

    public String getShopifyServiceCode() {
        return shopifyServiceCode;
    }

    public static CourierExpeditionEnum getExpedition (String shopifyServiceCode){
        for (CourierExpeditionEnum expedition : CourierExpeditionEnum.values()){
            if (expedition.getShopifyServiceCode() == shopifyServiceCode) {
                return expedition;
            }
        }
        Scanner scanner = new Scanner((System.in));
        System.out.println("Expedition name selected:  " + shopifyServiceCode );
        System.out.println("No expedition method was found for this order");
        int i = 1;
        for (CourierExpeditionEnum expedition : CourierExpeditionEnum.values()){
            System.out.println( i + " " +expedition.getCourierServiceCode());
            i++;
        }
        System.out.println("\n");
        System.out.println("Please choose expedition:");
        int expeditionType = scanner.nextInt();
        while (expeditionType > CourierExpeditionEnum.values().length){
            System.out.println("Invalid expedition");
            expeditionType = scanner.nextInt();
        }
        return CourierExpeditionEnum.values()[expeditionType-1];
    }
}
