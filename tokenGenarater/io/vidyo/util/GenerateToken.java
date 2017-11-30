package io.vidyo.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GenerateToken {

    public static final String PROVISION_TOKEN = "provision";
    private static final long EPOCH_SECONDS = 62167219200l;
    private static final String DELIM = "\0";


    public static String generateProvisionToken(String key, String jid, String expires, String vcard) throws NumberFormatException {
        String payload = String.join(DELIM, PROVISION_TOKEN, jid, calculateExpiry(expires), vcard);
        return new String(Base64.encodeBase64(
                (String.join(DELIM, payload, HmacUtils.hmacSha384Hex(key, payload))).getBytes()
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

    public static void main(String[] args) {

        if (args.length == 0) {
            printUsageAndExit();
        }

        String key = null;
        String appID = null;
        String userName = null;
        String vCardFilePath = null; // optional
        String expiresInSeconds = null;

        for(String arg : args) {
            String[] parts = arg.split("=");
            if (parts.length > 0) {
                if ("--key".equals(parts[0])) {
                    if (parts.length > 1) {
                        key = parts[1];
                    }
                } else if ("--appID".equals(parts[0])) {
                    if (parts.length > 1) {
                        appID = parts[1];
                    }
                } else if ("--userName".equals(parts[0])) {
                    if (parts.length > 1) {
                        userName = parts[1];
                    }
                } else if ("--vCardFile".equals(parts[0])) {
                    if (parts.length > 1) {
                        vCardFilePath = parts[1];
                    }
                } else if ("--expiresInSecs".equals(parts[0])) {
                    if (parts.length > 1) {
                        expiresInSeconds = parts[1];
                    }
                }
            }
        }

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
        if (vCardFilePath != null) {
            File vCardFile = new File(vCardFilePath);
            if (!vCardFile.exists()) {
                System.out.println("File not found: " + vCardFilePath);
                System.exit(1);
            }
            try {
                vCard = new String(Files.readAllBytes(vCardFile.toPath()));
            } catch (IOException ioe) {
                System.out.println("Failed to read file: " + vCardFilePath);
                System.exit(1);
            }
        }

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
}
