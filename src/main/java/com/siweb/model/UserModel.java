package com.siweb.model;
import org.json.JSONObject;


/***
 * UserModel stores the current Users. It extends the ObservableModel where an unmodifiable observable list of users can be produced.
 */
public class UserModel extends ObservableModel<User> {

    // Declares variables
    private static final UserModel instance = new UserModel();

    private int currentUserID;
    private String currentUserProfileRole;

    // Returns the instance of the controller
    public static UserModel getInstance(){
        return instance;
    }

    private UserModel(){}

    public void add(JSONObject jsonUser) {
        oList.add(new User(jsonUser));
    }

    public void setCurrentUser(JSONObject jsonUser) {
        currentUserID = jsonUser.getInt("id");
        currentUserProfileRole = jsonUser.getJSONObject("profile").getString("role");
    }

    public int getCurrentUserID(){
        return currentUserID;
    }

    public String getCurrentUserProfileRole(){
        return currentUserProfileRole;
    }

}
