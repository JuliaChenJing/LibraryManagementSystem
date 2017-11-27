package business;

import java.time.LocalDate;

public class CheckOutRecordEntries {
	private LocalDate checkOutDate;
	private LocalDate dueDate;
	private BookCopy bookCopy;
	
	public CheckOutRecordEntries(LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
		this.checkOutDate = checkoutDate;
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
	}
	
}
