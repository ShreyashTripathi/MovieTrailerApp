package com.example.movietrailerapp.model;

public class MovieCast {
    private int castId;
    private String castName;
    private String characterRole;
    private String castImageUrl;
    private String creditId;

    public MovieCast() {
    }

    public MovieCast(int castId, String castName, String characterRole, String castImageUrl, String creditId) {
        this.castId = castId;
        this.castName = castName;
        this.characterRole = characterRole;
        this.castImageUrl = castImageUrl;
        this.creditId = creditId;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCharacterRole() {
        return characterRole;
    }

    public void setCharacterRole(String characterRole) {
        this.characterRole = characterRole;
    }

    public String getCastImageUrl() {
        return castImageUrl;
    }

    public void setCastImageUrl(String castImageUrl) {
        this.castImageUrl = castImageUrl;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }
}
