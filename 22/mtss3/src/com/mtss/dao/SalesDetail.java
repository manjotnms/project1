package com.mtss.dao;

import java.util.List;
import java.util.Map;

import com.mtss.bean.InternalCoalTranfer;
import com.mtss.bean.Sales;


public interface SalesDetail {
	public boolean checkDoNoAvail(String doNo);
	public void saveSales(Sales sales)throws Exception;
	public Sales findSales(int doNo);
	public void removeSales(int doNo);
	public void updateSales(Sales sales);
	public void addNewTax(String taxName);
	public Map<String,String> getTaxesDetail();
	public List<String> getTaxesList();
	public void deleteTax(String taxName);
	public boolean addInternalCoalTransfer(InternalCoalTranfer ict) throws Exception;
	public int getDoNo()throws Exception;
}
