package ru.firstproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.Dish;
import ru.firstproject.repository.DishRepository;
import ru.firstproject.util.ValidationUtil;
import ru.firstproject.util.exception.TimesUpException;

import java.util.Date;
import java.util.List;



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
        if(isPossibleChangeMenu()){
            return dishRepository.save(dish);
        }else{
            throw new TimesUpException("vote already started. Cant change menu");
        }

    }

    @Override
    public void update(Dish dish, int id) {
        logger.info("update dish  wiht id = " + id );
        ValidationUtil.checkCorrectId(dish,id);
        if(isPossibleChangeMenu()){
            dishRepository.save(dish);
        }else{
            throw new TimesUpException("vote already started. Cant change menu");
        }

    }

    @Override
    public int delete(int id) {
        logger.info("delete dish wiht id = " + id);
        if(isPossibleChangeMenu()){
            return dishRepository.delete(id);
        }else{
            throw new TimesUpException("vote already started. Cant change menu");
        }
    }

    @Override
    public Dish get(int id) {
        logger.info("get dish wiht id = " + id);
        return dishRepository.get(id);
    }

    @Override
    public List<Dish> getAllByDateAndRestId(Date date, int restId) {
        logger.info("getAllbyDate and restId");
        return dishRepository.getAllByDateAndRestId(date,restId);
    }

    public boolean isPossibleChangeMenu(){
        return !dateLabelService.isPresentToday();
    }


}
