package ru.firstproject.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant  extends AbstractNamedEntity{
    @Column(name = "address")
    @NotBlank
    @Size(max = 100)
    private String address;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private List<Dish> dishList;

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Restaurant(){}

    public Restaurant(Integer id, String name,String address){
        super(id,name);
        this.address = address;
    }
    public Restaurant(Restaurant restaurant){
        this(restaurant.getId(),restaurant.getName(),restaurant.getAddress());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
