package com.todolistsystem.tdlsystemui;

import java.sql.Date;
import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        ToDoListDAO listDAO = new ToDoListDAO();
        TaskDAO taskDAO = new TaskDAO();

        String listName = "Test Tasks";

        // Create the test list
        listDAO.createList(listName);
        int listID = getListIDByName(listName);
        System.out.println("Test List ID: " + listID);

        if (listID == -1) {
            System.out.println("Failed to retrieve list ID.");
            return;
        }

        // Add a task to that list
        System.out.println("Adding task to listID: " + listID);
        taskDAO.addTask(listID, "Finish English paper", Date.valueOf("2025-02-20"));
        System.out.println("Task added: Finish English paper");

        // Retrieve and print all lists
        List<String> allLists = listDAO.getAllLists();
        System.out.println("To-Do Lists: " + allLists);

        // Retrieve and print tasks in that list
        List<Task> tasks = taskDAO.getTasksByList(listID);
        System.out.println("Tasks in List '" + listName + "': " + tasks);

        // 10 second pause for demo
        System.out.println("Waiting 10 seconds before deleting...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the task ID of the task we just added
        int taskID = tasks.stream()
                .filter(t -> t.getDescription().equals("Finish English paper"))
                .map(Task::getTaskID)
                .findFirst()
                .orElse(-1);

        if (taskID == -1) {
            System.out.println("Failed to retrieve task ID.");
            return;
        }

        // Update and delete the task
        taskDAO.updateTask(taskID, "Complete writing assignment", Date.valueOf("2025-02-25"), true);
        taskDAO.deleteTask(taskID);

        // Delete the test list
        listDAO.deleteList(listID);
        System.out.println("To-Do List deleted (ID: " + listID + ")");
    }

    // Retrieves the list ID from the database
    private static int getListIDByName(String listName) {
        String sql = "SELECT listID FROM ToDoLists WHERE listName = ?";
        try (var conn = DatabaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, listName);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("listID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
