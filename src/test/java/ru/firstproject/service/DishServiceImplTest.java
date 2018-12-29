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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.firstproject.DishTestData.*;
import static ru.firstproject.MenuTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishServiceImplTest {
    @Autowired
    private DishService dishService;

    @Test
    public void save() throws Exception {
        Dish newDish = new Dish(null,"Icecream",2.1,MENU2);
        newDish = dishService.save(newDish);
        List<Dish> dishList = dishService.getAllByDateAndRestId(new Date(),MENU_SEQ2);
        assertMatch(dishList,Arrays.asList(DISH1,newDish,DISH2),"menu","registered");
    }

    @Test
    public void update() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setName("REALY BIG BuRGER");
        dishService.update(updated,DISH_SEQ1);
        assertMatch(dishService.get(DISH_SEQ1),updated,"menu","registered");
    }

    @Test
    public void delete() throws Exception {
        dishService.delete(DISH_SEQ1);
        List<Dish> dishList = dishService.getAllByDateAndRestId(new Date(),MENU_SEQ2);
        dishList.stream().forEach(System.out::println);
        assertMatch(dishList, Collections.singleton(DISH2),"registered","menu");
    }

    @Test
    public void get() throws Exception {
        Dish actual = dishService.get(DISH_SEQ1);
        assertMatch(actual,DISH1,"registered","menu");
    }

    @Test
    public void getAllByDateAndRestId() throws Exception {
        List<Dish> dishList = dishService.getAllByDateAndRestId(new Date(),MENU_SEQ1);
        dishList.stream().forEach(System.out::println);
        Assert.assertEquals(dishList.size(),3);
        assertMatch(dishList, Arrays.asList(DISH4,DISH5,DISH3),"menu","registered");
    }

}