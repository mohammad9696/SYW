package deprecated;

import DTO.ProductDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.net.URL;
import java.util.List;

public class FeedReader {

    public static List<Object> products (String urlString) throws IOException {
        URL url = new URL(urlString);
        ObjectMapper mapper = new XmlMapper();
        InputStream is = url.openStream();
        
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        for (int i = 0; i< 200; i++){
            System.out.println(br.readLine());
        }

        TypeReference<List<ProductDTO>> typeReference = new TypeReference<List<ProductDTO>>(){};
        List<ProductDTO> productsDTOs = mapper.readValue(is, typeReference);
        for (ProductDTO p : productsDTOs){
            System.out.println(p); 
        }

        //BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        //System.out.println(br.read());
        return null;
    }

    public static void main (String[] args){
        try {
            products("https://www.adwordsrobot.com/xx/feed/shopify_feed--51484033175--36d70d06--smartifier-pt.myshopify.com--en--product-feed.xml");
        } catch (IOException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
        System.out.println("teste");
    }
}
