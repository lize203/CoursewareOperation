package com.zl.courseware.system.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import com.zl.courseware.system.model.kz.KzExperiment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * packaging xml convert for Experiment or Experiment to xml
 * 
 * @Date 2019-05-10
 * @author z
 *
 */
public class XMLUtil {
	private static final String tagNameIndex = "i";
	private static final String tagNameIndexName = "name";
	private static final String tagNameIndexPath = "path";
	private static final String tagNameIndexItem = "ii";

	public static SortIndexModel readSortIndex(String xmlPath) {
		DOMXmlParser parser = new DOMXmlParser();
		Element rootElement = null;
		try {
			rootElement = parser.parserXml(xmlPath);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SortIndexModel index = new SortIndexModel();

		// <block>标签对应的节点
		if (rootElement != null) {
			NodeList childNodes = rootElement.getChildNodes();

			// 遍历<block>标签下的所有子节点
			for (int i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					if (tagNameIndexName.equalsIgnoreCase(childNodes.item(i).getNodeName())) {
						index.setName(childNodes.item(i).getFirstChild().getNodeValue());
					} else if (tagNameIndexPath.equalsIgnoreCase(childNodes.item(i).getNodeName())) {
						index.setPath(childNodes.item(i).getFirstChild().getNodeValue());
					} else if (tagNameIndexItem.equalsIgnoreCase(childNodes.item(i).getNodeName())) {
						index.getIi().add(childNodes.item(i).getFirstChild().getNodeValue());
					}
				}
			}
		}
		return index;
	}

