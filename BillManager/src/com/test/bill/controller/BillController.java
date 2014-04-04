package com.test.bill.controller;

import com.test.dao.BillModel;

public class BillController {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BillModel billModelObj = new BillModel();
		billModelObj.batchInsert();

		System.out.println("Bills creations.");
/*		billModelObj.select();
		billModelObj.update();
		System.out.println("Bills updation");
		billModelObj.select();
		billModelObj.delete();
		System.out.println("Bills model after deletion");*/
	}
}
