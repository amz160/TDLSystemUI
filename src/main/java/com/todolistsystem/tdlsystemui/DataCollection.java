package com.todolistsystem.tdlsystemui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataCollection {
    public static void main(String[] args) {
        ToDoListDAO listDAO = new ToDoListDAO();
        TaskDAO taskDAO = new TaskDAO();

        try (FileWriter writer = new FileWriter("dataCollection.txt")) {
            List<String> lists = listDAO.getAllLists();

            for (String listName : lists) {
                int listID = getListIDByName(listName);
                writer.write("To-Do List: " + listName + " (ID: " + listID + ")\n");

                List<Task> tasks = taskDAO.getTasksByList(listID);
                if (tasks.isEmpty()) {
                    writer.write("  (No tasks found)\n");
                } else {
                    for (Task task : tasks) {
                        writer.write("  - " + task.getDescription() +
                                " | Due: " + task.getDueDate() +
                                " | Completed: " + (task.isCompleted() ? "Yes" : "No") + "\n");
                    }
                }
                writer.write("\n");
            }

            System.out.println("Success! Data exported to dataCollection.txt");

        } catch (IOException e) {
            System.out.println("Failed to write data to file.");
            e.printStackTrace();
        }
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