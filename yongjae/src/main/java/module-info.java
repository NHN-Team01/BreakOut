module com.nhnacademy {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    opens com.nhnacademy to javafx.fxml;

    exports com.nhnacademy;
    exports com.nhnacademy.effect;
    opens com.nhnacademy.effect to javafx.fxml;
}
