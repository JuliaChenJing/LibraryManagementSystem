package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import dao.Impl.DataAccessFacade;
import dao.Impl.TestData;
import dao.service.DataAccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Address;
import model.Auth;
import model.Book;
import model.LibraryMember;
import model.User;

public class SystemController implements ControllerInterface, Initializable {
	public static Auth currentAuth = null;
	@FXML
	private TextField userName;
	@FXML
	private TextField password;
	@FXML
	private Label errorMessage;

	public void loginCheck() throws LoginException, IOException {
		String name = userName.getText();
		String pwd = password.getText();
		login(name, pwd);
	}

	public void clear() {
		userName.setText("");
		password.setText("");
		errorMessage.setText("");
	}

	public void login(String name, String paswd) throws LoginException, IOException {
		System.out.println(name);
		System.out.println(paswd);
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(name)) {
			errorMessage.setText("*Name not found!");
			throw new LoginException("Name " + name + " not found");
		}
		String passwordFound = map.get(name).getPassword();
		if (!passwordFound.equals(paswd)) {
			errorMessage.setText("*Password not match!");
			throw new LoginException("Passord does not match password on record");
		}
		currentAuth = map.get(name).getAuthorization();
		System.out.println("Successfully login by " + currentAuth.toString());
		if (currentAuth.toString().equals("LIBRARIAN")) {
			Stage libStage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/LiberianMain.fxml"));
				libStage.setTitle("Librarian - Checkin/Checkout");
				Scene scene = new Scene(root, 1000, 800);
				libStage.setScene(scene);
				libStage.show();
				userName.getScene().getWindow().hide();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (currentAuth.toString().equals("ADMIN")) {
			Stage libStage = new Stage();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/AdministratorMain.fxml"));
				libStage.setTitle("Administrator");
				Scene scene = new Scene(root, 1000, 800);
				libStage.setScene(scene);
				libStage.show();
				userName.getScene().getWindow().hide();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method checks if memberId already exists -- if so, it cannot be
	 * added as a new member, and an exception is thrown. If new, creates a new
	 * LibraryMember based on input data and uses DataAccess to store it.
	 * 
	 */
	// commented by surendraM : this method has been moved to MemberController.java
	// public void addNewMember(String firstName, String lastName, String
	// telNumber, Address addr)
	// throws LibrarySystemException {
	//
	//
	// }

	/**
	 * Reads data store for a library member with specified id. Ids begin at
	 * 1001... Returns a LibraryMember if found, null otherwise
	 * 
	 */
	// public LibraryMember search(String memberId) {

	/**
	 * Same as creating a new member (because of how data is stored)
	 */
	// public void updateMemberInfo(String memberId, String firstName, String
	// lastName,

	/**
	 * Looks up Book by isbn from data store. If not found, an exception is
	 * thrown. If no copies are available for checkout, an exception is thrown.
	 * If found and a copy is available, member's checkout record is updated and
	 * copy of this publication is set to "not available"
	 */
	// public void checkoutBook(String memberId, String isbn) throws
	// LibrarySystemException {

	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}

	/**
	 * Looks up book by isbn to see if it exists, throw exceptioni. Else add the
	 * book to storage
	 */
	// public boolean addBook(String isbn, String title, int maxCheckoutLength,
	// List<Author> authors)
	// throws LibrarySystemException {

	public boolean addBookCopy(String isbn) throws LibrarySystemException {
		Book book = searchBook(isbn);
		if (book == null)
			throw new LibrarySystemException("No book with isbn " + isbn + " is in the library collection!");
		book.addCopy();
		return true;
	}

	// public static void main(String[] args) throws LibrarySystemException {
	//
	// }

	@Override
	public void checkoutBook(String memberId, String isbn) throws LibrarySystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TestData td = new TestData();
		td.bookData();
		td.libraryMemberData();
		td.userData();
		DataAccess da = new DataAccessFacade();
		System.out.println(da.readBooksMap());
		System.out.println(da.readUserMap());
		System.out.println(da.readMemberMap());

	}

}
