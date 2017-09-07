package com.lbsky.sysenv.datatype;

public class AppPushRelateInfo implements CommonConfigInfo {

    private String iOSCertDir = "";
    private String syncCertTokenFilePath = "";
    private String ds = "";

    public String getiOSCertDir() {
        return iOSCertDir;
    }

    protected void setiOSCertDir(String iOSCertDir) {
        this.iOSCertDir = iOSCertDir;
    }

    protected void setIOSCertDir(String iOSCertDir) {
        this.iOSCertDir = iOSCertDir;
    }

    public String getSyncCertTokenFilePath() {
        return syncCertTokenFilePath;
    }

    protected void setSyncCertTokenFilePath(String syncCertTokenPath) {
        this.syncCertTokenFilePath = syncCertTokenPath;
    }

    public String getDs() {
        return ds;
    }

    protected void setDs(String ds) {
        this.ds = ds;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof AppPushRelateInfo)) {
            return false;
        } else {
            AppPushRelateInfo compare = (AppPushRelateInfo) object;
            return iOSCertDir.equals(compare.getiOSCertDir()) && syncCertTokenFilePath.equals(compare.getSyncCertTokenFilePath()) &&
                    ds.equals(compare.getDs());
        }
    }

    @Override
    public String toString() {
        return "iOSCertDir:" + iOSCertDir + ",syncCertTokenFilePath:" + syncCertTokenFilePath + ",ds:" + ds;
    }
}
