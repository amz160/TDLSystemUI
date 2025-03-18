package com.todolistsystem.tdlsystemui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // Adds a new task to the DB under the specific listID
    public void addTask(int listID, String description, Date dueDate) {
        System.out.println("Adding task to listID: " + listID); // For debugging in case listIDS are messed up
        String sql = "INSERT INTO Tasks (listID, description, dueDate) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, listID);
            stmt.setString(2, description);
            stmt.setDate(3, dueDate);
            stmt.executeUpdate();
            System.out.println("Task added: " + description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Gets all tasks for listID
    public List<Task> getTasksByList(int listID) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Tasks WHERE listID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, listID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("taskID"),
                        rs.getInt("listID"),
                        rs.getString("description"),
                        rs.getDate("dueDate"),
                        rs.getBoolean("isCompleted")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Updates existing task details
    public void updateTask(int taskID, String newDescription, Date newDueDate, boolean isCompleted) {
        String sql = "UPDATE Tasks SET description = ?, dueDate = ?, isCompleted = ? WHERE taskID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newDescription);
            stmt.setDate(2, newDueDate);
            stmt.setBoolean(3, isCompleted);
            stmt.setInt(4, taskID);
            stmt.executeUpdate();
            System.out.println("com.todolistsystem.tdlsystemui.Task updated (ID: " + taskID + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletes task from DB
    public void deleteTask(int taskID) {
        String sql = "DELETE FROM Tasks WHERE taskID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskID);
            stmt.executeUpdate();
            System.out.println("com.todolistsystem.tdlsystemui.Task deleted (ID: " + taskID + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
