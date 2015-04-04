/*
 * THe main class gets the expected response by creating factory class and executes the host response.
 */
package com.cloudapi.cloud.rootadminhost;

import java.util.HashMap;
import org.w3c.dom.Document;

/**
 *
 * @author Mona<mohanapriya0713@gmail.com>
 */
public class listHosts {

    public static void main(String[] args) throws Exception {

        CloudStack client = CLI.factory();
        HashMap<String, String> options = CLI.args_to_options(args);
        Document hosts_list = (Document)client.listHosts(options);
        System.out.println("Request paramenters");
        String elements[] = {"clusterid","details","hahost", "id", "keyword", "name", "podid","resourcestate","state","type","virtualmachineid", "zoneid"};
       // CLI.printDocument((Document) hosts_list, "//hosts", elements);
        CloudHostResponse hosts=new CloudHostResponse(client);
        Document response_list = client.listHosts(options);
        System.out.println("response paramenters");
        CLI.printDocument(response_list, "//host", elements);
    }
    
}
