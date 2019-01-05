package ru.firstproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.Dish;
import ru.firstproject.repository.DishRepository;


import java.util.Date;
import java.util.List;

import static ru.firstproject.util.ValidationUtil.*;


@Service
public class DishServiceImpl implements DishService{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DateLabelService dateLabelService;

    @Override
    public Dish save(Dish dish) {
        logger.info("save dish");
        checkIfPossibleUpdate(dateLabelService.isPresentToday());
        return dishRepository.save(dish);


    }

    @Override
    public void update(Dish dish, int id) {
        logger.info("update dish  wiht id = " + id );
        checkCorrectId(dish,id);
        checkIfPossibleUpdate(dateLabelService.isPresentToday());
        dishRepository.save(dish);
    }


    @Override
    public int delete(int id) {
        logger.info("delete dish wiht id = " + id);
        checkIfPossibleUpdate(dateLabelService.isPresentToday());
            int result = dishRepository.delete(id);
            checkNotFoundWithId(result != 0,id);
            return result;
    }

    @Override
    public Dish get(int id) {
        logger.info("get dish wiht id = " + id);
        return  checkNotFound(dishRepository.get(id),"no dish with id = " + id);
    }

    @Override
    public List<Dish> getAllByDateAndRestId(Date date, int restId) {
        logger.info("getAllbyDate and restId");
        return dishRepository.getAllByDateAndRestId(date,restId);
    }


}
