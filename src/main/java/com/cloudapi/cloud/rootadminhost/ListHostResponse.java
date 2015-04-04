/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudapi.cloud.rootadminhost;

import java.util.List;

/**
 *
 * @author intern
 */
public class ListHostResponse {

    private List<AddHostResponse> hosts;

    public List<AddHostResponse> getHosts() {
        return hosts;
    }

    public void setHosts(List<AddHostResponse> hosts) {
        this.hosts = hosts;
    }

}
