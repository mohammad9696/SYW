package Services;
import Constants.HttpRequestAuthTypeEnum;
import DTO.MoloniProductProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


public class HttpRequestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestExecutor.class);

    public static <T> T getObjectRequest (TypeReference<T> objectClass, String requestUrl, Map<String, Object> params){
        return getObjectRequest ( objectClass,  requestUrl,  null,  null, params);
    }

    public static <T> T getObjectRequest (TypeReference<T> objectClass, String requestUrl, HttpRequestAuthTypeEnum httpRequestAuthTypeEnum, String authKey, Map<String, Object> params){
        try {
            if (requestUrl.contains("shopify")){// shopify often exceed with error 429
                Thread.sleep(500);
            }
            URL url= new URL(requestUrl);
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet get = new HttpGet(url.toString());

            if (httpRequestAuthTypeEnum == HttpRequestAuthTypeEnum.XXX_API_KEY && authKey != null){
                get.addHeader("x-api-key", authKey);
            }

            CloseableHttpResponse getResponse = client.execute(get);
            boolean firsTry = true;
            while (firsTry || getResponse.getCode()==429) {
                firsTry =false;
                if (getResponse.getCode() >= 200 && getResponse.getCode() < 300 ){
                    logger.info("Got success response for request with HTTP Status code {}", getResponse.getCode());
                } else if (getResponse.getCode()==429) {
                    logger.error("Got error response for request with HTTP Status code 429. Too many requests. Retrying in 1 second");
                    Thread.sleep(1000);
                    getResponse = client.execute(get);

                } else {
                    logger.error("Got error response for request with HTTP Status code {}", getResponse.getCode());
                }
            }


            //para a paginacao do shopify
            if (getResponse.containsHeader("Link")){
                String[] headerGroup = getResponse.getHeaders("Link")[0].getValue().split(",");
                for (String h : headerGroup){
                    if(h.contains("next")){
                        String link = h;
                        String newUrl =link.split("//")[1].split(">")[0];
                        String newReqUrl = requestUrl.split("@")[0]+"@"+newUrl;
                        params.put("newReqUrl", newReqUrl);
                    }
                }
            }



            ObjectMapper mapper = new ObjectMapper();
            Object object = mapper.readValue(EntityUtils.toString(getResponse.getEntity()), objectClass);

            return  (T) object;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static void patchObjectRequest (String requestUrl, HttpRequestAuthTypeEnum httpRequestAuthTypeEnum, String authKey){
        try {
            URL url= new URL(requestUrl);
            CloseableHttpClient client = HttpClients.createDefault();

            HttpPatch patch = new HttpPatch(url.toString());

            if (httpRequestAuthTypeEnum == HttpRequestAuthTypeEnum.XXX_API_KEY && authKey != null){
                patch.addHeader("x-api-key", authKey);
            }
            //patch.addHeader(HttpHeaders.CONTENT_LENGTH,"0");

            CloseableHttpResponse getResponse = client.execute(patch);
            System.out.println(getResponse.getEntity());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <S, T> S sendRequest (Class<S> responseObject, T requestObject, String requestUrl){
        return sendRequest(responseObject, requestObject, requestUrl, null, null);
    }

    public static <S, T> S sendRequest (Class<S> responseObject, T requestObject, String requestUrl, HttpRequestAuthTypeEnum httpRequestAuthTypeEnum, String authKey){

        try {
            URL url= new URL(requestUrl);

            HttpPost post = new HttpPost(url.toString());
            post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            if (httpRequestAuthTypeEnum == HttpRequestAuthTypeEnum.BEARER_TOKEN && authKey != null){
                post.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authKey);
            }

            if (httpRequestAuthTypeEnum == HttpRequestAuthTypeEnum.XXX_API_KEY && authKey != null){
                post.addHeader("x-api-key", authKey);
            }

            ObjectMapper mapper = new ObjectMapper();

            //hardcoded, later look for solution to make any array that json inputs as "" to return empty
            mapper.coercionConfigFor(MoloniProductProperty[].class).setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);

            String jsonValue = mapper.writeValueAsString(requestObject);
            StringEntity stringEntity = new StringEntity(jsonValue, ContentType.APPLICATION_JSON);

            post.setEntity(stringEntity);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse getResponse = client.execute(post);

            Object object = mapper.readValue(EntityUtils.toString(getResponse.getEntity()), responseObject);

            return  (S) object;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static <S, T> S updateRequest (Class<S> responseObject, T requestObject, String requestUrl){

        try {
            HttpPut put = new HttpPut(requestUrl);
            put.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            

            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(requestObject);
            StringEntity stringEntity = new StringEntity(jsonValue, ContentType.APPLICATION_JSON);

            put.setEntity(stringEntity);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse getResponse = client.execute(put);

            Object object = mapper.readValue(EntityUtils.toString(getResponse.getEntity()), responseObject);

            return  (S) object;

        } catch (MalformedURLException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
