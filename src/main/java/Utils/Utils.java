package Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class Utils {
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
}
