/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudapi.cloud.rootadminhost;

import java.util.LinkedList;
import javax.swing.text.Document;
import org.apache.commons.httpclient.NameValuePair;

/**
 *
 * @author Mona<mohanapriya0713@gmail.com>
 */
public class HostResponse {

        // adding identity for query
        public Document id(String id) throws Exception {
        LinkedList<NameValuePair> arguments = newQueryValues("id", null);
        arguments.add(new NameValuePair("id", id));
        return Request(arguments);
    }
        
        

    private LinkedList<NameValuePair> newQueryValues(String id, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Document Request(LinkedList<NameValuePair> arguments) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
