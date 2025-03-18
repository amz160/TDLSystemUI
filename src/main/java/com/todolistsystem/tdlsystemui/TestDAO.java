package com.todolistsystem.tdlsystemui;

public class TestDAO {
    public static void main(String[] args) {
        ToDoListDAO listDAO = new ToDoListDAO();
        TaskDAO taskDAO = new TaskDAO();

        // Creates test list "Personal"
        listDAO.createList("Personal"); // Personal Tasks

        // Retrieving all lists
        System.out.println("To-Do Lists: " + listDAO.getAllLists());

        // Adding a task
        taskDAO.addTask(1, "Finish English paper", java.sql.Date.valueOf("2025-02-20"));

        // Retrieving tasks for the list
        System.out.println("Tasks in List 1: " + taskDAO.getTasksByList(1));

        // Updating a task
        taskDAO.updateTask(1, "Complete writing assignment", java.sql.Date.valueOf("2025-02-25"), true);

        // Deleting a task
        taskDAO.deleteTask(1);

        // Deleting a list
        listDAO.deleteList(1);
    }
}
