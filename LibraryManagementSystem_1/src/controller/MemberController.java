package controller;

import java.util.HashMap;

import dao.Impl.DataAccessFacade;
import dao.Impl.MemberServiceImpl;
import dao.service.DataAccess;
import dao.service.MemberService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Address;
import model.LibraryMember;

public class MemberController {
	@FXML
	TextField memberID, firstName, lastName, telephone, street, city, state, zip;
	@FXML
	Label memberIdError;
	@FXML
	TextField mbrId;
	@FXML
	Button btnThankYou;
	MemberService service = new MemberServiceImpl();
	

	public void addMember() throws Exception {
		addNewMember(memberID.getText(), firstName.getText(), lastName.getText(), telephone.getText(),
				new Address(street.getText(), city.getText(), state.getText(), zip.getText()));
		
		Parent root=FXMLLoader.load(getClass().getResource("../view/AddedMemberDone.fxml"));
		Stage newStage=new Stage();
		newStage.setTitle("Librarian - Checkin/Checkout");
		Scene scene = new Scene(root, 374.0, 135.0);
		newStage.setScene(scene);
		newStage.show();
		memberID.setText("");
		firstName.setText("");lastName.setText("");
		telephone.setText("");
		street.setText("");city.setText("");
		state.setText("");
		zip.setText("");
		memberID.getScene().getWindow().hide();
		
		
	}
	
	public void AddedDone(){
		btnThankYou.getScene().getWindow().hide();
		
	}

	public void search() throws Exception {
		search(mbrId.getText());
	}

	// Use Factory Method to call the servie of dao layer
	// public void addMember(LibraryMember member) throws Exception {
	// // Adding Member
	// service.saveMember(member);
	// }

	/**
	 * This method checks if memberId already exists -- if so, it cannot be
	 * added as a new member, and an exception is thrown. If new, creates a new
	 * LibraryMember based on input data and uses DataAccess to store it.
	 * 
	 * @throws Exception
	 * 
	 */
	public void addNewMember(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws Exception {
		DataAccess dataAccess = new DataAccessFacade();
		HashMap<String, LibraryMember> memberMap = dataAccess.readMemberMap();
		if (!memberMap.containsKey(memberId)) {
			memberIdError.setText("");
			service.saveMember(new LibraryMember(memberId, firstName, lastName, telNumber, addr));
		} else {
			memberIdError.setText("Member with Id# " + memberID.getText() + " already exists!");
		}

	}

	/**
	 * Reads data store for a library member with specified id. Ids begin at
	 * 1001... Returns a LibraryMember if found, null otherwise
	 * 
	 * @throws Exception
	 * 
	 */
	public LibraryMember search(String memberId) throws Exception {
		MemberService ms = new MemberServiceImpl();
		return ms.searchMember(memberId);
	}

}
