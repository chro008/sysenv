package com.lbsky.sysenv.datatype;


/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class HotMapServerInfo implements CommonConfigInfo {

    private String uri = "";

    public String getUri() {
        return uri;
    }

    protected void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof HotMapServerInfo && uri.equals(((HotMapServerInfo) object).getUri());
    }

    @Override
    public String toString() {
        return "uri:" + uri;
    }
}
