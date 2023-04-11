package com.siweb.model;

import org.json.JSONObject;


/***
 * User Object. Constructs from a JSONObject of user.
 */
public class User implements TableViewModel {

    private final int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    private int profileId;
    private String profileRole;
    private String profileFatherName;
    private String profileMotherName;
    private String profileAddress1;
    private String profileAddress2;
    private String profileTel;


    public User(JSONObject jsonUser){

        this.id = jsonUser.getInt("id");
        if(jsonUser.has("username")) this.userName = jsonUser.getString("username");
        if(jsonUser.has("first_name")) this.firstName = jsonUser.getString("first_name");
        if(jsonUser.has("last_name")) this.lastName = jsonUser.getString("last_name");
        if(jsonUser.has("email")) this.email = jsonUser.getString("email");

        if(jsonUser.has("profile"))
        {
            JSONObject jsonUserProfile = jsonUser.getJSONObject("profile");
            if(jsonUserProfile.has("id")) this.profileId = jsonUserProfile.getInt("id");
            if(jsonUserProfile.has("role")) this.profileRole = jsonUserProfile.getString("role");
            if(jsonUserProfile.has("father_name")) this.profileFatherName = jsonUserProfile.getString("father_name");
            if(jsonUserProfile.has("mother_name")) this.profileMotherName = jsonUserProfile.getString("mother_name");
            if(jsonUserProfile.has("address_1")) this.profileAddress1 = jsonUserProfile.getString("address_1");
            if(jsonUserProfile.has("address_2")) this.profileAddress2 = jsonUserProfile.getString("address_2");
            if(jsonUserProfile.has("tel")) this.profileTel = jsonUserProfile.getString("tel");
        }
    }

    public int getId() {
        return id;
    }
    public String getUserName() {
        return userName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }

    public int getProfileId() {
        return profileId;
    }
    public String getProfileRole() {
        return profileRole;
    }
    public String getProfileFatherName() {
        return profileFatherName;
    }
    public String getProfileMotherName() {
        return profileMotherName;
    }
    public String getProfileAddress1() {
        return profileAddress1;
    }
    public String getProfileAddress2() {
        return profileAddress2;
    }
    public String getProfileTel() {
        return profileTel;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " (" + this.userName + ")";
    }

    public String getValText() {
        return id + "";
    }

}


