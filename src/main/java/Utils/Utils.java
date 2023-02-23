package Utils;

import Services.MoloniService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static String normalizeStringLenght(int spacing, String string){
        if (string == null){
            string ="";
        }
        int lenghtToAdd = 0;
        if(string.length() < spacing){
            lenghtToAdd = spacing - string.length();
        }
        String result = (String) string.subSequence(0, string.length() > spacing ? spacing: string.length());
        for (int i = 0; i<lenghtToAdd; i++ ){
            result = result + " ";
        }

        return result;
    }

    public static String[] getFirstLastName (String fullName){
        String[] names = fullName.split(" ");
        String firstName = null;
        String lastName = null;
        String[] result = new String[2];

        for (String name : names){
            if (firstName == null){
                firstName = name;
                lastName = "";
            } else {
                lastName = lastName + " " + name;
            }
        }

        result[0] = firstName;
        result[1] = lastName;


        return result;
    }

    public static String dateFormat(LocalDateTime dateTime){
        return dateTime.format(dateFormat);
    }
    public static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .appendPattern("EE, d 'de' MMMM")
            .toFormatter(new Locale("pt","PT"));

    public static Map<String, String> getQueryParamsFromUrl(String urlString){
        URL url = null;
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        try {
            logger.info("Getting query params for URL {}", urlString);
            url = new URL(urlString);
            String query = url.getQuery();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
            return query_pairs;
        } catch (MalformedURLException e) {
            logger.error("Could not get query params for URL {}", urlString);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("Could not get query params for URL {}", urlString);
            e.printStackTrace();
        }
        return query_pairs;
    }

    public static String addParameter(String URL, String name, String value){
        int qpos = URL.indexOf('?');
        int hpos = URL.indexOf('#');
        char sep = qpos == -1 ? '?' : '&';
        String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
        return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg
                + URL.substring(hpos);
    }

    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException(uee);
        }
    }
}
