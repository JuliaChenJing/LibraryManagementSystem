package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.LibraryMember;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	/**
	 * 
	 */
//	private static final long serialVersionUID = -5556150044061540965L;

	public static Auth currentAuth = null;
	
	DataAccess abc=new DataAccessFacade();
	
	@Override
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Passord does not match password on record");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	
	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}
	
	public LibraryMember searchMember(String memberId) {
		DataAccess da = new DataAccessFacade();
		return da.searchMember(memberId);
	}
	
	public boolean addBookCopy(String isbn) throws LibrarySystemException {
		Book book = searchBook(isbn);
		if(book == null) throw new LibrarySystemException("No book with isbn " + isbn 
			+ " is in the library collection!");
		book.addCopy();
		return true;
	}
	
	@Override
	public void checkoutBook(String memberId, String isbn)
			throws LibrarySystemException {
		// TODO Auto-generated method stub
		Book b;
		LibraryMember lm;
		lm = abc.searchMember(memberId);
		System.out.println(lm);
		b = searchBook(isbn);
		
		
		//DataAccess da = new DataAccessFacade();
		try{
		
		BookCopy bc = b.getNextAvailableCopy();
		
		lm.checkOut(bc, LocalDate.now(), b.getMaxCheckoutLength());
		bc.changeAvailability();
		
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			System.out.println(""+abc+lm);
		abc.updateMember(lm);
		System.out.println("something");
		
		abc.updateBook(b);
		}
		
	}
	
}
