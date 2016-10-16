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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.net.Authenticator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import application.UI.BookTableData;
import application.test.TableData;
import business.Address;
import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckOutRecordEntries;
import business.CheckOutRecords;
import business.LibrarySystemException;
import business.SystemController;

public class UI extends Application {

	DataAccessFacade df  = new DataAccessFacade();
	DataAccessFacade lm=new DataAccessFacade();
	ArrayList<Author> bookauthors=new ArrayList<>();
	SystemController sc = new SystemController();
	
	String Title = "Library Management System";
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
	//	primaryStage.setFullScreen(true);

		
		
		// initiate the pane
		login = getLoginPane();
		menu = getMenuFlow();

		// set the small pane on the background border
		border.getStyleClass().add("border");
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
		//while (true){
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				//while (true)
				try {

				{	
					auth = df.login(userIdTextField.getText(), passwordTextField.getText());
					if(auth==null)throw new Exception("Hw");
					//continue;
					Stage test=(Stage)loginGrid.getScene().getWindow();
					test.setMaximized(true);
					border.setCenter(getWelcomeLibrarian("Welcome to the Library Management System "));
					
					
					border.setLeft(getMenuFlow());
				}
				 
				}	// TODO Auto-generated catch block
				catch(Exception e)
				
					
				
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Something wrong with login info");
					alert.setContentText("Write your username and Password Correctly");

					alert.showAndWait();
					e.printStackTrace();
				}
				
					
				
			//}
				
				
			
					

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

	
	/**  */
	public GridPane getWelcomeLibrarian(String text) {
		
		
		GridPane test = new GridPane();
		test.getStyleClass().add("welcome");
		File file = new File("src/lms.jpg");
		Image image=new Image(file.toURI().toString());
		ImageView iv1=new ImageView();
		iv1.setImage(image);
		
		HBox box=new HBox();
		
		box.setPadding(new Insets(10,10,10,10));
		box.getChildren().add(iv1);
		box.setAlignment(Pos.TOP_CENTER);
		//test.setAlignment(Pos.CENTER);
		test.setHgap(20);
		test.setVgap(20);
		test.setPadding(new Insets(25, 25, 25, 25));
		Label Wel = new Label(text);
		test.add(Wel,0,2);
		test.add(box, 0, 0);
		//test.getChildren().add(box);
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

		Label bookTitleLable = new Label("Book ISBN:");
		checkoutGrid.add(bookTitleLable, 1, 1);

		Label informationLable = new Label(
		"information:if the book is available and if one is a library member  ");
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
		Button btnCheckCopyStatus = new Button("Check Member Status");
		HBox hbBtnAddCopy = new HBox(10);
		hbBtnAddCopy.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddCopy.getChildren().add(btnCheckCopyStatus);
		checkoutGrid.add(hbBtnAddCopy, 2, 3);
		btnCheckCopyStatus.setOnAction(evt->{
		
			if(memberIDTextField.getText().equals(""))
			{
				Alert a =new Alert(AlertType.ERROR, "Member ID cannot be null", ButtonType.CLOSE);
				a.show();
			}
			else
			memberCheckoutDetails(memberIDTextField.getText());
		
		
				
		});
		
		
		Button btnBorrow = new Button("Borrow");
		HBox hbBtnBorrow = new HBox(10);
		hbBtnBorrow.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnBorrow.getChildren().add(btnBorrow);
		checkoutGrid.add(hbBtnBorrow, 2, 4);
		btnBorrow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				
				if(memberIDTextField.getText().equals(""))
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					//alert.setHeaderText(e.getMessage());
					alert.setContentText("Member ID text Field cannot be blank");

					alert.showAndWait();
					}
				else {
					if(bookIsbnField.getText().equals(""))
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						//alert.setHeaderText(e.getMessage());
						alert.setContentText("Book ISBN Field cannot be blank");

						alert.showAndWait();
						}
				
				else{
				try {
					sc.checkoutBook(memberIDTextField.getText(), bookIsbnField.getText());
					
					informationLable.setText("Checkout successfully");
					
					
				} catch (LibrarySystemException e) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(e.getMessage());
					alert.setContentText(e.getMessage());

					alert.showAndWait();
				}
				}
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
		menuGrid.getStyleClass().add("menuflow");
		//menuGrid.applyCss();
		menuGrid.setVgap(8);
		menuGrid.setHgap(4);
		menuGrid.setPrefWrapLength(50);
		menuGrid.setPadding(new Insets(25, 25, 25, 25));

		Button btnLog = new Button("Log in / out");
		menuGrid.getChildren().add(btnLog);
		// btnLog.setDisable(true);
		btnLog.setOnAction(evt -> {
			auth=Auth.NONE;
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
		btn3.setOnAction(evt -> border.setCenter(getAddBookPanenew()));

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
				
				if(memId.getText().equals(""))
				{
					Alert a =new Alert(AlertType.ERROR, "Member ID cannot be null", ButtonType.CLOSE);
					a.show();
				}
				else {
				Address ad = new Address(streetTextField.getText(), cityTextField.getText(), stateTextField.getText(), zipTextField.getText());
				//String m = 
				try {
					df.saveNewMember(new LibraryMember(memId.getText(), firstnameTextField.getText(), lastnameTextField.getText(), cellTextField.getText(), ad));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			informationLable.setText("library member added successfully"+memId.getText());
			}
			}

		});

		return addGrid;
	}

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
				"you could print the information needed here");
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
				
					if(idTextField.getText().equals(""))
					{
						Alert a =new Alert(AlertType.ERROR, "Member ID cannot be null", ButtonType.CLOSE);
						a.show();
					}
					else
					memberCheckoutDetails(idTextField.getText());
					
				
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
				if(idTextField.getText().equals(""))
				{
					Alert a =new Alert(AlertType.ERROR, "Member ID cannot be null", ButtonType.CLOSE);
					a.show();
				}
				else{
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
				}
				
			//informationLable.setText("library member added successfully");
			}

			
		});
		
		//Stage something=new Stage();
		//something.setScene(getLibraryMemberTable());
		return searchGrid;

	}
	protected void memberCheckoutDetails(String text) {
		// TODO Auto-generated method stub
		Stage detail=new Stage();
		detail.setTitle("Checkout details of :"+text);
		BorderPane detailPane=new BorderPane();
		Button print =new Button("Print");
		HBox hbprint=new HBox();
		hbprint.getChildren().add(print);
		hbprint.setAlignment(Pos.TOP_RIGHT);
		
		detailPane.setTop(hbprint);
		print.setOnAction(evt->
		{
			System.out.println("CheckOut Records of "+text);
			df.readLibraryMap().get(text).getCheckoutrecords().print();
		}
				
				);
		detailPane.setBottom(getLibraryMemberTable(text));
		Scene detailScene=new Scene(detailPane);
		detail.setScene(detailScene);
		detail.show();
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
	   


}

