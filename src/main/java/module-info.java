module bmi.bmicalculatormysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens bmi.bmicalculatormysql to javafx.fxml;
    exports bmi.bmicalculatormysql;
}