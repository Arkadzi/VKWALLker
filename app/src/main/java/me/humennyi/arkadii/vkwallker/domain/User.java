package me.humennyi.arkadii.vkwallker.domain;

/**
 * Created by arkadii on 11/5/16.
 */

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String status;
    private String birthDate;
    private String about;
    private String activities;
    private String photoUrl;
    private String city;
    private String mobilePhone;
    private String interests;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getInterests() {
        return interests;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", about='" + about + '\'' +
                ", activities='" + activities + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", city='" + city + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", interests='" + interests + '\'' +
                '}';
    }
}
