package swatch.apps.com.kayatech.swatch.Networking;

import android.net.Uri;
import android.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.*;

public class NetworkUtils {



    //API URL
    final static String oMDBI_BASE_URL =
            DXDecryptorR35ueazy.decode("FhYP2w8C4ZjdtkvuCT40Tdj5L69LVo/YpcY58lg7aX4XJyFsK9GxPJroU433TyofswHJ");

    //Sarch by title parameter
    final static String PARAM_TITLE = DXDecryptorR35ueazy.decode("Cg==");



    /**
     * Builds the URL used to query oMDBI.
     *
     * @param movieTitleSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String movieTitleSearchQuery) {
        // COMPLETED (1) Fill in this method to build the proper movie query URL
        Uri builtUri = Uri.parse(oMDBI_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_TITLE, movieTitleSearchQuery)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter(DXDecryptorR35ueazy.decode("Ij46")/*"\\A"*/);

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
//created by Dingxiang Technologies Co., Ltd.
//please visit http://www.dingxiang-inc.com for more products.

class DXDecryptorR35ueazy {
    static String algo = "ARCFOUR";
    static String kp = "AIdWYGzZ21kchuQa";

    public static String decode(String s) {
        String str;
        String key = "mp3hCwRXeydHY+lzMsS9CQ==";
        try {
            Cipher rc4 = Cipher.getInstance(algo);
            Key kpk = new SecretKeySpec(kp.getBytes(), algo);
            rc4.init(Cipher.DECRYPT_MODE, kpk);
            byte[] bck = Base64.decode(key, Base64.DEFAULT);
            byte[] bdk = rc4.doFinal(bck);
            Key dk = new SecretKeySpec(bdk, algo);
            rc4.init(Cipher.DECRYPT_MODE, dk);
            byte[] bcs = Base64.decode(s, Base64.DEFAULT);
            byte[] byteDecryptedString = rc4.doFinal(bcs);
            str = new String(byteDecryptedString);
        } catch (Exception e) {
            str = "";
        }
        return str;
    }

}