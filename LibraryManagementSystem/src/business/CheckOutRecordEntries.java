package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutRecordEntries implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8525778192097574142L;
	private LocalDate checkOutDate;
	private LocalDate dueDate;
	private BookCopy bookCopy;
	
	public CheckOutRecordEntries(LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
		this.checkOutDate = checkoutDate;
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	@Override
	public String toString() {
		return String.format("CheckOutRecordEntries [checkOutDate=%s, dueDate=%s, bookCopy=%s]\n", checkOutDate, dueDate,
				bookCopy);
	}
	
	
	
}
