package Services;
import Constants.HttpRequestAuthTypeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpRequestExecutor {

    public static <T> T getObjectRequest (TypeReference<T> objectClass, String requestUrl){
        return getObjectRequest ( objectClass,  requestUrl,  null,  null);
    }

    public static <T> T getObjectRequest (TypeReference<T> objectClass, String requestUrl, HttpRequestAuthTypeEnum httpRequestAuthTypeEnum, String authKey){
        try {
            URL url= new URL(requestUrl);
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet get = new HttpGet(url.toString());

            if (httpRequestAuthTypeEnum == HttpRequestAuthTypeEnum.XXX_API_KEY && authKey != null){
                get.addHeader("x-api-key", authKey);
            }

            CloseableHttpResponse getResponse = client.execute(get);
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
            URL url= new URL(requestUrl);

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
