package com.siweb.model;

import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimeSlot implements TableViewModel {
    private int id;
    private int weekDay;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public TimeSlot(JSONObject jsonTimeSlot){
        this.id = jsonTimeSlot.getInt("id");
        this.weekDay = jsonTimeSlot.getInt("weekday");
        this.startTime = LocalTime.parse(jsonTimeSlot.getString("start_time"));
        this.endTime = LocalTime.parse(jsonTimeSlot.getString("end_time"));
    }

    public int getId() {
        return id;
    }
    public int getWeekDay() {
        return weekDay;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    @Override
    public String toString() {
        return DayOfWeek.of(weekDay).toString().substring(0, 3) + " (" + this.startTime + " - " + this.endTime + ")";
    }

    public String getValText() {
        return id + "";
    }

}
