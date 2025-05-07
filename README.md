# To-Do List Manager
The To-Do List Manager allows users to create, edit, and delete tasks on custom lists, tracking their progress so they can stay focused and organized. The system uses a MySQL database to store all to-do lists and tasks.

## Features
* Users can create, edit, or delete to-do lists.
* Users can create, edit, or delete tasks.
* All to-do lists and tasks are stored and managed in a MySQL database

## Installation
1. Import TDLSystem.sql
2. Open MySQL Workbench, connect to your MySQL server, and select TDLSystem.sql to create the database
3. Open DatabaseConnection.java and update the following with your MySQL credentials

```java
private static final String URL = "jdbc:mysql://localhost:3306/TDLSystem";
private static final String USERNAME = "USERNAME";
private static final String PASSWORD = "PASSWORD";
```
4. Unzip and open TDLSystemUI in your preferred IDE
5. Compile the project and run Main.java
6. Immediately begin creating to-do lists and tasks, or run TestDAO.java, IntegrationTest.java, or DataCollection.java to see a demo.

## Project Structure
* **TDLSystem.sql**: creates the system database.
* **DatabaseConnection.java**: Manages the connection to the MySQL database.
* **DAO Classes**: Manages CRUD operations for tasks and to-do lists.
* **Controller Classes**: Handles user interactions in the UI. Connects the frontend to the database.
* **Test Classes:** Demonstrates unit, integration, and data collection tests.
* **FXML Files:** User interface structure.
* **CSS:** User interface styling.

## Troubleshooting
* Verify that MySQL credentials in DatabaseConnection.java are correct and that the MySQL server is running.
* Verify that MySQL Connector/J is installed and added to the project.
