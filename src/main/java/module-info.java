module com.siweb.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.net.http;
    requires MaterialFX;


    requires org.kordamp.bootstrapfx.core;

    opens com.siweb to javafx.fxml;
    exports com.siweb;
    opens com.siweb.controller to javafx.fxml;
    exports com.siweb.controller.utility;
    opens com.siweb.controller.utility to javafx.fxml;
    exports com.siweb.model;
    opens com.siweb.model to javafx.fxml;
    exports com.siweb.controller;
}