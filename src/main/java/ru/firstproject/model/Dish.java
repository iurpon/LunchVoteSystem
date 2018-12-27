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
}
