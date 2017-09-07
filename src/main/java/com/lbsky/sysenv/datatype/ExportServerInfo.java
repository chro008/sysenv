package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class ExportServerInfo implements CommonConfigInfo {

    private String uri = "";
    private String ports = "";
    private String ipLimits = "";


    public String getUri() {
        return uri;
    }

    protected void setUri(String uri) {
        this.uri = uri;
    }

    public String getPorts() {
        return ports;
    }

    protected void setPorts(String ports) {
        this.ports = ports;
    }

    public String getIpLimits() {
        return ipLimits;
    }

    protected void setIpLimits(String ipLimits) {
        this.ipLimits = ipLimits;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof ExportServerInfo)) {
            return false;
        } else {
            ExportServerInfo compare = (ExportServerInfo) object;
            return uri.equals(compare.getUri()) && ipLimits.equals(compare.getIpLimits()) && ports.equals(compare.getPorts());
        }
    }

    @Override
    public String toString() {
        return "uri:" + uri + ",ipLimits:" + ipLimits + ",ports:" + ports;
    }

}
