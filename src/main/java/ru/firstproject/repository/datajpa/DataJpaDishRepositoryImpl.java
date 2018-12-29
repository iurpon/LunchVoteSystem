package ru.firstproject.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.firstproject.model.Dish;
import ru.firstproject.repository.DishRepository;

import java.util.Date;
import java.util.List;


@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    @Autowired
    private CrudDishRepository crudDishRepository;

    @Override
    public Dish save(Dish dish) {
        return crudDishRepository.save(dish);
    }

    @Override
    public Dish get(int id) {
        return crudDishRepository.findById(id).orElse(null);
    }

    @Override
    public int delete(int id) {
        return crudDishRepository.delete(id);
    }

    @Override
    public List<Dish> getAllByDateAndRestId(Date date, int restId) {
        return crudDishRepository.findAllByRegisteredAndRestaurant_IdOrderByIdAsc(date,restId);
    }
}
