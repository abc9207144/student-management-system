package com.siweb.controller;

import com.siweb.controller.utility.UtilityHttpController;
import com.siweb.model.UserModel;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import org.json.JSONObject;
import javafx.scene.control.ToggleGroup;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * BaseController is the base for all pages
 */
public class BaseController implements Initializable {

    protected final UtilityHttpController http = UtilityHttpController.getInstance();
    protected final UserModel userModel = UserModel.getInstance();
    protected ToggleGroup toggleGroup;

    public void initialize(URL location, ResourceBundle resources) {

    }
    public void logout(){
        http.logout((JSONObject res) -> {
            try{
                System.err.println("returning to login page...");

                // clear user information in Model when logging out...
                Platform.runLater(userModel::clear);

                com.siweb.App.setRoot("login");
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    protected ToggleButton createToggle(String icon, String text) {

        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);

        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);

        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
        toggleNode.setToggleGroup(toggleGroup);

        return toggleNode;
    }

}
