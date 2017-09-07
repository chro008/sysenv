package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class LocalStateInfo implements CommonConfigInfo {

    private String ip = "";
    private boolean onlineFlag = false;
    private boolean dlServerFlag = false;
    private boolean testFlag = false;
    private String largeEmailAttachBaseDir = "";

    public String getIp() {
        return ip;
    }

    protected void setIp(String ip) {
        this.ip = ip;
    }

    public boolean getOnlineFlag() {
        return onlineFlag;
    }

    protected void setOnlineFlag(boolean onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public boolean getDlServerFlag() {
        return dlServerFlag;
    }

    protected void setDlServerFlag(boolean dlServerFlag) {
        this.dlServerFlag = dlServerFlag;
    }

    public boolean getTestFlag() {
        return testFlag;
    }

    protected void setTestFlag(boolean testFlag) {
        this.testFlag = testFlag;
    }

    protected void setLargeEmailAttachBaseDir(String largeEmailAttachBaseDir) {
        this.largeEmailAttachBaseDir = largeEmailAttachBaseDir;
    }

    public String getLargeEmailAttachBaseDir() {
        return largeEmailAttachBaseDir;
    }

    @Override
    public boolean equals(Object info) {
        if (info == null || !(info instanceof LocalStateInfo)) {
            return false;
        } else {
            LocalStateInfo compare = (LocalStateInfo) info;
            return ip.equals(compare.getIp()) && onlineFlag == compare.getOnlineFlag()
                    && dlServerFlag == compare.getDlServerFlag() && testFlag == compare.getTestFlag()
                    && largeEmailAttachBaseDir.equals(compare.getLargeEmailAttachBaseDir());
        }
    }

    @Override
    public String toString() {
        return "ip:" + ip + ",onlineFlag:" + onlineFlag + ",dlServerFlag:" + dlServerFlag + ",testFlag:" + testFlag + ",largeEmailAttachBaseDir:" + largeEmailAttachBaseDir;
    }

}
