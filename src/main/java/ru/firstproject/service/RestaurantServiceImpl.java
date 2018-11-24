package ru.firstproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.Restaurant;
import ru.firstproject.repository.RestaurantRepository;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;
import  static ru.firstproject.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(int id) throws NotFoundException {

        checkNotFoundWithId(restaurantRepository.delete(id) != 0,id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.get(id),id);
    }

    @Override
    public Restaurant getByName(String name) throws NotFoundException {
        return checkNotFound(restaurantRepository.getByName(name),"wrong name : "+ name);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }
}
