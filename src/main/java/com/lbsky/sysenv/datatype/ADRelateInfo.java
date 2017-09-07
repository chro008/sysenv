package com.lbsky.sysenv.datatype;

public class ADRelateInfo implements CommonConfigInfo {
    private ShortLinkInfo shortLinkInfo;

    public void setShortLinkInfo(ShortLinkInfo shortLinkInfo) {
        this.shortLinkInfo = shortLinkInfo;
    }

    public ShortLinkInfo getShortLinkInfo() {
        return shortLinkInfo;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof ADRelateInfo &&
                shortLinkInfo.equals(((ADRelateInfo) object).getShortLinkInfo());
    }

    @Override
    public String toString() {
        return "shortLinkInfo:" + shortLinkInfo.toString();
    }
}
