package myapp.genaratetoken;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;



import org.apache.commons.codec.digest.HmacUtils;





public class GenarateToken extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genarate_token);
    }

    public static final String PROVISION_TOKEN = "provision";
    private static final long EPOCH_SECONDS = 62167219200l;
    private static final String DELIM = "\0";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {



        String key = "rUlaMASgt1Byi4Kp3sKYDeQzo";
        String appID = "ApplicationID";
        String userName = "dsvrev";
        String vCardFilePath = null; // optional
        String expiresInSeconds = "10";



        if (key == null) {
            System.out.println("key not set");
            printUsageAndExit();
        } else if (appID == null) {
            System.out.println("appID not set");
            printUsageAndExit();
        } else if (userName == null) {
            System.out.println("userName not set");
            printUsageAndExit();
        } else if (expiresInSeconds == null) {
            System.out.println("expiresInSecs not set");
            printUsageAndExit();
        }

        // vCardFile is optional
        String vCard = "";


        try {
            System.out.println("Setting key           :  " + key);
            System.out.println("Setting appID         :  " + appID);
            System.out.println("Setting userName      :  " + userName);
            System.out.println("Setting vCardFile     :  " + vCardFilePath);
            System.out.println("Setting expiresInSecs :  " + expiresInSeconds);
            System.out.println("Generating Token...");
            System.out.println(generateProvisionToken(key, userName + "@" + appID, expiresInSeconds, vCard));
        } catch (NumberFormatException nfe) {
            System.out.println("Failed to parse expiresInSeconds: " + expiresInSeconds);
            System.exit(1);
        }
        System.exit(0);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String generateProvisionToken(String key, String jid, String expires, String vcard) throws NumberFormatException {
        String[] payloadl = {DELIM, PROVISION_TOKEN, jid, calculateExpiry(expires), vcard};
        String payload = TextUtils.join(",",payloadl);
        String[] payloadS = {DELIM, payload, HmacUtils.hmacSha384Hex(key, payload)};
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(
                (TextUtils.join(",",payloadS)).getBytes()
        ));
    }

    public static String calculateExpiry(String expires) throws NumberFormatException {
        long expiresLong = 0l;
        long currentUnixTimestamp = System.currentTimeMillis() / 1000;
        expiresLong = Long.parseLong(expires);
        return ""+(EPOCH_SECONDS + currentUnixTimestamp + expiresLong);
    }

    private static void printUsageAndExit() {
        System.out.println();
        System.out.println("This script will generate a provision login token from a developer key");
        System.out.println("Options:");
        System.out.println("--key           Developer key supplied with the developer account");
        System.out.println("--appID         ApplicationID supplied with the developer account");
        System.out.println("--userName      Username to generate a token for");
        System.out.println("--vCardFile     Path to the XML file containing a vCard for the user (optional)");
        System.out.println("--expiresInSecs Number of seconds the token will be valid");
        System.out.println();
        System.exit(1);
    }
}
