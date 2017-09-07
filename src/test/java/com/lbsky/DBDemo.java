package com.lbsky;

import com.lbsky.sysenv.GlobalEnv;
import com.lbsky.sysenv.datatype.WebApp;

public class DBDemo {

    public static void main(String[] args) {

        System.out.println("temp:" + GlobalEnv.getJdbcDsInfo().getUrlTemplate());

        /*Map<String, CommonConfigInfo> map = ConfigManager.getConfigInfoMap();

        for(Map.Entry<String, CommonConfigInfo> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":");
            System.out.println(entry.getValue().toString() + "\n\n");
        }*/

        System.out.println(GlobalEnv.getWebProjectInfo(WebApp.SITEFLOW).toString());

        /*try {
            Connection connection = DBToolkit.getConnection(null);
            PreparedStatement pstmt = connection.prepareStatement("select * from company where closed=0");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println(new String(rs.getString("cname").getBytes("ISO8859_1"), "gbk"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
