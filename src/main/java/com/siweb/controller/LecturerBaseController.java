package com.siweb.controller;

import com.siweb.App;
import com.siweb.controller.BaseController;
import com.siweb.controller.utility.UtilityHttpController;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LecturerBaseController extends BaseController {
        protected final UtilityHttpController http = UtilityHttpController.getInstance();
        public Pane rootPane;
        protected ToggleGroup toggleGroup;

        @FXML
        protected StackPane contentArea;

        @FXML
        protected VBox mainMenu;

    public LecturerBaseController() {

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

        tempToggle = createToggle("mfx-users", "StudentProfile");
        tempToggle.setOnAction(event -> App.loadFXMLtoPane(this.contentArea, "lecturer-profiles"));
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