	public static void writeSortIndex(String path, SortIndexModel index) {
		DOMXmlParser parser = new DOMXmlParser();
		try {
			parser.createXml(path, index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeSort(String path, List<String> ii) {
		SortIndexModel index = new SortIndexModel();
		index.setName("排序索引文件");
		index.setPath("/");
		index.setIi(ii);
		XMLUtil.writeSortIndex(path, index);
	}


	public static SortIndexModel createIndex(String dirPath) {
		SortIndexModel experimentIndex = new SortIndexModel();
		if (null != dirPath) {
			String index = dirPath + File.separator + ".sort";
			File file = new File(index);
			if (file.exists() && file.isFile()) {
				experimentIndex = XMLUtil.readSortIndex(index);
			}
		}
		return experimentIndex;
	}


	public static List<KzExperiment> sortListByIndex(String parentPath, List<KzExperiment> KzExperiments) {
		SortIndexModel createIndex = createIndex(parentPath);
		List<String> indexList = createIndex.getIi();
		KzExperiment[] arrays = new KzExperiment[indexList.size()];
		List<KzExperiment> resultList = new ArrayList<>();
		List<KzExperiment> unFind = new ArrayList<>();
		for (KzExperiment kzExperiment : KzExperiments) {
			String name = kzExperiment.getExperimentname();

			boolean isFind = false;
			for (int i = 0; i < indexList.size(); i++) {
				String str = indexList.get(i);
				if(str.equalsIgnoreCase(name)) {
					arrays[i] = kzExperiment;
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				unFind.add(kzExperiment);
			}
		}
		resultList.addAll(Arrays.asList(arrays));
		resultList.addAll(unFind);

		return resultList;
	}


	public static List<Map<String, Object>> getKejainLibrary(String dirPath) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		readAndImportLibrary(result, null, dirPath);
		return result;
	}


	public static LinkedList<String> sortListByIndex(String parentPath, List<String> dataList, String afterFix) {
		SortIndexModel createIndex = createIndex(parentPath);
		List<String> indexList = createIndex.getIi();

		LinkedList<String> sortDataList = new LinkedList<String>();

		for (String index : indexList) {
			File fileTmp = new File(parentPath + File.separator + index + afterFix);
			if (fileTmp.exists()) {
				sortDataList.add(index + afterFix);
			}
		}

		for (int i = 0; i < dataList.size(); i++) {
			String childPath = parentPath + File.separator + dataList.get(i);
			File fileTmp = new File(childPath);
			if (fileTmp.exists()) {
				sortDataList.add(dataList.get(i));
			}
		}

		boolean bContinue = true;
		int nIndex = 0;

		while (bContinue) {
			if (nIndex == sortDataList.size()) {
				break;
			}

			String child = sortDataList.get(nIndex);
			boolean bFind = false;

			for (int i = nIndex + 1; i < sortDataList.size(); i++) {
				if (child.equalsIgnoreCase(sortDataList.get(i))) {
					bFind = true;
					sortDataList.remove(i);
					break;
				}
			}

			if (bFind) {
				bContinue = true;
				nIndex++;
			} else {
				if (nIndex < indexList.size()) {
					bContinue = true;
					nIndex++;
				} else {
					bContinue = false;
				}
			}
		}

		return sortDataList;
	}

	public static boolean isProject(File file) {
        File[] filelist = file.listFiles();
        boolean isProject = false;
        for (int i = 0; i < filelist.length; i++) {
            File fileTmp = filelist[i];
            if (!fileTmp.isDirectory() && (fileTmp.getName().equalsIgnoreCase(".project"))) {

                isProject = true;
                break;
            }
        }
        return isProject;
    }


	public static boolean hasChild(File file) {
		File[] filelist = file.listFiles();
		boolean hasProject = false;
		for (int i = 0; i < filelist.length; i++) {
			File fileTmp = filelist[i];
			if (fileTmp.isDirectory() || (fileTmp.getName().equalsIgnoreCase(".project"))) {

				hasProject = true;
				break;
			}
		}
		return hasProject;
	}

	private static void readAndImportLibrary(List<Map<String, Object>> result, String strParentDir, String currentDir) {
		File file = new File(currentDir);
		boolean isProject = false;
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				String childPath = currentDir + File.separatorChar + filelist[i];
				File fileTmp = new File(childPath);
				if (!fileTmp.isDirectory() && (filelist[i].equalsIgnoreCase(".project"))) {

					Map<String, Object> node = new HashMap<String, Object>();
					node.put("id", file.getName());
					node.put("text", file.getName());
					result.add(node);

					isProject = true;
					break;
				}
			}

			if (!hasChild(file)) {
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("id", file.getName());
				node.put("text", file.getName());
				result.add(node);
			}else if (!isProject) {
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("id", file.getName());
				node.put("text", file.getName());
				node.put("state", "closed");


				SortIndexModel createIndex = createIndex(currentDir);
				List<String> indexList = createIndex.getIi();
				List<String> childSortList = new LinkedList<String>();

				for (String index : indexList) {
					childSortList.add(currentDir + File.separator + index);
				}

				for (int i = 0; i < filelist.length; i++) {
					String childPath = currentDir + File.separator + filelist[i];
					File fileTmp = new File(childPath);
					if (fileTmp.isDirectory()) {
						childSortList.add(childPath);
					}
				}

				boolean bContinue = true;
				int nIndex = 0;

				while (bContinue) {
					if (nIndex == childSortList.size()) {
						break;
					}

					String child = childSortList.get(nIndex);
					boolean bFind = false;

					for (int i = nIndex + 1; i < childSortList.size(); i++) {
						if (child.equalsIgnoreCase(childSortList.get(i))) {
							bFind = true;
							childSortList.remove(i);
							break;
						}
					}

					if (bFind) {
						bContinue = true;
						nIndex++;
					} else {
						if (nIndex < indexList.size()) {
							bContinue = true;
							nIndex++;
						} else {
							bContinue = false;
						}
					}
				}

				List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();;

				// for (int i = 0; i < filelist.length; i++)
				for (String childPath : childSortList) {
					// String childPath = currentDir+File.separator + filelist[i];
					File fileTmp = new File(childPath);
					if (fileTmp.exists() && fileTmp.isDirectory()) {
						readAndImportLibrary(childs, currentDir, childPath);
					}
				}
				node.put("children", childs);
				result.add(node);
			}
		}
	}
}
