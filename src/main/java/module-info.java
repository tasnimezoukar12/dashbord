module com.example.dashborda {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.materialdesignicons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;


    opens com.example.dashborda to javafx.fxml;
    opens com.example.dashborda.Models to javafx.base;
    exports com.example.dashborda;
    exports com.example.dashborda.Controllers;
    exports com.example.dashborda.Views;


    opens com.example.dashborda.Controllers to javafx.fxml;

}


