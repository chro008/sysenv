package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class ShortLinkInfo implements CommonConfigInfo {
    private String redisUri = "";
    private String ds = "";
    private String baseName = "";

    public String getRedisUri() {
        return redisUri;
    }

    public void setRedisUri(String redisUri) {
        this.redisUri = redisUri;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof ShortLinkInfo)) {
            return false;
        } else {
            ShortLinkInfo compare = (ShortLinkInfo) object;
            return redisUri.equals(compare.getRedisUri()) && ds.equals(compare.getDs())
                    && baseName.equals(compare.getBaseName());
        }
    }

    @Override
    public String toString() {
        return "redisUri:" + redisUri + ",ds:" + ds + ",baseName:" + baseName;
    }

}
