package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class SsoRelateInfo implements CommonConfigInfo {

    private String serverUri = "";
    private String redisUri = "";

    public String getServerUri() {
        return serverUri;
    }

    protected void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public String getRedisUri() {
        return redisUri;
    }

    protected void setRedisUri(String redisUri) {
        this.redisUri = redisUri;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof SsoRelateInfo)) {
            return false;
        } else {
            SsoRelateInfo compare = (SsoRelateInfo) object;
            return serverUri.equals(compare.getServerUri()) && redisUri.equals(compare.getRedisUri());
        }
    }

    @Override
    public String toString() {
        return "serverUri:" + serverUri + ",redisUri:" + redisUri;
    }
}
