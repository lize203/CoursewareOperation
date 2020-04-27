package com.zl.courseware.system.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.zl.courseware.system.model.pojo.GzipDTO;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarOutputStream;

public class GZipUtils
{
	public static final String TARGZ = ".tar.gz";
	public static final String ZIP = ".zip";

	
	private GZipUtils()
	{
	}
	
	/**
	 * 中字节
	 * @author helix chen
	 */
	static int BUFFER_SIZE = 1024;
	
	
	/**
	 * 压缩多个文件
	 * @author helix chen
	 * @param inFiles 多个文件
	 * @throws IOException  文件读写异常
	 */
	public static void doCompress(List<File> inFiles, TarOutputStream aops)
	{
		try{
			InputStream in = null  ;
    		for (File file : inFiles)
    		{
    			String entryName = file.getName();
    			TarEntry entry = new TarEntry(entryName);
    			entry.setSize(file.length());
                aops.putNextEntry(entry);
    			int len = 0;
    			byte[] buffer = new byte[BUFFER_SIZE];
    			in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
    			while ((len = in.read(buffer,0,BUFFER_SIZE)) != -1)
    			{
    				aops.write(buffer, 0, len);
    			}
    			aops.closeEntry();
				in.close();
    		}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try
			{
				if(aops !=null){
					aops.flush();
					aops.close();
				} 
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
//		List<File> list = new ArrayList<File>();
//		list.add(new File("I:\\apache-tomcat-8.0.9\\upload_efiles\\a.zrc"));
//		list.add(new File("I:\\apache-tomcat-8.0.9\\upload_efiles\\b.zrc"));
//		list.add(new File("I:\\apache-tomcat-8.0.9\\upload_efiles\\c.zrc"));
//
//		try
//		{
//			FileOutputStream output = new FileOutputStream(new File("I:\\DOWN\\aaaaFFb.tar.gz"));
//			TarOutputStream output2 = new TarOutputStream(output);
//			GZipUtils.doCompress(list, output2);
//
//			readGZip("I:\\DOWN\\数字信号处理.tar.gz");
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		} catch (ArchiveException e)
//        {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//        }

		String s = "E:\\kejian\\CoursewareOperation\\out\\artifacts\\CoursewareOperation_war_exploded\\upload_coursewarefiles" +
				"\\export\\%E7%A7%BB%E5%8A%A8%E9%80%9A%E4%BF%A1\\%E4%BF%A1%E9%81%93%E7%BC%96%E7%A0%81\\.sort";
		System.out.println(s);
		s = URLDecoder.decode(s);
		System.out.println(s);
	}
	
	/**
	 * 解压ZIP
	 * @author helix chen
	 * @return 
	 * @throws ArchiveException 
	 */
	public static List<GzipDTO> readGZip(String gzipFilePath) throws IOException, ArchiveException
	{
		List<GzipDTO> zipEntries = new ArrayList<GzipDTO>();
		if (gzipFilePath.indexOf(TARGZ) > 0)
		{
			int indexOf = gzipFilePath.indexOf(TARGZ);
			String fname = gzipFilePath.substring(0,indexOf);
			File file = new File(gzipFilePath);
		    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		    String finalName =  fname+".tar";
		    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(finalName));
		    GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
		    byte[] buffer = new byte[1024];
		    int read = -1;
		    while((read = gcis.read(buffer)) != -1){
		        bos.write(buffer, 0, read);
		    }
		    gcis.close();
		    bos.close();
		    bis.close();
		    
		    File file2 = new File(finalName);
		    TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(file2));
		    TarArchiveEntry tarEntry = null;
		    while((tarEntry = tais.getNextTarEntry()) != null){
		        String name = tarEntry.getName();
//		        System.out.println(name);
        		boolean directory = tarEntry.isDirectory();
        		if(directory){
        			continue;
        		}else{
        			String entryName = tarEntry.getName();
        			if (entryName.indexOf(".project") > 0 && entryName.indexOf("PaxHeader")<0)
        			{
        				long size = tarEntry.getSize();
        				GzipDTO gzipDTO = new GzipDTO(entryName,size);
        				zipEntries.add(gzipDTO);
        			}
        		}
        	}
		    tais.close();
		    file2.delete();//删除tar文件
		} else if (gzipFilePath.indexOf(ZIP) > 0)
		{
			File file2 = new File(gzipFilePath);
			ZipFile zipFile = new ZipFile(file2, Charset.forName("GBK"));
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while(entries.hasMoreElements()){
				ZipEntry zipEntry = entries.nextElement();
				boolean directory = zipEntry.isDirectory();
				if(directory){
					continue;
				}else{
					String entryName = zipEntry.getName();
					if (entryName.indexOf(".project") > 0 && entryName.indexOf("PaxHeader")<0)
					{
						long size = zipEntry.getSize();
						GzipDTO gzipDTO = new GzipDTO(entryName,size);
						zipEntries.add(gzipDTO);
					}
				}
			}
			zipFile.close();
		}
		
		return zipEntries;
	}

	public static String archiveZip(String entry) throws IOException {
		File file = new File(entry);
		ZipOutputStream tos = new ZipOutputStream(new FileOutputStream(file.getAbsolutePath() + ZIP));
		String base = file.getName();
		if (file.isDirectory()) {
			archiveZipDir(file, tos, base);
		} else {
			archiveZipHandle(tos, file, base);
		}
		tos.close();
		return file.getAbsolutePath() + ZIP;
	}

	private static void archiveZipDir(File file, ZipOutputStream tos, String basePath) throws IOException {
		File[] listFiles = file.listFiles();
		for (File fi : listFiles) {
			if (fi.isDirectory()) {
				archiveZipDir(fi, tos, basePath + File.separator + fi.getName());
			} else {
				archiveZipHandle(tos, fi, basePath);
			}
		}
	}

	private static void archiveZipHandle(ZipOutputStream tos, File fi, String basePath) throws IOException {

		ZipEntry entry = new ZipEntry(basePath + File.separator + fi.getName());
		entry.setSize(fi.length());
		tos.putNextEntry(entry);
		InputStream in = null  ;
		int len = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		in = new BufferedInputStream(new FileInputStream(fi), BUFFER_SIZE);
		while ((len = in.read(buffer,0,BUFFER_SIZE)) != -1)
		{
			tos.write(buffer, 0, len);
		}
		tos.closeEntry();
		in.close();
	}
}