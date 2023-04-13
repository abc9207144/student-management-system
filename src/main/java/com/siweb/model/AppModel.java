package com.siweb.model;

import javafx.scene.Scene;
import javafx.stage.Stage;

/***
 * AppModel is a model to manage the configuration of the application
 */
public class AppModel {
    public static final String APP_NAME = "SIWeb";
    public static final String APP_VERSION = "0.0.1";
    public static final String APP_DESC = "a simple student project";
    public static final String API_URI = "https://siweb.ltech.com.mo/api";
    //public static final String API_URI = "http://127.0.0.1:8000/api";

    // keep scene and stage as static so controllers can get it directly.
    // not the best approach for multi-thread applications.
    // considering we use only one scene and one stage for the whole application, should be ok for now.
    // TODO: use the dependency injection design pattern instead of public static
    public static Scene scene;
    public static Stage stage;
}
