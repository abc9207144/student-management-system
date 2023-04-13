package com.siweb.model;

import org.json.JSONObject;


/***
 * User Object. Constructs from a JSONObject of user.
 */
public class User {

    private final int id;
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;

    private int profileId;
    private String profileRole;
    private String profileFatherName;
    private String profileMotherName;
    private String profileAddress1;
    private String profileAddress2;
    private String profileTel;


    public User(JSONObject jsonUser){

        this.id = jsonUser.getInt("id");
        this.userName = jsonUser.getString("username");
        this.firstName = jsonUser.getString("first_name");
        this.lastName = jsonUser.getString("last_name");
        this.email = jsonUser.getString("email");

        if(jsonUser.has("profile"))
        {
            JSONObject jsonUserProfile = jsonUser.getJSONObject("profile");
            this.profileId = jsonUserProfile.getInt("id");
            this.profileRole = jsonUserProfile.getString("role");
            this.profileFatherName = jsonUserProfile.getString("father_name");
            this.profileMotherName = jsonUserProfile.getString("mother_name");
            this.profileAddress1 = jsonUserProfile.getString("address_1");
            this.profileAddress2 = jsonUserProfile.getString("address_2");
            this.profileTel = jsonUserProfile.getString("tel");
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
}


