package com.lbsky.sysenv;


import com.alibaba.druid.pool.DruidDataSource;
import com.lbsky.sysenv.datatype.JdbcDsInfo;
import com.lbsky.sysenv.datatype.LocalStateInfo;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DBToolkit {
    private static Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();
    private static Map<String, JdbcDsInfo> dsInfoMap = new HashMap<>();
    private static JdbcDsInfo init_dsInfo;
    private static String INIT_DS;
    private static boolean ONLINE;

    static {
        init_dsInfo = GlobalEnv.getJdbcDsInfo();
        INIT_DS = init_dsInfo.getDs();
        LocalStateInfo localStateInfo = GlobalEnv.getLocalStateInfo();
        ONLINE = !localStateInfo.getTestFlag();
        initDataSource();
        if (ONLINE) {
            initDataSourceTimer();
        }
    }

    public static Connection getConnection(String ds) {
        if (ds == null) {
            ds = INIT_DS;
        }
        try {
            if (ONLINE) {
                return dataSourceMap.get(ds).getConnection();
            } else {
                if (!dataSourceMap.containsKey(ds)) {
                    ds = INIT_DS;
                }
                return dataSourceMap.get(ds).getConnection();
            }
        } catch (Exception e) {
            System.out.println("dbtookit error ds:" + ds);
            e.printStackTrace();
        }
        return null;
    }

    private static void initDataSource() {
        Connection conn = getJdbcConnectoin(init_dsInfo);
        if (conn == null) {
            System.out.println("dbtookit error: can not connect default base!");
            return;
        } else {
            System.out.println("dbtookit base ds: " + init_dsInfo.ds);
            dataSourceMap.put(init_dsInfo.ds, getNewDataSource(init_dsInfo));
        }

        for (JdbcDsInfo dsInfo : getDsInfos()) {
            dataSourceMap.put(dsInfo.ds, getNewDataSource(dsInfo));
            dsInfoMap.put(dsInfo.ds, dsInfo);
        }
    }

    private static List<JdbcDsInfo> getDsInfos() {
        List<JdbcDsInfo> dsInfos = new ArrayList<>();
        try (
                Connection conn = dataSourceMap.containsKey(INIT_DS) ? getConnection(INIT_DS) : getJdbcConnectoin(init_dsInfo);
                PreparedStatement pstmt = conn.prepareStatement("select ds,username,password from sysdsinfo");
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                dsInfos.add(new JdbcDsInfo(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return dsInfos;
    }

    private static void initDataSourceTimer() {
        Timer dataSourceTimer = new Timer();
        dataSourceTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Set<String> dsSet = new HashSet<>();
                for (JdbcDsInfo dsInfo : getDsInfos()) {
                    dsSet.add(dsInfo.ds);
                    if (dsInfoMap.containsKey(dsInfo.ds)) {
                        if (!dsInfo.equals(dsInfoMap.get(dsInfo.ds))) {
                            dataSourceMap.put(dsInfo.ds, getNewDataSource(dsInfo));
                            dsInfoMap.put(dsInfo.ds, dsInfo);
                            System.out.println("dbtookit datasource update ds: " + dsInfo.ds);
                        }
                    } else {
                        dataSourceMap.put(dsInfo.ds, getNewDataSource(dsInfo));
                        dsInfoMap.put(dsInfo.ds, dsInfo);
                        System.out.println("dbtookit datasource new ds: " + dsInfo.ds);
                    }
                }
                Set<String> tempSet = new HashSet<>();
                for (String t : dsInfoMap.keySet()) {
                    if (!dsSet.contains(t) && !t.equals(INIT_DS)) {
                        tempSet.add(t);
                    }
                }
                for (String t : tempSet) {
                    dataSourceMap.remove(t);
                    dsInfoMap.remove(t);
                    System.out.println("dbtookit datasource remove ds: " + t);
                }

            }
        }, 1000 * 60 * 3, 1000 * 60 * 60 * 2);
    }

    private static DataSource getNewDataSource(JdbcDsInfo dsInfo) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(getUrl(dsInfo));
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(dsInfo.username);
        dataSource.setPassword(dsInfo.password);
        dataSource.setMaxActive(300);
        dataSource.setMaxWait(10000);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(10000);
        return dataSource;
    }

    private static Connection getJdbcConnectoin(JdbcDsInfo dsInfo) {
        Connection conn = null;
        try {
            loadDriver(DBToolkit.class.getClassLoader(), "com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(getUrl(dsInfo), dsInfo.username, dsInfo.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    private static String getUrl(JdbcDsInfo dsInfo) {
        String tempUrl = GlobalEnv.getJdbcDsInfo().getUrlTemplate();
        tempUrl = MessageFormat.format(tempUrl, dsInfo.ds);
        return tempUrl;
    }

    private static boolean loadDriver(ClassLoader classLoader, String driverClassName) {
        try {
            Class<?> loadedClass = classLoader.loadClass(driverClassName);

            if (!Driver.class.isAssignableFrom(loadedClass)) {
                return false;
            }

            @SuppressWarnings("unchecked")
            Class<Driver> driverClass = (Class<Driver>) loadedClass;
            Constructor<Driver> driverConstructor = driverClass.getConstructor();

            // make Constructor accessible if it is private
            boolean isConstructorAccessible = driverConstructor.isAccessible();
            if (!isConstructorAccessible) {
                driverConstructor.setAccessible(true);
            }

            try {
                Driver driver = driverConstructor.newInstance();
                DriverManager.registerDriver(driver);
            } finally {
                driverConstructor.setAccessible(isConstructorAccessible);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

