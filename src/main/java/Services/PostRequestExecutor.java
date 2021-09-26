package Services;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class PostRequestExecutor {

    public static <T> T getObjectRequest (Class<T> objectClass, String requestUrl){
        try {
            URL url= new URL(requestUrl);

            HttpPost post = new HttpPost(url.toString());
            post.addHeader("Content-Type", "application/json");
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet get = new HttpGet(url.toString());
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

}
