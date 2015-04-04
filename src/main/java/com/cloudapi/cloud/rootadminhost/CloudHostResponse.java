/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudapi.cloud.rootadminhost;

import java.util.HashMap;
import java.util.LinkedList;
import org.apache.commons.httpclient.NameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Mona<mohanapriya0713@gmail.com>
 */
public class CloudHostResponse {

    private CloudStack client;

    public CloudHostResponse(CloudStack client) {
        this.client = client;
    }
    /**
     * Lists hosts.
     *     
     * @param optional
     * @return
     * @throws Exception
     */
    public ListHostResponse listHosts(HashMap<String, String> optional)throws Exception {
        LinkedList<NameValuePair> arguments = client.newQueryValues("listHosts", optional);
        Document response= client.Request(arguments);
        return getListHostResponse(response);
    }

    /**
     * Converts XML document into ListHostsResponse object
     *     
     * @param doc
     * @return
     */
    private ListHostResponse getListHostResponse(Document doc) {
        ListHostResponse response = new ListHostResponse();
        NodeList list = doc.getElementsByTagName("host");
        LinkedList<AddHostResponse> hosts = new LinkedList<AddHostResponse>();
        if (list.getLength() > 0) {
            for (int index = 0; index < list.getLength(); index++) {
                Node hostNode = list.item(index);
                AddHostResponse host = new AddHostResponse();
                NodeList hostProperties = hostNode.getChildNodes();
                for (int childIndex = 0; childIndex < hostProperties.getLength(); childIndex++) {
                    Node property = hostProperties.item(childIndex);
                    if (property.getNodeName().equals("id")) {
                        host.setHostId(property.getTextContent());
                    } else if (property.getNodeName().equals("averageload")) {
                        host.setHostAverageLoad(property.getTextContent());
                    } else if (property.getNodeName().equals("capabilities")) {
                        host.setHostCapabilities(property.getTextContent());
                    } else if (property.getNodeName().equals("clusterid")) {
                        host.setHostClusterId(property.getTextContent());
                    } else if (property.getNodeName().equals("clustername")) {
                        host.setHostClusterName(property.getTextContent());
                    } else if (property.getNodeName().equals("clustertype")) {
                        host.setHostClusterType(property.getTextContent());
                    } else if (property.getNodeName().equals("cpuallocated")) {
                        host.setHostCpuAllocated(property.getTextContent());
                    } else if (property.getNodeName().equals("cpunumber")) {
                        host.setHostCpuNumber(property.getTextContent());
                    } else if (property.getNodeName().equals("cpuspeed")) {
                        host.setHostCpuSpeed(property.getTextContent());
                    } else if (property.getNodeName().equals("cpuused")) {
                        host.setHostCpuUsed(property.getTextContent());
                    } else if (property.getNodeName().equals("cpuwithoverprovisioning")) {
                        host.setHostCpuWithOverProvisioning(property.getTextContent());
                    } else if (property.getNodeName().equals("created")) {
                        host.setHostCreatedDate(property.getTextContent());
                    } else if (property.getNodeName().equals("disconnected")) {
                        host.setHostDisconnected(property.getTextContent());
                    } else if (property.getNodeName().equals("disksizeallocated")) {
                        host.setHostDiskSizeAllocated(property.getTextContent());
                    } else if (property.getNodeName().equals("disksizetotal")) {
                        host.setHostDiskSizeTotal(property.getTextContent());
                    } else if (property.getNodeName().equals("events")) {
                        host.setHostEvents(property.getTextContent());
                    } else if (property.getNodeName().equals("hasenoughcapacity")) {
                        host.setHasEnoughCapacity(property.getTextContent());
                    } else if (property.getNodeName().equals("hosttags")) {
                        host.setHostTags(property.getTextContent());
                    } else if (property.getNodeName().equals("hypervisor")) {
                        host.setHostHypervisor(property.getTextContent());
                    } else if (property.getNodeName().equals("hypervisorversion")) {
                        host.setHypervisorVersion(property.getTextContent());
                    } else if (property.getNodeName().equals("ipaddress")) {
                        host.setHostIpAddress(property.getTextContent());
                    } else if (property.getNodeName().equals("islocalstorageactive")) {
                        host.setIsLocalStorageActive(property.getTextContent());
                    } else if (property.getNodeName().equals("managementserverid")) {
                        host.setHostManagementServerId(property.getTextContent());
                    } else if (property.getNodeName().equals("memoryallocated")) {
                        host.setHostMemoryAllocated(property.getTextContent());
                    } else if (property.getNodeName().equals("memorytotal")) {
                        host.setHostMemoryTotal(property.getTextContent());
                    } else if (property.getNodeName().equals("memoryused")) {
                        host.setHostMemoryUsed(property.getTextContent());
                    } else if (property.getNodeName().equals("name")) {
                        host.setHostName(property.getTextContent());
                    } else if (property.getNodeName().equals("oscategoryid")) {
                        host.setHostOsCategoryId(property.getTextContent());
                    } else if (property.getNodeName().equals("oscategoryname")) {
                        host.setHostOsCategoryName(property.getTextContent());
                    } else if (property.getNodeName().equals("podid")) {
                        host.setHostPodId(property.getTextContent());
                    } else if (property.getNodeName().equals("podname")) {
                        host.setHostPodName(property.getTextContent());
                    } else if (property.getNodeName().equals("removed")) {
                        host.setHostRemovedDate(property.getTextContent());
                    } else if (property.getNodeName().equals("resourcestate")) {
                        host.setHostResourceState(property.getTextContent());
                    } else if (property.getNodeName().equals("state")) {
                        host.setHostSate(property.getTextContent());
                    } else if (property.getNodeName().equals("suitableformigration")) {
                        host.setSuitableForMigration(property.getTextContent());
                    } else if (property.getNodeName().equals("type")) {
                        host.setHostType(property.getTextContent());
                    } else if (property.getNodeName().equals("version")) {
                        host.setHostVersion(property.getTextContent());
                    } else if (property.getNodeName().equals("zoneid")) {
                        host.setHostZoneId(property.getTextContent());
                    } else if (property.getNodeName().equals("zonename")) {
                        host.setHostZoneName(property.getTextContent());
                    } else if (property.getNodeName().equals("jobid")) {
                        host.setJobId(property.getTextContent());
                    } else if (property.getNodeName().equals("jobstatus")) {
                        host.setJobStatus(property.getTextContent());
                    }
                }
                hosts.add(host);
                System.out.println(hosts);
            }
        }
        response.setHosts(hosts);
            return response;
    }

}
