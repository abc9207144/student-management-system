package com.siweb.model;
import org.json.JSONObject;


public class TimeSlotModel extends ObservableModel<TimeSlot> {

    // Declares variables
    private static final TimeSlotModel instance = new TimeSlotModel();

    // Returns the instance of the controller
    public static TimeSlotModel getInstance(){
        return instance;
    }

    private TimeSlotModel(){}

    public TimeSlot add(JSONObject jsonTimeSlot, Boolean isAddToObservableList) {
        TimeSlot timeSlot = new TimeSlot(jsonTimeSlot);
        modelsMap.put(timeSlot.getId(), timeSlot);
        if(isAddToObservableList)
            obsList.add(timeSlot);
        return timeSlot;
    }


}

