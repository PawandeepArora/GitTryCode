package com.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillModel {
	QueryHelper qryHelperObj = new QueryHelper();
	
	public String insert() {
		List rowParams = new ArrayList();
		int units = 3;
		int pricePerUnit = 3;
		int totalAmount = units * pricePerUnit;
		rowParams.add(101);
		rowParams.add("Shampoo");
		rowParams.add(units);
		rowParams.add(totalAmount);
		rowParams.add(1);
		boolean retVal = qryHelperObj.insertQuery(rowParams);
		rowParams.add(101);
		rowParams.add("Shampoo");
		rowParams.add(units);
		rowParams.add(totalAmount);
		rowParams.add(1);
		if (retVal) {
			return "Insert successful";
		}
		else {
			return "Insert unsuccessful";
		}
	}
	
	public void batchInsert(){
		Map<String, List> batchParamsMap = new HashMap<String, List>();
		List<String> billItemsIdList = new ArrayList<String>();
		List<String> billItemsNamesList = new ArrayList<String>();
		List<Integer> billItemsQtyList = new ArrayList<Integer>();
		List<Integer> billItemsAmountList = new ArrayList<Integer>();
		List<String> billIdsList = new ArrayList<String>();
		billItemsIdList.add("101");
		billItemsIdList.add("102");
		billItemsIdList.add("103");
		billItemsIdList.add("104");
		billItemsNamesList.add("Shampoo");
		billItemsNamesList.add("Soap");
		billItemsNamesList.add("Toothpaste");
		billItemsNamesList.add("Honey");
		billItemsQtyList.add(2);
		billItemsQtyList.add(3);
		billItemsQtyList.add(4);
		billItemsQtyList.add(1);
		billItemsAmountList.add(4);
		billItemsAmountList.add(40);
		billItemsAmountList.add(80);
		billItemsAmountList.add(150);
		billIdsList.add("1");
		batchParamsMap.put("BillItemsID", billItemsIdList);
		batchParamsMap.put("BillItemsName", billItemsNamesList);
		batchParamsMap.put("BillItemsQtyList", billItemsQtyList);
		batchParamsMap.put("BillItemsAmountList", billItemsAmountList);
		batchParamsMap.put("ParentBillId", billIdsList);
		qryHelperObj.batchInsertQuery(batchParamsMap);
		
		
		
	}

	public String update() {
		List rowParams = new ArrayList();
		int units = 3;
		int pricePerUnit = 3;
		int totalAmount = units * pricePerUnit;
		rowParams.add(101);
		rowParams.add("Shampoo");
		rowParams.add(units);
		rowParams.add(totalAmount);
		rowParams.add(1);
		int retVal = qryHelperObj.updateQuery(rowParams);
		if (retVal != 0) {
			return "Update successful";
		}
		else {
			return "Update unsuccessful";
		}
	}

	public String delete() {
		String billId = "1";
		int retVal = qryHelperObj.deleteQuery(billId);
		if (retVal != 0) {
			return "Delete successful";
		}
		else {
			return "Delete unsuccessful";
		}
	}

	public List select() {
		String billId = "101";
		List billEntry = qryHelperObj.selectQuery(billId);
		if(billEntry != null && !billEntry.isEmpty()){
			System.out.println("Select is successful");
			System.out.println(billEntry);
		}
		else{
			System.out.println("Unable to obtain the selected row.");
		}
		return billEntry;
	}
}
