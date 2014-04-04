package com.test.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.db2.jcc.b.SqlException;
import com.test.db.connections.DBConfigFactory;

public class QueryHelper {
//	private static final String CREATE_BILLITEM_QUERY = "INSERT INTO BFTB_BILL" + "(BILL_ID, TOTAL_BILL_AMOUNT, BILL_DATE, ITEM_AMOUNT, BILLID, VERSIONNUM)" + "values(?,?,?,?,?,1)";
	private static final String CREATE_BILLITEM_QUERY = "INSERT INTO BANKFUSION.BFTB_BILLITEMS" + "(BILL_ITEM_ID, BILL_ITEM_NAME, ITEM_QUANTITY, ITEM_AMOUNT, BILLID, VERSIONNUM)" + "values(?,?,?,?,?,1)";
	private static final String UPDATE_BILLITEM_QUERY = "UPDATE BANKFUSION.BFTB_BILLITEMS" + "(BILL_ITEM_ID, BILL_ITEM_NAME, ITEM_QUANTITY, ITEM_AMOUNT, BILLID, VERSIONNUM)" + "values(?,?,?,?,?,1)";
	private static final String DELETE_BILLITEM_QUERY = "DELETE FROM BANKFUSION.BFTB_BILLITEMS WHERE BILL_ITEM_ID = ?";
	private static final String SELECT_BILL_QUERY = "SELECT * FROM BANKFUSION.BFTB_BILL B INNER JOIN BANKFUSION.BFTB_BILLITEMS WHERE B.BILL_ID= ? AND B.BILL_ID = BI.BILLID";
	private static final String CREATE_BILL_QUERY = "INSERT INTO BANKFUSION.BFTB_BILL" + "(BILL_ID, TOTAL_BILL_AMOUNT)" + "values(?,?)";
	private static final String CREATE_TOTAL_AMOUNT = "SELECT SUM(ITEM_AMOUNT) FROM BANKFUSION.BFTB_BILLITEMS WHERE BILLID = ? GROUP BY BILLID";
	static Connection connection = null;
	static PreparedStatement ps = null;

	public synchronized boolean insertQuery(List params) {
		connection = DBConfigFactory.getConnection();
		boolean resExecute = false;
		try {
			ps = DBConfigFactory.getPreparedStatement(CREATE_BILL_QUERY);
			ps.setString(1, (String)params.get(4));
			ps.setInt(2,  (Integer)params.get(3));	
		//	ps.setDate(3, new Date(1970-01-01));
			resExecute = ps.execute();
			ps = DBConfigFactory.getPreparedStatement(CREATE_BILLITEM_QUERY);
			ps.setString(1, (String) params.get(0));
			ps.setString(2, (String) params.get(1));
			ps.setInt(3, (Integer) params.get(2));
			ps.setInt(4, (Integer) params.get(3));
			ps.setString(5, (String) params.get(4));
			resExecute = ps.execute();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			DBConfigFactory.closePreparedStatement(ps);
			return resExecute;
		}
	}
	
	
	public synchronized boolean batchInsertQuery(Map<String, List> batchParamsMap) {
		connection = DBConfigFactory.getConnection();
		boolean resExecute = false;
		int totalAmount = 0;
		String billId= null;
		try {
			List<String> billItemsIdList = batchParamsMap.get("BillItemsID");
			List<String> billItemsNamesList = batchParamsMap.get("BillItemsName");
			List<Integer> billItemsQtyList = batchParamsMap.get("BillItemsQtyList");
			List<Integer> billItemsAmountList = batchParamsMap.get("BillItemsAmountList");
			List<String> billIdsList = batchParamsMap.get("ParentBillId");
			billId = billIdsList.get(0);
			ps = DBConfigFactory.getPreparedStatement(CREATE_BILLITEM_QUERY);
			for(int i=0; i< billItemsIdList.size(); i++){
				ps.setString(1, (String) billItemsIdList.get(i));
				ps.setString(2, (String) billItemsNamesList.get(i));
				int itemsQty = (int) billItemsQtyList.get(i);
				int pricePerPiece = (int) billItemsAmountList.get(i);
				int amountPerItem = itemsQty*pricePerPiece;
				totalAmount = totalAmount + amountPerItem;
				ps.setInt(3, itemsQty);
				ps.setInt(4, (Integer) amountPerItem);
				ps.setString(5, (String) billIdsList.get(0));
				ps.addBatch();
			}
		PreparedStatement psBill = DBConfigFactory.getPreparedStatement(CREATE_BILL_QUERY);
			psBill.setString(1, billId);
			psBill.setInt(2,  totalAmount);	
			resExecute = psBill.execute();
			int[] resExecuteBatch = ps.executeBatch();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			DBConfigFactory.closePreparedStatement(ps);
			return resExecute;
		}
	}

	public synchronized int updateQuery(List params) {
		int rowsAffected = 0;
		try {
			ps = DBConfigFactory.getPreparedStatement(UPDATE_BILLITEM_QUERY);
			ps.setString(1, (String) params.get(0));
			ps.setString(2, (String) params.get(1));
			ps.setInt(3, (Integer) params.get(2));
			ps.setInt(4, (Integer) params.get(3));
			ps.setString(5, (String) params.get(4));
			rowsAffected = ps.executeUpdate();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			DBConfigFactory.closePreparedStatement(ps);
			return rowsAffected;
		}
	}

	public synchronized List selectQuery(String billId) {
		List billDetails = new ArrayList();
		try {
			ps = DBConfigFactory.getPreparedStatement(SELECT_BILL_QUERY);
			ps.setString(1, billId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			return billDetails;
		}
	}

	public synchronized int deleteQuery(String billItemId) {
		int rowsAffected = 0;
		try {
			ps = DBConfigFactory.getPreparedStatement(DELETE_BILLITEM_QUERY);
			ps.setString(1, billItemId);
			rowsAffected = ps.executeUpdate();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			return rowsAffected;
		}
	}
}
