package ru.firstproject.repository;

import ru.firstproject.model.Dish;

import java.util.Date;
import java.util.List;

public interface DishRepository {
    Dish save(Dish menu);

    // null if not found
    Dish get(int id);

    int delete(int id);

    List<Dish> getAllByDateAndRestId(Date date,int id);
}
