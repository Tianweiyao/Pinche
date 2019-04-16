package com.hodehtml.demo.utils;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

public class XMLUtils {
    private static Logger logger = Logger.getLogger(XMLUtils.class);

    private boolean compress;
    private final String ENCODING = "UTF-8";

    public XMLUtils() {
        compress = true;
    }

    public XMLUtils(boolean compress) {
        this.compress = true;
        this.compress = compress;
    }

    public String toXML(String root, Object object) throws Exception {
        Document doument = getXML(root, object);
        return toXML(doument);
    }

    public String toXML(Document doument) {
        if (doument == null) {
            return "";
        }
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");

        if (compress) {
            format.setIndent("");
            format.setLineSeparator("");
        }
        XMLOutputter xmlOutputter = new XMLOutputter(format);
        return xmlOutputter.outputString(doument).trim();
    }

    public boolean toXML(String root, Object object, String filename)
            throws Exception {
        Document doument = getXML(root, object);
        return toXML(doument, filename);
    }

    public Document getXML(String root, Object object) throws Exception {
        if (root == null || root.trim().length() == 0) {
            throw new Exception("root error");
        }
        if (object == null) {
            return null;
        } else {
            Document document = new Document();
            Element element = new Element(root);
            document.setRootElement(element);
            objectToDom(element, object);
            return document;
        }
    }

    private void objectToDom(Element element, Object object) {
        if (element == null || object == null) {
            return;
        }
        if (object instanceof Map) {
            Map map = (Map) object;
            String key;
            Element subelement;
            for (Iterator iterator = map.keySet().iterator(); iterator
                    .hasNext(); objectToDom(subelement, map.get(key))) {
                key = (String) iterator.next();
                subelement = new Element(key);
                element.getChildren().add(subelement);
            }

        } else if (object instanceof Collection) {
            Collection collection = (Collection) object;
            Object subobject;
            for (Iterator iterator1 = collection.iterator(); iterator1
                    .hasNext(); objectToDom(element, subobject)) {
                subobject = iterator1.next();
            }

        } else {
            element.addContent(object.toString());
        }
    }

    public boolean toXML(Document doument, String filename)
            throws Exception {
        File file;
        XMLOutputter xmlOutputter;
        OutputStream outputStream;
        if (doument == null) {
            return false;
        }
        file = new File(filename);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return false;
        }
        if (file.exists() && !file.delete()) {
            return false;
        }
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        if (compress) {
            format.setIndent("");
            format.setLineSeparator("");
        }
        xmlOutputter = new XMLOutputter(format);
        outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            xmlOutputter.output(doument, outputStream);
        } catch (Exception e) {
            throw new Exception((new StringBuilder(String.valueOf(TraceInfoUtils.getTraceInfo()))).append(e.getMessage()).toString());
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
            }
            outputStream = null;
        }
        return true;
    }

    public static Map<String, String> xmlToMap(String xml) throws Exception {
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        if (xml == null) {
            return resultMap;
        }
        try {
            xml = URLDecoder.decode(xml, "UTF-8");
            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(source);
            Element rootElement = doc.getRootElement();
            List<Element> list = rootElement.getChildren();
            getAllElement(list, resultMap);
        } catch (Exception e) {
            throw new Exception((new StringBuilder(String.valueOf(TraceInfoUtils.getTraceInfo()))).append(e.getMessage()).toString());
        }
        return resultMap;
    }

    public static void getAllElement(List<Element> list, Map<String, String> map) {
        for (Element element : list) {
            List<Element> children = element.getChildren();
            if (children == null || children.isEmpty()) {
                map.put(element.getName(), element.getText());
            } else {
                getAllElement(children, map);
            }
        }
    }
}
