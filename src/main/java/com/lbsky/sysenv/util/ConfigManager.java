package com.lbsky.sysenv.util;


import com.lbsky.sysenv.GlobalEnv;
import com.lbsky.sysenv.datatype.CommonConfigInfo;
import com.lbsky.sysenv.datatype.StateChangeItem;
import com.lbsky.sysenv.datatype.WebApp;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigManager {

    private static Map<String, CommonConfigInfo> configInfoMap = new ConcurrentHashMap<>();
    private static File confFile;
    private static long lastModifiedTime;
    private static Map<WebApp, StateChangeCallback> stateChangeCallbackMap = new HashMap<>();
    private volatile static SysEnvThread thread;
    private static Map<String, String> xmlParamsMap = new ConcurrentHashMap<>();
    private static final String paramElementName = "properties";

    public static Map<String, CommonConfigInfo> getConfigInfoMap() {
        return configInfoMap;
    }

    static {    //调用该类的第一时间 将把配置文件的内容初始化到configManager的map中
        /*
        GlobalEnv.class.getProtectionDomain().getCodeSource().getLocation().getFile()
        如果直接执行.class文件那么会得到当前class的绝对路径。
        如果封装在jar包里面执行jar包那么会得到当前jar包的绝对路径。
         */
        String path = GlobalEnv.class.getProtectionDomain().getCodeSource().getLocation().getFile();//jar包 所在目录
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8"); // 转换处理中文及空格
        } catch (UnsupportedEncodingException e) {
            System.out.println("sysenv jar decode path exception:");
            e.printStackTrace();
        }
        path = new File(path).getParent() + "/sysenv.xml";
        File confFile = new File(path);

        if (!confFile.exists()) {   //如果在当前jar所在目录没有找到 sysenv.xml 将把jar中的配置文件（resources/sysenv.xml） 放到当前jar所在目录 并使用他
            System.out.println("sysenv can not find the config file in the location of this jar {" + path +
                    "}, it will use the default config file in the jar");

            InputStream is = GlobalEnv.class.getResourceAsStream("/sysenv.xml");
            confFile = getFileFromInputStream(is, path);

            init(confFile);
        } else {
            System.out.println("sysenv.xml path is " + confFile.getAbsolutePath());
            init(confFile);
        }
    }

    private static File getFileFromInputStream(InputStream stream, String path) {
        File confFile = new File(path);
        if (confFile.exists()) {
            return confFile;
        } else {
            try {
                boolean ok = confFile.createNewFile();
                if (!ok) {
                    System.out.println("sysenv create failed");
                } else {
                    OutputStream os = new FileOutputStream(confFile);
                    int bytesRead;
                    byte[] buffer = new byte[8192];
                    while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.close();
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return confFile;
    }


    //初始化
    private static synchronized void init(File config) {
        confFile = config;
        lastModifiedTime = confFile.lastModified();
        initConfigParamsMap();
        initConfigInfoMap();
    }

    //启动一个线程负责 监测 配置文件的变化
    private static void startThread() {
        if (thread == null) {
            thread = SysEnvThread.getInstance(confFile);
        }
        thread.start();
    }

    //客户端 注入到 sysenv 中，传入参数为系统的名称标识 以及 callback实例
    public synchronized static void registChangeCallBack(WebApp app, StateChangeCallback callback) {
        if (stateChangeCallbackMap.containsKey(app)) {
            System.out.println("sysenv: there has already exists an entry which app name is " + app.toString());
        }
        stateChangeCallbackMap.put(app, callback);
        if (stateChangeCallbackMap.size() == 1) {
            startThread();
        }
    }

    public synchronized static void removeChangeCallBack(WebApp webApp) {
        stateChangeCallbackMap.remove(webApp);
    }

    static Map<WebApp, StateChangeCallback> getChangeCallBackMap() {
        return stateChangeCallbackMap;
    }

    //获取根节点
    private static Element getConfigRootElement() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(confFile);
            return document.getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("sysenv parse xml(get config root element) failed.the exception is : ");
            e.printStackTrace();
        }
        return null;
    }

    //初始化 xml 里面 配置的全局参数
    private static void initConfigParamsMap() {
        Element root = getConfigRootElement();
        if (root != null) {
            NodeList nodes = root.getChildNodes();
            Node node;
            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);
                if (node instanceof Element && paramElementName.equals(node.getNodeName())) { //找到配置全局变量属性的标签
                    NodeList children = node.getChildNodes();
                    Node child;
                    List<Node> validChildren = new ArrayList<>();

                    for (int j = 0; j < children.getLength(); j++) {
                        child = children.item(j);
                        if (child instanceof Element) {
                            validChildren.add(child);
                        }
                    }

                    if (validChildren.size() > 0) { //该标签的 子标签名 是 param key  子标签value 是 param
                        String key, value;
                        for (Node aValidChildren : validChildren) {
                            key = aValidChildren.getNodeName();
                            value = aValidChildren.getTextContent().trim();
                            //System.out.println("find param entry, key is " + key + ", value is " + value);
                            xmlParamsMap.put(key, value);
                        }
                    }
                    break;
                }
            }
        } else {
            throw new RuntimeException("root node not exists error!");
        }
    }

    //初始化map对象，将配置信息放到map中
    private static void initConfigInfoMap() {
        Element root = getConfigRootElement();
        if (root != null) {
            NodeList nodes = root.getChildNodes();
            Node node;
            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);
                if (node instanceof Element && !node.getNodeName().equals(paramElementName)) {
                    putItemInfoToMap(node);
                }
            }
        } else {
            throw new RuntimeException("root node not exists error!");
        }
    }

    // node 是  root下的子节点
    private static void putItemInfoToMap(Node node) {
        CommonConfigInfo xmlConfigInfo = getConfigInfoFromElement(node);
        if (node == null || xmlConfigInfo == null) {
            System.out.println("node or xmlConfigInfo object may cause null pointer exception");
        } else {
            configInfoMap.put(node.getNodeName(), xmlConfigInfo);
        }
    }

    private static String getReplaceStrByParamMap(String val) {
        StringBuffer sBuffer = new StringBuffer();
        String regex = "\\$\\{[a-zA-Z_\\d]+}";      //匹配 ${paramKey}
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);
        String paramKey, paramValue;
        while (matcher.find()) {
            paramKey = matcher.group().substring(2, matcher.group().length() - 1);
            if (xmlParamsMap.containsKey(paramKey)) {
                paramValue = xmlParamsMap.get(paramKey);
                matcher.appendReplacement(sBuffer, paramValue);
            }
        }
        matcher.appendTail(sBuffer);

        return sBuffer.toString();
    }


    //通过某个节点 得到 java bean 对象  包括创建对象的实例 和 属性的注入
    private static CommonConfigInfo getConfigInfoFromElement(Node node) {
        String beanName = node.getNodeName();    //java bean的 className
        CommonConfigInfo commonConfigInfo = ObjectReflectUtil.getInfoInstanceByBeanName(beanName);
        NamedNodeMap attributes = node.getAttributes();

        Node attr;
        for (int i = 0; i < attributes.getLength(); i++) {   //设置该节点的属性
            attr = attributes.item(i);
            ObjectReflectUtil.setObjectVal(commonConfigInfo, attr.getNodeName(), getReplaceStrByParamMap(attr.getNodeValue()));
        }


        NodeList children = node.getChildNodes();
        Node child;
        List<Node> validChildren = new ArrayList<>();

        for (int i = 0; i < children.getLength(); i++) {
            child = children.item(i);
            if (child instanceof Element) {
                validChildren.add(child);
            }
        }

        if (validChildren.size() > 0) { //设置该节点 的 子节点属性
            for (Node aValidChildren : validChildren) {
                CommonConfigInfo childInfo = getConfigInfoFromElement(aValidChildren);
                ObjectReflectUtil.setObjectVal(commonConfigInfo, aValidChildren.getNodeName(), childInfo);
            }
        }

        return commonConfigInfo;
    }

    //通过elementName 获取 对象
    private static CommonConfigInfo getConfigInfoFromXMLByName(String elementName) {
        Element root = getConfigRootElement();
        NodeList children;
        if (root != null) {
            children = root.getChildNodes();
            Node child = null;
            for (int i = 0; i < children.getLength(); i++) {
                if (elementName.equals(children.item(i).getNodeName())) {
                    child = children.item(i);
                    break;
                }
            }

            if (child == null) {
                System.out.println("can not get info from elementName->" + elementName);
                return ObjectReflectUtil.getInfoInstanceByBeanName(elementName);
            } else {
                return getConfigInfoFromElement(child);
            }
        } else {
            return ObjectReflectUtil.getInfoInstanceByBeanName(elementName);
        }
    }

    //从全局map中获取相应信息
    private static CommonConfigInfo getConfigInfoFromMapByName(String keyName) {
        if (configInfoMap.containsKey(keyName)) {
            return configInfoMap.get(keyName);
        } else {
            return ObjectReflectUtil.getInfoInstanceByBeanName(keyName);
        }
    }

    private static synchronized void triggerUpdate() {
        if (lastModifiedTime != confFile.lastModified()) {
            new Thread(() -> {
                getModifiedConfigList();
            }).start();
        }
    }

    //获取修改的配置集合， 集合的每个元素包含2个，分别是修改之前的对象，修改之后的对象
    static List<StateChangeItem> getModifiedConfigList() {
        List<StateChangeItem> lists = new ArrayList<>();
        CommonConfigInfo xmlConfig; //通过配置文件获取的对象
        CommonConfigInfo mapConfig; //全局map中存储的对象
        String name;
        if (configInfoMap != null) {
            for (Map.Entry<String, CommonConfigInfo> entry : configInfoMap.entrySet()) {
                name = entry.getKey();
                xmlConfig = getConfigInfoFromXMLByName(name);
                mapConfig = getConfigInfoFromMapByName(name);

                if (!mapConfig.equals(xmlConfig)) {
                    System.out.println("update config which called " + name);
                    lists.add(new StateChangeItem(mapConfig, xmlConfig));
                    configInfoMap.put(name, xmlConfig);
                }
            }
        } else {
            System.out.println("config info map doesn't init");
        }
        lastModifiedTime = confFile.lastModified();
        return lists;
    }


    public static <T extends CommonConfigInfo> T getConfigInfoFromMapByClass(Class<T> classz) {
        if (stateChangeCallbackMap.size() == 0 && lastModifiedTime != confFile.lastModified()) {
            triggerUpdate();
        }
        String beanName = classz.getSimpleName();
        return (T) getConfigInfoFromMapByName(beanName);
    }
}
