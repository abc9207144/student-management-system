module com.siweb.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.siweb to javafx.fxml;
    exports com.siweb;
    opens com.siweb.controller to javafx.fxml;
    exports com.siweb.controller;
}