package com.siweb.controller;

import com.siweb.view.builder.BuilderMFXTextFieldController;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentProfilesController extends BaseController{
    @FXML
    private VBox leftUserDetailVBox;
    @FXML
    private MFXScrollPane leftUserDetailPane;

    @FXML
    private VBox rightUserDetailVBox;

    @FXML
    private MFXScrollPane rightUserDetailPane;


    /***
     * initialize the page. Setup TableView, pagination, search field, and the ordering select
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {

        // default ordering, will be used in TableView and the ordering select
        String defaultOrdering = "-date_joined";

        // Create a new Facade class to easily manage the tableView with pagination

        http.get("/user/current/",(JSONObject res) ->{
            Platform.runLater(() ->{
                leftUserDetailVBox.getChildren().add(new Label("Basic Information"));
                leftUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("id","ID").setText(String.valueOf(res.getInt("id"))).setDisable(true).build().get());
                leftUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("username","Username *").setText(res.getString("username")).setDisable(true).build().get());
                leftUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("first_name","First Name").setText(res.getString("first_name")).setDisable(true).build().get());
                leftUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("last_name","Last Name").setText(res.getString("last_name")).setDisable(true).build().get());
                leftUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("email","Email").setText(res.getString("email")).setDisable(true).build().get());

//                userDetailVBox.getChildren().add(new Separator());
                JSONObject profile = res.getJSONObject("profile");
                // show profile details
                rightUserDetailVBox.getChildren().add(new Label("Profile Details"));
                rightUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("","Profile ID").setText(String.valueOf(profile.getInt("id"))).setDisable(true).build().get());

                // admin cannot change its own "role", otherwise the current admin will not have the permission to keep browsing on this page anymore.
//
//                rightUserDetailVBox.getChildren().add(new BuilderMFXComboBoxController.Builder("role", "Role *", List.of(new SelectOption("admin"), new SelectOption("lecturer"), new SelectOption("student"))).setValText(newSelection.getProfileRole()).build().get());
//                rightUserDetailVBox.getChildren().add(new BuilderMFXComboBoxController.Builder("role", "Role *", List.of(new SelectOption("admin"), new SelectOption("lecturer"), new SelectOption("student"))).setValText(newSelection.getProfileRole()).setDisable(true).build().get());
                rightUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("father_name","Father Name").setText(profile.getString("father_name")).setDisable(true).build().get());
                rightUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("mother_name","Mother Name").setText(profile.getString("mother_name")).setDisable(true).build().get());
                rightUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("address_1","Address 1").setText(profile.getString("address_1")).setDisable(true).build().get());
                rightUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("address_2","Address 2").setText(profile.getString("address_2")).setDisable(true).build().get());
                rightUserDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("tel","Tel.").setText(profile.getString("tel")).setDisable(true).build().get());
            });
        });

        // show basic information


    }
}
