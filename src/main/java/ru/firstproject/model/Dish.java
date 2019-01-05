package ru.firstproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "dishes")
public class Dish  extends AbstractNamedEntity{

    @Column(name = "registered")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date registered = new Date();

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="rest_id")
    @JsonIgnore
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, double price, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
    }
    public Dish(Integer id, String name, double price, Date registered, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.registered = registered;
    }

    public Dish(Dish dish){
        super(dish.getId(),dish.getName());
        this.registered = dish.getRegistered();
        this.price = dish.getPrice();
        this.restaurant = dish.getRestaurant();
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                " id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
