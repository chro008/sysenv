package com.lbsky.sysenv.util;

import com.lbsky.sysenv.datatype.*;

import java.util.List;

public class DefaultStateChangeCallback implements StateChangeCallback {

    @Override
    public void onChange(List<StateChangeItem> list) {
        CommonConfigInfo changedObject;
        for (StateChangeItem item : list) {
            changedObject = item.getBefore();

            if (changedObject instanceof LocalStateInfo) {
                onChangeLocalState(item);
            } else if (changedObject instanceof JdbcDsInfo) {
                onChangeJdbc(item);
            } else if (changedObject instanceof EmailRelateInfo) {
                onChangeEmailRelate(item);
            } else if (changedObject instanceof SsoRelateInfo) {
                onChangeSsoRelate(item);
            } else if (changedObject instanceof ResServerInfo) {
                onChangeResServer(item);
            } else if (changedObject instanceof ExportServerInfo) {
                onChangeExportServer(item);
            } else if (changedObject instanceof HotMapServerInfo) {
                onChangeHotMapServer(item);
            } else if (changedObject instanceof WebProjectInfo) {
                onChangeWebProject(item);
            }

        }
    }

    protected void onChangeLocalState(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    protected void onChangeJdbc(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    protected void onChangeEmailRelate(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    protected void onChangeSsoRelate(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    protected void onChangeResServer(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    protected void onChangeExportServer(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    protected void onChangeHotMapServer(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    protected void onChangeWebProject(StateChangeItem target) {
        onChangeDefautCallback(target);
    }

    private void onChangeDefautCallback(StateChangeItem target) {
        System.out.println(target.toString());
    }

}
