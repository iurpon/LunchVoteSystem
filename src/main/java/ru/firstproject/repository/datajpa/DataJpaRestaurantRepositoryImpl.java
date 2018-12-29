package ru.firstproject.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.firstproject.model.Restaurant;
import ru.firstproject.repository.RestaurantRepository;

import java.util.Date;
import java.util.List;

@Repository
public class DataJpaRestaurantRepositoryImpl implements RestaurantRepository {

    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "id");

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public int  delete(int id) {
        return restaurantRepository.delete(id);
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant getByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(SORT_NAME);
    }

    @Override
    public Restaurant getRestaurantMenu(int restId, Date date) {
        return restaurantRepository.getRestaurantMenu(restId,date);
    }

    @Override
    public List<Restaurant> getMenu(Date date) {
        return restaurantRepository.getMenu(date);
    }
}
