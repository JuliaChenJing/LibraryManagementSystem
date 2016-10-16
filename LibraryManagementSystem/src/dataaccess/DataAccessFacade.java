package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.BookCopy;


public class DataAccessFacade implements DataAccess {
	
	
	
	public static final String OUTPUT_DIR = System.getProperty("user.dir") 
			+ "\\src\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
		
	////specialized lookup methods
	public LibraryMember searchMember(String memberId) {
		HashMap<String,LibraryMember> libMap =  readLibraryMap();
		LibraryMember member = libMap.get(memberId);
		System.out.println(member);
		return member;
	}
	
	public Book searchBook(String isbn) {
		HashMap<String,Book> booksMap =  readBooksMap();
		Book b = booksMap.get(isbn);
		return b;
	}
	
	public Auth login(String id, String pwd) {
		HashMap<String, User> userMap = readUserMap();
		if(!userMap.containsKey(id)) return null;
		User user = userMap.get(id);
		if(!pwd.equals(user.getPassword())) {
			return null;
		}
		return user.getAuthorization();
	}
	
	
	///////save methods
	//saveNewMember
	
	public void saveNewMember(LibraryMember member){
		HashMap<String, LibraryMember> libraryMap = readLibraryMap();
		String memId = member.getMemberId();
		libraryMap.put(memId, member);
		saveToStorage(StorageType.MEMBERS, libraryMap);
	}
	public void saveNewAuthor(Author member){
		HashMap<String, Author> authormap = new HashMap();
		String memId = member.getTelephone();
		authormap.put(memId, member);
		saveToStorage(StorageType.AUTHORS, authormap);
	}
		
	
	public HashMap<String, Author> readAuthorMap() {
		// TODO Auto-generated method stub
		return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
		//return null;
	}

	public void updateMember(LibraryMember member) {
	//	LibraryMember mem =  searchMember(member.getMemberId());
		HashMap<String, LibraryMember> hmap = readLibraryMap();
	System.out.println(member.getMemberId());
		hmap.replace(member.getMemberId(), member);
		saveToStorage(StorageType.MEMBERS, hmap);
	}
	
	//save new lendable item
	public void saveNewBook(Book book) {
		HashMap<String, Book> bookMap = readBooksMap();
		String isbn = book.getIsbn();
		bookMap.put(isbn, book);
		saveToStorage(StorageType.BOOKS, bookMap);	
	}
	

	
	////// read methods that return full maps
	
	
	//@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		//if(readFromStorage(StorageType.BOOKS) instanceof HashMap<String,Book>)
		
				return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}
	
	//@SuppressWarnings("unchecked")
	public  HashMap<String,LibraryMember> readLibraryMap() {	
		return (HashMap<String,LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}
	
	//public HashMap<String, LibraryMember> readMemberMap() {
	
	
	//@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}
	
	
	/////load methods - these place test data into the storage area
	///// - used just once at startup  
	//static void loadMemberMap(List<LibraryMember> memberList) {
		
	//static
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> map = new HashMap<String, Book>();
		//extract each book in the bookList, and add (ISBN, each bookItem) as an entry in the HashMap
		bookList.forEach(book -> map.put(book.getIsbn(), book));
//		System.out.println(map);
		saveToStorage(StorageType.BOOKS, map);
	}
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> map = new HashMap<String, User>();
		userList.forEach(user -> map.put(user.getId(), user));
		saveToStorage(StorageType.USERS, map);
	}
	
	static void loadLibraryMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> map = new HashMap<String, LibraryMember>();
		memberList.forEach(lambda -> map.put(lambda.getMemberId(), lambda));
		System.out.println("--_++"+map);
		saveToStorage(StorageType.MEMBERS, map);
	}
	static	void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR+"\\"+type.toString());
			System.out.println(path);
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR+"\\"+type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}

	@Override
	public void updateBook(Book b) {
		// TODO Auto-generated method stub
		HashMap<String, Book> hmap = readBooksMap();
		//bc.changeAvailability();
		hmap.replace(b.getIsbn(), b);
		saveToStorage(StorageType.BOOKS, hmap);
	}

	public static void loadAuthorMap(List<Author> allAuthors) {
		// TODO Auto-generated method stub
		HashMap<String, Author> map = new HashMap<String, Author>();
		allAuthors.forEach(lambda -> map.put(lambda.getLastName(), lambda));
		System.out.println("--_++"+map);
		saveToStorage(StorageType.AUTHORS, map);
	}
		
}
