/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudapi.cloud.rootadminhost;

import java.util.HashMap;
import org.w3c.dom.Document;

/**
 *
 * @author intern
 */
public class listHosts {

    public static void main(String[] args) throws Exception {

        CloudStack client = CLI.factory();
        HashMap<String, String> options = CLI.args_to_options(args);
        Document hosts_list = (Document)client.listHosts(options);
        String elements[] = {"id", "hypervisor", "ipaddress", "podid","oscategoryid","zoneid"};
        CLI.printDocument((Document) hosts_list, "//hosts", elements);
    }
}
