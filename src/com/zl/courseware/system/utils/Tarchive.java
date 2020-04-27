package com.zl.courseware.system.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Tarchive {
	/**
	 * 归档
	 * 
	 * @param entry
	 * @throws IOException
	 * @author z
	 * @return
	 */
	public static String archive(String entry) throws IOException {
		File file = new File(entry);
		TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(file.getAbsolutePath() + ".tar"));
		String base = file.getName();
		if (file.isDirectory()) {
			archiveDir(file, tos, base);
		} else {
			archiveHandle(tos, file, base);
		}
		tos.close();
		return file.getAbsolutePath() + ".tar";
	}

	/**
	 * 递归处理，准备好路径
	 * 
	 * @param file
	 * @param tos
	 * @param basePath
	 * @throws IOException
	 * @author z
	 */
	private static void archiveDir(File file, TarArchiveOutputStream tos, String basePath) throws IOException {
		File[] listFiles = file.listFiles();
		for (File fi : listFiles) {
			if (fi.isDirectory()) {
				archiveDir(fi, tos, basePath + File.separator + fi.getName());
			} else {
				archiveHandle(tos, fi, basePath);
			}
		}
	}

	/**
	 * 具体归档处理（文件）
	 * 
	 * @param tos
	 * @param fi
	 * @param basePath
	 * @throws IOException
	 * @author z
	 */
	private static void archiveHandle(TarArchiveOutputStream tos, File fi, String basePath) throws IOException {
		TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + fi.getName());
		tEntry.setSize(fi.length());
		tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
		tos.putArchiveEntry(tEntry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fi));
		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = bis.read(buffer)) != -1) {
			tos.write(buffer, 0, read);
		}
		bis.close();
		tos.closeArchiveEntry();// 这里必须写，否则会失败
	}

	/**
	 * 解压
	 * 
	 * @param archive
	 * @author z
	 * @throws IOException
	 */
	public static void unCompressArchiveGz(String archive) throws IOException {
		File file = new File(archive);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
		String finalName = file.getParent() + File.separator + fileName;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(finalName));
		GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = gcis.read(buffer)) != -1) {
			bos.write(buffer, 0, read);
		}
		gcis.close();
		bos.close();
		unCompressTar(finalName);
	}
	

	/**
	 * 解压到指定目录
	 * 
	 * @param archive
	 * @author z
	 * @throws IOException
	 */
	public static void unCompressArchiveGzTo(String archive, String path) throws IOException {
		File file = new File(archive);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
		String finalName = path + File.separator + fileName;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(finalName));
		GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = gcis.read(buffer)) != -1) {
			bos.write(buffer, 0, read);
		}
		gcis.close();
		bos.close();
		unCompressTar(finalName);
	}

	/**
	 * 解压tar
	 * 
	 * @param finalName
	 * @author yutao
	 * @throws IOException
	 */
	private static void unCompressTar(String finalName) throws IOException {
		File file = new File(finalName);
		String parentPath = file.getParent();
		TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(file));
		TarArchiveEntry tarArchiveEntry = null;

		while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
			String name = tarArchiveEntry.getName();
			File tarFile = new File(parentPath, name);
			if (tarArchiveEntry.isDirectory()) {
				if (!tarFile.exists()) {
					tarFile.mkdirs();
				}
			} else {
				if (!tarFile.getParentFile().exists()) {
					tarFile.getParentFile().mkdirs();
				}
				try {
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));
					int read = -1;
					byte[] buffer = new byte[1024];
					while ((read = tais.read(buffer)) != -1) {
						bos.write(buffer, 0, read);
					}
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		tais.close();
		file.delete();// 删除tar文件
	}

    /**
     * 解压zip
     *
     */
    public static void unCompressZip(String finalName) throws IOException {
        File file = new File(finalName);
        String parentPath = file.getParent();
        ZipArchiveInputStream tais = new ZipArchiveInputStream(new FileInputStream(file));
        ZipArchiveEntry tarArchiveEntry = null;

        try {
            while ((tarArchiveEntry = tais.getNextZipEntry()) != null) {
                String name = tarArchiveEntry.getName();
                File tarFile = new File(parentPath, name);
                if (tarArchiveEntry.isDirectory()) {
                    if (!tarFile.exists()) {
                        tarFile.mkdirs();
                    }
                } else {
                    if (!tarFile.getParentFile().exists()) {
                        tarFile.getParentFile().mkdirs();
                    }
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));
                        int read = -1;
                        byte[] buffer = new byte[1024];
                        while ((read = tais.read(buffer)) != -1) {
                            bos.write(buffer, 0, read);
                        }
                        bos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tais.close();
    }

    /**
     * 解压zip
     *
     */
    public static void unZipFile(String finalName) throws IOException {
        File file = new File(finalName);
        String parentPath = file.getParent();

        ZipFile zipFile = new ZipFile(file, Charset.forName("GBK"));
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        try {
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();
                InputStream inputStream = zipFile.getInputStream(zipEntry);

                File tarFile = new File(parentPath, name);

                if (zipEntry.isDirectory()) {
                    if (!tarFile.exists()) {
                        tarFile.mkdirs();
                    }
                } else {
                    if (!tarFile.getParentFile().exists()) {
                        tarFile.getParentFile().mkdirs();
                    }
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));
                        int read = -1;
                        byte[] buffer = new byte[1024];
                        while ((read = inputStream.read(buffer)) != -1) {
                            bos.write(buffer, 0, read);
                        }
                        bos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        zipFile.close();
    }

	/**
	 * 把tar包压缩成gz
	 * 
	 * @param path
	 * @throws IOException
	 * @author yutao
	 * @return
	 * @date 2017年5月27日下午2:08:37
	 */
	public static String compressArchive(String path) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(
				new BufferedOutputStream(new FileOutputStream(path + ".gz")));
		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = bis.read(buffer)) != -1) {
			gcos.write(buffer, 0, read);
		}
		gcos.close();
		bis.close();
		return path + ".gz";
	}

	public static void main(String[] args) throws IOException {
//		String entry = "/home/zhiling/workspace/eclipse/课件库/科研实验";// 需要压缩的文件夹
//		String archive = archive(entry);// 生成tar包
//		String path = compressArchive(archive);// 生成gz包
//		System.out.println(path);
//		new File(archive).delete();
		// unCompressArchiveGz("/home/zhiling/workspace/eclipse/课件库/科研实验.tar.gz");//解压

//		String s = intConvertString(2000);
//		System.out.println(s);
	}

	public static String addPrefix(String str, String prefix) {
		File file = new File(str);
//		if (file.isDirectory()) {
			String experiment = file.getName();
			File parentFile = file.getParentFile();
			int count = getFileChildrenDirCount(parentFile);
			String convertString = intConvertString(count + 1);
			if ("1".equals(prefix)) {
				return parentFile.getPath() + File.separator + convertString + "、" + experiment;
			} else if ("2".equals(prefix)) {
				return parentFile.getPath() + File.separator + "实验" + convertString + "-" + experiment;
			}
//		}
		return str;
	}

	public static int getFileChildrenDirCount(File f) {
		if (f != null && f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			int count = 0;
			if (files.length > 0) {
				for (File file : files) {
					if (file.isDirectory()) {
						count ++;
					}
				}
				return count;
			}
		}
		return 0;
	}

	public static String intConvertString(int c) {
		Map<Integer, String> m = new HashMap<>();
		m.put(1, "一");
		m.put(2, "二");
		m.put(3, "三");
		m.put(4, "四");
		m.put(5, "五");
		m.put(6, "六");
		m.put(7, "七");
		m.put(8, "八");
		m.put(9, "九");
		m.put(10, "十");

		int k = c / 1000;
		String result = "";
		if (k > 0 && k < 10) {
			String kString = m.get(k);
			result += kString + "千";
		}
		int b = c / 100;
		if (b > 0) {
			if ((b % 10) == 0) {
				result += "零";
			} else {
				if (b > 10) {
					b = (c % 1000) / 100;
				}
				if (b < 10) {
					String bString = m.get(b);
					result += bString + "百";
				}
			}
		}
		int s = c / 10;
		if (s > 0) {
			if ((s % 10) == 0) {
				result += "零";
			} else {
				if (s > 10) {
					s = (c % 100) / 10;
				}
				if (s > 0 && s < 10) {
					String sString = m.get(s);
					result += sString + "十";
				}
			}
		}
		if (c > 0) {
			int g = c;
			if (c >= 10) {
				g = c % 10;
			}
			if (g > 0) {
				String gString = m.get(g);
				result += gString;
			}
		}

		if (c > 9 && c < 20) {
			result = result.substring(1);
		}
		result = result.replaceAll("零零", "零");
		if (result.length() - 1 == result.lastIndexOf("零")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String exportCourseware(String[] fArray, String path, String prefix) throws IOException
	{
		String temp = "export";
		String tempDir = path + File.separator + temp;
		for (String fileStr : fArray) {
			String str = fileStr.replace("/", File.separator);
			str = str.replace("\\", File.separator);

			String courPath = str.substring(1);
			String courName = courPath.substring(0, courPath.indexOf(File.separator));

			File exp = new File(path + str);

			if (XMLUtil.isProject(exp)) {
				String targetDir = tempDir + str;

				targetDir = addPrefix(targetDir, prefix);

				FileUtil.copyDirectiory(exp.getPath(), targetDir + File.separator);
				resetProjectName(targetDir);
				createSort(targetDir, courName);
			}
		}

		// 把要导出的文件夹生成zip包
//		String gzPath = GZipUtils.archiveZip(tempDir);

		// 把要导出的文件夹生成tar.gz包
		String archive = archive(tempDir);// 生成tar包
		String gzPath = compressArchive(archive);// 生成gz包
		new File(archive).delete();

		FileUtil.deleteFile(tempDir);
		return gzPath;
	}

	public static void resetProjectName(String path) {
		File pro = new File(path + File.separator + ".project");
		if (pro.exists() && pro.isFile()) {
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(pro);
				Element rootElement = document.getDocumentElement();

				if (rootElement != null) {
					NodeList childNodes = rootElement.getChildNodes();

					// 遍历所有子节点
					for (int i = 0; i < childNodes.getLength(); i++) {
						Node item = childNodes.item(i);
						if (item.getNodeType() == Node.ELEMENT_NODE) {
							if ("name".equalsIgnoreCase(item.getNodeName())) {
								String nodeValue = item.getNodeValue();
								item.setTextContent(new File(path).getName());
							}
						}
					}
				}

				TransformerFactory tft = TransformerFactory.newInstance();
				Transformer tf = tft.newTransformer();
				tf.setOutputProperty(OutputKeys.INDENT, "yes");
				FileOutputStream fos = new FileOutputStream(pro);
				tf.transform(new DOMSource(document), new StreamResult(fos));
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void createSort(String path,String courName) throws IOException {
		File tempExp = new File(path);
		File parentFile = tempExp.getParentFile();

		String sortFilePath = parentFile.getPath() + File.separator + ".sort";
		File sortFile = new File(sortFilePath);
		SortIndexModel sortIndexModel = null;
		if (sortFile.exists() && sortFile.isFile()) {
			sortIndexModel = XMLUtil.readSortIndex(sortFilePath);
			List<String> ii = sortIndexModel.getIi();
			if (!ii.contains(tempExp.getName())) {
				ii.add(tempExp.getName());
			}
			XMLUtil.writeSortIndex(sortFilePath, sortIndexModel);
		} else {
			sortFile.createNewFile();
			List<String> ii = new ArrayList<>();
			ii.add(tempExp.getName());
			XMLUtil.writeSort(sortFilePath, ii);
		}

		if (!parentFile.getName().equals(courName)) {
			createSort(parentFile.getPath(), courName);
		}
	}

}
