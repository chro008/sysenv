package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class EmailRelateInfo implements CommonConfigInfo {

    private String errorBaseDir = "";
    private String globalEmail = "";
    private EmailHostList emailHostList = new EmailHostList();

    public String getErrorBaseDir() {
        return errorBaseDir;
    }

    protected void setErrorBaseDir(String errorBaseDir) {
        this.errorBaseDir = errorBaseDir;
    }

    public String getGlobalEmail() {
        return globalEmail;
    }

    protected void setGlobalEmail(String globalEmail) {
        this.globalEmail = globalEmail;
    }

    public com.lbsky.sysenv.datatype.EmailHostList getEmailHostList() {
        return emailHostList;
    }

    protected void setEmailHostList(com.lbsky.sysenv.datatype.EmailHostList emailHostList) {
        this.emailHostList = emailHostList;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof EmailRelateInfo)) {
            return false;
        } else {
            EmailRelateInfo compare = (EmailRelateInfo) object;
            return errorBaseDir.equals(compare.getErrorBaseDir()) && globalEmail.equals(compare.getGlobalEmail()) &&
                    emailHostList.equals(compare.getEmailHostList());
        }
    }

    @Override
    public String toString() {
        return "errorBaseDir:" + errorBaseDir + ",globalEmail:" + globalEmail + "," + emailHostList.toString();
    }
}
