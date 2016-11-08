package me.humennyi.arkadii.vkwallker.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkadii on 11/5/16.
 */

public class ProfileEntity {
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String id;
    @SerializedName("photo_100")
    private String photo;


    public ProfileEntity(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileEntity that = (ProfileEntity) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id='" + id + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
