package com.mtss.daoImpl;

import java.sql.Date;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mtss.bean.Sales;
import com.mtss.dao.SalesDetail;

public class SalesDetailImplement implements SalesDetail {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void saveSales(Sales sales) throws Exception {
		// TODO Auto-generated method stub
		//String UPDATE_SALES="insert into salesdetail values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
		/*Date date = new Date(12012015);
		
		sales.setDoDate(date.toString());
		sales.setApplDate(date.toString());
		sales.setDoDate(date.toString());
		sales.setDoDate(date.toString());
		sales.setDoEndDate(date.toString());*/
		
		
		/*Object salesDetails[] = {sales.getDoNo(),sales.getWayBridgeId(),sales.getPurchaserName(),sales.getDestination(),sales.getStateCode(),sales.getGrade(),sales.getDoDate(),sales.getApplNo(),sales.getApplDate(),sales.getDoQty(),
								 sales.getDraftNo1(),sales.getDraftDt1(),sales.getDraftAmt1(),sales.getBank1(), sales.getDraftNo2(),sales.getDraftDt2(),sales.getDraftAmt2(),sales.getBank2(), sales.getDraftNo3(),sales.getDraftDt3(),
								 sales.getDraftAmt3(),sales.getBank3(),sales.getQtyBalance(),sales.getTextType(),sales.getTextPercent(),sales.getCustCd(),sales.getExcRegNo(),sales.getRange(),sales.getDivision(),sales.getCommission(),
								 sales.getVattinNo(),sales.getCstNo(),sales.getBasicRate(),sales.getRoyalty(),sales.getSed(),sales.getCleanEngyCess(),sales.getWeighMeBt(),sales.getSlc(),sales.getWrc(),sales.getBazarFee(),
								 sales.getPan(),sales.getCentExcRate(),sales.getEduCessRate(),sales.getHighEduRate(),sales.getDoStartDate(),sales.getDoEndDate(),sales.getRoadCess(),sales.getAmbhCess()};
		System.out.println("Update Sales...................."+sales.getDoNo());*/
		
		String UPDATE_SALES="insert into salesDemo(doNo,wayBridgeId,purchaserName,destination,stateCode,grade,doDate,applNo,applDate,doQty,draftNo1,draftDt1,draftAmt1,bank1,draftNo2,draftDt2,draftAmt2,bank2,draftNo3,draftDt3,draftAmt3,bank3,qtyBalance,textType,custCd,excRegNo,range,division,commission,vattinNo,cstNo,basicRate,pan,doStartDate,doEndDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//String UPDATE_SALES="insert into salesDetail(doNo,destination,stateCode,grade) values(?,?,?,?)";
		
		Object salesDetails[] = {sales.getDoNo(),sales.getWayBridgeId(),sales.getPurchaserName(),sales.getDestination(),sales.getStateCode(),sales.getGrade(),sales.getDoDate(),sales.getApplNo(),sales.getApplDate(),sales.getDoQty(),
				                 sales.getDraftNo1(),sales.getDraftDt1(),sales.getDraftAmt1(),sales.getBank1(),sales.getDraftNo2(),sales.getDraftDt2(),sales.getDraftAmt2(),sales.getBank2(),sales.getDraftNo3(),sales.getDraftDt3(),sales.getDraftAmt3(),sales.getBank3(),
				                 sales.getQtyBalance(),sales.getTaxtType(),sales.getCustCd(),sales.getExcRegNo(),sales.getRange(),sales.getDivision(),sales.getCommission(),sales.getVattinNo(),sales.getCstNo(),
				                 sales.getBasicRate(),sales.getPan(),sales.getDoStartDate(),sales.getDoEndDate()};
		jdbcTemplate.update(UPDATE_SALES,salesDetails);
		
		//Update Taxes and Prices
		Object taxesAndPrice[]={sales.getTaxPercent(),sales.getRoyalty(),sales.getSed(),sales.getCleanEngyCess(),sales.getWeighMeBt(),sales.getSlc(),sales.getWrc(),
				                sales.getBazarFee(),sales.getCentExcRate(),sales.getEduCessRate(),sales.getHighEduRate(),sales.getRoadCess(),sales.getAmbhCess(),sales.getDoNo(),sales.getId()};
		
		
		String UPDATE_TAX_PRICE = "insert into tax_and_price(royalty,sed,cleanEngyCess,weighMeBt,slc,wrc,bazarFee,centExcRate,eduCessRate,highEduRate,roadCess,ambhCess,doNo,id values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) where id=?)";
		jdbcTemplate.update(UPDATE_TAX_PRICE,taxesAndPrice);
	}

	@Override
	public Sales findSales(int doNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSales(int doNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSales(Sales sales) {
		// TODO Auto-generated method stub

	}

}
