package ru.firstproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "dishes")
public class Dish  extends AbstractNamedEntity{

    @Column(name = "registered")
    @NotNull
    private Date registered = new Date();

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="menu_id")
    private Menu menu;

    public Dish() {
    }

    public Dish(Integer id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, double price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

    public Dish(Dish dish){
        super(dish.getId(),dish.getName());
        this.registered = dish.getRegistered();
        this.price = dish.getPrice();
        this.menu = dish.getMenu();
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
