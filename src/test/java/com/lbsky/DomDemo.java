package com.lbsky;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomDemo {

    public static void main(String[] args) {
        File confFile = new File("D:\\Workspaces\\IntelliJ\\99click-sf\\sz-sysenv\\src\\main\\resources\\sysenv.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element root = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(confFile);
            root = document.getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("sysenv parse xml(get config root element) failed.the exception is : ");
            e.printStackTrace();
        }
        if (root == null) {
            System.out.println("config is null");
        } else {
            NodeList nodes = root.getChildNodes();
            Node node;
            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);
                if (node instanceof Element) {
                    System.out.println(node.getNodeName());
                }


               /* if (node.getNodeName().equals("LocalStateInfo")) {

                    NamedNodeMap attrs = node.getAttributes();
                    for (int j = 0; j < attrs.getLength(); j++) {
                        System.out.println(attrs.item(j).getNodeName());
                    }
                }*/
            }
        }
    }

}
