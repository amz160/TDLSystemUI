package com.todolistsystem.tdlsystemui;

import javafx.beans.property.*;

import java.sql.Date;

public class Task {
    private final IntegerProperty taskID;
    private final IntegerProperty listID;
    private final StringProperty description;
    private final ObjectProperty<Date> dueDate;
    private final BooleanProperty isCompleted;

    public Task(int taskID, int listID, String description, Date dueDate, boolean isCompleted) {
        this.taskID = new SimpleIntegerProperty(taskID);
        this.listID = new SimpleIntegerProperty(listID);
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.isCompleted = new SimpleBooleanProperty(isCompleted);
    }

    public int getTaskID() { return taskID.get(); }
    public int getListID() { return listID.get(); }
    public String getDescription() { return description.get(); }
    public Date getDueDate() { return dueDate.get(); }
    public boolean isCompleted() { return isCompleted.get(); }

    public void setDescription(String description) { this.description.set(description); }
    public void setDueDate(Date dueDate) { this.dueDate.set(dueDate); }
    public void setCompleted(boolean isCompleted) { this.isCompleted.set(isCompleted); }

    public StringProperty descriptionProperty() { return description; }
    public ObjectProperty<Date> dueDateProperty() { return dueDate; }
    public BooleanProperty completedProperty() { return isCompleted; }
}