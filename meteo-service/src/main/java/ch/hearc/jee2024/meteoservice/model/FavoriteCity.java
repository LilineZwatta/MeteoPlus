package ch.hearc.jee2024.meteoservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_city")
public class FavoriteCity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String cityName;

    // Constructeurs, getters/setters
    public FavoriteCity() {}
    public FavoriteCity(String userName, String cityName) {
        this.userName = userName;
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}