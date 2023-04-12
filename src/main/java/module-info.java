module com.siweb.client {

    // for reading and writing JSON
    requires org.json;

    // for making http requests
    requires java.net.http;

    // for applying material design
    requires MaterialFX;

    // core
    opens com.siweb to javafx.fxml;
    exports com.siweb;
    exports com.siweb.model;
    opens com.siweb.model to javafx.fxml;
    exports com.siweb.controller;
    opens com.siweb.controller to javafx.fxml;
    exports com.siweb.controller.utility;
    opens com.siweb.controller.utility to javafx.fxml;
    exports com.siweb.view.facade;
    opens com.siweb.view.facade to javafx.fxml;
}