import Services.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import org.junit.BeforeClass;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleSheetsLiveTest {
    private static Sheets sheetsService;
    private static String SPREADSHEET_ID = "";

    @BeforeClass
    public static void setup() throws GeneralSecurityException, IOException {
        sheetsService = SheetsServiceUtil.getSheetsService();
    }
}
