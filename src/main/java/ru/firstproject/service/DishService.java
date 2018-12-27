package ru.firstproject.service;

import ru.firstproject.model.Dish;

import java.util.Date;
import java.util.List;

public interface DishService {

    Dish save(Dish menu);

    // false if not found
    int delete(int id);

    // null if not found
    Dish get(int id);

    List<Dish> getAllByDateAndRestId(Date date,int restId);

    void update(Dish menu, int id);
}
