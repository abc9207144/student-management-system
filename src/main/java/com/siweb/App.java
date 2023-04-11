package com.siweb;

import com.siweb.model.AppModel;
import com.siweb.model.User;
import javafx.application.Application; // Application class from which JavaFX applications extend. https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html
import javafx.fxml.FXMLLoader;
// FXML introduction: http://fxexperience.com/2011/10/fxml-why-it-rocks-and-the-next-phase/
// Scene Builder Download: https://gluonhq.com/products/scene-builder/#download
// Skin JMetro: https://www.pixelduke.com/java-javafx-theme-jmetro/

import javafx.scene.Parent; // The base class for all nodes that have children in the scene graph. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Parent.html
import javafx.scene.Scene; // The container for all content in a scene graph. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html
import javafx.scene.layout.Pane;
import javafx.stage.Stage; // The top level JavaFX container. https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html

import javafx.scene.image.Image;
import javafx.scene.SceneAntialiasing; // Specifies the level of anti-aliasing desired. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/SceneAntialiasing.html

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    private static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // set application icon
        stage.getIcons().add(new Image(getClass().getResource("images/logo.png").toString()));

        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/login.fxml"));
        scene = new Scene(loadFXML("login"), 1600, 900, true, SceneAntialiasing.BALANCED);

        AppModel.stage = stage;
        AppModel.scene = scene;

        stage.setTitle(String.format("%s (Ver. %s) - %s", AppModel.APP_NAME, AppModel.APP_VERSION, AppModel.APP_DESC));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    public static void loadFXMLtoPane(Pane p, String fxml) {
        try {
            p.getChildren().clear();
            p.getChildren().add(loadFXML(fxml));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static URL getFXMLURL(String fxml) {

        URL res = App.class.getResource("views/" + fxml + ".fxml");
        return res;
    }

    public static void main(String[] args) {
        launch();
    }
}