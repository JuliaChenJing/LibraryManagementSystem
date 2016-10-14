package application;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import application.MemberTable.TableData;
import dataaccess.DataAccessFacade;
import dataaccess.LibraryMember;

public class MemberTable extends Stage {

	
	
	
	MemberTable() {
		
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
		searchGrid.add(getLibraryMemberTable(),2,9);
		//searchGrid.add();
	
		// Scene scene = new Scene(loginGrid, 300, 200);
		//Scene searchScene = new Scene(searchGrid);
		//primaryStage.setScene(searchScene);
		//primaryStage.show();

	}
	public TableView getLibraryMemberTable() {
		TableView table = new TableView();
		table.setEditable(true);
		
		/*final ObservableList<TableData> data = FXCollections.observableArrayList(  
			    new TableData("Harry Potter", "Smith", "jacob.smith@example.com","201601","201603"),  
			    new TableData("Isabella", "Johnson", "isabella.johnson@example.com","201601","201603"), 
			    new TableData("Ethan", "Williams", "ethan.williams@example.com","201601","201603"),  
			    new TableData("Emma", "Jones", "emma.jones@example.com","201601","201603"),
			    new TableData("Michael", "Brown", "michael.brown@example.com","201601","201603")
			);  */
		DataAccessFacade lm=new DataAccessFacade();
		HashMap<String, LibraryMember> libmem=lm.readLibraryMap();
		System.out.println("----------");
		System.out.println(libmem.entrySet());
		ObservableList<TableData> data = FXCollections.observableArrayList();
		Set<String> keySet = libmem.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
			System.out.println("------------------------------------------------");
			   System.out.println("Iterating Map in Java using KeySet Iterator");
			   String key = keySetIterator.next();
			   System.out.println("key: " + key + " value: " + libmem.get(key));
		
			//String key = keySetIterator.next();
			LibraryMember ll=libmem.get(key);
			
			TableData t=new TableData(ll.getFirstName()+" "+ll.getLastName(),key, "jacob.smith@example.com","201601","201603");
		  data.add(t);
	}
//		ObservableList xx=FXCollections.observableArrayList(libmem.entrySet());
//		System.out.println(xx);
		
		
		//TableData t=new TableData(Title, Title, Title, Title, Title);
		
		//libmem.get(Title);
		//data.forEach(
		
		
		//data.add(e);
		
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
}
