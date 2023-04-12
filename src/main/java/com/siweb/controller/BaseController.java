package com.siweb.controller;

import com.siweb.controller.utility.UtilityHttpController;
import com.siweb.model.UserModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/***
 * BaseController is the base for all pages
 */
public class BaseController implements Initializable {

    protected final UtilityHttpController http = UtilityHttpController.getInstance();
    protected final UserModel userModel = UserModel.getInstance();

    public void initialize(URL location, ResourceBundle resources) {

    }

}
