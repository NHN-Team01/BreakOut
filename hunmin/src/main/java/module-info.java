module com.nhnacademy {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    opens com.nhnacademy to javafx.fxml;

    exports com.nhnacademy;
    exports com.nhnacademy.legacy;
    opens com.nhnacademy.legacy to javafx.fxml;
    exports com.nhnacademy.controller;
    opens com.nhnacademy.controller to javafx.fxml;
}
