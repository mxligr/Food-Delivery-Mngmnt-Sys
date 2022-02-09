module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.java;

    opens org.example to javafx.fxml;
    opens org.example.BussinessLayer to javafx.base;
    exports org.example;
}