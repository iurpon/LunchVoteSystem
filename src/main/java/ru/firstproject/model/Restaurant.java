package ru.firstproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurants")
public class Restaurant  extends AbstractNamedEntity{
    @Column(name = "address")
    @NotBlank
    @Size(max = 100)
    private String address;

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
