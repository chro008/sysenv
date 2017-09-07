package com.lbsky.sysenv;

import com.lbsky.sysenv.datatype.*;
import com.lbsky.sysenv.util.ConfigManager;

/**
 * Created by Administrator on 2017/5/19 0019.
 */
public class GlobalEnv {

    public static LocalStateInfo getLocalStateInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(LocalStateInfo.class);
    }

    public static boolean isWindows() {
        return System.getProperty("os").toLowerCase().contains("windows");
    }

    public static SsoRelateInfo getSsoRelateInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(SsoRelateInfo.class);
    }

    public static JdbcDsInfo getJdbcDsInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(JdbcDsInfo.class);
    }

    public static EmailRelateInfo getEmailRelateInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(EmailRelateInfo.class);
    }

    public static WebProjectInfo getWebProjectInfo(WebApp webApp) {
        WebProjectInfoList list = ConfigManager.getConfigInfoFromMapByClass(WebProjectInfoList.class);
        for (WebProjectInfo info : list.getWebProjectInfos()) {       //约定 WebProjectInfo的name 和 webapp对象 名称一致
            if (info.getName().equalsIgnoreCase(webApp.toString())) {
                return info;
            }
        }
        return new WebProjectInfo();
    }

    public static ResServerInfo getResServerInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(ResServerInfo.class);
    }

    public static ExportServerInfo getExportServerInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(ExportServerInfo.class);
    }

    public static HotMapServerInfo getHotMapServerInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(HotMapServerInfo.class);
    }

    public static ADRelateInfo getADRelateInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(ADRelateInfo.class);
    }

    public static MonitorSDKRelateInfo getMonitorSDKRelateInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(MonitorSDKRelateInfo.class);
    }

    public static AppPushRelateInfo getAppPushRelateInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(AppPushRelateInfo.class);
    }

    public static DsRelateInfo getDsRelateInfo() {
        return ConfigManager.getConfigInfoFromMapByClass(DsRelateInfo.class);
    }
}
