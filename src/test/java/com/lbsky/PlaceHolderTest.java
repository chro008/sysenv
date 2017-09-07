package com.lbsky;

import com.lbsky.sysenv.GlobalEnv;
import com.lbsky.sysenv.datatype.WebApp;

public class PlaceHolderTest {
    public static void main(String[] args) {
        System.out.println(GlobalEnv.getWebProjectInfo(WebApp.SITEFLOW).getServerUri());
    }
}
