package main.java.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository("urlShortenerDao")
public class UrlShortenerDao {

	final String DriverName = "com.mysql.cj.jdbc.Driver";
	final String DBUrl = "jdbc:mysql://13.209.233.110:3306/testdb?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8";
	final String DBUser = "jilee";
	final String DBPassword = "1q2w3e4r%T";
	
	public int getUrlCount(String shortenUrl) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			Class.forName(DriverName);
			conn = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			
			String sql = "SELECT COUNT(*) FROM URL WHERE SHORTEN_URL = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, shortenUrl);
			
			rs = pstmt.executeQuery();
			if(rs.next())result=rs.getInt(1);
			
			if(result > 0) {
				System.out.println("getUrlCount data : " + result);
			}else {
				System.out.println("getUrlCount no data");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException : " + e);
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		finally {
			try {
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
				if(pstmt != null && pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String getOriginalUrl(String shortenUrl) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		try {
			Class.forName(DriverName);
			conn = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			
			String sql = "SELECT ORIGINAL_URL FROM URL WHERE SHORTEN_URL = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, shortenUrl);
			
			rs = pstmt.executeQuery();
			if(rs.next())result=rs.getString(1);
			
			if(!"".equals(result)) {
				System.out.println("getOriginalUrl data : " + result);
			}else {
				System.out.println("getOriginalUrl no data");
			}
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException : " + e);
			} catch (SQLException e) {
				System.out.println("SQLException : " + e);
			}
		finally {
			try {
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
				if(pstmt != null && pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int insertUrl(String shortenUrl, String originalUrl) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			Class.forName(DriverName);
			conn = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			
			String sql = "INSERT INTO URL(SHORTEN_URL, ORIGINAL_URL) VALUES(?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, shortenUrl);
			pstmt.setString(2, originalUrl);
			
			result = pstmt.executeUpdate();
			if(result > 0) {
				System.out.println("insertUrl success : " + shortenUrl);
			}else {
				System.out.println("insertUrl fail");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException : " + e);
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		finally {
			try {
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
				if(pstmt != null && pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
