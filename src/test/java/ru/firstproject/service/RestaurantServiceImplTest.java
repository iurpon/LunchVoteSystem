package ru.firstproject.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import static ru.firstproject.RestaurantTestData.*;
import ru.firstproject.model.Restaurant;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;



@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceImplTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void create() throws Exception {
        Restaurant newR = new Restaurant(null,"Another Place","Another address");
        restaurantService.create(newR);
        List<Restaurant> all = restaurantService.getAll();
        assertMatch(all,newR,RESTAURANT1,RESTAURANT2,RESTAURANT3);
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("New Burger King");
        restaurantService.update(updated, REST_SEQ1);
        Restaurant restaurant = restaurantService.get(REST_SEQ1);
        assertMatch(restaurant,updated);
    }

    @Test
    public void delete() throws Exception {
        restaurantService.delete(REST_SEQ1);
        Assert.assertEquals(2,restaurantService.getAll().size());
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongId() throws Exception {
        restaurantService.delete(1);

    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = restaurantService.get(REST_SEQ1);
        assertMatch(RESTAURANT1,restaurant);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongId() throws Exception {
        int id = 10;
        Restaurant restaurant = restaurantService.get(id);
    }

    @Test
    public void getByName() throws Exception {
        Restaurant restaurant = restaurantService.getByName("OLIVE GARDEN");
        assertMatch(RESTAURANT3,restaurant);
    }
    @Test(expected = NotFoundException.class)
    public void getByWrongName() throws Exception {
        Restaurant restaurant = restaurantService.getByName("MELON GARDEN");
        assertMatch(RESTAURANT3,restaurant);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> list = restaurantService.getAll();
        assertMatch(list,RESTAURANT1,RESTAURANT2,RESTAURANT3);
    }


}