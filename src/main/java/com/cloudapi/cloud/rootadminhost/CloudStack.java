/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudapi.cloud.rootadminhost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.Document;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.codehaus.plexus.digest.Digester;

/**
 *
 * @author Mona<mohanapriya0713@gmail.com>
 */
public class CloudStack {

    //enter the username
    private static String username;

    //enter the password for validation whether he is a authorized or unauthorized users
    private static String password;

    //secret key for generating signature
    private String secret;

    //apikey for accessing the user accounts.
    private static String apikey;

    CloudStack(String new_secret, String new_apikey) {

        //value of the secret key.
        secret = "1hfcAFNy-SwRkywTh38_nMNSsFuNKvaTCAszgMzlJE-iVNSKqMLKqp878OpHW4NGRpDe6EJZCezmeN1U8ybPuA";

        //value of the api key.
        apikey = "Sfs-bdpx5Eu_oYGrOGavwMPudoJYcTtNEbohknlMaq8TxuKKxJulhzG9s7cc3aWnPVBfxn9BEVGRwkWxjbMDcg";
    }

//    public static void main(String[] args) {
//        String output = getUrlContents("http://demo.fogpanel.com:8080/client");
//        System.out.println(output);
//    }
//
//    //passing the desired url.
//    private static String getUrlContents(String theUrl) {
//        StringBuilder content = new StringBuilder();
//
//        // establishing a connection for url
//        try {
//            // create a url object
//            URL url = new URL(theUrl);
//
//            // create a urlconnection object
//            URLConnection urlConnection = url.openConnection();
//
//            // wrap the urlconnection in a bufferedreader
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//
//            String line;
//
//            // read from the urlconnection via the bufferedreader
//            while ((line = bufferedReader.readLine()) != null) {
//                StringBuilder output = content.append(line).append("\n");
//            }
//            bufferedReader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return content.toString();
//    }

   org.w3c.dom.Document ListHosts(Object object) {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

    class login {

        public Document login(String username, String password, HashMap<String, String> optional) throws Exception {
            LinkedList<NameValuePair> arguments = newQueryValues("login", optional);
               arguments.add(new NameValuePair("username", username));
            arguments.add(new NameValuePair("password", password));
            return Request(arguments);
        }

        private LinkedList<NameValuePair> newQueryValues(String command, HashMap<String, String> optional) {
            LinkedList<NameValuePair> queryValues = new LinkedList<NameValuePair>();
            queryValues.add(new NameValuePair("command", command));
            //String apikey = "Sfs-bdpx5Eu_oYGrOGavwMPudoJYcTtNEbohknlMaq8TxuKKxJulhzG9s7cc3aWnPVBfxn9BEVGRwkWxjbMDcg";
            queryValues.add(new NameValuePair("apiKey", apikey));
                     if (optional != null) {
                Iterator optional_it = optional.entrySet().iterator();
                while (optional_it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) optional_it.next();
                    queryValues.add(new NameValuePair((String) pairs.getKey(), (String) pairs.getValue()));
                }
            }
            return queryValues;
        }

        private String sign_request(LinkedList<NameValuePair> queryValues) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
            Collections.sort(queryValues, new Comparator<NameValuePair>() {
                public int compare(NameValuePair o1, NameValuePair o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            String query_string = EncodingUtil.formUrlEncode(queryValues.toArray(new NameValuePair[0]), "UTF-8");
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            byte[] digest = mac.doFinal(query_string.toLowerCase().getBytes());
            return DatatypeConverter.printBase64Binary(digest);
        }

        private Document Request(LinkedList<NameValuePair> queryValues) throws Exception {
            HttpMethod method;
            method = makeHttpGet(queryValues);
            return executeGet(method);
        }

        private HttpMethod makeHttpGet(LinkedList<NameValuePair> queryValues) throws Exception {
            String query_signature = sign_request(queryValues);
            queryValues.add(new NameValuePair("signature", query_signature));
            HttpMethod method = new GetMethod("http://demo.fogpanel.com:88080/client");
            method.setFollowRedirects(true);
            method.setQueryString(queryValues.toArray(new NameValuePair[0]));
            return method;

        }

        private Document executeGet(HttpMethod method) throws HttpException, IOException, Exception {
            HttpClient client = new HttpClient();
            Document response = null;
            client.executeMethod(method);
// System.out.println(method.getResponseBodyAsString());
            response = handleResponse(method.getResponseBodyAsStream());
//clean up the connection resources
            method.releaseConnection();
            return response;
        }

        public class CloudStackException extends Exception {

            CloudStackException(String errorcode, String errortext) {
                super(errorcode + ": " + errortext);
            }
        }

        private Document handleResponse(InputStream ResponseBody) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException, CloudStackException, XPathExpressionException {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(ResponseBody);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            try {
                XPathExpression error_code_path = xpath.compile("/*/errorcode/text()");
                XPathExpression error_text_path = xpath.compile("/*/errortext/text()");
                String error_code = (String) error_code_path.evaluate(doc, XPathConstants.STRING);
                String error_text = (String) error_text_path.evaluate(doc, XPathConstants.STRING);
                if (error_code.length() > 0) {
                    throw new CloudStackException(error_code, error_text);
                }
            } catch (XPathExpressionException e) {
// ignore, should never happen
            }
            return doc;
        }

        // Section: Logout
        public Document logout() throws Exception {
            LinkedList<NameValuePair> arguments = newQueryValues("logout", null);
            return Request(arguments);
        }

    }

}
