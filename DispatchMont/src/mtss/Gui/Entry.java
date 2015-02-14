package mtss.Gui;

public class Entry {
	
	/**
	 * @author gurpreet
	 */

	private String epcNum = null;
	private String weight = null;
	
	public Entry(String epcNum, String weight) {
		super();
		this.epcNum = epcNum;
		this.weight = weight;
	}
	
	public String getEpcNum() {
		return epcNum;
	}
	
	public void setEpcNum(String epcNum) {
		this.epcNum = epcNum;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	

}
