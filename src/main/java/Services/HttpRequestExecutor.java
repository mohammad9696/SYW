package Services;
import Constants.HttpRequestAuthTypeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
            URL url= new URL(requestUrl);
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet get = new HttpGet(url.toString());

            if (httpRequestAuthTypeEnum == HttpRequestAuthTypeEnum.XXX_API_KEY && authKey != null){
                get.addHeader("x-api-key", authKey);
            }

            CloseableHttpResponse getResponse = client.execute(get);


            if (getResponse.getStatusLine().getStatusCode() >= 200 || getResponse.getStatusLine().getStatusCode() < 300 ){
                logger.info("Got success response for request with HTTP Status code {}", getResponse.getStatusLine().getStatusCode());
            } else {
                logger.error("Got error response for request with HTTP Status code {}", getResponse.getStatusLine().getStatusCode());
            }

            //para a paginacao dos produtos do shopify
            if (getResponse.containsHeader("Link") && getResponse.getHeaders("Link")[0].getValue().contains("next")){
                String link = getResponse.getHeaders("Link")[0].getValue();
                String newUrl =link.split("//")[1].split(">")[0];
                String newReqUrl = requestUrl.split("@")[0]+"@"+newUrl;
                params.put("newReqUrl", newReqUrl);
            }

            ObjectMapper mapper = new ObjectMapper();
            Object object = mapper.readValue(EntityUtils.toString(getResponse.getEntity()), objectClass);

            return  (T) object;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            String jsonValue = mapper.writeValueAsString(requestObject);
            StringEntity stringEntity = new StringEntity(jsonValue, "UTF-8");
            stringEntity.setContentType("application/json");

            post.setEntity(stringEntity);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse getResponse = client.execute(post);

            Object object = mapper.readValue(EntityUtils.toString(getResponse.getEntity()), responseObject);

            return  (S) object;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <S, T> S updateRequest (Class<S> responseObject, T requestObject, String requestUrl){

        try {
            HttpPut put = new HttpPut(requestUrl);
            put.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            

            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(requestObject);
            StringEntity stringEntity = new StringEntity(jsonValue, "UTF-8");
            stringEntity.setContentType("application/json");

            put.setEntity(stringEntity);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse getResponse = client.execute(put);

            Object object = mapper.readValue(EntityUtils.toString(getResponse.getEntity()), responseObject);

            return  (S) object;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
