package com.siweb.model;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Semester {

    private final int id;
    private final String year;
    private final String semester;
    private final String date_start;
    private final String date_end;

    public Semester(JSONObject jsonSemester) {
        this.id = jsonSemester.getInt("id");
        this.year = jsonSemester.getString("year");
        this.semester = jsonSemester.getInt("semester") + "";
        this.date_start = jsonSemester.getString("date_start");
        this.date_end = jsonSemester.getString("date_end");
    }

    public int getId() {
        return id;
    }
    public String getYear() {
        return year;
    }
    public String getSemester() {
        return semester;
    }
    public String getDateStart() {
        return date_start;
    }
    public String getDateEnd() {
        return date_end;
    }


}
