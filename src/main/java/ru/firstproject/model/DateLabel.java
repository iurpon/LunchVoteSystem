package ru.firstproject.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "date_label")
public class DateLabel extends AbstractBaseEntity {
    private Date registered = new Date();

    public DateLabel() {
    }

    public DateLabel(Integer id) {
        super(id);
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }
}
