package ru.firstproject.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {

    private Date registered = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu",fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private List<Dish> dishList;

    public Menu() {
    }

    public Menu(Integer id) {
        super(id);
    }

    public Menu(Menu menu) {
        id = menu.getId();
        registered = menu.getRegistered();
        dishList = menu.getDishList();
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
                "registered=" + registered +
                ", restaurant=" + restaurant +
                ", dishList=" + dishList +
                ", id=" + id +
                '}';
    }
}
