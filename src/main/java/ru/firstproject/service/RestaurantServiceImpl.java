package ru.firstproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.Restaurant;
import ru.firstproject.repository.RestaurantRepository;
import ru.firstproject.util.exception.NotFoundException;

import java.util.Date;
import java.util.List;
import  static ru.firstproject.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DateLabelService dateLabelService;

    @Override
    public Restaurant create(Restaurant restaurant) {
        logger.info("create restaurant");
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant,int id) {
        logger.info("update restaurant");
        checkCorrectId(restaurant,id);
        checkIfPossibleUpdate(dateLabelService.isPresentToday());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        logger.info("delete restaurant");
        checkIfPossibleUpdate(dateLabelService.isPresentToday());
        checkNotFoundWithId(restaurantRepository.delete(id) != 0,id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        logger.info("get restaurant");
        return checkNotFoundWithId(restaurantRepository.get(id),id);
    }

    @Override
    public Restaurant getByName(String name) throws NotFoundException {
        logger.info("getByName restaurant");
        return checkNotFound(restaurantRepository.getByName(name),"wrong name : "+ name);
    }

    @Override
    public List<Restaurant> getAll() {
        logger.info("getAll restaurants");
        return restaurantRepository.getAll();
    }

    @Override
    public Restaurant getRestaurantMenu(int restId, Date date) {
        logger.info("getRestaurantMenu(id,date)");
        return checkNotFound(restaurantRepository.getRestaurantMenu(restId,date),"not found restaurant with id = " + restId);
    }

    @Override
    public List<Restaurant> getMenu(Date date) {
        logger.info("get Menu(date)");
        return restaurantRepository.getMenu(date);
    }
}