//	

 

	public GridPane getAddCopyPane() {
		GridPane addCopyGrid = new GridPane();
		addCopyGrid.setAlignment(Pos.CENTER);
		addCopyGrid.setHgap(20);
		addCopyGrid.setVgap(20);
		addCopyGrid.setPadding(new Insets(25, 25, 25, 25));

		// LALBLE

		
		
		Label isbnLable = new Label("ISBN:");
		addCopyGrid.add(isbnLable, 1, 1);
		Label numbook=new Label("No of Books");
		addCopyGrid.add(numbook, 2, 1);
		Label bookTitle=new Label ("");
		addCopyGrid.add(bookTitle, 1, 5);
		

		// TEXTField

		TextField IsbnTextField = new TextField();
		addCopyGrid.add(IsbnTextField, 1, 2);

		TextField numberField = new TextField();
		addCopyGrid.add(numberField, 2,2 );

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
				
				try{
					
				Integer.parseInt(numberField.getText());
				
					
				Book b;
					
					try {
						
						if(IsbnTextField.getText().equals(""))
						{
							Alert a =new Alert(AlertType.ERROR, "Book ISBN field cannot be null", ButtonType.CLOSE);
							a.show();
						}
						b = df.searchBook(IsbnTextField.getText());
						
					
				
				
				for(int i=0;i<Integer.valueOf(numberField.getText());i++)
					b.addCopy();
				df.updateBook(b);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
						Alert a =new Alert(AlertType.ERROR, "Book not found in Database Please enter new book", ButtonType.CLOSE);
						a.show();
					
				}
				
					bookTitle.setText(numberField.getText()+"copies of "+IsbnTextField.getText()+" Added successfully");
			//informationLable.setText("library member added successfully");
			}
			
			catch(Exception e)
			{
				Alert a =new Alert(AlertType.ERROR, "Number of Book Copies must be Integer", ButtonType.CLOSE);
				a.show();
			}
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

		Label bookIsbnLable = new Label("Book Isbn:");
		checkCopyStatusGrid.add(bookIsbnLable, 1, 1);

		

		// TEXTField

		TextField bookIsbnTextField = new TextField();
		checkCopyStatusGrid.add(bookIsbnTextField, 2, 1);

		

		Button btnAddCopy = new Button("Check Book Copy Status");
		HBox hbBtnAddCopy = new HBox(10);
		hbBtnAddCopy.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddCopy.getChildren().add(btnAddCopy);
		checkCopyStatusGrid.add(hbBtnAddCopy, 2, 4);
		btnAddCopy.setOnAction(evt->{
			Book book;
			try {
				book = df.readBooksMap().get(bookIsbnTextField.getText());
			
			String isbn=book.getIsbn();
			BookCopy[] copy=book.getCopies();
			int count=book.getCopyNums().size();
			int countavailable=0;
			for(int i=0;i<count;i++)
				if(copy[i].isAvailable())countavailable++;
			
			//HashMap<String,CheckOutRecordEntries> jpt=new HashMap();
			List<LibraryMember> jpt=new ArrayList<>();
			
				HashMap<String,LibraryMember> lmmap=df.readLibraryMap();
				
				for(Entry<String, LibraryMember> entry:lmmap.entrySet())
				{
					for(int i=0;i<entry.getValue().getCheckoutrecords().getCe().size();i++)
					{
					if(entry.getValue().getCheckoutrecords().getCe().get(i).getBookCopy().getBook().getIsbn().equals(book.getIsbn()))
					{
						jpt.add(entry.getValue());
						System.out.println("***********************************************************8");

					}
						
					}
				}
				//System.out.println(jpt);
				
			
			bookCheckoutDetails(book,jpt,countavailable);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Book not Found in database");
				alert.setContentText("Write your Book ISBN correctly");
						alert.showAndWait();
			}
			
		});

		return checkCopyStatusGrid;
	

	          
	}

	 
	public TableView getLibraryBookTable(Book book, List<LibraryMember> libmem, int countavailable) {
		TableView table = new TableView();
		table.setEditable(false);
		
		
		//DataAccessFacade lm=new DataAccessFacade();
		//HashMap<String, LibraryMember> libmem=lm.readLibraryMap();
		System.out.println("----------");
		//System.out.println(hash.entrySet());
		ObservableList<BookTableData> data = FXCollections.observableArrayList();
			//LibraryMember ll=libmem.get(user);
		
				//CheckOutRecords some=ll.getCheckoutrecords();
				//List<CheckOutRecordEntries> test=some.getCe();
				BookTableData t;
				for(LibraryMember chk: libmem)
					{
				for(int i=0;i<chk.getCheckoutrecords().getCe().size();i++)
				{
				if(chk.getCheckoutrecords().getCe().get(i).getBookCopy().getBook().getIsbn().equals(book.getIsbn()))
				{
			t=new BookTableData(book.getTitle(),book.getIsbn(),Integer.toString(chk.getCheckoutrecords().getCe().get(i).getBookCopy().getCopyNum()),chk.getCheckoutrecords().getCe().get(i).getCheckOutDate().toString(),chk.getCheckoutrecords().getCe().get(0).getCheckOutDate().plusDays(book.getMaxCheckoutLength()).toString(), chk.getMemberId());
			System.out.println(chk.getMemberId());
			data.add(t);
				}
				}
					}
				while(data.size()<countavailable)
				{
					t=new BookTableData(book.getTitle(),book.getIsbn(),"Available Copy","In Library","Not Issued ","Not Issued");
					countavailable--;
					data.add(t);
				}
			
		
				TableColumn memberNameCol = new TableColumn("Member Name");  
		        memberNameCol.setMinWidth(200);  
		        memberNameCol.setCellValueFactory(  
		                new PropertyValueFactory<>("memberID"));  
		
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
        table.getColumns().addAll(bookNameCol,isbnCol, copyNumCol,checkoutDateCol,dueDateCol,memberNameCol);  
		return table;
		
	}
	
	public static class BookTableData {  
		private final SimpleStringProperty memberID;
		//private final SimpleStringProperty MemberID;
	    private final SimpleStringProperty bookName;  
	    private final SimpleStringProperty isbn;  
	    private final SimpleStringProperty copyNum;  
	    private final SimpleStringProperty checkoutDate;  
	    private final SimpleStringProperty dueDate;  
	    
	   
	    private BookTableData(String b, String i, String c,String checkoutDate,String dueDate,String memberID) {  
	        this.bookName = new SimpleStringProperty(b);  
	        this.isbn = new SimpleStringProperty(i);  
	        this.copyNum = new SimpleStringProperty(c);  
	        this.checkoutDate=new SimpleStringProperty(checkoutDate);  
	        this.dueDate=new SimpleStringProperty(dueDate);  
	        this.memberID=new SimpleStringProperty(memberID);
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



		public String getMemberID() {
			return memberID.get();
		}  
		
		public void setMemberID(String fName)
		{
			memberID.set(fName);
		}
	    
	   
	    /*public TableView getLibraryBookTable() {
			TableView table = new TableView();
			table.setEditable(false);

			TableColumn nameCol = new TableColumn("Name");
			TableColumn memberIDCol = new TableColumn("member ID");
			TableColumn bookCol = new TableColumn("Book");
			TableColumn dueDateCol = new TableColumn("Due Date");
			TableColumn isOverdueCol = new TableColumn("Is Overdue?");
			TableColumn memberIDCol1 = new TableColumn("member ID");

			table.getColumns().addAll(nameCol, memberIDCol, bookCol,dueDateCol,isOverdueCol,memberIDCol1);
			return table;
		}*/

}
	protected void bookCheckoutDetails(Book book, List<LibraryMember> jpt, int countavailable) {
		// TODO Auto-generated method stub
		Stage detail=new Stage();
		detail.setTitle("Checkout details of :"+book);
		BorderPane detailPane=new BorderPane();
		//Button print =new Button("Print");
		//HBox hbprint=new HBox();
		///hbprint.getChildren().add(print);
	//	hbprint.setAlignment(Pos.TOP_RIGHT);
		
		//detailPane.setTop(hbprint);
		//print.setOnAction(evt->
		//{
		//	System.out.println("CheckOut Records of "+book);
		//	df.readLibraryMap().get(book).getCheckoutrecords().print();
		//}
				
	//			);
		detailPane.setCenter(getLibraryBookTable(book,jpt,countavailable));
		Scene detailScene=new Scene(detailPane);
		detail.setScene(detailScene);
		detail.show();
	}
	
	public GridPane getAddBookPanenew() {
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

		Label Maxcheckoutlength = new Label("Maximum Checkout Length");
		addBookGrid.add(Maxcheckoutlength, 1, 3);

		Label numberOfCopies = new Label("Number of copies");
		addBookGrid.add(numberOfCopies, 1, 4);

		// TEXTField

		TextField bookTextField = new TextField();
		addBookGrid.add(bookTextField, 2, 1);

		TextField isbnTextField = new TextField();
		addBookGrid.add(isbnTextField, 2, 2);

		
		TextField MaxcheckoutlengthTextField = new TextField();
		addBookGrid.add(MaxcheckoutlengthTextField, 2, 3);

		TextField numberOfCopiesTextField = new TextField();
		addBookGrid.add(numberOfCopiesTextField, 2, 4);

		Label bookauthorLabel=new Label("Book Authors:");
		addBookGrid.add(bookauthorLabel, 1, 5);
		Label bookauthorLabel1=new Label("");
		addBookGrid.add(bookauthorLabel1, 2, 5);
		
		
		//bookauthors.add(null);
		Button btnAddBook = new Button("Add Book");
		btnAddBook.applyCss();
		//btnAddBook.css
		HBox hbBtnAddBook = new HBox(10);
		hbBtnAddBook.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddBook.getChildren().add(btnAddBook);
		addBookGrid.add(hbBtnAddBook, 2, 8);
		btnAddBook.setDisable(true);
		
		Button btnAddAuthor = new Button("Add Authors");
		btnAddAuthor.applyCss();
		Label informationLable=new Label("");
		addBookGrid.add(informationLable, 1, 10);
		//btnAddBook.css
		HBox hbBtnAddAuthor = new HBox(10);
		hbBtnAddAuthor.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnAddAuthor.getChildren().add(btnAddAuthor);
		addBookGrid.add(hbBtnAddAuthor, 2, 9);
		ArrayList<Author> list=new ArrayList<>();
		System.out.println("Book authors"+bookauthors);
		
		btnAddAuthor.setOnAction(evt->
		{
			if(isbnTextField.getText().equals(""))
			{
				Alert a =new Alert(AlertType.ERROR, "Book ISBN cannot be null", ButtonType.CLOSE);
				a.showAndWait();
				return;
			}
			try {
				Integer.parseInt(MaxcheckoutlengthTextField.getText());
			}
			catch(Exception e)
			{
				Alert a =new Alert(AlertType.ERROR, "Checkout length must be integer", ButtonType.CLOSE);
				a.showAndWait();
				return;
			}
			try {
				Integer.parseInt(numberOfCopiesTextField.getText());
			}
			catch(Exception e)
			{
				Alert a =new Alert(AlertType.ERROR, "Number of Copies must be integer", ButtonType.CLOSE);
				a.showAndWait();
				return;
			}
			
				
			String namelabel="";
		getauthornew();
		
		Iterator it=bookauthors.iterator();
		
		while(it.hasNext())
		{
			Author temp=(Author)it.next();
			System.out.println(temp.getFirstName());
			namelabel=namelabel.concat(temp.getFirstName());
		}
		
		bookauthorLabel1.setText(namelabel);
	btnAddBook.setDisable(false);
		
		//refresh();
//		String name="";//=bookauthorLabel1.getText();
//		for(int i=0;i<df.readBooksMap().get(isbnTextField.getText()).getAuthors().size();i++)
//		name=name.concat(df.readBooksMap().get(isbnTextField.getText()).getAuthors().get(i).getFirstName());
//		System.out.println(name);
//		//System.out.println(".................................................");
//		System.out.println(df.readBooksMap().get(isbnTextField.getText()).getAuthors().toString());

		//bookauthorLabel1.setText(df.readBooksMap().get(isbnTextField.getText()).getAuthors().toString());
		});
		
//		HashMap<String,Author> test =df.readAuthorMap();
//		Author abcd=new Author();
//		
//		for (Entry<String, Author> entry : test.entrySet()) {
//		   // String key = entry.getKey();
//		    Author value = entry.getValue();
//		    // you code here
//		    abcd=value;
//		    	}
//		bookauthors.add(abcd);
		//bookauthorLabel1.setText(bookauthorLabel1.getText()+abcd.getFirstName());
		
		
		
		
		

		btnAddBook.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if(isbnTextField.getText().equals(""))
				{
					Alert a =new Alert(AlertType.ERROR, "Book ISbn must not be blank", ButtonType.CLOSE);
					a.showAndWait();
					return;
				}
				try {
				df.saveNewBook(new Book(isbnTextField.getText(), bookTextField.getText(),Integer.valueOf(MaxcheckoutlengthTextField.getText()), bookauthors));	
				informationLable.setText("Book added successfully");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Alert a =new Alert(AlertType.ERROR, "Something Happened with dataaccess try again", ButtonType.CLOSE);
				a.showAndWait();
				}
				
				
			//informationLable.setText("library member added successfully");
			}

		});
		
		
		return addBookGrid;
			
	}

	public  void getauthornew() {
		// TODO Auto-generated method stub
		Stage authorstage =new Stage();
		BorderPane authorPane=new BorderPane();
		Scene authorscene=new Scene(authorPane);
		authorstage.setScene(authorscene);
		
		
		GridPane addGrid=new GridPane();
		authorPane.setCenter(addGrid);
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

		Label cellLable = new Label("Phone Number:");
		addGrid.add(cellLable, 1, 7);
		Label credentials=new Label("Credentials");
		addGrid.add(credentials, 1, 8);
		Label bio=new Label("Bio:");
		addGrid.add(bio, 1, 9);

		Label informationLable = new Label("information needed to show:");
		//addGrid.add(informationLable, 1, 9);
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
		
		TextField credentialField=new TextField();
		addGrid.add(credentialField, 2, 8);
		TextArea bioArea=new TextArea();
		addGrid.add(bioArea, 2, 9);
		
		Button submit=new Button("Submit");
		HBox hbsubmit=new HBox();
		hbsubmit.setAlignment(Pos.BOTTOM_RIGHT);
		hbsubmit.getChildren().add(submit);
		addGrid.add(hbsubmit, 2, 12);
		//Author author =new Author();
		submit.setOnAction(
				evt->{
					Address a=new Address(streetTextField.getText(), cityTextField.getText(), stateTextField.getText(), zipTextField.getText());
					Author newauthor=new Author(firstnameTextField.getText(), lastnameTextField.getText(), cellTextField.getText(), a, bio.getText(),credentialField.getText());
					//HashMap<String,Book> test=df.readBooksMap();
					//Book b=test.get(isbn);
					//System.out.println(b);
					
					//List<Author> authortemp=new ArrayList<>();
					//list.addAll(b.getAuthors());
					bookauthors.add(newauthor);
					//b.setAuthors(authortemp);
					//df.updateBook(b);
					//test.put(isbn, b);
					System.out.println("List"+bookauthors);
					//authors.setText(authors.getText()+newauthor.getFirstName()+"\n");
					authorstage.close();	
		
				});
		authorstage.showAndWait();
		//return author;
		//return provideauthor(author);
		
		//return author;
		}
	

}