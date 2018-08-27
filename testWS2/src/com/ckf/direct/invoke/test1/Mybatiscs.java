package com.ckf.direct.invoke.test1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 
 * @author ckf
 *
 */
public class Mybatiscs {   
	public static int is_pager = 1; //0:��ҳ 1:����ҳ
	public static String dbname = "";
	private static Connection getConn() { 
	    String driver = "com.mysql.jdbc.Driver";
	    //String url =  "jdbc:mysql://localhost:3306/"+dbname;
	    String url = "jdbc:mysql://192.168.1.235:3306/"+dbname;
	    String username = "root";
	    //String password = "root"; 
	    String password = "123456";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,���ض�Ӧ����
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	public static void makeSql(String tableName,String db_name) throws IOException{  
		dbname = db_name;
		String url = "C:\\Users\\ckf\\Desktop\\testsql.txt";
		String insertName = tableName.replaceAll("_", "");
		String prefixAdd = "<insert id=\"insert"+insertName+"\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n"+ 
				"insert into "+tableName+"(\n"; 
		String endAdd = ")values (\n"; 
		String addSQL ="";
		String select = ""; 
		String prefixUpdate = "<update id=\"update"+insertName+"\">\n"+ 
		"update "+tableName+" \n<set> \n id=id,";  
		String endUpdate = "\n</set>\n where id = #{id} \n </update>"; 
		String updateSQL = ""; 
		String queryPrefix="<select id=\"query"+insertName+"byId\" resultType=\"pd\">\nselect\nid,\n"; 
		String queryEnd="\nfrom "+tableName+" where id = #{id}\n</select>";  
		String queryListEnd="";
		if(is_pager==0){ 
			queryListEnd = "\nfrom "+tableName+" order by id limit #{index},#{size}\n</select>"; 
		}else{ 
			queryListEnd = "\nfrom "+tableName+" order by id\n</select>"; 
		}
		String selectSQL = ""; 
		String selectListSQL = "";
	    Connection conn = getConn(); 
	    PreparedStatement pstmt;
	    String sql = "select column_name from information_schema.COLUMNS where table_name="+"'"+tableName+"'" 
	    		+"and TABLE_SCHEMA='"+dbname+"'"; 
	    try { 
	    	pstmt = conn.prepareStatement(sql);
		    ResultSet set = pstmt.executeQuery(); 
		    while(set.next()){   
		    	
		    	String columName =  set.getString(1); 
		    	//System.out.println(columName);  
		    	if(!columName.equals("id")){ 
		    		if(set.isLast()){ 
		    			prefixAdd+=columName+"\n";//���� 
		    			prefixUpdate+="\n<if test=\""+columName+" !=null and "+columName+" !=''\">\n "
		    					+ columName+"=#{"+columName+"}\n" 
		    					+ "</if>";//�޸� 
		    			queryPrefix+=columName;
		    		}else{ 
		    			prefixAdd+=columName+","+"\n";//���� 
		    			prefixUpdate+="\n<if test=\""+columName+" !=null and "+columName+" !=''\">\n "
		    					+ columName+"=#{"+columName+"},\n" 
		    					+ "</if>";//�޸� 
		    			queryPrefix+=columName+",\n";
		    		}
		    	} 
		    	if(!columName.equals("id")){  
		    		if(set.isLast()){ 
			        	endAdd+="#{"+columName+"}"+"\n";//����
		    		}else{ 
			        	endAdd+="#{"+columName+"}"+","+"\n";//����
		    		}
		    	}  
		    	
		    } 
		    addSQL = prefixAdd+endAdd+")\n</insert>"; 
		    updateSQL = prefixUpdate+endUpdate; 
		    selectSQL = queryPrefix+queryEnd;
		    selectListSQL = queryPrefix.replaceAll("byId", "ListPage")+queryListEnd;
		    //System.out.println(addSQL);  
		    //System.out.println(updateSQL); 
		    //System.out.println(selectSQL);  
		    File file = new File(url); 
		    if(file.exists()){ 
		    	file.mkdirs();
		    }else{ 
		    	file.delete();
		    }
	        pstmt.close();
	        conn.close(); 
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(addSQL+"\n"+updateSQL+"\n"+selectSQL+"\n"+selectListSQL);
		    writer.flush();
		    writer.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    System.out.println("������sqlcurd,��ַ��"+url);
	} 
	 
	public static void main(String[] args) throws IOException {  
		System.out.println("github test");
		makeSql("certificate","spw"); 
	}
}
