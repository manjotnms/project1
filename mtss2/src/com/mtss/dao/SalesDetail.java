package com.mtss.dao;

import java.util.List;
import java.util.Map;

import com.mtss.bean.Sales;


public interface SalesDetail {
	public void saveSales(Sales sales)throws Exception;
	public Sales findSales(int doNo);
	public void removeSales(int doNo);
	public void updateSales(Sales sales);
	public void addNewTax(String taxName);
	public Map<String,String> getTaxesDetail();
	public List<String> getTaxesList();
	public void deleteTax(String taxName);
}
