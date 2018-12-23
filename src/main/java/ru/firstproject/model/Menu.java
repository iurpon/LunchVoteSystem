package ru.firstproject.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {
    private String description1;
    private String description2;
    private String description3;
    private String description4;
    private String description5;

    private Date registered = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Integer id, String description1, String description2, String ... description3) {
        super(id);
        this.description1 = description1;
        this.description2 = description2;
        int size = description3.length;
        this.description3 = size > 0 ? description3[0] : "empty";
        this.description4 = size > 1 ? description3[1] : "empty";
        this.description5 = size > 2 ? description3[2] : "empty";
    }

    public Menu(Menu menu) {
        id = menu.getId();
        registered = menu.getRegistered();
        description1 = menu.getDescription1();
        description2 = menu.getDescription2();
        description3 = menu.getDescription3();
        description4 = menu.getDescription4();
        description5 = menu.getDescription5();
    }



    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public String getDescription4() {
        return description4;
    }

    public void setDescription4(String description4) {
        this.description4 = description4;
    }

    public String getDescription5() {
        return description5;
    }

    public void setDescription5(String description5) {
        this.description5 = description5;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "description1='" + description1 + '\'' +
                ", description2='" + description2 + '\'' +
                ", description3='" + description3 + '\'' +
                ", description4='" + description4 + '\'' +
                ", description5='" + description5 + '\'' +
                ", registered=" + registered +
                ", id=" + id +
                '}';
    }
}
