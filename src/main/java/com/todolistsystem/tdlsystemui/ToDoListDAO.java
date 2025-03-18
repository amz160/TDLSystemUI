package com.todolistsystem.tdlsystemui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListDAO {

    // Creates a new to-do list in the DB
    public void createList(String listName) {
        String sql = "INSERT INTO ToDoLists (listName) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, listName);
            stmt.executeUpdate();
            System.out.println("To-Do List created: " + listName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Gets all to-do lists from DB
    public List<String> getAllLists() {
        List<String> lists = new ArrayList<>();
        String sql = "SELECT listName FROM ToDoLists";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lists.add(rs.getString("listName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }

    // Deletes a to-do list from the DB
    public void deleteList(int listID) {
        String sql = "DELETE FROM ToDoLists WHERE listID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, listID);
            stmt.executeUpdate();
            System.out.println("To-Do List deleted (ID: " + listID + ")"); // Confirms to-do list ID that was deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}