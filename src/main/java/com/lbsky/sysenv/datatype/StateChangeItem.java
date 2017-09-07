package com.lbsky.sysenv.datatype;

public class StateChangeItem {

    private CommonConfigInfo before;

    private CommonConfigInfo after;

    public StateChangeItem(CommonConfigInfo before, CommonConfigInfo after) {
        this.before = before;
        this.after = after;
    }

    public CommonConfigInfo getBefore() {
        return before;
    }

    protected void setBefore(CommonConfigInfo before) {
        this.before = before;
    }

    public CommonConfigInfo getAfter() {
        return after;
    }

    protected void setAfter(CommonConfigInfo after) {
        this.after = after;
    }

    @Override
    public String toString() {
        return "\nbefore is :" + before.toString() +
                "\nafter is :" + after.toString();
    }


}
