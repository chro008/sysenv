package com.lbsky.sysenv.datatype;

public class JdbcDsInfo implements CommonConfigInfo {
    public String ds = "";
    public String username = "";
    public String password = "";
    public String urlTemplate = "";

    public JdbcDsInfo() {
    }

    public JdbcDsInfo(String ds, String username, String password) {
        super();
        this.ds = ds;
        this.username = username;
        this.password = password;
    }

    public String getDs() {
        return ds;
    }

    protected void setDs(String ds) {
        this.ds = ds;
    }

    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    protected void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    @Override
    public boolean equals(Object jdbcDsInfo) {
        if (jdbcDsInfo == null || !(jdbcDsInfo instanceof JdbcDsInfo)) {
            return false;
        } else {
            JdbcDsInfo compare = (JdbcDsInfo) jdbcDsInfo;
            return ds.equals(compare.getDs()) && username.equals(compare.getUsername()) &&
                    password.equals(compare.getPassword()) && urlTemplate.equals(compare.getUrlTemplate());
        }
    }

    @Override
    public String toString() {
        return "username:" + username + ",password:" + password + ",ds:" + ds + ",urlTemplate:" + urlTemplate;
    }
}
