package com.todolistsystem.tdlsystemui;

import javafx.beans.property.*;

import java.sql.Timestamp;

public class ToDoList {
    private final IntegerProperty listID;
    private final StringProperty listName;
    private final ObjectProperty<Timestamp> createdAt;

    public ToDoList(int listID, String listName, Timestamp createdAt) {
        this.listID = new SimpleIntegerProperty(listID);
        this.listName = new SimpleStringProperty(listName);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    public int getListID() { return listID.get(); }
    public String getListName() { return listName.get(); }
    public Timestamp getCreatedAt() { return createdAt.get(); }

    public void setListName(String listName) { this.listName.set(listName); }

    public StringProperty listNameProperty() { return listName; }
}
