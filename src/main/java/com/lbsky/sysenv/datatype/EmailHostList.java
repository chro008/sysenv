package com.lbsky.sysenv.datatype;

import java.util.ArrayList;
import java.util.List;

public class EmailHostList implements CommonConfigInfo {

    private List<EmailHost> emailHosts = new ArrayList<>();

    public List<EmailHost> getEmailHosts() {
        return emailHosts;
    }

    protected void setEmailHosts(List<EmailHost> list) {
        this.emailHosts = list;
    }

    protected void addEmailHost(EmailHost emailHost) {
        this.emailHosts.add(emailHost);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof EmailHostList)) {
            return false;
        } else {
            List<EmailHost> compare = ((EmailHostList) object).getEmailHosts();
            if (emailHosts.size() != compare.size()) {
                return false;
            } else {
                boolean flag = true;
                for (int i = 0; i < emailHosts.size(); i++) {
                    flag = flag && emailHosts.get(i).equals(compare.get(i));
                }
                return flag;
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder hostStr = new StringBuilder();
        for (EmailHost host : emailHosts) {
            hostStr.append("[").append(host.toString()).append("],");
        }
        String str;
        if (hostStr.toString().length() > 0) {
            str = "EmailHostList:{" + hostStr.toString().substring(0, hostStr.toString().length() - 1) + "}";
        } else {
            str = "EmailHostList size is 0";
        }
        return str;
    }
}
