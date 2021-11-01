package Constants;

public enum KuantoKustaOrdersEnum {
    GET_ORDERS_IN_TRANSIT(ConstantsEnum.KUANTOKUSTA_GET_ORDERS.getConstantValue().toString() + "?orderState=InTransit"),
    GET_ORDERS_APPROVED(ConstantsEnum.KUANTOKUSTA_GET_ORDERS.getConstantValue().toString() + "?orderState=Approved"),
    GET_ORDERS_WAITING_APPROVAL(ConstantsEnum.KUANTOKUSTA_GET_ORDERS.getConstantValue().toString() + "?orderState=WaitingApproval"),
    APPROVE_ORDER_PREFIX(ConstantsEnum.KUANTOKUSTA_GET_ORDERS.getConstantValue().toString() + "/"),
    APPROVE_ORDER_SUFIX("/approve");

    private String orderUrl;

    KuantoKustaOrdersEnum(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public static String getUrlApproveOrder(String orderId){
        return APPROVE_ORDER_PREFIX.getOrderUrl() + orderId + APPROVE_ORDER_SUFIX.getOrderUrl();
    }
}
