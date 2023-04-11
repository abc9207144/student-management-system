package com.siweb.model;
import com.siweb.view.SelectOption;
import org.json.JSONObject;

import java.util.*;


public class UserModel extends ObservableModel<User> {

    // Declares variables
    private static final UserModel instance = new UserModel();

    private int currentUserID;
    private String currentUserProfileRole;

    private TreeMap<String, String> allStudentNames = new TreeMap<String, String>();

    // Returns the instance of the controller
    public static UserModel getInstance(){
        return instance;
    }

    private UserModel(){}

    public User add(JSONObject jsonUser, Boolean isAddToObservableList) {
        User user = new User(jsonUser);
        modelsMap.put(user.getId(), user);
        if(isAddToObservableList)
            obsList.add(user);
        return user;
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

    public Map<String, String> getAllStudentNames() {
        return Collections.unmodifiableSortedMap(allStudentNames);
    }



    // Overloading this method to support getting select option list for students, lecturers, or admins only.
    public List<SelectOption> getSelectOptionList(String role) {
        ArrayList<SelectOption> res = new ArrayList<>();

        for (int key : modelsMap.keySet()) {
            User user = modelsMap.get(key);
            if (user.getProfileRole().equals(role))
                res.add(new SelectOption(modelsMap.get(key).toString(), modelsMap.get(key)));
        }

        return res;

    }




}
