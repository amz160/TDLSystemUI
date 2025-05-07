package com.todolistsystem.tdlsystemui;

import java.util.List;

public class IntegrationTest {
    public static void main(String[] args) {
        ToDoListDAO listDAO = new ToDoListDAO();
        TaskDAO taskDAO = new TaskDAO();

        String testListName = "Integration Test";
        String testTaskDescription = "Test task";

        // Create a new to-do list
        listDAO.createList(testListName);

        // Get the ID of the list we just created
        int listID = getListIDByName(testListName);
        if (listID == -1) {
            System.out.println("Failed: Could not retrieve newly created list ID.");
            return;
        }

        // Add a task to that list
        taskDAO.addTask(listID, testTaskDescription, null);

        // Retrieve the task
        List<Task> tasks = taskDAO.getTasksByList(listID);
        boolean taskExists = tasks.stream()
                .anyMatch(task -> task.getDescription().equals(testTaskDescription));

        if (taskExists) {
            System.out.println("Passed: Task was successfully added and retrieved.");
        } else {
            System.out.println("Failed: Task not found in the list.");
        }


        // 10 second pause for demo
        System.out.println("Waiting 10 seconds before cleanup...");
        try {
            Thread.sleep(10000); // 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Clean up
        listDAO.deleteList(listID);
    }

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