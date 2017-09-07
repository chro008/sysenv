package com.lbsky.sysenv.datatype;

import java.util.ArrayList;
import java.util.List;

public class EmailHost implements CommonConfigInfo {

    private String host = "";
    private int port = 0;
    private List<EmailServer> emailServers = new ArrayList<>();

    public String getHost() {
        return host;
    }

    protected void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    protected void setPort(int port) {
        this.port = port;
    }

    public List<EmailServer> getEmailServers() {
        return emailServers;
    }

    protected void setEmailServers(List<EmailServer> emailServers) {
        this.emailServers = emailServers;
    }

    protected void addEmailServer(EmailServer server) {
        this.emailServers.add(server);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof EmailHost)) {
            return false;
        } else {
            EmailHost compare = (EmailHost) object;
            List<EmailServer> compareList = compare.getEmailServers();
            if (emailServers.size() != compareList.size()) {
                return false;
            } else {
                boolean flag = true;
                for (int i = 0; i < emailServers.size(); i++) {
                    flag = flag && emailServers.get(i).equals(compareList.get(i));
                }
                return host.equals(compare.getHost()) && port == compare.getPort() && flag;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder serverStr = new StringBuilder();
        for (EmailServer server : emailServers) {
            serverStr.append("[").append(server.toString()).append("],");
        }
        String str;
        if (serverStr.toString().length() > 0) {
            str = "emailServers:{" + serverStr.toString().substring(0, serverStr.toString().length() - 1) + "}";
        } else {
            str = "emailServers size is 0";
        }
        return "host:" + host + ",port:" + port + "," + str;
    }
}
