package com.siweb.controller.utility;

import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;


public class UtilityNotificationController {

    private static final UtilityNotificationController instance = new UtilityNotificationController();
    private List<Popup> popupList = new ArrayList<Popup>();
    private UtilityNotificationController(){}

    // Returns the instance of the controller
    public static UtilityNotificationController getInstance(){
        return instance;
    }

    // WIP, not finished


}
