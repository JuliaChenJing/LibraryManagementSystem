package application;

import dataaccess.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import application.test.TableData;
import business.Address;
import business.Author;
import business.Book;
import business.CheckOutRecordEntries;
import business.CheckOutRecords;
import business.LibrarySystemException;
import business.SystemController;

public class UI extends Application {

	DataAccessFacade df  = new DataAccessFacade();
	DataAccessFacade lm=new DataAccessFacade();
	
	SystemController sc = new SystemController();
	
	String Title = "LMS";
	// master pane,show the border of the window
	BorderPane border = new BorderPane();

	// the pane in the left
	FlowPane menu = new FlowPane();

	// all the panes in the middle
	GridPane login = new GridPane();

	Auth auth=Auth.NONE;

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// stage is like window
		primaryStage.setTitle(Title);
		

		// initiate the pane
		login = getLoginPane();
		menu = getMenuFlow();

		// set the small pane on the background border
		border.setCenter(login);
		border.setLeft(menu);
		//getLibraryMemberTable();
		// show the window
		
		Scene loginScene = new Scene(border);
		File f = new File("application.css");
		loginScene.getStylesheets().clear();
		loginScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		//DynamicCSS.class.getResource("/jarcss.css").toExternalForm();
		//loginScene.getStylesheets();
		// right=loginScene;
		primaryStage.setScene(loginScene);
		
		primaryStage.show();

	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public GridPane getLoginPane() {

		GridPane loginGrid = new GridPane();
		loginGrid.setAlignment(Pos.CENTER);
		loginGrid.setHgap(20);
		loginGrid.setVgap(20);
		loginGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE
		// Label loginLable = new Label("Log In:");
		// loginGrid.add(loginLable, 0, 0);
		// loginLable.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));

		Label userIdLable = new Label("User ID:");
		loginGrid.add(userIdLable, 2, 1);

		Label passWordLable = new Label("Password:");
		loginGrid.add(passWordLable, 2, 3);

		Label informationLable = new Label("            ");
		loginGrid.add(informationLable, 0, 7);
		informationLable.setTextFill(Color.RED);  

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

		// loginGrid.add
		// TEXTField

		TextField userIdTextField = new TextField();
		loginGrid.add(userIdTextField, 2, 2);

		TextField passwordTextField = new TextField();
		loginGrid.add(passwordTextField, 2, 4);

		// Event Handler

		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				//while (true)
				//try {
				while(auth.equals(Auth.NONE))
				{	auth = df.login(userIdTextField.getText(), passwordTextField.getText());
				 
					// TODO Auto-generated catch block
				if(auth.equals(Auth.NONE))
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Something wrong with login info");
					alert.setContentText("Write your username and Password Correctly");

					alert.showAndWait();
				//	e.printStackTrace();
				}
				}
			//}
				
				
			
