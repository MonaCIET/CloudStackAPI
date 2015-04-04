/*
 * It access the root admin api by using apikey and generates response in the xml format.
 */
package com.cloudapi.cloud.rootadminhost;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.w3c.dom.Document;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Mona<mohanapriya0713@gmail.com>
 */
public class CloudStack {

    //secret key for generating signature
    private String secret;

    //apikey for accessing the user accounts.
    private static String apikey;

    CloudStack(String new_secret, String new_apikey) {

        secret = "_ryH9nn-0M1zfVWkKOJOt_cwQWvQPdK8DleUB_IYZ2L3308k8nfcCkg1eKp1gPxC70JEeCpxd72l5ZOwipw7Mw";

        apikey = "F8xoPUX_t9Iokbi349rnPsdCfmHoRsi2xk1Llgac1KtVUZdITptw2O66Zu1EkZxMHPOS207pvp7mLlLRwSpRgA";
    }

    //this method lists the arguments in the list host.
    public Document listHosts(HashMap<String, String> optional) throws Exception {
        LinkedList<NameValuePair> arguments = newQueryValues("listHosts", optional);
        System.out.print(arguments);
        return Request(arguments);
    }

    //add new query values to the list.
    LinkedList<NameValuePair> newQueryValues(String command, HashMap<String, String> optional) {
        LinkedList<NameValuePair> queryValues = new LinkedList<>();
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

    //compare the command and apikey to authenticate to enter into the request Url.
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

    Document Request(LinkedList<NameValuePair> queryValues) throws Exception {
        HttpMethod method;
        method = makeHttpGet(queryValues);
        return executeGet(method);
    }

    //get the requested  url and establishes the connection.
    private HttpMethod makeHttpGet(LinkedList<NameValuePair> queryValues) throws Exception {
        String query_signature = sign_request(queryValues);
        queryValues.add(new NameValuePair("signature", query_signature));
        HttpMethod method = new GetMethod("http://demo.fogpanel.com:8080/client/api");
        method.setFollowRedirects(true);
        method.setQueryString(queryValues.toArray(new NameValuePair[0]));
        return method;

    }

    //executes the method using http client.
    private Document executeGet(HttpMethod method) throws HttpException, IOException, Exception {
        HttpClient client = new HttpClient();
        Document response = null;
        client.executeMethod(method);
        System.out.println("\n");
        System.out.println(method.getResponseBodyAsString());
        response = handleResponse(method.getResponseBodyAsStream());
        System.out.println("response: " + method.getPath());
        //System.out.println("response: " + method.getResponseBodyAsStream());
        method.releaseConnection();
        return response;
    }

     public class CloudStackException extends Exception {

        CloudStackException(String errorcode, String errortext) {
            super(errorcode + ": " + errortext);
        }
    }

    private Document handleResponse(InputStream ResponseBodyAsStream) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException, CloudStackException, XPathExpressionException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        // System.out.println(ResponseBodyAsStream);
        Document doc = (Document) dbFactory.newDocumentBuilder().parse(ResponseBodyAsStream);
        //  System.out.println(doc);
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

    public static Document xmlStringToDocument(String response) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(response));
        Document dom = (Document) builder.parse(is);
        return dom;
    }

    // Logout
    public Document logout() throws Exception {
        LinkedList<NameValuePair> arguments = newQueryValues("logout", null);
        return Request(arguments);
    }

}
