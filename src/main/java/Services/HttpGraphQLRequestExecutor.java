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
import java.util.stream.Collectors;

public class HttpGraphQLRequestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(HttpGraphQLRequestExecutor.class);

    public static void removeMetafield (ProductMetafieldDTO p){
        String query = "{\n" +
                "  \"query\": \"mutation DeleteMetafield($metafields: [MetafieldIdentifierInput!]!) { metafieldsDelete(metafields: $metafields) { userErrors { field message } }}\",\n" +
                "  \"variables\": {\n" +
                "    \"metafields\": [\n" +
                "      {\n" +
                "        \"key\": \""+p.getKey()+"\",\n" +
                "        \"namespace\": \""+p.getNamespace()+"\",\n" +
                "        \"ownerId\": \""+p.getOwner_id()+"\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        HttpRequestExecutor.sendRequestGraphQL(Object.class, query , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
    }
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
                "  \"query\": \"query { products(first: 250"+after+") { edges { node { id title descriptionHtml vendor productType createdAt handle updatedAt publishedAt templateSuffix status tags metafields(first: 20, namespace: \\\"custom\\\") { edges { node { id key namespace value type } } } variants (first: 10) { edges{ node { id title price compareAtPrice taxable sku position barcode inventoryPolicy inventoryQuantity inventoryItem { id inventoryLevel (locationId:\\\"gid://shopify/Location/58086129815\\\") { quantities (names:[\\\"available\\\", \\\"committed\\\", \\\"incoming\\\", \\\"on_hand\\\", \\\"reserved\\\"]) { quantity name } } measurement{ weight{ unit value } } requiresShipping } } } } media (first: 10){ edges { node { alt id status mediaContentType preview { image { url } } } } } } cursor } pageInfo { hasNextPage } }}\"\n" +
                "}";

        return query;


    }
    public static void registerTranslationsMutation (List<GQLMutationTranslationsVariablesDTO.Translation> translations){
        HttpRequestExecutor.sendRequestGraphQL(Object.class, updateTranslationsMutationsQuery(translations) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
    }
    public static List<GQLMutationTranslationsVariablesDTO.Translation> translate(List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> toTranslate, int tries){
        logger.info("Translating from OpenAI. Request try {}", tries);

        Map<String, List<GQLMutationTranslationsVariablesDTO.Translation>> translated = new HashMap<>();
        List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> notTranslated = new ArrayList<>();

        // 1. Verifica cache
        for (GQLQueryTranslatableResourceResponseDTO.TranslatableContent i : toTranslate) {
            if (OpenAIService.memory.containsKey(i.getValue())) {
                List<GQLMutationTranslationsVariablesDTO.Translation> cachedOriginal = OpenAIService.memory.get(i.getValue());
                List<GQLMutationTranslationsVariablesDTO.Translation> cached = new ArrayList<>();
                for (GQLMutationTranslationsVariablesDTO.Translation t : cachedOriginal) {
                    GQLMutationTranslationsVariablesDTO.Translation copy = new GQLMutationTranslationsVariablesDTO.Translation();
                    copy.setKey(t.getKey());
                    copy.setLocale(t.getLocale());
                    copy.setValue(t.getValue());
                    // aqui ainda vais sobrepor:
                    copy.setDigest(i.getDigest());
                    copy.setResourceId(i.getResourceId());

                    cached.add(copy);
                }
                // Atualiza digest
                for (GQLMutationTranslationsVariablesDTO.Translation j : cached) {
                    j.setDigest(i.getDigest());
                    j.setResourceId(i.getResourceId());
                }
                translated.put(i.getResourceId(), cached);
            } else {
                notTranslated.add(i);
            }
        }

        List<GQLMutationTranslationsVariablesDTO.Translation> newTranslations = new ArrayList<>();

        // 2. Faz chamada à API para novos
        if (!notTranslated.isEmpty()) {
            try {
                OpenAIResponseDTO result = OpenAIService.getTranslations(notTranslated);
                OpenAIResponseDTO.Choice choice = result.getChoices().get(0);
                String contentJson = choice.getMessage().getContent();

                newTranslations = new ObjectMapper().readValue(
                        contentJson,
                        new TypeReference<List<GQLMutationTranslationsVariablesDTO.Translation>>() {}
                );
            } catch (Exception e) {
                String id1 = toTranslate.size() > 0 ? toTranslate.get(0).getResourceId() : "n/a";
                String id2 = toTranslate.size() > 1 ? toTranslate.get(1).getResourceId() : "n/a";
                String id3 = toTranslate.size() > 2 ? toTranslate.get(2).getResourceId() : "n/a";
                logger.error("Try {} ; Error parsing OpenAI response JSON for resource IDs: {}, {}, {}", tries, id1, id2, id3);
                if (tries < 5) {
                    return translate(toTranslate, tries + 1);
                }
            }
        }

        // 3. Atualiza cache com novas traduções
        for (GQLMutationTranslationsVariablesDTO.Translation t : newTranslations) {
            if (!OpenAIService.memory.containsKey(t.getTranslatedContent())) {
                List<GQLMutationTranslationsVariablesDTO.Translation> sameResource = newTranslations.stream()
                        .filter(j -> j.getResourceId().equals(t.getResourceId()))
                        .collect(Collectors.toList());
                OpenAIService.memory.put(t.getTranslatedContent(), sameResource);
            }
        }

        // 4. Junta novas + cache e retorna
        List<GQLMutationTranslationsVariablesDTO.Translation> finalTranslations = new ArrayList<>(newTranslations);
        translated.values().forEach(finalTranslations::addAll);

        return finalTranslations;
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
                if (entry.getKey() == null) {
                    continue;
                }
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
        int interval = 5;
        int k = 0;
        List<String> ids = new ArrayList<>();

        for (ProductDTO i : productDTOS){
            if (!i.getVariants().get(0).getInventoryPolicy().equalsIgnoreCase("continue") && i.getVariants().get(0).getInventoryQuantity()<1) {
                continue;
            }
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
