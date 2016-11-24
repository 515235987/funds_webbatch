package com.ibm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CSVFileUtil {
	// CSV文件编码  
    public static final String ENCODE = "UTF-8";  
  
    private FileInputStream fis = null;  
    private InputStreamReader isw = null;  
    private BufferedReader br = null;  
    
    private FileOutputStream out = null;
    private OutputStreamWriter osw = null;  
    private BufferedWriter writer = null;
  
     
    public CSVFileUtil(String filename,String io) throws Exception {
    	if(io.equals("read")){
    		fis = new FileInputStream(filename);  
    		isw = new InputStreamReader(fis, ENCODE);  
    		br = new BufferedReader(isw);  
    	}else if(io.equals("write")){
    		out = new FileOutputStream(filename);
    		osw = new OutputStreamWriter(out, ENCODE);
			writer = new BufferedWriter(osw);
    	}
    }  
  
    public void CSVStreamClose() throws IOException{
    	if(null != br){
    		br.close();
    		isw.close();
    		fis.close();
    	}else if (null != out){
    		out.close();
    		osw.close();
    		writer.close();
    	}
    }
    /** 
     * 从CSV文件流中读取一个CSV行。 
     * 
     * @throws Exception 
     */  
    public String readLine() throws Exception {  
  
        StringBuffer readLine = new StringBuffer();  
        boolean bReadNext = true;  
  
        while (bReadNext) {  
            //  
            if (readLine.length() > 0) {  
                readLine.append("\r\n");  
            }  
            // 一行  
            String strReadLine = br.readLine();  
  
            // readLine is Null  
            if (null == strReadLine) {  
                return null;  
            }  
            readLine.append(strReadLine);  
  
            // 如果双引号是奇数的时候继续读取。考虑有换行的是情况。  
            if (countChar(readLine.toString(), '"', 0) % 2 == 1) {  
                bReadNext = true;  
            } else {  
                bReadNext = false;  
            }  
        }  
        return readLine.toString();  
    }  
  
    public void writeStringToFile(String str) {

    	try {
			writer.write(str);
			writer.newLine();
	    	writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /** 
     *计算指定文字的个数。 
     * 
     * @param str 文字列 
     * @param c 文字 
     * @param start  开始位置 
     * @return 个数 
     */  
    private int countChar(String str, char c, int start) {  
        int i = 0;  
        int index = str.indexOf(c, start);  
        return index == -1 ? i : countChar(str, c, index + 1) + 1;  
    }  
    


}
