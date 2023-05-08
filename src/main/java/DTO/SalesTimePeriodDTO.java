package DTO;

import Services.MoloniService;
import Utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesTimePeriodDTO {

    private static final Logger logger = LoggerFactory.getLogger(SalesTimePeriodDTO.class);

    private String sku;
    private Integer days;
    private Integer unitsSold;
    private Integer unitsReturned;
    private Integer customers;
    private Double salesPerDay;

    public Double getSalesPerDay() {
        return salesPerDay;
    }

    @Override
    public String toString() {
        return Utils.normalizeStringLenght(15,this.sku) + "Sales in last " + Utils.normalizeStringLenght(4, this.days.toString()) + " days were " + Utils.normalizeStringLenght(5, this.unitsSold.toString()) +
                " units to " + Utils.normalizeStringLenght(4, this.customers.toString()) +  " customers and " + Utils.normalizeStringLenght(5,this.unitsReturned.toString()) + " units were returned";
    }


    public SalesTimePeriodDTO(String sku, Integer days, MoloniProductStocksDTO[] stockMovements) {
        logger.info("Getting {} sales for last {} days", sku, days);
        this.sku = sku;
        this.days = days;


        if (this.unitsSold == null){
            this.unitsSold = 0;
        }
        if (this.unitsReturned == null){
            this.unitsReturned = 0;
        }

        MoloniService moloniService = new MoloniService();
        for (MoloniProductStocksDTO mps :stockMovements){
            mps.setMovementDate(Utils.StringMoloniDateTime(mps.getMovementDateString()));
        }
        LocalDateTime daysAgo = LocalDateTime.now().minusDays(days);
        List<Long> clientIds = new ArrayList<>();

        for (MoloniProductStocksDTO mps : stockMovements){
            if (mps.getQuantityMoved()< 0 && mps.getDocumentDTO() != null && mps.getDocumentId() != 0
                    && !moloniService.isSupplierDocumentTypeId(mps.getDocumentDTO().getDocumentTypeId())){
                if (mps.getMovementDate().isAfter(daysAgo)){
                    this.unitsSold = (this.unitsSold - mps.getQuantityMoved());
                    if(!clientIds.contains(mps.getDocumentId())){
                        clientIds.add(mps.getDocumentId());
                    }
                }
            } else if (mps.getQuantityMoved() > 0 && mps.getDocumentDTO() != null &&
                    !moloniService.isSupplierDocumentTypeId(mps.getDocumentDTO().getDocumentTypeId())){
                this.unitsReturned = (this.unitsReturned + mps.getQuantityMoved());
            }
        }

        this.customers = clientIds.size();
        this.salesPerDay = unitsSold  == 0 ? 0 : Double.parseDouble(this.unitsSold.toString()) / Double.parseDouble(this.days.toString());
        logger.info(this.toString());


    }

    public SalesTimePeriodDTO() {
    }

    public String getSku() {
        return sku;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getUnitsSold() {
        if (unitsSold == null){
            unitsSold = 0;
        }
        return unitsSold;
    }

    public Integer getUnitsReturned() {
        if (unitsReturned == null){
            unitsReturned = 0;
        }
        return unitsReturned;
    }

    public Integer getCustomers() {
        if (customers == null){
            customers = 0;
        }
        return customers;
    }
}
