package ru.firstproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @Column(name = "registered")
    @NotNull
    private Date registered = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Vote() {
    }

    public Vote(Integer id, Restaurant restaurant, User user) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote(Vote vote){
        this.id = vote.getId();
        this.registered = vote.getRegistered();
        this.restaurant = vote.getRestaurant();
        this.user = vote.getUser();
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "registered=" + registered +
                ", restaurant=" + restaurant +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}
