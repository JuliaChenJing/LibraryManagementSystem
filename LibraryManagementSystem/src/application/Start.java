package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Start extends Application {

	
	Stage librarianWindow;
	Stage theStage;
	
	//½¨Á¢µÚ¶þ¸ö´°¿Ú
	private void createSecondWindow() {
		librarianWindow = new LibrarianUI(theStage);
     
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.theStage=primaryStage;

		primaryStage.setTitle("Library Management System");
		GridPane loginGrid = new GridPane();
		loginGrid.setAlignment(Pos.CENTER);
		loginGrid.setHgap(20);
		loginGrid.setVgap(20);
		loginGrid.setPadding(new Insets(25, 25, 25, 25));


		// LALBLE
		Label loginLable = new Label("Log In:");
		loginGrid.add(loginLable, 0, 0);
		loginLable.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
		

		Label userIdLable = new Label("User ID:");
		loginGrid.add(userIdLable, 2, 1);

		Label passWordLable = new Label("Password:");
		loginGrid.add(passWordLable, 2, 3);

		Label logoutLable = new Label("Logout");
		loginGrid.add(logoutLable, 0, 6);
		logoutLable.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));

		Label informationLable = new Label("            ");
		loginGrid.add(informationLable, 0, 7);

		// BUTTON
		Button btnSubmit = new Button("Submit");
		HBox hbBtnSubmit = new HBox(10);
		hbBtnSubmit.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnSubmit.getChildren().add(btnSubmit);
		loginGrid.add(hbBtnSubmit, 2, 5);

		Button btnLogout = new Button("Logout");
		HBox hbBtnLogout = new HBox(10);
		hbBtnLogout.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnLogout.getChildren().add(btnLogout);
		loginGrid.add(hbBtnLogout, 2, 7);


		// TEXTField

		TextField userIdTextField = new TextField();
		loginGrid.add(userIdTextField, 2, 2);

		TextField passwordTextField = new TextField();
		loginGrid.add(passwordTextField, 2, 4);

		
	
		
		// Event Handler

		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// do something if the button is clicked
				if (userIdTextField.getText().equals("1")
						&& passwordTextField.getText().equals("1")) {
					informationLable.setText(("librarian login successfully"));
				
					//invoke librarian window
					//librarianWindow.show();
				}

				if (userIdTextField.getText().equals("2")
						&& passwordTextField.getText().equals("2"))
				// System.out.println("admin login successfully");
				{
					informationLable.setText(("admin login successfully"));
					//invoke admin window
					
				}

				if (userIdTextField.getText().equals("3")
						&& passwordTextField.getText().equals("3")) {
					// System.out.println("admin &librarian login successfully");
					informationLable
							.setText("admin &librarian login successfully");
			
					//invoke both window
				}

			}
		});

		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			
				informationLable.setText("log out successfully");
				

			}
		});
		
	
		
	
		// Scene scene = new Scene(loginGrid, 300, 200);
		Scene loginScene = new Scene(loginGrid);
		primaryStage.setScene(loginScene);
		primaryStage.show();
		

	
	}

}