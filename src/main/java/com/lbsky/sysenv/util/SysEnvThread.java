package com.lbsky.sysenv.util;

import com.lbsky.sysenv.datatype.StateChangeItem;
import com.lbsky.sysenv.datatype.WebApp;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SysEnvThread extends Thread {

    private static File confFile;

    static SysEnvThread getInstance(File file) {
        confFile = file;
        return new SysEnvThread();
    }

    @Override
    public void run() {
        System.out.println("sysenv is ready checking!!!");
        long modifyTime;    //上次修改时间
        if (confFile.exists()) {
            modifyTime = confFile.lastModified();
        } else {
            throw new RuntimeException("config file (sysenv.xml) not exists!");
        }

        long tempTime;
        Map<WebApp, StateChangeCallback> registClients;
        boolean shouldRun = true;
        while (shouldRun) {
            tempTime = confFile.lastModified();
            if (tempTime != modifyTime) {
                modifyTime = tempTime;
                System.out.println("sysenv config file was changed! time:" + LocalDateTime.now().toString());

                registClients = ConfigManager.getChangeCallBackMap();
                if (registClients != null && registClients.size() > 0) {
                    List<StateChangeItem> changeItems = ConfigManager.getModifiedConfigList();
                    for (Map.Entry<WebApp, StateChangeCallback> entry : registClients.entrySet()) {
                        System.out.println(entry.getKey() + " is ready to run its sysenv change callback! ");
                        entry.getValue().onChange(changeItems);
                    }
                }
            } else {
                System.out.println("sysenv has no change happend! time:" + LocalDateTime.now().toString());   //测试用，后期删掉即可
            }

            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shouldRun = ConfigManager.getChangeCallBackMap().size() > 0;   //只有注册了callback 才需要跑这个
        }
    }

}