					border.setCenter(getWelcomeLibrarian("Welcome to the Library Management System "));
					
			
				border.setLeft(getMenuFlow());

			}

		});

		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				informationLable.setText("log out successfully");
				auth =Auth.NONE;

				border.setLeft(getMenuFlow());
			}
		});

		return loginGrid;

	}

	public GridPane getWelcomeLibrarian(String text) {
		// TODO Auto-generated method stub

		GridPane test = new GridPane();
		test.setAlignment(Pos.CENTER);
		test.setHgap(20);
		test.setVgap(20);
		test.setPadding(new Insets(25, 25, 25, 25));
		Label Wel = new Label(text);
		test.getChildren().add(Wel);
		return test;

	}

	public GridPane getCheckoutPane() {
		GridPane checkoutGrid = new GridPane();
		checkoutGrid.setAlignment(Pos.CENTER);
		checkoutGrid.setHgap(20);
		checkoutGrid.setVgap(20);
		checkoutGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE

		Label memberIDLable = new Label("Library Member ID:");
		checkoutGrid.add(memberIDLable, 1, 0);

		Label bookTitleLable = new Label("Book Title:");
		checkoutGrid.add(bookTitleLable, 1, 1);

		Label copyNumberLable = new Label("Copy Number:");
		checkoutGrid.add(copyNumberLable, 1, 2);

		Label informationLable = new Label(
				"information:if the book is available or if one is a library member  ");
		checkoutGrid.add(informationLable, 1, 4);
		informationLable.setTextFill(Color.RED);  

		// TEXTField
		TextField memberIDTextField = new TextField();
		checkoutGrid.add(memberIDTextField, 2, 0);

		TextField bookIsbnField = new TextField();
		checkoutGrid.add(bookIsbnField, 2, 1);

		//TextField copyNumberTextField = new TextField();
		//checkoutGrid.add(copyNumberTextField, 2, 2);

		// Button
		Button btnCheckCopyStatus = new Button("Check Book Copy Status");
		HBox hbBtnAddCopy = new HBox(10);
		hbBtnAddCopy.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddCopy.getChildren().add(btnCheckCopyStatus);
		checkoutGrid.add(hbBtnAddCopy, 2, 3);

		Button btnBorrow = new Button("Borrow");
		HBox hbBtnBorrow = new HBox(10);
		hbBtnBorrow.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnBorrow.getChildren().add(btnBorrow);
		checkoutGrid.add(hbBtnBorrow, 2, 4);
		btnBorrow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					sc.checkoutBook(memberIDTextField.getText(), bookIsbnField.getText());
					informationLable.setText("Checkout successfully");
				} catch (LibrarySystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//auth = null;

				border.setLeft(getMenuFlow());
			}
		});
		/*btnBorrow.setOnAction(lambda->{
			try {
				sc.checkoutBook(memberIDTextField.getText(), bookIsbnField.getText());
			} catch (LibrarySystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
*/
		return checkoutGrid;
	}

	public FlowPane getMenuFlow() {
		// TODO Auto-generated method stub
		// menuGrid=m;
		FlowPane menuGrid = new FlowPane();
		menuGrid.setVgap(8);
		menuGrid.setHgap(4);
		menuGrid.setPrefWrapLength(50);
		menuGrid.setPadding(new Insets(25, 25, 25, 25));

		Button btnLog = new Button("Log in / out");
		menuGrid.getChildren().add(btnLog);
		// btnLog.setDisable(true);
		btnLog.setOnAction(evt -> {
			border.setCenter(getLoginPane());
		//	border.setLeft(getMenuFlow());
		});

		Button btn0 = new Button("Add Member");
		menuGrid.getChildren().add(btn0);
		btn0.setDisable(true);
		btn0.setOnAction(evt -> border.setCenter(getAddMemberPane()));

		Button btn1 = new Button("Search/Edit Member");
		menuGrid.getChildren().add(btn1);
		btn1.setDisable(true);
		btn1.setOnAction(evt -> border.setCenter(getSearchMemberPane()));

		Button btn2 = new Button("Check Out");
		// HBox hbBtn2 = new HBox(10);
		// hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		// hbBtn2.getChildren().add(btn2);
		menuGrid.getChildren().add(btn2);
		btn2.setDisable(true);
		btn2.setOnAction(evt -> border.setCenter(getCheckoutPane()));

		Button btn3 = new Button("Add Book");
		// HBox hbBtn3 = new HBox(10);
		// hbBtn3.setAlignment(Pos.BOTTOM_RIGHT);
		// hbBtn3.getChildren().add(btn3);
		// menuGrid.add(hbBtn3, 0, 4);
		menuGrid.getChildren().add(btn3);
		btn3.setDisable(true);
		btn3.setOnAction(evt -> border.setCenter(getAddBookPane()));

		Button btn4 = new Button("Add a Copy");
		// HBox hbBtn4 = new HBox(10);
		// hbBtn4.setAlignment(Pos.BOTTOM_RIGHT);
		// hbBtn4.getChildren().add(btn4);
		// menuGrid.add(hbBtn4, 0, 5);
		menuGrid.getChildren().add(btn4);
		btn4.setDisable(true);
		btn4.setOnAction(evt -> border.setCenter(getAddCopyPane()));

		Button btn5 = new Button("Check Copy Status");
		// HBox hbBtn5 = new HBox(10);
		// hbBtn5.setAlignment(Pos.BOTTOM_RIGHT);
		// hbBtn5.getChildren().add(btn5);
		// menuGrid.add(hbBtn5, 0, 6);
		menuGrid.getChildren().add(btn5);
		btn5.setDisable(true);
		// return menuGrid;
		btn5.setOnAction(evt -> border.setCenter(getCheckCopyStatusPane()));

		// };
		Button btn6 = new Button("Reset UI");
		// HBox hbBtn5 = new HBox(10);
		// hbBtn5.setAlignment(Pos.BOTTOM_RIGHT);
		// hbBtn5.getChildren().add(btn5);
		// menuGrid.add(hbBtn5, 0, 6);
		menuGrid.getChildren().add(btn6);
		btn6.setDisable(false);
		// return menuGrid;
		//GridPane blank=new GridPane();
		btn6.setOnAction(evt -> border.setBottom(getBlankPane()));

		// };
		switch (auth) {
		
		case LIBRARIAN:

		{
			btn0.setDisable(true);
			btn1.setDisable(true);
			btn2.setDisable(false);
			btn3.setDisable(true);
			btn4.setDisable(true);
			btn5.setDisable(false);
			break;
		}

		case ADMIN:

		{
			btn0.setDisable(false);
			btn1.setDisable(false);
			btn2.setDisable(true);
			btn3.setDisable(false);
			btn4.setDisable(false);
			btn5.setDisable(true);
			break;
		}
		case BOTH:

		{
			btn0.setDisable(false);
			btn1.setDisable(false);
			btn2.setDisable(false);
			btn3.setDisable(false);
			btn4.setDisable(false);
			btn5.setDisable(false);
			break;
		}
		default: {
			btn0.setDisable(true);
			btn1.setDisable(true);
			btn2.setDisable(true);
			btn3.setDisable(true);
			btn4.setDisable(true);
			btn5.setDisable(true);
			break;
		}

		}

		return menuGrid;
	}

	private GridPane getBlankPane() {
		// TODO Auto-generated method stub
		GridPane blank = new GridPane();
		return blank;
		
	}

	public GridPane getAddMemberPane() {
		GridPane addGrid = new GridPane();
		addGrid.setAlignment(Pos.CENTER);
		addGrid.setHgap(20);
		addGrid.setVgap(20);
		addGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE

		Label firstnameLable = new Label("First Name:");
		addGrid.add(firstnameLable, 1, 1);

		Label lastnameLable = new Label("Last Name:");
		addGrid.add(lastnameLable, 1, 2);

		Label streetLable = new Label("Street:");
		addGrid.add(streetLable, 1, 3);

		Label cityLable = new Label("City:");
		addGrid.add(cityLable, 1, 4);

		Label stateLable = new Label("State:");
		addGrid.add(stateLable, 1, 5);

		Label zipLable = new Label("Zip:");
		addGrid.add(zipLable, 1, 6);

		Label cellLable = new Label("Cell:");
		addGrid.add(cellLable, 1, 7);

		Label informationLable = new Label("information needed to show:");
		addGrid.add(informationLable, 1, 9);
		informationLable.setTextFill(Color.RED);  
		
		// TEXTField

		TextField firstnameTextField = new TextField();
		addGrid.add(firstnameTextField, 2, 1);

		TextField lastnameTextField = new TextField();
		addGrid.add(lastnameTextField, 2, 2);

		TextField streetTextField = new TextField();
		addGrid.add(streetTextField, 2, 3);

		TextField cityTextField = new TextField();
		addGrid.add(cityTextField, 2, 4);

		TextField stateTextField = new TextField();
		addGrid.add(stateTextField, 2, 5);

		TextField zipTextField = new TextField();
		addGrid.add(zipTextField, 2, 6);

		TextField cellTextField = new TextField();
		addGrid.add(cellTextField, 2, 7);
		
		Label memidLabel=new Label("Member ID:");
		addGrid.add(memidLabel, 1, 0);
		TextField memId = new TextField();
		addGrid.add(memId, 2, 0);

		// Button
		Button btnAddLibraryMember = new Button("Add New Library Member");
		HBox hbBtnUpdate = new HBox(10);
		hbBtnUpdate.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnUpdate.getChildren().add(btnAddLibraryMember);
		addGrid.add(hbBtnUpdate, 2, 8);

		// event controller
		btnAddLibraryMember.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// do something if the button is clicked
				
				Address a = new Address(streetTextField.getText(), cityTextField.getText(), stateTextField.getText(), zipTextField.getText());
				//String m = 
				try {
					df.saveNewMember(new LibraryMember(memId.getText(), firstnameTextField.getText(), lastnameTextField.getText(), cellTextField.getText(), a));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			informationLable.setText("library member added successfully");
			}

		});

		return addGrid;
	}
