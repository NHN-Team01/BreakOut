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
    exports com.nhnacademy.domain.data;
    opens com.nhnacademy.domain.data to javafx.fxml;
    exports com.nhnacademy.domain.model;
    opens com.nhnacademy.domain.model to javafx.fxml;
    exports com.nhnacademy.view to javafx.fxml;
    opens com.nhnacademy.view to javafx.fxml;
}
