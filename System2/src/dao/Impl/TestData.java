package dao.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Address;
import model.Auth;
import model.Author;
import model.Book;
import model.LibraryMember;
import model.User;

public class TestData {
	
	@SuppressWarnings("serial")
	List<Address> addresses = new ArrayList<Address>() {
		{
			add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
			add(new Address("51 S. George", "Georgetown", "MI", "65434"));
			add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
			add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
			add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
			add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
			add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
			add(new Address("501 Central", "Mountain View", "CA", "94707"));
		}
	};
	
	@SuppressWarnings("serial")
	List<LibraryMember> allMembers = new ArrayList<LibraryMember>() {
		{
			add(new LibraryMember("1", "Alex", "Black", "641-232-2388", addresses.get(0)));
			add(new LibraryMember("2", "Bob", "Hanson", "641-232-2388", addresses.get(1)));
			add(new LibraryMember("3", "Eve", "Torres", "641-232-2388", addresses.get(1)));
			add(new LibraryMember("4", "Miley", "Cyrus", "641-232-2388", addresses.get(0)));
		}
	};
	
	@SuppressWarnings("serial")
	public List<Author> allAuthors = new ArrayList<Author>() {
		{
			add(new Author("Joe", "Thomas", "641-445-2123", addresses.get(0), "A happy man is he."));
			add(new Author("Sandra", "Thomas", "641-445-2123", addresses.get(0), "A happy wife is she."));
			add(new Author("Nirmal", "Pugh", "641-919-3223", addresses.get(1), "Thinker of thoughts."));
			add(new Author("Andrew", "Cleveland", "976-445-2232", addresses.get(2), "Author of childrens' books."));
			add(new Author("Sarah", "Connor", "123-422-2663", addresses.get(3), "Known for her clever style."));
		}
	};

	// Book(int id, String isbn, String title, int maxCheckoutLength,
	// List<Author> authors)
	@SuppressWarnings("serial")
	List<Book> allBooks = new ArrayList<Book>() {
		{
			add(new Book("23-11451", "The Big Fish", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
			add(new Book("28-12331", "Antartica", 7, Arrays.asList(allAuthors.get(2))));
			add(new Book("99-22223", "Thinking Java", 21, Arrays.asList(allAuthors.get(3))));
			add(new Book("48-56882", "Jimmy's First Day of School", 7, Arrays.asList(allAuthors.get(4))));

		}
	};

	// List<CheckoutRecord> allRecords = new ArrayList<CheckoutRecord>() {
	// {
	// add(new CheckoutRecord());
	// add(new CheckoutRecord());
	// add(new CheckoutRecord());
	// add(new CheckoutRecord());
	// add(new CheckoutRecord());
	// add(new CheckoutRecord());
	// add(new CheckoutRecord());
	// }
	// };
	@SuppressWarnings("serial")
	List<User> allUsers = new ArrayList<User>() {
		{
			add(new User("librarian", "librarian", Auth.LIBRARIAN));
			add(new User("admin", "admin", Auth.ADMIN));
			add(new User("both", "both", Auth.BOTH));
		}
	};

	/// create books
	public void bookData() {
		allBooks.get(0).addCopy();
		allBooks.get(0).addCopy();
		allBooks.get(1).addCopy();
		allBooks.get(3).addCopy();
		allBooks.get(2).addCopy();
		allBooks.get(2).addCopy();
		DataAccessFacade.loadBookMap(allBooks);
	}

	public void userData() {
		DataAccessFacade.loadUserMap(allUsers);
	}

	/*
	 * public void checkoutRecordData() {
	 * allRecords.get(0).addEntry(allEntries.get(0));
	 * allRecords.get(0).addEntry(allEntries.get(4));
	 * allRecords.get(1).addEntry(allEntries.get(1));
	 * allRecords.get(1).addEntry(allEntries.get(5));
	 * allRecords.get(2).addEntry(allEntries.get(2));
	 * allRecords.get(2).addEntry(allEntries.get(6));
	 * allRecords.get(3).addEntry(allEntries.get(3));
	 * allRecords.get(3).addEntry(allEntries.get(7)); }
	 */

	// create library members

	 public void libraryMemberData() {
		 DataAccessFacade.loadMemberMap(allMembers);
	 }

}
