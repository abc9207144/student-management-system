package com.siweb.controller;

import com.siweb.App;
import com.siweb.controller.utility.UtilityHttpController;
import com.siweb.model.UserModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.json.JSONObject;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class StudentBaseController extends BaseController
{
    protected final UtilityHttpController http = UtilityHttpController.getInstance();
    protected ToggleGroup toggleGroup;

    @FXML
    protected StackPane contentArea;

    @FXML
    protected VBox mainMenu;

    public StudentBaseController() {

        super();
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Label menuLabel = new Label("SI Web");
        menuLabel.setPadding(new Insets(8));
        this.mainMenu.getChildren().add(menuLabel);

        ToggleButton tempToggle = null;

        tempToggle = createToggle("mfx-users", "Users");
        tempToggle.setOnAction(event -> App.loadFXMLtoPane(this.contentArea, "admin-users"));
        tempToggle.fire();
        tempToggle.setSelected(true);
        this.mainMenu.getChildren().add(tempToggle);

        tempToggle = createToggle("mfx-shortcut", "Log out");
        tempToggle.setOnAction(event -> logout());
        this.mainMenu.getChildren().add(tempToggle);

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
