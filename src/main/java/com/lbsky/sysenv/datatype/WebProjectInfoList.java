package com.lbsky.sysenv.datatype;

import java.util.ArrayList;
import java.util.List;

public class WebProjectInfoList implements CommonConfigInfo {

    private List<WebProjectInfo> webProjectInfos = new ArrayList<>();

    public List<com.lbsky.sysenv.datatype.WebProjectInfo> getWebProjectInfos() {
        return webProjectInfos;
    }

    protected void setWebProjectInfos(List<com.lbsky.sysenv.datatype.WebProjectInfo> list) {
        webProjectInfos = list;
    }

    protected void addWebProjectInfo(WebProjectInfo info) {
        this.webProjectInfos.add(info);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof WebProjectInfoList)) {
            return false;
        } else {
            List<WebProjectInfo> compare = ((WebProjectInfoList) object).getWebProjectInfos();
            if (webProjectInfos.size() != compare.size()) {
                return false;
            } else {
                boolean flag = true;
                for (int i = 0; i < webProjectInfos.size(); i++) {
                    flag = flag && webProjectInfos.get(i).equals(compare.get(i));
                }
                return flag;
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder hostStr = new StringBuilder();
        for (WebProjectInfo info : webProjectInfos) {
            hostStr.append("[").append(info.toString()).append("],");
        }
        String str;
        if (hostStr.toString().length() > 0) {
            str = "WebProjectInfoList:{" + hostStr.toString().substring(0, hostStr.toString().length() - 1) + "}";
        } else {
            str = "WebProjectInfoList size is 0";
        }
        return str;
    }
}
