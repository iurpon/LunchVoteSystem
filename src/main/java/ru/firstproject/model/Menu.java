package ru.firstproject.model;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {

    private Date registered = new Date();

    public Menu() {
    }

    public Menu(Integer id) {
        super(id);
    }

    public Menu(Menu menu) {
        id = menu.getId();
        registered = menu.getRegistered();

    }



    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }


}
