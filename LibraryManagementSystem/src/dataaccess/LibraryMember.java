package dataaccess;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import business.Address;
import business.BookCopy;
import business.CheckOutRecordEntries;
import business.CheckOutRecords;
import business.Person;
;

public class LibraryMember extends Person implements Serializable{
	
	private static final long serialVersionUID = 1234234234230073L;
	private String memberId;
	private CheckOutRecords checkoutrecords=new CheckOutRecords();
	
	public LibraryMember(String m, String f, String l, String t, Address a) {
		super(f, l, t, a);
		// TODO Auto-generated constructor stub
		memberId = m;
	}
	public String getMemberId() {
		return memberId;
	}
	public void checkOut(BookCopy copy, LocalDate today, int checkOutLength){
		if(copy.isAvailable())
		{
			CheckOutRecordEntries c = new CheckOutRecordEntries(today, today.plusDays(checkOutLength), copy); 
			
			List<CheckOutRecordEntries> records = checkoutrecords.getCe();
			//System.out.println(c);
			records.add(c);
			checkoutrecords.setCe(records);
			//setCheckoutrecords(records);
			
		}
	}
	
	
		public void setCheckoutrecords(CheckOutRecords checkoutrecords) {
		this.checkoutrecords = checkoutrecords;
	}
		public CheckOutRecords getCheckoutrecords() {
		return checkoutrecords;
	}
		@Override
	public String toString() {
		return "LibraryMember [memberId=" + memberId + ", getFirstName()=" + getFirstName() + "]";
	}
		public static void main(String[] args) {
		LibraryMember l = new LibraryMember("1", "22", "www", "ee", new Address("22", "33", "323", "2312"));
		System.out.println(l.getMemberId());
	}
		
	
}
