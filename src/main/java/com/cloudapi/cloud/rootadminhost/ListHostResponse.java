/*
 *This class uses getter setter properties for listing the hosts.
 *
 */
package com.cloudapi.cloud.rootadminhost;

import java.util.List;

/**
 *
 * @author Mona<mohanapriya0713@gmail.com>
 */
public class ListHostResponse {

    //gives the host lists.
    private List<AddHostResponse> hosts;

    public List<AddHostResponse> getHosts() {
        return hosts;
    }

    public void setHosts(List<AddHostResponse> hosts) {
        this.hosts = hosts;
    }

}
