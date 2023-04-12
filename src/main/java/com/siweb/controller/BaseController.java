package com.siweb.controller;

import com.siweb.controller.utility.UtilityHttpController;
import com.siweb.model.*;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;
import javafx.scene.control.ToggleGroup;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;

/***
 * BaseController is the base for all pages
 */
public class BaseController implements Initializable {

    protected final UtilityHttpController http = UtilityHttpController.getInstance();


    protected final TimeSlotModel timeSlotModel = TimeSlotModel.getInstance();
    protected final UserModel userModel = UserModel.getInstance();
    @FXML
    protected ToggleGroup toggleGroup;
    @FXML
    protected StackPane contentArea;
    @FXML
    protected VBox mainMenu;

    public BaseController() {

        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);

    }

    public void initialize(URL location, ResourceBundle resources) {


    }
    public void logout(){
        http.logout((JSONObject res) -> {
            try{
                System.err.println("returning to login page...");

                // clear user information in Model when logging out...
                Platform.runLater(() -> {

                    // remove all data in the models when logging out...


                    timeSlotModel.clearAll();
                    userModel.clearAll();

                });

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

    protected void toggleClearSelectedExcept(Pane pane, ToggleButton toggleBtn) {
        pane.getChildren().forEach((item) -> {
            if(item instanceof ToggleButton && toggleBtn != item)
            {
                ((ToggleButton) item).setSelected(false);
            }
        });
    }

}
