package com.SanSanCDATA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	static Connection con;

	public static Connection createConnection() {
		try {

			Class.forName("cdata.jdbc.sansan.SansanDriver");

			Properties prop = new Properties();
			prop.setProperty("APIKey", "");
			prop.setProperty("Verbosity", "3");
			prop.setProperty("Logfile", "D:\\\\DataX\\\\testing\\\\cdata_sansan.log");
			con = DriverManager.getConnection("jdbc:sansan:", prop);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void CloseConnection(Connection con, PreparedStatement state) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
