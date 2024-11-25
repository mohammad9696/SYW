package Services;

import Constants.ConstantsEnum;
import DTO.*;
import Utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpGraphQLRequestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(HttpGraphQLRequestExecutor.class);

    public static void updateProductVariantPrice(String productId, String variantId, Double priceToSet, Double compareAtPriceToSet){
        if (priceToSet == null || priceToSet < 0.01){
            throw new NumberFormatException();
        }

        if (compareAtPriceToSet == null || priceToSet < 0.01){
            compareAtPriceToSet = 0.0;
        }

        String mutation = "{\n" +
                "  \"query\": \"mutation UpdateVariantPrice { productVariantsBulkUpdate(productId: \\\""+productId+"\\\", variants: [{id: \\\""+variantId+"\\\", price: \\\""+priceToSet+"\\\", compareAtPrice: \\\""+compareAtPriceToSet+"\\\"}]) { product { id variants(first: 10) { edges { node { id price } } } } userErrors { field message } }}\"\n" +
                "}";
        HttpRequestExecutor.sendRequestGraphQL(Object.class, mutation , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());

    }
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
    public static void registerTranslationsMutation (List<GQLMutationTranslationsVariablesDTO.Translation> translations){
        HttpRequestExecutor.sendRequestGraphQL(Object.class, updateTranslationsMutationsQuery(translations) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
    }
    public static List<GQLMutationTranslationsVariablesDTO.Translation> translate(List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> toTranslate, int tries){
        logger.info("Translating from OpenAI. Request try {}", tries);
        List<GQLMutationTranslationsVariablesDTO.Translation> dto = null;
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
                OpenAIResponseDTO result = OpenAIService.getTranslations(notTranslated);
                OpenAIResponseDTO.Choice choice = result.getChoices().get(0);

                contentJson = choice.getMessage().getContent();

                dto =  new ObjectMapper().readValue(contentJson, new TypeReference<List<GQLMutationTranslationsVariablesDTO.Translation>>(){});
            } else {
                dto = new ArrayList<>();
            }

        } catch (JsonProcessingException e) {
            logger.error("Try {} ; Error parsing OpenAI response JSON for resource ID {}, {}, {}", tries, toTranslate.get(0).getResourceId(), toTranslate.get(1).getResourceId(), toTranslate.get(2).getResourceId());
            if (tries <5){
                return translate(toTranslate,tries+1);
            }
        }

        for (GQLMutationTranslationsVariablesDTO.Translation i : dto){
            if (!OpenAIService.memory.containsKey(i.getTranslatedContent())){
                List<GQLMutationTranslationsVariablesDTO.Translation> translations = new ArrayList<>();
                for (GQLMutationTranslationsVariablesDTO.Translation j : dto){
                    if (j.getResourceId().equals(i.getResourceId())){
                        translations.add(j);
                    }
                }
                OpenAIService.memory.put(i.getTranslatedContent(), translations);
            }
        }
        /*
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

*/
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


    /*
    private static String updateTranslationsMutationsQuery (List<GQLMutationTranslationsVariablesDTO.Translation> dto){

        ObjectMapper objectMapper = new ObjectMapper();
        String variables;
        try {
            variables = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String mutation = "{\n" +
                "  \"query\": \"mutation translationsRegister( $resourceId1: ID!, $translations1: [TranslationInput!]!, $resourceId2: ID!, $translations2: [TranslationInput!]!, $resourceId3: ID!, $translations3: [TranslationInput!]!) { resource1: translationsRegister(resourceId: $resourceId1, translations: $translations1) { userErrors { message field } translations { key value } } resource2: translationsRegister(resourceId: $resourceId2, translations: $translations2) { userErrors { message field } translations { key value } } resource3: translationsRegister(resourceId: $resourceId3, translations: $translations3) { userErrors { message field } translations { key value } }}\",\n" +
                "  \"variables\": "+variables+"\n" +
                "}";

        String mutation = "{\n" +
                "  \"query\": \"mutation RegisterTranslations($resourceId: ID!, $translations: [TranslationInput!]!) { translationsRegister(resourceId: $resourceId, translations: $translations) { translations { key locale value } userErrors { field message } }}\",\n" +
                "  \"variables\":" + variables + "\n" +
                "}";
        return mutation;
    }

*/
    private static String updateTranslationsMutationsQuery(List<GQLMutationTranslationsVariablesDTO.Translation> translations) {
        try {
            // Agrupar traduções por resourceId
            Map<String, List<GQLMutationTranslationsVariablesDTO.Translation>> groupedByResourceId = new HashMap<>();
            for (GQLMutationTranslationsVariablesDTO.Translation translation : translations) {
                groupedByResourceId
                        .computeIfAbsent(translation.getResourceId(), k -> new ArrayList<>())
                        .add(translation);
            }

            // Construir dinamicamente a mutation
            StringBuilder queryBuilder = new StringBuilder();
            StringBuilder variablesBuilder = new StringBuilder();
            queryBuilder.append("mutation RegisterTranslations(");

            int index = 1;
            for (String resourceId : groupedByResourceId.keySet()) {
                queryBuilder.append("$resourceId").append(index).append(": ID!, ");
                queryBuilder.append("$translations").append(index).append(": [TranslationInput!]!");
                if (index < groupedByResourceId.size()) {
                    queryBuilder.append(", ");
                }
                index++;
            }
            queryBuilder.append(") { ");

            // Construir dinamicamente o corpo da query
            index = 1;
            for (String resourceId : groupedByResourceId.keySet()) {
                queryBuilder.append("resource").append(index).append(": translationsRegister(resourceId: $resourceId")
                        .append(index).append(", translations: $translations").append(index).append(") { ")
                        .append("userErrors { message field } translations { key locale value } } ");
                index++;
            }
            queryBuilder.append("}");

            // Construir variáveis dinamicamente
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> variables = new HashMap<>();
            index = 1;
            for (Map.Entry<String, List<GQLMutationTranslationsVariablesDTO.Translation>> entry : groupedByResourceId.entrySet()) {
                for (GQLMutationTranslationsVariablesDTO.Translation i : entry.getValue()){
                    i.setResourceId(null);
                    i.setTranslatedContent(null);

                }
                variables.put("resourceId" + index, entry.getKey());
                variables.put("translations" + index, entry.getValue());
                index++;
            }

            String variablesJson = objectMapper.writeValueAsString(variables);

            // Montar a mutation final
            String mutation = "{\n" +
                    "  \"query\": \"" + queryBuilder.toString() + "\",\n" +
                    "  \"variables\": " + variablesJson + "\n" +
                    "}";
            return mutation;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao construir a mutation GraphQL", e);
        }
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


    public static void updateETATranslations (List<String> productIds){

        List<String> metafieldIds = new ArrayList<>();
        List<GQLQueryTranslationsResponseDTO.Node> toTranslate;

        for (String productId : productIds){
            toTranslate = metafieldsToTranslate(productId);
            for (GQLQueryTranslationsResponseDTO.Node i : toTranslate){
                metafieldIds.add(i.getId());
            }
        }

        List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> translatableContents = getTranslatableResourcesById(metafieldIds);
        List<GQLMutationTranslationsVariablesDTO.Translation> openAiTranslation = translate(translatableContents,0);

        registerTranslationsMutation(openAiTranslation);
    }

    public static void main(String[] args) {
        List<ProductDTO> productDTOS = getAllProducts();
        int interval = 10;
        int k = 0;
        List<String> ids = new ArrayList<>();

        for (ProductDTO i : productDTOS){
            if (k % interval == 0 && k>0){
                try {
                    logger.info("{} Translating ETAs for last {} before {} {}",k,interval, i.sku(), i.getTitle());
                    updateETATranslations(ids);
                } catch (Exception e){
                    try {
                        logger.warn("{} Retrying Translating ETAs for {} {}",k, i.sku(), i.getTitle());
                        updateETATranslations(ids);
                    } catch (Exception f){
                        logger.error("{} Couldn't translate {} {}",k,i.sku(), i.getTitle());
                    }
                }
                ids = new ArrayList<>();
            }
            ids.add(i.getId());
            k++;

        }

    }
}
