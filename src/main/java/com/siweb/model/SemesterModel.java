package com.siweb.model;
import org.json.JSONObject;


/***
 * SemesterModel stores the current Semesters. It extends the ObservableModel where an unmodifiable observable list of semesters can be produced.
 */
public class SemesterModel extends ObservableModel<Semester> {

    // Declares variables
    private static final SemesterModel instance = new SemesterModel();

    // Returns the instance of the controller
    public static SemesterModel getInstance(){
        return instance;
    }

    private SemesterModel(){}

    public void add(JSONObject jsonSemester) {
        oList.add(new Semester(jsonSemester));
    }

}

