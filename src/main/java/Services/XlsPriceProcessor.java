package Services;

import DTO.*;
import Utils.Utils;
import com.google.api.client.util.ArrayMap;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class XlsPriceProcessor {
    private static final Logger logger = LoggerFactory.getLogger(XlsPriceProcessor.class);

    private Map<Integer, List<String>> data = new HashMap<>();
    private List<String> competitorNames = new ArrayList<>();
    private List<ProductCompareDataDTO> productCompareData = new ArrayList<>();
    private List<ProductDTO> productDTOList = new ArrayList<>();
    public Map<Integer, List<String>> getData() {
        return this.data;
    }

    public Map<Integer, List<String>> readExcel(String fileLocation) throws IOException {

        try (FileInputStream file = new FileInputStream(fileLocation); ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(r -> {
                    int rowNum = r.getRowNum();

                    // Check if the row number is already present in the map
                    List<String> rowList = data.computeIfAbsent(rowNum, k -> new ArrayList<>());

                    for (Cell cell : r) {
                        // Check if the cell is not null before getting the raw value
                        if (cell != null) {
                            rowList.add(cell.getRawValue());
                        } else {
                            // Handle the case where the cell is null (optional)
                            rowList.add("0"); // or any other default value
                        }
                    }
                });

            }
        }

        return data;
    }

    public List<ProductCompareDataDTO> addSkuDetails (){
        MoloniService moloniService = new MoloniService();
        productDTOList = ShopifyProductService.getShopifyProductList();
        Map<String, StockDetailsDTO> reservations = StockKeepingUnitsService.getStockReservations();
        List<SupplierOrderedLineDate> supplierOrderedLineDates = MoloniService.getSupplierOrderedLines();
        ProductDTO productDTO = null;

        for (ProductCompareDataDTO dto : productCompareData){
            for (ProductDTO productDTO1 : productDTOList){
                if (productDTO1.sku().equals(dto.getSku())){
                    productDTO = productDTO1;
                    break;
                }
            }
            logger.debug("Getting SKU Details for {}", dto.getSku());
            dto.setStockDetailsDTO(StockKeepingUnitsService.getSkuDetails(productDTO, reservations.get(dto.getSku()), moloniService, new ArrayMap<>(), MoloniService.getSupplierOrderedLineDatesPerSku(dto.getSku(),supplierOrderedLineDates)));
            dto.setCostPrice(StockKeepingUnitsService.getCostPrice(null, dto.getSku()));
            dto.setMarginWithCurrent(1-(dto.getCostPrice()*1.23/dto.getSmartifyPrice()));
            dto.setMarginWithMin(1-(dto.getCostPrice()*1.23/dto.getMinPrice()));

        }
        return productCompareData;
    }
    public List<ProductCompareDataDTO> getProcessedPrices (String filePath){

        try {
            readExcel(filePath);
            int i = -1;
            XlsPorductDataPositionsDTO xlsPorductDataPositionsDTO = new XlsPorductDataPositionsDTO();
            for (String j : data.get(1)){
                i++;
                switch (j){
                    case "ean":
                        xlsPorductDataPositionsDTO.setEan(i);
                        break;
                    case "sku":
                        xlsPorductDataPositionsDTO.setSku(i);
                        break;
                    case "name":
                        xlsPorductDataPositionsDTO.setName(i);
                        break;
                    case "min":
                        xlsPorductDataPositionsDTO.setMin(i);
                        break;
                    case "max":
                        xlsPorductDataPositionsDTO.setMax(i);
                        break;
                    case "Smartify":
                        xlsPorductDataPositionsDTO.setSmartifyPrice(i);
                        break;

                    default:
                        xlsPorductDataPositionsDTO.getCompetitorPositions().add(i);
                        competitorNames.add(j);
                }
            }

            List<ProductCompareDataDTO> productCompareDataDTOS = new ArrayList<>();
            for (Map.Entry<Integer, List<String>> j : data.entrySet()){
                if (j.getKey()==1) {continue;}

                List<CompetitorCompareDataDTO> competitorCompareDataDTOS = new ArrayList<>();
                int u = 0;
                for (Integer k : xlsPorductDataPositionsDTO.getCompetitorPositions()){
                    if (Double.parseDouble(j.getValue().get(k)) != 0){
                        CompetitorCompareDataDTO competitorCompareDataDTO = new CompetitorCompareDataDTO(competitorNames.get(u),  Double.parseDouble(j.getValue().get(k)));
                        competitorCompareDataDTOS.add(competitorCompareDataDTO);
                    }
                    u++;
                }
                ProductCompareDataDTO productCompareDataDTO = new ProductCompareDataDTO(j.getValue().get(xlsPorductDataPositionsDTO.getEan()),
                        j.getValue().get(xlsPorductDataPositionsDTO.getSku()), j.getValue().get(xlsPorductDataPositionsDTO.getName()),
                        competitorCompareDataDTOS, Double.parseDouble(j.getValue().get(xlsPorductDataPositionsDTO.getSmartifyPrice())),Double.parseDouble(j.getValue().get(xlsPorductDataPositionsDTO.getMin())), Double.parseDouble(j.getValue().get(xlsPorductDataPositionsDTO.getMax())));
                productCompareDataDTOS.add(productCompareDataDTO);
            }
            productCompareData = productCompareDataDTOS;
            return productCompareDataDTOS;

        } catch (Exception e) {
            return null;
        }
    }

    public void print(){
        Collections.sort(productCompareData);
        int i = 0;
        for (ProductCompareDataDTO dto : productCompareData) {
            System.out.println(Utils.normalizeStringLenght(5, i+"") + dto.toString());
            i++;
        }
    }

    public static void main(String[] args) {
        XlsPriceProcessor xlsPriceProcessor = new XlsPriceProcessor();
        System.out.println("/Users/mohammadmudassirrafiq/Desktop/prices.xlsx");
        System.out.println("/Users/aisha/Desktop/prices.xlsx");
        logger.info("Initiating xlsPriceProcessor");
        System.out.println("Please insert file name");
        Scanner scanner = new Scanner(System.in);
        xlsPriceProcessor.getProcessedPrices(scanner.next());
        xlsPriceProcessor.addSkuDetails();
        xlsPriceProcessor.print();
        System.out.println("Which product number would you like to select?");
        xlsPriceProcessor.optionsForSelected(scanner.nextInt(), scanner);

    }

    public void setData(final Map<Integer, List<String>> data) {
        this.data = data;
    }

    public void optionsForSelected (int selected, Scanner scanner){
        System.out.println(selected + "  " +productCompareData.get(selected));
        System.out.println("Please choose one of the options:");
        System.out.println("1: Set new Price with vat");
        System.out.println("2: Set new price with margin 0-100");
        System.out.println("3: Check next");
        System.out.println("4: Check previous");
        System.out.println("5: Reorder");
        System.out.println("6: Check All");
        int option = scanner.nextInt();
        if(option == 1){

            ProductDTO p = null;
            for (ProductDTO dto : productDTOList ){
                if (dto.sku().equals(productCompareData.get(selected).getSku())){
                    p = dto;
                    break;
                }
            }

            System.out.println("Current price: " + p.getVariants().get(0).getPrice());
            System.out.println("Current compare price: " + p.getVariants().get(0).getCompareAtPrice());

            System.out.println("Please insert new price for " + p.sku());
            Double priceDouble = scanner.nextDouble();
            System.out.println("Please insert compare at price for " + p.sku());
            Double comparePriceDouble = scanner.nextDouble();
            ShopifyProductService.updateProductPrice(p, priceDouble, comparePriceDouble);

            if (productCompareData.size()-1 <= selected) selected = productCompareData.size()-2;
            optionsForSelected(selected+1, scanner);
        } else if (option == 2){
            ProductDTO p = null;
            for (ProductDTO dto : productDTOList ){
                if (dto.sku().equals(productCompareData.get(selected).getSku())){
                    p = dto;
                    break;
                }
            }

            System.out.println("Current price: " + p.getVariants().get(0).getPrice());
            System.out.println("Current compare price: " + p.getVariants().get(0).getCompareAtPrice());

            System.out.println("Please insert margin 0-100");
            Double priceDouble = (productCompareData.get(selected).getCostPrice()*1.23)/(1-scanner.nextDouble());
            System.out.println("Setting price to " + priceDouble);
            System.out.println("Please insert compare at price for " + p.sku());
            Double comparePriceDouble = scanner.nextDouble();
            ShopifyProductService.updateProductPrice(p,priceDouble , comparePriceDouble);
        } else if (option == 3){
            if (productCompareData.size()-1 <= selected) selected = productCompareData.size()-2;
            optionsForSelected(selected+1, scanner);
        } else if (option == 4){
            if (selected == 0) selected = 1;
            optionsForSelected(selected-1, scanner);
        } else if (option == 5){
            productDTOList = ShopifyProductService.getShopifyProductList();
            print();
            System.out.println("Which product number would you like to select?");
            optionsForSelected(scanner.nextInt(), scanner);
        } else if (option == 6){
            print();
            System.out.println("Which product number would you like to select?");
            optionsForSelected(scanner.nextInt(), scanner);
        }


    }


}
