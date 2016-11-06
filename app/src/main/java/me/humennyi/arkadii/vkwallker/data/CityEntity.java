package me.humennyi.arkadii.vkwallker.data;

/**
 * Created by arkadii on 11/5/16.
 */
public class CityEntity {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
