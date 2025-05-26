package com.SanSanCDATA.domain;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.SanSanCDATA.DBConnection;
import com.SanSanCDATA.domain.entity.RegistrationRequest;
import com.SanSanCDATA.exceptions.BusinessLogicException;

@Service
public class SanSanCdataService {

	public List<String> getAllTableName() {
		List<String> result = new ArrayList<>();
		try {

			Connection con = DBConnection.createConnection();
			DatabaseMetaData table_meta = con.getMetaData();
			ResultSet rs = table_meta.getTables(null, null, "%", null);
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_NAME"));
				result.add(rs.getString("TABLE_NAME"));
			}

		} catch (SQLException e) {
			throw new BusinessLogicException("Getting Table Process failed..", e);
		}
		return result;
	}

	public List<String> getAllColumnName(String tableName) {
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnection.createConnection();
			DatabaseMetaData table_meta = conn.getMetaData();
			ResultSet rs = table_meta.getColumns(null, null, null, null);
			while (rs.next()) {
				result.add(rs.getString("COLUMN_NAME"));
			}
		} catch (SQLException e) {
			throw new BusinessLogicException("Getting Column Process failed..", e);
		}

		return result;
	}

	public List<Map<String, String>> getAllCoulumnAndValue() {
		List<Map<String, String>> result = new ArrayList<>();
		try {
			Connection con = DBConnection.createConnection();
			Statement stat = con.createStatement();
			boolean ret = stat.execute("SELECT * FROM Bizcards");
			if (ret) {
				ResultSet rs = stat.getResultSet();

				while (rs.next()) {
					Map<String, String> rowMap = new LinkedHashMap<>();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						String key = rs.getMetaData().getColumnLabel(i);
						String value = rs.getString(i);
						rowMap.put(key, value);
					}
					result.add(rowMap);
				}
			}
			con.close();
		} catch (SQLException e) {
			throw new BusinessLogicException("Getting Table Process failed..", e);
		}
		return result;
	}

	public List<Map<String, String>> getColumnAndValueById(String tableName, String keyFilter, String id) {
		List<Map<String, String>> result = new ArrayList<>();

		String query = "SELECT * FROM " + tableName + " WHERE " + keyFilter + "= ?";

		try (Connection conn = DBConnection.createConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Map<String, String> rowMap = new LinkedHashMap<>();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						String key = rs.getMetaData().getColumnLabel(i);
						String value = rs.getString(i);
						rowMap.put(key, value);
					}
					result.add(rowMap);
				}
			}
		} catch (SQLException e) {
			throw new BusinessLogicException("Getting Column And Value By Id failed..", e);
		}
		return result;
	}

	public void insertRowToTable(RegistrationRequest request) {
		String query = "INSERT INTO Bizcards (Email, OwnerId , CompanyName , FirstName , LastName) VALUES (?,?,?,?,?)";

		try (Connection con = DBConnection.createConnection();
				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, request.getEmail());
			ps.setString(2, request.getOwnerId());
			ps.setString(3, request.getCompanyName());
			ps.setString(4, request.getFirstName());
			ps.setString(5, request.getLastName());

			int rowsInserted = ps.executeUpdate();

			if (rowsInserted == 0) {
				throw new BusinessLogicException("Insert failed. No rows inserted..");
			}

		} catch (SQLException e) {
			throw new BusinessLogicException("Insert failed. No rows inserted..", e);
		}
	}
}