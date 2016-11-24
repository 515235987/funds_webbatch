package com.ibm.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBMysqlUtil {
	
	private MysqlDataSource mysqlDataSource = null;
	
	public DBMysqlUtil(String sqlURL,String sqlUser,String sqlPasswd){
		mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setURL(sqlURL);
		mysqlDataSource.setUser(sqlUser);
		mysqlDataSource.setPassword(sqlPasswd);
	}
	
	public boolean insertBatch(String sql,List<List<String>> lst) throws FileNotFoundException, IOException, SQLException{
//		sql = "insert into employee (name, city, phone) values (?, ?, ?)";
		Connection conn = mysqlDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		final int batchSize = 10000;
		
		for (int index = 0; index < lst.size();index++) {
			for(int n = 0 ; n < lst.get(index).size();n++){
				String data = lst.get(index).get(n);
				if("".equals(data) 
						|| "NULL".equals(data) 
						|| null == data
						|| "null".equals(data)){
					ps.setString(n+1, null);
				}else{
					ps.setString(n+1, data);
				}
			}
		    ps.addBatch();
		    if(index % batchSize == 0) {
		    	System.out.println(ps.toString());
		        ps.executeBatch();
		    }
		}
		ps.executeBatch(); // insert remaining records
		ps.close();
		conn.close();
		return true;
	}
	
	public List<String> queryList(String sql,List<String> param) throws SQLException{
		List<String> result = new ArrayList<>();
		Connection conn = mysqlDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		if(null != param && param.size()>0){
			int n = 1;
			for(String str:param){
				ps.setString(n, str);
				n++;
			}
		}
		System.out.println(ps.toString());
		ResultSet rs = ps.executeQuery();
		int col = rs.getMetaData().getColumnCount();
		String str = null;
		while (rs.next()) {
			str = "";
            for (int i = 1; i <= col; i++) {
            	if(i == 1){
            		str = rs.getString(i);
            	}else{
            		str = str + "," + rs.getString(i);
            	}
             }
            result.add(str);
        }
		ps.close();
		conn.close();
		return result;
	}
	
	public int updateBatch(String sql,List<List<String>> lst) throws FileNotFoundException, IOException, SQLException{
		Connection conn = mysqlDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		int count = 0;
		if(lst != null){
			final int batchSize = 10000;
			for (int index = 0; index < lst.size();index++) {
				for(int n = 0 ; n < lst.get(index).size();n++){
					String data = lst.get(index).get(n);
					if("".equals(data) 
							|| "NULL".equals(data) 
							|| null == data
							|| "null".equals(data)){
						ps.setString(n+1, null);
					}else{
						ps.setString(n+1, data);
					}
				}
				count++;
				ps.addBatch();
				if(index % batchSize == 0) {
					System.out.println(ps.toString());
					ps.executeBatch();
				}
			}
			ps.executeBatch(); // insert remaining records
		}else{
			count = ps.executeUpdate();
		}
		ps.close();
		conn.close();
		return count;
	}

	public int deleteBatch(String sql,List<List<String>> lst) throws FileNotFoundException, IOException, SQLException{
//		sql = "DELETE FROM fund.stock_index WHERE stock_index.date> ?";
		Connection conn = mysqlDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		final int batchSize = 10000;
		int count = 0;
		for (int index = 0; index < lst.size();index++) {
			for(int n = 0 ; n < lst.get(index).size();n++){
				String data = lst.get(index).get(n);
				if("".equals(data) 
						|| "NULL".equals(data) 
						|| null == data
						|| "null".equals(data)){
					ps.setString(n+1, null);
				}else{
					ps.setString(n+1, data);
				}
			}
			count++;
		    ps.addBatch();
		    if(index % batchSize == 0) {
		    	System.out.println(ps.toString());
		    	ps.executeBatch();
		    }
		}
		ps.executeBatch(); // insert remaining records
		ps.close();
		conn.close();
		return count;
	}
	
	public boolean TruncateTbl(String tbl) throws SQLException{
		Connection conn = mysqlDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("TRUNCATE " + tbl);
		boolean result = ps.execute();
		ps.close();
		conn.close();
		return result;
	}
}
