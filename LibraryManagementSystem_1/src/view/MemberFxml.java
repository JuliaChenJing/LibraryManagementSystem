package view;

import java.net.URL;
import java.util.ResourceBundle;

import controller.MemberController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.LibraryMember;

public class MemberFxml implements Initializable{
	@FXML
	private TextField id;
	@FXML
	private TextField name;
	
	public void submit(){
		try{
		//	LibraryMember member = new LibraryMember(Integer.parseInt(id.getText()), name.getText());
		//	new MemberController().addMember(member);
			
			//Success Message 
			
		}catch(Exception e){
			
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setText("Hello");
	}

}
