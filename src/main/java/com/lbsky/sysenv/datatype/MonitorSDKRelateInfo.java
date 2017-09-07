package com.lbsky.sysenv.datatype;

public class MonitorSDKRelateInfo implements CommonConfigInfo {

    private String confFilePath = "";

    public String getConfFilePath() {
        return confFilePath;
    }

    protected void setConfFilePath(String path) {
        this.confFilePath = path;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof MonitorSDKRelateInfo && confFilePath.equals(((MonitorSDKRelateInfo) object).getConfFilePath());
    }

    @Override
    public String toString() {
        return "confFilePath:" + confFilePath;
    }
}
