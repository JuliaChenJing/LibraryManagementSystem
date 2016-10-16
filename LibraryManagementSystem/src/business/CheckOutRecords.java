package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRecords implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5209738615538766072L;
	List<CheckOutRecordEntries> ce = new ArrayList<>();

	public List<CheckOutRecordEntries> getCe() 
	{
		return ce;
	}
	public void setCe(List<CheckOutRecordEntries> ce) 
	{
		this.ce = ce;
	}
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(ce);
		
	}
	

	
}
