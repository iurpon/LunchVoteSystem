package ru.firstproject.service;

import ru.firstproject.model.Restaurant;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    // false if not found
    void delete(int id) throws NotFoundException;

    // null if not found
    Restaurant get(int id) throws NotFoundException;

    // null if not found
    Restaurant getByName(String name) throws NotFoundException;

    List<Restaurant> getAll();
}
