package com.lbsky.sysenv.datatype;

public class EmailServer implements CommonConfigInfo {

    private String username = "";
    private String password = "";

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

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof EmailServer)) {
            return false;
        } else {
            EmailServer compare = (EmailServer) object;
            return username.equals(compare.getUsername()) && password.equals(compare.getPassword());
        }
    }

    @Override
    public String toString() {
        return "username:" + username + ",password:" + password;
    }
}
