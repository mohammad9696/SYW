package Services;


import Constants.ConstantsEnum;
import Constants.HttpRequestAuthTypeEnum;
import DTO.GQLMutationTranslationsVariablesDTO;
import DTO.GQLQueryTranslatableResourceResponseDTO;
import DTO.OpenAIRequestDTO;
import DTO.OpenAIResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAIService {

    public static Map<String, List<GQLMutationTranslationsVariablesDTO.Translation>> memory = new HashMap<>();
    public static OpenAIResponseDTO getTranslate(List<GQLQueryTranslatableResourceResponseDTO.TranslatableContent> translatableContents) throws JsonProcessingException {

        String schema = "{\"resourceId1\":\"gid://shopify/Metafield/42895600320858\",\"translations1\":[{\"locale\":\"fr\",\"translatableContentDigest\":\"4c8f146e56cb2d367f703b8eeb16d9f834d854be7d31864efc2021378944bd39\",\"key\":\"value\",\"value\":\"Disponible pour ✅ LIVRAISON IMMÉDIATE. n\uD83D\uDE9A Reçoit \uD83C\uDDEB\uD83C\uDDF7 mardi, 5 novembre\"},{\"locale\":\"en\",\"translatableContentDigest\":\"4c8f146e56cb2d367f703b8eeb16d9f834d854be7d31864efc2021378944bd39\",\"key\":\"value\",\"value\":\"Available for ✅ IMMEDIATE DELIVERY. n\uD83D\uDE9A Receives \uD83C\uDDEC\uD83C\uDDE7 Tuesday, November 5th\"},{\"locale\":\"es\",\"translatableContentDigest\":\"4c8f146e56cb2d367f703b8eeb16d9f834d854be7d31864efc2021378944bd39\",\"key\":\"value\",\"value\":\"Disponible para ✅ ENTREGA INMEDIATA. n\uD83D\uDE9A Recibe \uD83C\uDDEA\uD83C\uDDF8 martes, 5 de noviembre\"},{\"locale\":\"it\",\"translatableContentDigest\":\"4c8f146e56cb2d367f703b8eeb16d9f834d854be7d31864efc2021378944bd39\",\"key\":\"value\",\"value\":\"Disponibile per ✅ CONSEGNA IMMEDIATA. n\uD83D\uDE9A Riceve \uD83C\uDDEE\uD83C\uDDF9 martedì, 5 novembre\"},{\"locale\":\"de\",\"translatableContentDigest\":\"4c8f146e56cb2d367f703b8eeb16d9f834d854be7d31864efc2021378944bd39\",\"key\":\"value\",\"value\":\"Verfügbar für ✅ SOFORTIGE LIEFERUNG. n\uD83D\uDE9A Erhält \uD83C\uDDE9\uD83C\uDDEA Dienstag, 5. November\"}],\"resourceId2\":\"gid://shopify/Metafield/42895600976218\",\"translations2\":[{\"locale\":\"fr\",\"translatableContentDigest\":\"9501a90325d5c262f9fa313cab370e0a7f998a86d18511da4baf292e92ec98e8\",\"key\":\"value\",\"value\":\"mardi, 5 novembre et jeudi, 7 novembre\"},{\"locale\":\"en\",\"translatableContentDigest\":\"9501a90325d5c262f9fa313cab370e0a7f998a86d18511da4baf292e92ec98e8\",\"key\":\"value\",\"value\":\"Tuesday, November 5th and Thursday, November 7th\"},{\"locale\":\"es\",\"translatableContentDigest\":\"9501a90325d5c262f9fa313cab370e0a7f998a86d18511da4baf292e92ec98e8\",\"key\":\"value\",\"value\":\"martes, 5 de noviembre y jueves, 7 de noviembre\"},{\"locale\":\"it\",\"translatableContentDigest\":\"9501a90325d5c262f9fa313cab370e0a7f998a86d18511da4baf292e92ec98e8\",\"key\":\"value\",\"value\":\"martedì, 5 novembre e giovedì, 7 novembre\"},{\"locale\":\"de\",\"translatableContentDigest\":\"9501a90325d5c262f9fa313cab370e0a7f998a86d18511da4baf292e92ec98e8\",\"key\":\"value\",\"value\":\"Dienstag, 5. November und Donnerstag, 7. November\"}],\"resourceId3\":\"gid://shopify/Metafield/42895601238362\",\"translations3\":[{\"locale\":\"fr\",\"translatableContentDigest\":\"77a95ff813b2c196a92b0ae4bd3364799f72278f01ca8081972e0f432c04680e\",\"key\":\"value\",\"value\":\"jeudi, 7 novembre et vendredi, 8 novembre\"},{\"locale\":\"en\",\"translatableContentDigest\":\"77a95ff813b2c196a92b0ae4bd3364799f72278f01ca8081972e0f432c04680e\",\"key\":\"value\",\"value\":\"Thursday, November 7th and Friday, November 8th\"},{\"locale\":\"es\",\"translatableContentDigest\":\"77a95ff813b2c196a92b0ae4bd3364799f72278f01ca8081972e0f432c04680e\",\"key\":\"value\",\"value\":\"jueves, 7 de noviembre y viernes, 8 de noviembre\"},{\"locale\":\"it\",\"translatableContentDigest\":\"77a95ff813b2c196a92b0ae4bd3364799f72278f01ca8081972e0f432c04680e\",\"key\":\"value\",\"value\":\"giovedì, 7 novembre e venerdì, 8 novembre\"},{\"locale\":\"de\",\"translatableContentDigest\":\"77a95ff813b2c196a92b0ae4bd3364799f72278f01ca8081972e0f432c04680e\",\"key\":\"value\",\"value\":\"Donnerstag, 7. November und Freitag, 8. November\"}]}";
        ObjectMapper mapper = new ObjectMapper();
        String jsonRequest = mapper.writeValueAsString(translatableContents);
        OpenAIRequestDTO dto = new OpenAIRequestDTO("gpt-4o-mini", null, 1500, 0.5);
        List<OpenAIRequestDTO.Message> messages = new ArrayList<>();
        messages.add(new OpenAIRequestDTO.Message("system","You are a translation assistant skilled in providing JSON-formatted translations in multiple languages that ONLY outputs the json object in one line ensuring there are no extra `{` or `}` characters that may be resulted from poor escaping"));
        messages.add(new OpenAIRequestDTO.Message("system","You always check and fix json formatting and specially second last '}' of the response, iy often gets added wrongfully and messes entire structure, by removing it the structure gets fixed"));
        messages.add(new OpenAIRequestDTO.Message("user","Keep emojis as they are. Translate flags only to France, Britain, Spain, Italy, Germany"));
        messages.add(new OpenAIRequestDTO.Message("user","translate the following texts/ resources into all 5 languages [fr,en,es,it,de], and return the result in JSON format"));
        messages.add(new OpenAIRequestDTO.Message("user","This schema is a valid response example. Follow this structure and only change values and not keys. Schema: "+schema));
        messages.add(new OpenAIRequestDTO.Message("user","It is very important for schema to always have the keys: resourceId1, translations1, resourceId2, translations2, resourceId3, translations3"));
        messages.add(new OpenAIRequestDTO.Message("user","It is very important for schema of object translations to always have keys: locale, translatableContentDigest, key, value"));
        messages.add(new OpenAIRequestDTO.Message("user","Request: "+ jsonRequest));
        dto.setMessages(messages);

        OpenAIResponseDTO result = HttpRequestExecutor.sendRequest(OpenAIResponseDTO.class, dto, null, ConstantsEnum.OPENAI_REQUEST_URL.getConstantValue().toString(), HttpRequestAuthTypeEnum.BEARER_TOKEN,
                ConstantsEnum.OPENAI_API_KEY.getConstantValue().toString());


        return result;
    }
    public static void main(String[] args) {




    }
}
