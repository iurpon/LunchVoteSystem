package ru.firstproject.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import static ru.firstproject.RestaurantTestData.*;

import ru.firstproject.MenuTestData;
import ru.firstproject.model.Dish;
import ru.firstproject.model.Restaurant;
import ru.firstproject.util.exception.ChangeDeniedException;
import ru.firstproject.util.exception.NotFoundException;

import static ru.firstproject.DishTestData.*;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DateLabelService dateLabelService;

    @Autowired
    private DishService dishService;

    @Test
    public void create() throws Exception {
        Restaurant newR = new Restaurant(null,"Another Place","Another address");
        restaurantService.create(newR);
        List<Restaurant> all = restaurantService.getAll();
        assertMatch(all, Arrays.asList(RESTAURANT1,RESTAURANT2,RESTAURANT3,newR),"dishList");
    }


    @Test
    public void update() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("New Burger King");
        restaurantService.update(updated, REST_SEQ1);
        Restaurant restaurant = restaurantService.get(REST_SEQ1);
        assertMatch(restaurant,updated,"dishList");
    }
    @Test(expected = ChangeDeniedException.class)
    public void updateDenied() throws Exception {
        dateLabelService.startVoting();
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("New Burger King");
        restaurantService.update(updated, REST_SEQ1);

    }

    @Test
    public void delete() throws Exception {
        restaurantService.delete(REST_SEQ1);
        Assert.assertEquals(2,restaurantService.getAll().size());
    }

    @Test(expected = ChangeDeniedException.class)
    public void deleteDenied() throws Exception {
        dateLabelService.startVoting();
        restaurantService.delete(REST_SEQ1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongId() throws Exception {
        restaurantService.delete(1);

    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = restaurantService.get(REST_SEQ1);
        logger.info(restaurant.toString());
        assertMatch(RESTAURANT1,restaurant,"dishList");
    }

    @Test(expected = NotFoundException.class)
    public void getWrongId() throws Exception {
        Restaurant restaurant = restaurantService.get(REST_SEQ1-100);
    }

    @Test
    public void getByName() throws Exception {
        Restaurant restaurant = restaurantService.getByName("OLIVE GARDEN");
        assertMatch(RESTAURANT1,restaurant,"dishList");
    }
    @Test(expected = NotFoundException.class)
    public void getByWrongName() throws Exception {
        Restaurant restaurant = restaurantService.getByName("MELON GARDEN");
        assertMatch(RESTAURANT3,restaurant);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> list = restaurantService.getAll();
        assertMatch(list,Arrays.asList(RESTAURANT1,RESTAURANT2,RESTAURANT3),"dishList");
    }

    @Test
    public void getRestaurantWithTodayMenu() throws Exception{
        Restaurant restaurantMenu = restaurantService.getRestaurantMenu(REST_SEQ1, new Date());
        assertMatch(restaurantMenu,RESTAURANT1,"dishList");
        MenuTestData.assertMatch(restaurantMenu.getDishList(),Arrays.asList(DISH3,DISH4,DISH5),"registered","restaurant");
    }

    @Test
    public void getMenu() throws Exception{
        Date date = new Date(115,4,5);
        List<Restaurant> oldMenu = restaurantService.getMenu(date);
        oldMenu.stream().forEach(r -> r.getDishList().stream().forEach(d -> logger.info("old date " + d.toString())));

        List<Restaurant> restaurantList = restaurantService.getMenu(new Date());
        restaurantList.stream().forEach(restaurant -> restaurant
                                                    .getDishList()
                                                    .stream().forEach(d -> logger.info("this date " + d.toString())));

        assertMatch(restaurantList,Arrays.asList(RESTAURANT1,RESTAURANT3),"dishList");
        MenuTestData.assertMatch(restaurantList.get(0).getDishList()
                ,Arrays.asList(DISH3,DISH4,DISH5),"registered","restaurant");
        MenuTestData.assertMatch(restaurantList.get(1).getDishList()
                ,Arrays.asList(DISH1,DISH2),"registered","restaurant");

    }




}