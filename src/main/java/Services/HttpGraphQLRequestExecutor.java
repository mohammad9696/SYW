package Services;

import Constants.ConstantsEnum;
import DTO.GQLMutationTranslationsVariablesDTO;
import DTO.GQLQueryTranslatableResourceResponseDTO;
import DTO.GQLQueryTranslationsResponseDTO;
import DTO.OpenAIResponseDTO;
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

    public static void registerTranslationsMutation (GQLMutationTranslationsVariablesDTO translations){
        HttpRequestExecutor.sendRequestGraphQL(Object.class, updateTranslationsMutationsQuery(translations) , ConstantsEnum.SHOPIFY_GRAPHQL_URL.getConstantValue().toString());
    }
    public static GQLMutationTranslationsVariablesDTO translate(List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> toTranslate){
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

        try {
            if (notTranslated.size()!=0){
                OpenAIResponseDTO result = OpenAIService.getTranslate(notTranslated);
                OpenAIResponseDTO.Choice choice = result.getChoices().get(0);

                String contentJson = choice.getMessage().getContent();

                dto = new ObjectMapper().readValue(contentJson, GQLMutationTranslationsVariablesDTO.class);
            } else {
                dto = new GQLMutationTranslationsVariablesDTO();
            }


        } catch (JsonProcessingException e) {
            logger.error("Error parsing OpenAI response JSON for resource ID {}, {}, {}", toTranslate.get(0).getResourceId(), toTranslate.get(1).getResourceId(), toTranslate.get(2).getResourceId());
            throw new RuntimeException(e);
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
        GQLMutationTranslationsVariablesDTO openAiTranslation = translate(translatableContents);

        registerTranslationsMutation(openAiTranslation);
    }

    public static void main(String[] args) {
        String id ="gid://shopify/Product/9004516114778";
        updateETATranslations(id);


    }
}
