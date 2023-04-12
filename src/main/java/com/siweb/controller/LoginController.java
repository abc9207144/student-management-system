package com.siweb.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;

import javafx.application.Platform;
import javafx.fxml.FXML;

import org.json.JSONObject;

public class LoginController extends BaseController {

    @FXML
    private MFXTextField usernameTextField;

    @FXML
    private MFXPasswordField passwordTextField;

    @FXML
    public void demoAdmin(){
        // just for testing
        this.usernameTextField.setText("P15999999");
        this.passwordTextField.setText("P15999999");
    }

    @FXML
    public void demoLecturer(){
        // just for testing
        this.usernameTextField.setText("P15888888");
        this.passwordTextField.setText("P15888888");
    }

    @FXML
    public void demoStudent(){
        // just for testing
        this.usernameTextField.setText("P21777777");
        this.passwordTextField.setText("P21777777");
    }

    @FXML
    public void login(){
        try {

            http.login(this.usernameTextField.getText(), this.passwordTextField.getText(), (JSONObject resLogin) -> {

                // check the role of the user
                http.get("/user/current/", (JSONObject jsonUserCurrent) -> {

                    Platform.runLater(() -> {

                        userModel.setCurrentUser(jsonUserCurrent);

                        // the user is a student
                        if (userModel.getCurrentUserProfileRole().equals("admin")) {

                            System.err.println("Welcome Back, Admin!");
                            System.err.println();

                            com.siweb.App.setRoot("base");

                        } else if (userModel.getCurrentUserProfileRole().equals("lecturer")) {

                            /*System.err.println("Welcome Back, Lecturer!");
                            System.err.println();

                            com.siweb.App.setRoot("lecturer-dashboard");*/
                        } else if (userModel.getCurrentUserProfileRole().equals("student")) {

                            //System.err.println("Welcome Back, Student!");
                            //System.err.println();

                            com.siweb.App.setRoot("student-dashboard");
                        } else {

                            // WIP, redirect admins / lecturers to different views

                        }
                    });

                });

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}