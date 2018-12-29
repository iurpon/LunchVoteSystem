package ru.firstproject.repository;


import ru.firstproject.model.Restaurant;

import java.util.Date;
import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant user);

    // false if not found
    int delete(int id);

    // null if not found
    Restaurant get(int id);

    // null if not found
    Restaurant getByName(String name);

    List<Restaurant> getAll();

    //null if not found
    Restaurant  getRestaurantMenu(int restId, Date date);

    List<Restaurant> getMenu(Date date);
}
