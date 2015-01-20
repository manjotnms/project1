package com.mtss.dao;

import com.mtss.bean.Sales;

public interface SalesDetail {
	public void saveSales(Sales sales)throws Exception;
	public Sales findSales(int doNo);
	public void removeSales(int doNo);
	public void updateSales(Sales sales);
}
