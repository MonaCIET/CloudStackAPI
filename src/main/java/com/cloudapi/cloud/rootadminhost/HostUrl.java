/**
 *
 * .*
 */
package com.cloudapi.cloud.rootadminhost;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * @author Mona <mohanapriya0713@gmail.com>
 */
public class HostUrl {

    public static void main(String[] args) {
        // Host
        String host = "http//";

        // Fully qualified URL with http(s)://host:port
        String Url = "demo.fogpanel.com/client";

        //username for login
        String userName = "test";

        //password for login
        String password = "testtest";

        //Secret key for generating the signature.
        String secretKey = "1hfcAFNy-SwRkywTh38_nMNSsFuNKvaTCAszgMzlJE-iVNSKqMLKqp878OpHW4NGRpDe6EJZCezmeN1U8ybPuA";

        String apiUrl = "" + host + "" + Url + "" + userName + "" + password + "";

        System.out.println(apiUrl);

        try {

            // To LowerCase all the parameters, URL encode each parameter value, and the sort the parameters in alphabetical order
            // if any parameters with a '&' as a value will cause this test client to fail since we are using '&' to delimit 
            // the string
            List sortedParams = new ArrayList();

            // ApiKey given for indiviual user 
            String apiKey = "Sfs-bdpx5Eu_oYGrOGavwMPudoJYcTtNEbohknlMaq8TxuKKxJulhzG9s7cc3aWnPVBfxn9BEVGRwkWxjbMDcg";
            sortedParams.add("apikey=" + apiKey);
            StringTokenizer st = new StringTokenizer(apiUrl, "&");
            while (st.hasMoreTokens()) {
                String paramValue = st.nextToken().toLowerCase();
                //  String param = paramValue.substring(0, paramValue.indexOf("="));
                //   String value = URLEncoder.encode(paramValue.substring(paramValue.indexOf("=") + 1, paramValue.length()), "UTF-8");
                //  sortedParams.add(param + "=" + value);
            }
            Collections.sort(sortedParams);
            System.out.println("Sorted Parameters: " + sortedParams);

            // Construct the sorted URL and sign and URL encode the sorted URL with secret key
            String sortedUrl = null;
            boolean first = true;
            for (Object param : sortedParams) {
                if (first) {
                    sortedUrl = (String) param;
                    first = false;
                } else {
                    sortedUrl = sortedUrl + "&" + param;
                }
            }
            System.out.println("sorted URL : " + sortedUrl);
            String encodedSignature = signRequest(sortedUrl, secretKey);

            // Construct the final url 
            String finalUrl = host + "?" + apiUrl + "&apiKey=" + apiKey + "&signature=" + encodedSignature;
            System.out.println("final URL : " + finalUrl);

            // Perform a HTTP GET on this URL to execute the command
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(finalUrl);
            int responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                // SUCCESS!
                System.out.println("Successfully executed command");
            } else {
                // FAILED!
                System.out.println("Unable to execute command with response code: " + responseCode);
            }

        } catch (Throwable t) {
            System.out.println(t);
        }
    }

    /**
     * @param request
     * @param key
     * @return
     */
    public static String signRequest(String request, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            mac.update(request.getBytes());
            byte[] encryptedBytes = mac.doFinal();
          //  return URLEncoder.encode(Base64.encodeBytes(encryptedBytes), "UTF-8");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

}