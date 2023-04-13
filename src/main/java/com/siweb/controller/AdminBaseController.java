package com.siweb.controller;

import com.siweb.App;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * AdminBaseController is the base for all admin pages. It provides the admin menu and a contentArea which child admin pages can be loaded into
 */
public class AdminBaseController extends BaseController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // App Logo
        Label menuLabel = new Label("SI Web");
        menuLabel.setPadding(new Insets(8));
        this.mainMenu.getChildren().add(menuLabel);


        ToggleButton tempToggle = null;

        // Page - Users
        tempToggle = createToggle("mfx-users", "Users");
        tempToggle.setOnAction(event -> {
            App.loadFXMLtoPane(this.contentArea, "admin-users");
            toggleClearSelectedExcept(this.mainMenu, (ToggleButton) event.getSource());
        });
        tempToggle.fire();
        tempToggle.setSelected(true);
        this.mainMenu.getChildren().add(tempToggle);

        // Page - Semesters
        tempToggle = createToggle("mfx-calendars", "Semesters");
        tempToggle.setOnAction(event -> {
            App.loadFXMLtoPane(this.contentArea, "admin-semester");
            toggleClearSelectedExcept(this.mainMenu, (ToggleButton) event.getSource());
        });
        this.mainMenu.getChildren().add(tempToggle);

        // Page - Logout
        tempToggle = createToggle("mfx-shortcut", "Log out");
        tempToggle.setOnAction(event -> logout());
        this.mainMenu.getChildren().add(tempToggle);

    }

}
