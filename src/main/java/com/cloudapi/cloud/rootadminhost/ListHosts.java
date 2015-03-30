/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudapi.cloud.rootadminhost;

/**
 *
     * @author intern
 */
import org.w3c.dom.Document;

public class ListHosts {

    public static void main(String[] args) throws Exception {

        CloudStack client = HostRequest.factory();
        Document host_list = client.ListHosts(null);
        String elements[] = {"id", "hostid", "name", "podid", "projectid", "tags", "types", "zoneid"};
        ListHosts.printDocument(host_list, "//host", elements);
    }

    private static void printDocument(Document host_list, String host, String[] elements) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
