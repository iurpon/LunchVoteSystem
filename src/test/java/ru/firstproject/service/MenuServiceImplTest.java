package ru.firstproject.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.firstproject.model.Menu;
import ru.firstproject.util.exception.ChangeDeniedException;
import ru.firstproject.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static ru.firstproject.MenuTestData.*;
import static ru.firstproject.RestaurantTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuServiceImplTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DateLabelService dateLabelService;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("date_label").clear();
    }

    @Test
    public void save() throws Exception {
        Menu menu = new Menu();
        menu.setRestaurant(RESTAURANT3);
        menuService.save(menu);
        assertEquals(3,menuService.getAllByDate(new Date()).size());
    }

    @Test(expected = ChangeDeniedException.class)
    public void saveDenied() throws Exception {
        dateLabelService.startVoting();
        Menu menu = new Menu();
        menu.setRestaurant(RESTAURANT1);
        menuService.save(menu);
        assertEquals(3,menuService.getAllByDate(new Date()).size());
    }



    @Test
    public void get() throws Exception {
        Menu menu = menuService.get(MENU_SEQ1);
        assertMatch(MENU1,menu,"registered","restaurant");
    }
    @Test(expected = NotFoundException.class)
    public void getWrongId() throws Exception {
        Menu menu = menuService.get(1);

    }

    @Test
    public void getAllByDate() throws Exception {
        Date date = new Date();
        List<Menu> list = menuService.getAllByDate(date);
        assertMatch(list, Arrays.asList(MENU1,MENU2),"restaurant","registered");
        list.stream().forEach(System.out::println);
    }
}