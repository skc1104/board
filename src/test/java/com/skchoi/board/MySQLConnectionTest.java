package com.skchoi.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLConnectionTest {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String URL = "jdbc:mysql://mystb.iptime.org:5206/board?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
	static final String USERNAME = "new";
	static final String PASSWORD = "Lecture!2020";

	@Test
	public void getMySQLConnectionTest() {
		Connection conn = null;
		Statement stmt = null;

		try {
			//System.out.println("==================== MySQL Connection START ====================");
			logger.info("==================== MySQL Connection START ====================");

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();

			String sql = "SELECT BOARD_SUBJECT, BOARD_CONTENT, BOARD_WRITER FROM TB_BOARD";

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String boardSubject = rs.getString("BOARD_SUBJECT");
				String boardContent = rs.getString("BOARD_CONTENT");
				String boardWriter = rs.getString("BOARD_WRITER");

				/*
				 * System.out.print("boardSubject : " + boardSubject + ", ");
				 * System.out.print("boardContent : " + boardContent + ", ");
				 * System.out.println("boardWriter : " + boardWriter);
				 */
				logger.info("boardSubject : " + boardSubject + ", ");
				logger.info("boardContent : " + boardContent + ", ");
				logger.info("boardWriter : " + boardWriter);
				logger.info("=============================================");

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException sel) {
			sel.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException se) {
				se.printStackTrace();
			}

			try {
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		//System.out.println("==================== MySQL Connection END ====================");
		logger.info("==================== MySQL Connection END ====================");
	}
}
