package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class WebProjectInfo implements CommonConfigInfo {

    private String name = "";
    private String serverUri = "";
    private String dlServerUri = "";
    private String dlBaseDir = "";
    private String tempDir = "";
    private String resStateFilePath = "";
    private String resConfPath = "";

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getServerUri() {
        return serverUri;
    }

    protected void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public String getDlServerUri() {
        return dlServerUri;
    }

    protected void setDlServerUri(String dlServerUri) {
        this.dlServerUri = dlServerUri;
    }

    public String getDlBaseDir() {
        return dlBaseDir;
    }

    protected void setDlBaseDir(String dlBaseDir) {
        this.dlBaseDir = dlBaseDir;
    }

    public String getTempDir() {
        return tempDir;
    }

    protected void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public String getResStateFilePath() {
        return resStateFilePath;
    }

    protected void setResStateFilePath(String resStatePath) {
        this.resStateFilePath = resStatePath;
    }

    public String getResConfPath() {
        return resConfPath;
    }

    protected void setResConfPath(String resConfPath) {
        this.resConfPath = resConfPath;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof WebProjectInfo)) {
            return false;
        } else {
            WebProjectInfo compare = (WebProjectInfo) object;
            return name.equals(compare.getName()) && serverUri.equals(compare.getServerUri()) && dlServerUri.equals(compare.getDlServerUri()) &&
                    dlBaseDir.equals(compare.getDlBaseDir()) && tempDir.equals(compare.getTempDir()) &&
                    resStateFilePath.equals(compare.getResStateFilePath()) && resConfPath.equals(compare.getResConfPath());
        }
    }

    @Override
    public String toString() {
        return "name:" + name + ",serverUri:" + serverUri + ",dlServerUri:" + dlServerUri + ",dlBaseDir:" + dlBaseDir +
                ",tempDir:" + tempDir + ",resStateFilePath:" + resStateFilePath + ",resConfPath:" + resConfPath;
    }
}