/*public GridPane getSearchGridPane()
{
	//primaryStage.setTitle("Library Management System");
	
	GridPane searchGrid = new GridPane();
	searchGrid.setAlignment(Pos.CENTER);
	searchGrid.setHgap(20);
	searchGrid.setVgap(20);
	searchGrid.setPadding(new Insets(25, 25, 25, 25));
	
	// LALBLE
	Label idLable = new Label("ID:");
	searchGrid.add(idLable, 1, 0);

	Label firstnameLable = new Label("First Name:");
	searchGrid.add(firstnameLable, 1, 1);

	Label lastnameLable = new Label("Last Name:");
	searchGrid.add(lastnameLable, 1, 2);

	Label streetLable = new Label("Street:");
	searchGrid.add(streetLable, 1, 3);

	Label cityLable = new Label("City:");
	searchGrid.add(cityLable, 1, 4);

	Label stateLable = new Label("State:");
	searchGrid.add(stateLable, 1, 5);

	Label zipLable = new Label("Zip:");
	searchGrid.add(zipLable, 1, 6);

	Label cellLable = new Label("Cell:");
	searchGrid.add(cellLable, 1, 7);

	// TEXTField

	TextField idTextField = new TextField();
	searchGrid.add(idTextField, 2, 0);
	// idTextField.setVisible(false);

	TextField firstnameTextField = new TextField();
	searchGrid.add(firstnameTextField, 2, 1);

	TextField lastnameTextField = new TextField();
	searchGrid.add(lastnameTextField, 2, 2);

	TextField streetTextField = new TextField();
	searchGrid.add(streetTextField, 2, 3);

	TextField cityTextField = new TextField();
	searchGrid.add(cityTextField, 2, 4);

	TextField stateTextField = new TextField();
	searchGrid.add(stateTextField, 2, 5);

	TextField zipTextField = new TextField();
	searchGrid.add(zipTextField, 2, 6);

	TextField cellTextField = new TextField();
	searchGrid.add(cellTextField, 2, 7);
	
	Button btnSearch = new Button("Search");
	HBox hbBtnSearch = new HBox(10);
	hbBtnSearch.setAlignment(Pos.BOTTOM_RIGHT);
	hbBtnSearch.getChildren().add(btnSearch);
	searchGrid.add(hbBtnSearch, 3, 0);
	
	Button btnUpdate = new Button("Update");
	HBox hbBtnUpdate = new HBox(10);
	hbBtnUpdate.setAlignment(Pos.BOTTOM_RIGHT);
	hbBtnUpdate.getChildren().add(btnUpdate);
	searchGrid.add(hbBtnUpdate, 2, 8);
	searchGrid.add(getLibraryMemberTable(idTextField.getText()),2,9);
	//searchGrid.add();
	return searchGrid;

	// Scene scene = new Scene(loginGrid, 300, 200);
	//Scene searchScene = new Scene(searchGrid);
	//primaryStage.setScene(searchScene);
	//primaryStage.show();
}*/
	public GridPane getSearchMemberPane() {
		GridPane searchGrid = new GridPane();
		searchGrid.setAlignment(Pos.CENTER);
		searchGrid.setHgap(20);
		searchGrid.setVgap(20);
		searchGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE
		Label idLable = new Label("ID:");
		searchGrid.add(idLable, 1, 0);

		Label firstnameLable = new Label("First Name:");
		searchGrid.add(firstnameLable, 1, 1);

		Label lastnameLable = new Label("Last Name:");
		searchGrid.add(lastnameLable, 1, 2);

		Label streetLable = new Label("Street:");
		searchGrid.add(streetLable, 1, 3);

		Label cityLable = new Label("City:");
		searchGrid.add(cityLable, 1, 4);

		Label stateLable = new Label("State:");
		searchGrid.add(stateLable, 1, 5);

		Label zipLable = new Label("Zip:");
		searchGrid.add(zipLable, 1, 6);

		Label cellLable = new Label("Cell:");
		searchGrid.add(cellLable, 1, 7);

		Label informationLable = new Label(
				"you could print the informaiton  needed here");
		searchGrid.add(informationLable, 1, 9);
		informationLable.setTextFill(Color.RED);  
		// TEXTField

		TextField idTextField = new TextField();
		searchGrid.add(idTextField, 2, 0);
		// idTextField.setVisible(false);

		TextField firstnameTextField = new TextField();
		searchGrid.add(firstnameTextField, 2, 1);

		TextField lastnameTextField = new TextField();
		searchGrid.add(lastnameTextField, 2, 2);

		TextField streetTextField = new TextField();
		searchGrid.add(streetTextField, 2, 3);

		TextField cityTextField = new TextField();
		searchGrid.add(cityTextField, 2, 4);

		TextField stateTextField = new TextField();
		searchGrid.add(stateTextField, 2, 5);

		TextField zipTextField = new TextField();
		searchGrid.add(zipTextField, 2, 6);

		TextField cellTextField = new TextField();
		searchGrid.add(cellTextField, 2, 7);

		Button btnSearch = new Button("Search");
		HBox hbBtnSearch = new HBox(10);
		hbBtnSearch.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnSearch.getChildren().add(btnSearch);
		searchGrid.add(hbBtnSearch, 3, 0);

		
		Button btncheckoutDetails = new Button("Show Details");
		HBox hbBtncheckoutDetails= new HBox(10);
		hbBtnSearch.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnSearch.getChildren().add(btncheckoutDetails);
		searchGrid.add(hbBtncheckoutDetails, 4, 0);
		btncheckoutDetails.setOnAction(new EventHandler<ActionEvent>()
				{
				@Override
				public void handle(ActionEvent event) {
				
				border.setBottom(getLibraryMemberTable(idTextField.getText()));
				}
		
	});
		
		
		Button btnUpdate = new Button("Update");
		HBox hbBtnUpdate = new HBox(10);
		hbBtnUpdate.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnUpdate.getChildren().add(btnUpdate);
		searchGrid.add(hbBtnUpdate, 2, 8);
		btnUpdate.setDisable(true);
		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// do something if the button is clicked
				
				Address a = new Address(streetTextField.getText(), cityTextField.getText(), stateTextField.getText(), zipTextField.getText());
				LibraryMember member=new LibraryMember(idTextField.getText(), firstnameTextField.getText(), lastnameTextField.getText(),
						cellTextField.getText(), a);
				//String m = 
				try {
				HashMap<String,LibraryMember> test=df.readLibraryMap();
				LibraryMember nn=test.get(member.getMemberId());
				nn.setAddress(a);
				nn.setFirstName(member.getFirstName());
				nn.setLastName(member.getLastName());
				nn.setTelephone(member.getTelephone());
					df.updateMember(nn);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			informationLable.setText("library member added successfully");
			}

		});
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// do something if the button is clicked
				LibraryMember lm = df.searchMember(idTextField.getText());
				System.out.println("ccheck"+lm);
				idTextField.setText(lm.getMemberId());
				firstnameTextField.setText(lm.getFirstName());
				lastnameTextField.setText(lm.getLastName());
				streetTextField.setText(lm.getAddress().getStreet());
				cellTextField.setText(lm.getTelephone());
				zipTextField.setText(lm.getAddress().getZip());
				stateTextField.setText(lm.getAddress().getState());
				cityTextField.setText(lm.getAddress().getCity());
				btnUpdate.setDisable(false);
				idTextField.setEditable(false);
				
			//informationLable.setText("library member added successfully");
			}

			
		});
		
		//Stage something=new Stage();
		//something.setScene(getLibraryMemberTable());
		return searchGrid;

	}
	public TableView getLibraryMemberTable(String user) {
		TableView table = new TableView();
		table.setEditable(true);
		
		
		DataAccessFacade lm=new DataAccessFacade();
		HashMap<String, LibraryMember> libmem=lm.readLibraryMap();
		System.out.println("----------");
		System.out.println(libmem.entrySet());
		ObservableList<TableData> data = FXCollections.observableArrayList();
			LibraryMember ll=libmem.get(user);
		
				CheckOutRecords some=ll.getCheckoutrecords();
				List<CheckOutRecordEntries> test=some.getCe();
				TableData t;
				for(CheckOutRecordEntries chk: test)
					{
					
					
			t=new TableData(chk.getBookCopy().getBook().getTitle(),chk.getBookCopy().getBook().getIsbn(),Integer.toString(chk.getBookCopy().getCopyNum()),chk.getCheckOutDate().toString(),chk.getCheckOutDate().plusDays(chk.getBookCopy().getBook().getMaxCheckoutLength()).toString());
					
			data.add(t);
					}
			
		
		TableColumn bookNameCol = new TableColumn("Book Name");  
        bookNameCol.setMinWidth(200);  
        bookNameCol.setCellValueFactory(  
                new PropertyValueFactory<>("bookName"));  
   
        TableColumn isbnCol = new TableColumn("ISBN");  
        isbnCol.setMinWidth(200);  
        isbnCol.setCellValueFactory(  
                new PropertyValueFactory<>("ISBN"));  
   
        TableColumn copyNumCol = new TableColumn("Book Copy Number");  
        copyNumCol.setMinWidth(200);  
        copyNumCol.setCellValueFactory(  
                new PropertyValueFactory<>("copyNum"));  
        
        TableColumn checkoutDateCol = new TableColumn("Checkout Date");  
        checkoutDateCol.setMinWidth(200);  
        checkoutDateCol.setCellValueFactory(  
                new PropertyValueFactory<>("checkoutDate"));  
   
        TableColumn dueDateCol = new TableColumn("Due Date");  
        dueDateCol.setMinWidth(200);  
        dueDateCol.setCellValueFactory(  
                new PropertyValueFactory<>("dueDate"));  
        
        
        //set data to table
        table.setItems(data);  
        table.getColumns().addAll(bookNameCol,isbnCol, copyNumCol,checkoutDateCol,dueDateCol);  
		return table;
		
	}
	
	public static class TableData {  
	    private final SimpleStringProperty bookName;  
	    private final SimpleStringProperty isbn;  
	    private final SimpleStringProperty copyNum;  
	    private final SimpleStringProperty checkoutDate;  
	    private final SimpleStringProperty dueDate;  
	    
	   
	    private TableData(String b, String i, String c,String checkoutDate,String dueDate) {  
	        this.bookName = new SimpleStringProperty(b);  
	        this.isbn = new SimpleStringProperty(i);  
	        this.copyNum = new SimpleStringProperty(c);  
	        this.checkoutDate=new SimpleStringProperty(checkoutDate);  
	        this.dueDate=new SimpleStringProperty(dueDate);  
	    }  
	   
	    

		public String getBookName() {  
	        return bookName.get();  
	    }  
	    public void setBookName(String fName) {  
	        bookName.set(fName);  
	    }  
	          
	    public String getISBN() {  
	        return isbn.get();  
	    }  
	    public void setISBN(String fName) {  
	        isbn.set(fName);  
	    }  
	      
	    public String getCopyNum() {  
	        return copyNum.get();  
	    }  
	    public void setCopyNum(String fName) {  
	        copyNum.set(fName);  
	    }  
	    public String getCheckoutDate() {  
	        return checkoutDate.get();  
	    }  
	    public void setCheckoutDate(String fName) {  
	        checkoutDate.set(fName);  
	    }  
	    
	    public String getDueDate() {  
	        return dueDate.get();  
	    }  
	    public void setDueDate(String fName) {  
	        dueDate.set(fName);  
	    }  
	   
	    public TableView getLibraryMemberTable() {
			TableView table = new TableView();
			table.setEditable(true);

			TableColumn nameCol = new TableColumn("Name");
			TableColumn memberIDCol = new TableColumn("member ID");
			TableColumn bookCol = new TableColumn("Book");
			TableColumn dueDateCol = new TableColumn("Due Date");
			TableColumn isOverdueCol = new TableColumn("Is Overdue?");

			table.getColumns().addAll(nameCol, memberIDCol, bookCol,dueDateCol,isOverdueCol);
			return table;
		}

}

	public GridPane getAddBookPane() {
		GridPane addBookGrid = new GridPane();
		addBookGrid.setAlignment(Pos.CENTER);
		addBookGrid.setHgap(20);
		addBookGrid.setVgap(20);
		addBookGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE

		Label bookTitleLable = new Label("Book Title:");
		addBookGrid.add(bookTitleLable, 1, 1);

		Label isbnLable = new Label("ISBN:");
		addBookGrid.add(isbnLable, 1, 2);

		Label authorCreLable = new Label("Author Credentials:");
		addBookGrid.add(authorCreLable, 1, 3);

		Label authorBioLable = new Label("Author Bio:");
		addBookGrid.add(authorBioLable, 1, 4);

		// TEXTField

		TextField isbnTextField = new TextField();
		addBookGrid.add(isbnTextField, 2, 2);

		TextField bookTextField = new TextField();
		addBookGrid.add(bookTextField, 2, 1);

		TextField nextCopyTextField = new TextField();
		addBookGrid.add(nextCopyTextField, 2, 3);

		TextField maxLengthTextField = new TextField();
		addBookGrid.add(maxLengthTextField, 2, 4);

		Button btnAddBook = new Button("Add");
		btnAddBook.applyCss();
		//btnAddBook.css
		HBox hbBtnAddBook = new HBox(10);
		hbBtnAddBook.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddBook.getChildren().add(btnAddBook);
		addBookGrid.add(hbBtnAddBook, 2, 8);
		

		btnAddBook.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// do something if the button is clicked
				//Address a = new Address(streetTextField.getText(), cityTextField.getText(), stateTextField.getText(), zipTextField.getText());
				//String m = 
				Author a=new Author("ashutosh", "Ghimire", "something", new Address("shfg","jgfs","sajkfg","kjagf"), "Super Gero");
				List<Author> m=new ArrayList();
				m.add(a);
				try {
				df.saveNewBook(new Book(isbnTextField.getText(), bookTextField.getText(),7, m));	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			//informationLable.setText("library member added successfully");
			}

		});
		return addBookGrid;
			
	}

	public GridPane getAddCopyPane() {
		GridPane addCopyGrid = new GridPane();
		addCopyGrid.setAlignment(Pos.CENTER);
		addCopyGrid.setHgap(20);
		addCopyGrid.setVgap(20);
		addCopyGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE

		Label bookTitleLable = new Label("Book Title:");
		addCopyGrid.add(bookTitleLable, 0, 0);
		Label bookTitle=new Label ("");
		addCopyGrid.add(bookTitle, 0, 1);
		Label isbnLable = new Label("ISBN:");
		addCopyGrid.add(isbnLable, 1, 2);

		// TEXTField

		TextField IsbnTextField = new TextField();
		addCopyGrid.add(IsbnTextField, 2, 2);

		

		Button btnAddCopy = new Button("Add");
		HBox hbBtnAddCopy = new HBox(10);
		hbBtnAddCopy.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddCopy.getChildren().add(btnAddCopy);
		addCopyGrid.add(hbBtnAddCopy, 2, 5);
		
		btnAddCopy.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// do something if the button is clicked
				//Address a = new Address(streetTextField.getText(), cityTextField.getText(), stateTextField.getText(), zipTextField.getText());
				//String m = 
				
				
				Book b;
					
					try {
						
						b = df.searchBook(IsbnTextField.getText());
						bookTitle.setText(b.getTitle()+"Added successfully");
					
				
				
				
					b.addCopy();
				df.updateBook(b);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			//informationLable.setText("library member added successfully");
			}

		});

		return addCopyGrid;
	}

	public GridPane getCheckCopyStatusPane() {
		GridPane checkCopyStatusGrid = new GridPane();
		checkCopyStatusGrid.setAlignment(Pos.CENTER);
		checkCopyStatusGrid.setHgap(20);
		checkCopyStatusGrid.setVgap(20);
		checkCopyStatusGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE

		Label bookTitleLable = new Label("Book Title:");
		checkCopyStatusGrid.add(bookTitleLable, 1, 1);

		Label copyNumberLable = new Label("Copy Number:");
		checkCopyStatusGrid.add(copyNumberLable, 1, 2);

		// TEXTField

		TextField memberIDTextField = new TextField();
		checkCopyStatusGrid.add(memberIDTextField, 2, 1);

		TextField bookTextField = new TextField();
		checkCopyStatusGrid.add(bookTextField, 2, 2);

		Button btnAddCopy = new Button("Check Book Copy Status");
		HBox hbBtnAddCopy = new HBox(10);
		hbBtnAddCopy.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddCopy.getChildren().add(btnAddCopy);
		checkCopyStatusGrid.add(hbBtnAddCopy, 2, 4);

		return checkCopyStatusGrid;
	

	          
	}  

}