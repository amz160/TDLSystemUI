module com.todolistsystem.tdlsystemui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.todolistsystem.tdlsystemui to javafx.fxml;
    exports com.todolistsystem.tdlsystemui;
}