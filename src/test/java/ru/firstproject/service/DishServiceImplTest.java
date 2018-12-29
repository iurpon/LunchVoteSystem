package ru.firstproject.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.firstproject.model.Dish;
import ru.firstproject.util.exception.NotFoundException;
import ru.firstproject.util.exception.TimesUpException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.firstproject.DishTestData.*;
import static ru.firstproject.MenuTestData.*;
import static ru.firstproject.RestaurantTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishServiceImplTest {
    @Autowired
    private DishService dishService;
    @Autowired
    private DateLabelService dateLabelService;

    @Test
    public void save() throws Exception {
        Dish newDish = new Dish(null,"Icecream",2.1,RESTAURANT1);
        newDish = dishService.save(newDish);
        List<Dish> dishList = dishService.getAllByDateAndRestId(new Date(), REST_SEQ1);
        assertMatch(dishList,Arrays.asList(DISH1,DISH2,newDish),"restaurant","registered");
    }
    @Test(expected = TimesUpException.class)
    public void saveWrongTime() throws Exception {
        dateLabelService.startVoting();
        Dish newDish = new Dish(null,"Icecream",2.1,RESTAURANT1);
        newDish = dishService.save(newDish);
    }

    @Test
    public void update() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("REALY BIG BuRGER");
        dishService.update(updated,DISH_SEQ1);
        assertMatch(dishService.get(DISH_SEQ1),updated,"restaurant","registered");
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateWrongId() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("REALY BIG BuRGER");
        dishService.update(updated,DISH_SEQ2);
    }
    @Test(expected = TimesUpException.class)
    public void updateWrongTime() throws Exception {
        dateLabelService.startVoting();
        Dish updated = new Dish(DISH1);
        updated.setName("REALY BIG BuRGER");
        dishService.update(updated,DISH_SEQ1);
    }

    @Test
    public void delete() throws Exception {
        dishService.delete(DISH_SEQ1);
        List<Dish> dishList = dishService.getAllByDateAndRestId(new Date(), REST_SEQ1);
        dishList.stream().forEach(System.out::println);
        assertMatch(dishList, Collections.singleton(DISH2),"registered","restaurant");
    }
    @Test(expected = NotFoundException.class)
    public void deleteWrongId() throws Exception {
        dishService.delete(DISH_SEQ1-100);
    }
    @Test(expected = TimesUpException.class)
    public void deleteWrongTime() throws Exception {
        dateLabelService.startVoting();
        dishService.delete(DISH_SEQ1);
    }

    @Test
    public void get() throws Exception {
        Dish actual = dishService.get(DISH_SEQ1);
        assertMatch(actual,DISH1,"registered","restaurant");
    }
    @Test(expected = NotFoundException.class)
    public void getWrongId() throws Exception{
        Dish wrong = dishService.get(1);
    }

    @Test
    public void getAllByDateAndRestId() throws Exception {
        List<Dish> dishList = dishService.getAllByDateAndRestId(new Date(), REST_SEQ3);
        dishList.stream().forEach(System.out::println);
        Assert.assertEquals(dishList.size(),3);
        assertMatch(dishList, Arrays.asList(DISH3,DISH4,DISH5),"restaurant","registered");
    }

}