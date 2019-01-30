package com.practice.cs_wm.Util;

public class StatsUtil {

	public static float getlinearExecutionPerOrder(float size,  float quantity) {
		return quantity/size;
	}


	public static double getEffectiveQtyForExec(int accumltdOrders, double accExecQty, long quantity) {
		if((accExecQty+quantity)> accumltdOrders)
			return accumltdOrders-accExecQty;
		return quantity;
	}

	
}
