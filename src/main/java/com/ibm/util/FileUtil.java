package com.ibm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static boolean mkdirIfNotExists(String dir) {
		boolean result = false;
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			System.out.println("[FileUtil] mkdir '" + fileDir.getAbsolutePath() + "'");

			result = fileDir.mkdirs();
		}

		return result;
	}

	public static void deleteFileIfExists(String file) {

		File deleteFile = new File(file);
		if (deleteFile.exists()) {
			System.out.println("[FileUtil] delete '" + deleteFile.getAbsolutePath() + "'");

			deleteFile.delete();
		}
	}

	public static void clearDir(String dir) {

		File fileDir = new File(dir);
		if (fileDir.isDirectory()) {
			File[] listFiles = fileDir.listFiles();

			if (listFiles.length > 0) {
				for (File file : listFiles) {
					if (file.isFile()) {
						System.out.println("[FileUtil] delete '" + file.getAbsolutePath() + "'");

						file.delete();
					}
				}
			}
		}
	}

	//relative dir
	public static void copyFileToDir(String oldFilePath, String dirStr) {

		File oldFile = new File(oldFilePath);
		File dir = new File(dirStr);
		String outputName = dir + "/" + oldFile.getName();

		FileInputStream inStream = null;
		BufferedReader reader = null;
		FileOutputStream outStream = null;
		BufferedWriter writer = null;

		try {
			inStream = new FileInputStream(oldFile);
			reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			outStream = new FileOutputStream(outputName);
			writer = new BufferedWriter(new OutputStreamWriter(outStream, "utf-8"));

			String data = null;
			while ((data = reader.readLine()) != null) {
				writer.write(data);
				writer.newLine();
			}
			writer.flush();

			System.out.println("[FileUtil] copy file " + oldFilePath + " >> " + outputName);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (outStream != null) {
					outStream.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public static void writeListToFile(List<String> list, String filepath) {

		FileOutputStream out = null;
		BufferedWriter writer = null;
		try {

			out = new FileOutputStream(new File(filepath));
			writer = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));

			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i));
				if (i < list.size() - 1) {
					writer.newLine();
				}
			}
			writer.flush();

			System.out.println("[FileUtil] write list to file >> " + filepath);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	public static List<File> getFileList(String filePath){
		List<File> resultList = new ArrayList<File>();
		File file = new File(filePath);
		if (!file.isDirectory()) {
			System.out.println("文件");
			resultList.add(file);
		} else if (file.isDirectory()) {
		    System.out.println("文件夹");
		    String[] filearr = file.list();
		    for (int i = 0; i < filearr.length; i++) {
	            File readfile = new File(filePath + "/" + filearr[i]);
	            if (!readfile.isDirectory()) {
	            	resultList.add(readfile);
	            }
		    }
		}
		return resultList;
	}

}
