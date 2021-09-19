package Services;

import Constants.ConstantsEnum;
import DTO.ProductDTO;
import DTO.ProductListDTO;
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
import java.util.List;


public class PostRequestExecutor {

    public static List<ProductDTO> getProductsFromShopify(){
        try {
            URL url= new URL(ConstantsEnum.GET_REQUEST_SHOPIFY_PRODUCTS.getConstantValue().toString());

            HttpPost post = new HttpPost(url.toString());
            post.addHeader("Content-Type", "application/json");
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet get = new HttpGet(url.toString());
            CloseableHttpResponse getResponse = client.execute(get);
            ObjectMapper mapper = new ObjectMapper();
            ProductListDTO products = mapper.readValue(EntityUtils.toString(getResponse.getEntity()), ProductListDTO.class);

            for (ProductDTO p : products.getProducts()){
                System.out.println(p.toString());
            }

            return  products.getProducts();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
