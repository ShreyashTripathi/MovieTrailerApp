package com.example.movietrailerapp.model;

public class CastInfo {
    String name;
    String birthday;
    String gender;
    String biography;
    String pob;
    String profile_pic;
    String person_id;

    public CastInfo(String name, String birthday, String gender, String biography, String pob, String profile_pic, String person_id) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.biography = biography;
        this.pob = pob;
        this.profile_pic = profile_pic;
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }
}
