package com.lbsky.sysenv.util;

import com.lbsky.sysenv.datatype.StateChangeItem;

import java.util.List;

public interface StateChangeCallback {

    void onChange(List<StateChangeItem> list);
}
