package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpGraphQLRequestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(HttpGraphQLRequestExecutor.class);

    public static void setProductMetafields (List<ProductMetafieldDTO> list){

        GQLMutationMetafieldsVariablesDTO qglVariables = new GQLMutationMetafieldsVariablesDTO(list);
        HttpRequestExecutor.sendRequestGraphQL(Object.class, getMetafieldMutationQuery(qglVariables) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());

    }

    private static String getMetafieldMutationQuery(GQLMutationMetafieldsVariablesDTO metafieldsVariablesDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        String variables;
        try {
            variables = objectMapper.writeValueAsString(metafieldsVariablesDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String query ="{\n" +
                "  \"query\": \"mutation MetafieldsSet($metafields: [MetafieldsSetInput!]!) { metafieldsSet(metafields: $metafields) { metafields { key namespace value createdAt updatedAt } userErrors { field message code } }}\",\n" +
                "  \"variables\": "+variables+"\n" +
                "}";

        return query;
    }

    public static void setAvailableInventory (int quantity, String inventoryItemId){
        HttpRequestExecutor.sendRequestGraphQL(Object.class, mutationSetInventory(quantity, inventoryItemId) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());

    }
    public static String mutationSetInventory (int quantity, String inventoryItemId){
        String query = "{\n" +
                "  \"query\": \"mutation SetProductAvailableInventory($name: String!, $quantities: [InventoryQuantityInput!]!, $reason: String!, $ignoreCompareQuantity: Boolean!) { inventorySetQuantities(input: {name: $name, quantities: $quantities, reason: $reason, ignoreCompareQuantity: $ignoreCompareQuantity}) { inventoryAdjustmentGroup { id createdAt reason } userErrors { field message } }}\",\n" +
                "  \"variables\": {\n" +
                "    \"name\": \"available\",\n" +
                "    \"quantities\": [\n" +
                "      {\n" +
                "        \"inventoryItemId\": \""+inventoryItemId+"\",\n" +
                "        \"locationId\": \"gid://shopify/Location/58086129815\",\n" +
                "        \"quantity\": "+quantity+"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"reason\": \"correction\",\n" +
                "    \"ignoreCompareQuantity\": true\n" +
                "  }\n" +
                "}";

        return query;
    }
    public static List<ProductDTO> getAllProducts (){
        boolean first = true;
        List<ProductGQLDTO> productListList = new ArrayList<>();
        ProductGQLDTO products = null;
        String lastCursor ="";

        while (first || (products!= null && products.getData() != null && products.getData().getProducts() != null && !products.getData().getProducts().getEdges().isEmpty() &&products.getData().getProducts().getPageInfo().isHasNextPage()==true)) {
            if (!first){
                lastCursor = products.getData().getProducts().getEdges().get(products.getData().getProducts().getEdges().size()-1).getCursor();
            }
            first = false;
            products = HttpRequestExecutor.sendRequestGraphQL(ProductGQLDTO.class, getProductsQuery(lastCursor) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
            productListList.add(products);
        }

        List<ProductDTO> result = Utils.getProductsDTOfromProductGQLDTO(productListList);

        return result;
    }

    private static String getProductsQuery(String cursor){
        String after = "";

        if (cursor != null && !cursor.equals("")){
            after = ", after: \\\""+cursor+"\\\"";
        }

        String query = "{\n" +
                "  \"query\": \"query { products(first: 250"+after+") { edges { node { id title descriptionHtml vendor productType createdAt handle updatedAt publishedAt templateSuffix status tags metafields(first: 10, namespace: \\\"custom\\\") { edges { node { id key namespace value type } } } variants (first: 10) { edges{ node { id title price compareAtPrice taxable sku position barcode inventoryPolicy inventoryQuantity inventoryItem { id inventoryLevel (locationId:\\\"gid://shopify/Location/58086129815\\\") { quantities (names:[\\\"available\\\", \\\"committed\\\", \\\"incoming\\\", \\\"on_hand\\\", \\\"reserved\\\"]) { quantity name } } measurement{ weight{ unit value } } requiresShipping } } } } media (first: 10){ edges { node { alt id status mediaContentType preview { image { url } } } } } } cursor } pageInfo { hasNextPage } }}\"\n" +
                "}";

        return query;


    }
    public static void registerTranslationsMutation (GQLMutationTranslationsVariablesDTO translations){
        HttpRequestExecutor.sendRequestGraphQL(Object.class, updateTranslationsMutationsQuery(translations) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
    }
    public static GQLMutationTranslationsVariablesDTO translate(List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> toTranslate, int tries){
        logger.info("Translating from OpenAI. Request try {}", tries);
        GQLMutationTranslationsVariablesDTO dto = null;
        Map<String, List<GQLMutationTranslationsVariablesDTO.Translation>> translated = new HashMap<>();
        List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> notTranslated = new ArrayList<>();

        for (GQLQueryTranslatableResourceResponseDTO.TranslatableContent i : toTranslate){
            if (OpenAIService.memory.containsKey(i.getValue())){
                List<GQLMutationTranslationsVariablesDTO.Translation> translations = OpenAIService.memory.get(i.getValue());
                for (GQLMutationTranslationsVariablesDTO.Translation j : translations){
                    j.setDigest(i.getDigest());
                }
                translated.put(i.getResourceId(), translations);
            } else {
                notTranslated.add(i);
            }
        }

        String contentJson = null;
        try {
            if (notTranslated.size()!=0){
                OpenAIResponseDTO result = OpenAIService.getTranslate(notTranslated);
                OpenAIResponseDTO.Choice choice = result.getChoices().get(0);

                contentJson = choice.getMessage().getContent();

                dto = new ObjectMapper().readValue(contentJson, GQLMutationTranslationsVariablesDTO.class);
            } else {
                dto = new GQLMutationTranslationsVariablesDTO();
            }


        } catch (JsonProcessingException e) {
            logger.error("Try {} ; Error parsing OpenAI response JSON for resource ID {}, {}, {}", tries, toTranslate.get(0).getResourceId(), toTranslate.get(1).getResourceId(), toTranslate.get(2).getResourceId());
            if (tries <5){
                return translate(toTranslate,tries+1);
            }
        }
        if(notTranslated.size()>0){//nt=1 t=2
            for (GQLQueryTranslatableResourceResponseDTO.TranslatableContent i : toTranslate){
                if (dto.getResourceId1().equals(i.getResourceId())){
                    OpenAIService.memory.put(i.getValue(), dto.getTranslations1());
                    break;
                }
            }

        }
        if(notTranslated.size()>1){//nt=2 t=1
            for (GQLQueryTranslatableResourceResponseDTO.TranslatableContent i : toTranslate){
                if (dto.getResourceId2().equals(i.getResourceId())){
                    OpenAIService.memory.put(i.getValue(), dto.getTranslations2());
                    break;
                }
            }
        }
        if(notTranslated.size()>2){//nt=3 t=0
            for (GQLQueryTranslatableResourceResponseDTO.TranslatableContent i : toTranslate){
                if (dto.getResourceId3().equals(i.getResourceId())){
                    OpenAIService.memory.put(i.getValue(), dto.getTranslations3());
                    break;
                }
            }
        }

        boolean used3 = false;
        boolean used2 = false;
        boolean used1 = false;
        for(Map.Entry<String,List<GQLMutationTranslationsVariablesDTO.Translation>> i : translated.entrySet()){
            if (translated.size() == 0) {
                break;
            }
            if (translated.size() >0 && !used3) {
                dto.setResourceId3(i.getKey());
                dto.setTranslations3(i.getValue());
                used3=true;
                continue;
            }

            if (translated.size() > 1 && !used2) {
                dto.setResourceId2(i.getKey());
                dto.setTranslations2(i.getValue());
                used2=true;
                continue;
            }

            if (translated.size() > 2 && !used1) {
                dto.setResourceId1(i.getKey());
                dto.setTranslations1(i.getValue());
            }
        }



        return dto;
    }

    public static List<GQLQueryTranslationsResponseDTO.Node> metafieldsToTranslate(String id){
        GQLQueryTranslationsResponseDTO a = getProductETAsById(id);
        List<GQLQueryTranslationsResponseDTO.Node> nodes = new ArrayList<>();
        for (GQLQueryTranslationsResponseDTO.Edge i :a.getData().getProduct().getMetafields().getEdges()) {
            if(i.getNode().getKey().equals("etaResult") || i.getNode().getKey().equals("etaCartResult") || i.getNode().getKey().equals("etaCartResultNoStock")){
                nodes.add(i.getNode());
            }
        }
        return nodes;
    }

    public static GQLQueryTranslationsResponseDTO getProductETAsById(String id){
        return HttpRequestExecutor.sendRequestGraphQL(GQLQueryTranslationsResponseDTO.class, getProductETAsMetafieldIdsByProductIdQuery(id) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
    }

    private static List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> getTranslatableResourcesById(List<String> metafieldGIDs){
        GQLQueryTranslatableResourceResponseDTO response = HttpRequestExecutor.sendRequestGraphQL(GQLQueryTranslatableResourceResponseDTO.class, getDigestByMetafieldIdQuery(metafieldGIDs) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
        List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> nodes = new ArrayList<>();

        for (GQLQueryTranslatableResourceResponseDTO.Edge i : response.getData().getTranslatableResourcesByIds().getEdges()){
            GQLQueryTranslatableResourceResponseDTO.TranslatableContent j = i.getNode().getTranslatableContent().get(0);
            j.setResourceId(i.getNode().getResourceId());
            nodes.add(j);
        }
        return nodes;
    }

    private static String getProductETAsMetafieldIdsByProductIdQuery(String metafieldGID){
        String query ="{\n" +
                "  \"query\": \"{ product(id: \\\""+metafieldGID+"\\\") { id metafields(first: 20, namespace: \\\"custom\\\") { edges { node { id key value } } } }}\"\n" +
                "}";
        return query;
    }

    private static String updateTranslationsMutationsQuery (GQLMutationTranslationsVariablesDTO var){

        ObjectMapper objectMapper = new ObjectMapper();
        String variables;
        try {
            variables = objectMapper.writeValueAsString(var);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String mutation = "{\n" +
                "  \"query\": \"mutation translationsRegister( $resourceId1: ID!, $translations1: [TranslationInput!]!, $resourceId2: ID!, $translations2: [TranslationInput!]!, $resourceId3: ID!, $translations3: [TranslationInput!]!) { resource1: translationsRegister(resourceId: $resourceId1, translations: $translations1) { userErrors { message field } translations { key value } } resource2: translationsRegister(resourceId: $resourceId2, translations: $translations2) { userErrors { message field } translations { key value } } resource3: translationsRegister(resourceId: $resourceId3, translations: $translations3) { userErrors { message field } translations { key value } }}\",\n" +
                "  \"variables\": "+variables+"\n" +
                "}";

        return mutation;
    }

    private static String getDigestByMetafieldIdQuery(List<String> metafieldGIDs){


        StringBuilder ids = new StringBuilder();
        ids.append("[");
        boolean first = true;
        for (String i : metafieldGIDs){
            if (!first){
                ids.append(",");
            }
            String id = "\\\""+i+"\\\"";
            ids.append(id);
            first = false;
        }
        ids.append("]");
        System.out.println(ids.toString());
        String query ="{\n" +
                "  \"query\": \"query { translatableResourcesByIds(first: 50, resourceIds: "+ids.toString()+") { edges { node { resourceId translatableContent { key value digest } } } }}\"\n" +
                "}";
        return query;
    }


    public static void updateETATranslations (String id){
        List<GQLQueryTranslationsResponseDTO.Node> toTranslate = metafieldsToTranslate(id);
        List<String> ids = new ArrayList<>();
        for (GQLQueryTranslationsResponseDTO.Node i : toTranslate){
            ids.add(i.getId());
        }

        List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> translatableContents = getTranslatableResourcesById(ids);
        GQLMutationTranslationsVariablesDTO openAiTranslation = translate(translatableContents,0);

        registerTranslationsMutation(openAiTranslation);
    }

    public static void main(String[] args) {
        List<ProductDTO> productDTOS = getAllProducts();

        int j = 0;
        for (ProductDTO i : productDTOS){
            try {
                logger.info("{} Translating ETAs for {} {}", j, i.sku(), i.getTitle());
                updateETATranslations(i.getId());
                logger.info("{} Translated ETAs for {} {}", j, i.sku(), i.getTitle());
            } catch (Exception e){
                try {
                    logger.warn("{} Retrying Translating ETAs for {} {}",j, i.sku(), i.getTitle());
                    updateETATranslations(i.getId());
                } catch (Exception f){
                    logger.error("{} Couldn't translate {} {}",j,i.sku(), i.getTitle());
                }

            }
            j++;

        }

    }
}
