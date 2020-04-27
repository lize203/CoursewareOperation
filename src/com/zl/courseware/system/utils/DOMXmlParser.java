package com.zl.courseware.system.utils;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class DOMXmlParser {
	public Element parserXml(String fileName) throws ParserConfigurationException, SAXException, IOException {
		// InputStream input =
		// this.getClass().getClassLoader().getSystemResourceAsStream(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(fileName));
		Element element = document.getDocumentElement();
		/*
		 * NodeList blockList2 = element.getChildNodes();
		 * 
		 * for (int i = 0; i < blockList2.getLength(); i++) { Node child =
		 * blockList2.item(i); LogHelp.info(child); }
		 * 
		 * NodeList blockList = element.getElementsByTagName("block");
		 * 
		 * if (blockList.getLength() != 1) return null;
		 */
		return element; // (Element)blockList.item(0);
	}

	public void createXml(String filepath, SortIndexModel index)
			throws ParserConfigurationException, TransformerException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		Element i = document.createElement("i");
		Element name = document.createElement("name");
		name.setTextContent(index.getName());
		Element path = document.createElement("path");
		path.setTextContent(index.getPath());
		i.appendChild(name);
		i.appendChild(path);
		for (int j = 0; j < index.getIi().size(); j++) {
			Element ii = document.createElement("ii");
			ii.setTextContent(index.getIi().get(j));
			i.appendChild(ii);
		}
		document.appendChild(i);
		TransformerFactory tft = TransformerFactory.newInstance();
		Transformer tf = tft.newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		FileOutputStream fos = new FileOutputStream(new File(filepath));
		tf.transform(new DOMSource(document), new StreamResult(fos));
		fos.close();
	}
}
