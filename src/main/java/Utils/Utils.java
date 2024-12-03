package Utils;

import Constants.ProductMetafieldEnum;
import DTO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static String normalizeStringLenght(int spacing, String string){
        if (string == null){
            string ="";
        }
        int lenghtToAdd = 0;
        if(string.length() < spacing){
            lenghtToAdd = spacing - string.length();
        }
        String result = (String) string.subSequence(0, string.length() > spacing ? spacing: string.length());
        for (int i = 0; i<lenghtToAdd; i++ ){
            result = result + " ";
        }

        return result;
    }

    public static String[] getFirstLastName (String fullName){
        String[] names = fullName.split(" ");
        String firstName = null;
        String lastName = null;
        String[] result = new String[2];

        for (String name : names){
            if (firstName == null){
                firstName = name;
                lastName = "";
            } else {
                lastName = lastName + " " + name;
            }
        }

        result[0] = firstName;
        result[1] = lastName;


        return result;
    }

    public static String dateFormat(LocalDateTime dateTime){
        return dateTime.format(dateFormat);
    }
    public static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .appendPattern("EE, d 'de' MMMM")
            .toFormatter(new Locale("pt","PT"));

    public static LocalDateTime StringMoloniDateTime (String dateTime){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return LocalDateTime.parse(dateTime.split("\\+")[0], dtf);
    }

    public static Map<String, String> getQueryParamsFromUrl(String urlString){
        URL url = null;
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        try {
            logger.info("Getting query params for URL {}", urlString);
            url = new URL(urlString);
            String query = url.getQuery();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
            return query_pairs;
        } catch (MalformedURLException e) {
            logger.error("Could not get query params for URL {}", urlString);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("Could not get query params for URL {}", urlString);
            e.printStackTrace();
        }
        return query_pairs;
    }

    public static String addParameter(String URL, String name, String value){
        int qpos = URL.indexOf('?');
        int hpos = URL.indexOf('#');
        char sep = qpos == -1 ? '?' : '&';
        String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
        return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg
                + URL.substring(hpos);
    }

    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException(uee);
        }
    }

    public static List<ProductDTO> getProductsDTOfromProductGQLDTO(List<ProductGQLDTO> productGQLDTOList) {
        List<ProductDTO> result = new ArrayList<>();

        for (ProductGQLDTO productGQLDTO : productGQLDTOList) {
            for (ProductGQLDTO.ProductEdge productEdge : productGQLDTO.getData().getProducts().getEdges()) {
                ProductGQLDTO.ProductNode productNode = productEdge.getNode();

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(productNode.getId());
                productDTO.setTitle(productNode.getTitle());
                productDTO.setBodyHtml(productNode.getDescriptionHtml());
                productDTO.setBrand(productNode.getVendor());
                productDTO.setProductType(productNode.getProductType());
                productDTO.setCreatedAt(productNode.getCreatedAt());
                productDTO.setHandle(productNode.getHandle());
                productDTO.setUpdatedAt(productNode.getUpdatedAt());
                productDTO.setPublishedAt(productNode.getPublishedAt());
                productDTO.setTemplateSuffix(productNode.getTemplateSuffix());
                productDTO.setStatus(productNode.getStatus());
                productDTO.setTags(productNode.getTags().toArray(new String[0]));

                List<String> tagsList = productNode.getTags();
                if (tagsList != null) {
                    productDTO.setTags(tagsList.toArray(new String[0]));
                }
                // Variants
                List<ProductVariantDTO> variants = new ArrayList<>();
                for (ProductGQLDTO.VariantEdge variantEdge : productNode.getVariants().getEdges()) {
                    ProductGQLDTO.VariantNode variantNode = variantEdge.getNode();

                    ProductVariantDTO variantDTO = new ProductVariantDTO();
                    variantDTO.setId(variantNode.getId());
                    variantDTO.setTitle(variantNode.getTitle());
                    variantDTO.setPrice(variantNode.getPrice() != null ? Double.valueOf(variantNode.getPrice()) : null);
                    variantDTO.setSku(variantNode.getSku());
                    variantDTO.setPosition((long) variantNode.getPosition());
                    variantDTO.setBarcode(variantNode.getBarcode());
                    variantDTO.setInventoryPolicy(variantNode.getInventoryPolicy());
                    variantDTO.setRequiresShipping(variantNode.getInventoryItem().isRequiresShipping());
                    variantDTO.setInventoryQuantity(variantNode.getInventoryQuantity());
                    variantDTO.setWeight(variantNode.getInventoryItem().getMeasurement().getWeight().getValue());
                    variantDTO.setWeightUnit(variantNode.getInventoryItem().getMeasurement().getWeight().getUnit());
                    variantDTO.setCompareAtPrice(variantNode.getCompareAtPrice() != null ? Double.valueOf(variantNode.getCompareAtPrice()) : null);
                    variantDTO.setTaxable(variantNode.getTaxable());
                    variantDTO.setInventoryItemId(variantNode.getInventoryItem().getId());
                    // Set InventoryItemDTO
                    if (variantNode.getInventoryItem() != null) {
                        ProductVariantDTO.InventoryItemDTO inventoryItemDTO = new ProductVariantDTO.InventoryItemDTO();
                        inventoryItemDTO.setId(variantNode.getInventoryItem().getId());

                        // Set InventoryLevel
                        if (variantNode.getInventoryItem().getInventoryLevel() != null) {
                            ProductVariantDTO.InventoryLevelQuantitiesDTO inventoryLevelDTO = new ProductVariantDTO.InventoryLevelQuantitiesDTO();
                            List<ProductVariantDTO.Quantities> quantities = new ArrayList<>();
                            for (ProductGQLDTO.Quantity quantity : variantNode.getInventoryItem().getInventoryLevel().getQuantities()) {
                                ProductVariantDTO.Quantities quantityDTO = new ProductVariantDTO.Quantities();
                                quantityDTO.setQuantity(quantity.getQuantity());
                                quantityDTO.setName(quantity.getName());
                                quantities.add(quantityDTO);
                            }
                            inventoryLevelDTO.setQuantities(quantities);
                            inventoryItemDTO.setInventoryLevelQuantitiesDTO(inventoryLevelDTO);
                        }

                        variantDTO.setInventoryItemDTO(inventoryItemDTO);
                    }
                    variants.add(variantDTO);
                }
                productDTO.setVariants(variants);

                // Media
                List<ProductImageDTO> images = new ArrayList<>();
                for (ProductGQLDTO.MediaEdge mediaEdge : productNode.getMedia().getEdges()) {
                    ProductGQLDTO.MediaNode mediaNode = mediaEdge.getNode();

                    ProductImageDTO imageDTO = new ProductImageDTO();
                    imageDTO.setId(mediaNode.getId());
                    imageDTO.setAltText(mediaNode.getAlt());
                    imageDTO.setSrc(mediaNode.getPreview() != null ? mediaNode.getPreview().getImage().getUrl() : null);
                    images.add(imageDTO);
                }
                productDTO.setImages(images);

                // Metafields
                List<ProductMetafieldDTO> metafields = new ArrayList<>();
                if (productNode.getMetafields() != null) {
                    for (ProductGQLDTO.MetafieldEdge metafieldEdge : productNode.getMetafields().getEdges()) {
                        ProductGQLDTO.MetafieldNode metafieldNode = metafieldEdge.getNode();

                        ProductMetafieldDTO metafieldDTO = new ProductMetafieldDTO();
                        metafieldDTO.setId(metafieldNode.getId());
                        metafieldDTO.setNamespace(metafieldNode.getNamespace());
                        metafieldDTO.setKey(metafieldNode.getKey());
                        metafieldDTO.setValue(metafieldNode.getValue());
                        metafieldDTO.setOwner_id(productDTO.getId());

                        for (ProductMetafieldEnum i : ProductMetafieldEnum.values()){
                            if (i.getKey().equals(metafieldDTO.getKey())){
                                metafieldDTO.setProductMetafieldEnum(i);
                            }
                        }
                        metafields.add(metafieldDTO);
                    }
                }
                productDTO.setMetafields(metafields);

                result.add(productDTO);
            }
        }

        return result;
    }
}
