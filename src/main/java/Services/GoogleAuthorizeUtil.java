package Services;

import Constants.ConstantsEnum;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleAuthorizeUtil {
    /*public static Credential authorize() throws GeneralSecurityException, IOException {
        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream(ConstantsEnum.SHEETS_CLIENT_SECRET.getConstantValue().toString());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes).setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        return credential;
    }
*/
        public static GoogleCredential authorize() throws GeneralSecurityException, IOException {
            InputStream in = Services.GoogleAuthorizeUtil.class.getResourceAsStream(ConstantsEnum.SHEETS_CLIENT_SECRET.getConstantValue().toString());
            GoogleCredential googleCredential = GoogleCredential.fromStream(in).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
            return googleCredential;
        }

}
