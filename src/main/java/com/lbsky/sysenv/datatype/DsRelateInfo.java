package com.lbsky.sysenv.datatype;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class DsRelateInfo implements CommonConfigInfo {
    private String siteBaseDs = "";
    private String logBaseDs = "";
    private String memberLogBaseDs = "";
    private String saSiteBaseDs = "";

    public String getSiteBaseDs() {
        return siteBaseDs;
    }

    protected void setSiteBaseDs(String siteBaseDs) {
        this.siteBaseDs = siteBaseDs;
    }

    public String getLogBaseDs() {
        return logBaseDs;
    }

    protected void setLogBaseDs(String logBaseDs) {
        this.logBaseDs = logBaseDs;
    }

    public String getMemberLogBaseDs() {
        return memberLogBaseDs;
    }

    protected void setMemberLogBaseDs(String memberLogBaseDs) {
        this.memberLogBaseDs = memberLogBaseDs;
    }

    public String getSaSiteBaseDs() {
        return saSiteBaseDs;
    }

    protected void setSaSiteBaseDs(String saSiteBaseDs) {
        this.saSiteBaseDs = saSiteBaseDs;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof DsRelateInfo)) {
            return false;
        } else {
            DsRelateInfo compare = (DsRelateInfo) object;
            return siteBaseDs.equals(compare.getSiteBaseDs()) && logBaseDs.equals(compare.getLogBaseDs())
                    && memberLogBaseDs.equals(compare.getMemberLogBaseDs())
                    && saSiteBaseDs.equals(compare.getSaSiteBaseDs());
        }
    }

    @Override
    public String toString() {
        return "siteBaseDs:" + siteBaseDs + ",logBaseDs:" + logBaseDs
                + ",memberLogBaseDs:" + memberLogBaseDs + ",saSiteBaseDs:" + saSiteBaseDs;
    }
}
