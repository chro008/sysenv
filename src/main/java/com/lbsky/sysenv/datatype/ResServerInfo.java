package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class ResServerInfo implements CommonConfigInfo {

    private String uri = "";
    private String clientIpLimits = "";
    private String serverIp = "";
    private String resStateFilePath = "";
    private String resConfPath = "";
    private String clientUris = "";

    public String getUri() {
        return uri;
    }

    protected void setUri(String uri) {
        this.uri = uri;
    }

    protected void setClientIpLimits(String clientIpLimits) {
        this.clientIpLimits = clientIpLimits;
    }

    public String getClientIpLimits() {
        return clientIpLimits;
    }

    protected void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getResStateFilePath() {
        return resStateFilePath;
    }

    public void setResStateFilePath(String resStateFilePath) {
        this.resStateFilePath = resStateFilePath;
    }

    public String getResConfPath() {
        return resConfPath;
    }

    public void setResConfPath(String resConfPath) {
        this.resConfPath = resConfPath;
    }

    public String getClientUris() {
        return clientUris;
    }

    public void setClientUris(String clientUris) {
        this.clientUris = clientUris;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof ResServerInfo)) {
            return false;
        } else {
            ResServerInfo compare = (ResServerInfo) object;
            return uri.equals(compare.getUri()) && clientIpLimits.equals(compare.getClientIpLimits())
                    && serverIp.equals(compare.getServerIp())
                    && resStateFilePath.equals(compare.getResStateFilePath())
                    && resConfPath.equals(compare.getResConfPath())
                    && clientUris.equals(compare.getClientUris());
        }
    }

    @Override
    public String toString() {
        return "uri:" + uri + ",clientIpLimits:" + clientIpLimits + ",serverIp:" + serverIp
                + ",resStateFilePath:" + resStateFilePath + ",resConfPath:" + resConfPath
                + ",clientUris:" + clientUris;
    }

}
