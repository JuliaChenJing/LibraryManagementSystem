# Library Management System

## 1. Function	:

* Completed a desktop application to help managing the daily work of librarians (check in, check out, update new books, check user information and whether the book borrowed is overdue). 

---

## 2. To run	:
* Check the build path. 
* Run the dataaccess.TestData.java
* Run application.UI.java
* There are 3 type of roles: admin, librarian and both.
* JavaFX, MVC, and Façade were utilized in this project.
-----
## 3. Requirements	:
1.  Create the class diagram, at first without operations.

     a. Isolate the concepts or candidate classes as described earlier in the course, by examining and filtering nouns and noun phrases in the problem statement.

     b. Decide on a set of classes.
     
     c. Add appropriate attributes to the classes.
     d. Identify inheritance relationships
     e. Create a more complete class diagram that includes the above as well as associations
between your classes. Add names and/or roles to your associations as well as
multiplicities. Check if there are any relationships that should be modeled with an
association class. Decide if some associations should be changed to dependencies and
make the changes in your class diagram.
--------------------------------------------------------------------------------

2. To help identify operations in your classes, create one sequence diagram for each of the use cases described above. The actor in each case is either a Librarian or an Administrator who will be using the system. The actor will be interacting with the UI, so the first object the actor sends a message to will be, in each case, the UI. Each user request will be handled by an eventhandler. There should be a SystemController class that organizes the steps of execution
necessary to fulfill the needs of the event-handlers; the SystemController will accomplish this by delegating tasks to appropriate objects in the system and gathering the results for the event handler.
----

3. Use your sequence diagrams to help identify operations in your classes, and add these to your  class diagram.
------------------------------------------    
4. Design the user interface. 
     
     a. Spend time thinking about a good way to organize the look of the UI, given the use cases  that need to be supported. Can everything be done on a single screen? Should you use a menu? Aim to make it possible for the user to accomplish each use case with as few  steps as possible. Plan to use a TableView to display (read-only) checkout records. (See the sample code in the package lesson6.lecture.javafx.tables.) Draw  by hand sketches of the screens you decide to use.
    
     b. Code the UI based on your sketches either by directly writing the code or by using
SceneBuilder.
----------------------------------------- 

5. Data Access
     
     a. This project will not make use of a database. Instead, all classes that need to be
persisted will be serialized. Serialization in java is a mechanism of writing the state of an
object into a byte stream. The reverse operation of serialization is called deserialization.
Plan to have a separate package called dataaccess and a subpackage storage. All
persisted classes (via serialization) should be placed inside this storage package.
     
     b. A TestData class in dataaccess should provide a main method that will load up test
data to run tests on the application. This data should include at least 4 library members,
and 4 books (two of which have 3 copies, the rest, just 1 copy). All 4 library members
should have at least 1 book checked out. Two members should each have an overdue
publication.
        
     c. Each persistence operation, and each read operation, should be represented by a public  method on a DataAccessFacade class – for instance, saveNewMember,findCustomerById. Read operations will locate the serialized objects in the storage
package, deserialize them, perform other logic as necessary, and return the results. Save
operations will store the appropriate objects in serialized form in the storage package
